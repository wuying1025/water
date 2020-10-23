package com.water.module.video.util;

import com.water.module.video.pojo.Angle;
import de.onvif.soap.OnvifDevice;
import de.onvif.soap.devices.PtzDevices;
import org.onvif.ver10.schema.PTZNode;
import org.onvif.ver10.schema.PTZVector;
import org.onvif.ver10.schema.Profile;

import javax.xml.soap.SOAPException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：my
 * @date：2019/12/1 13:37
 * @describe：摄像机云台控制
 */

public class OnvifUtil {

    //--------------------------------------------------- 与设备建立协议 ---------------------------------------------------
    private static OnvifDevice nvt;
    //------------------------------------------------------ 设备标识 ------------------------------------------------------
    private static String profileToken;
    //---------------------------------------------------- 设备配置信息 ----------------------------------------------------
    private static List<Profile> profiles;
    //------------------------------------------------------ 云台节点 ------------------------------------------------------
    private static PTZNode node;
    public static Map<String, Object> map = new HashMap<>();

    public static void connect(String ip, String user, String password) {

        try {
//--------------------------------------------------- 与设备建立协议 ---------------------------------------------------
            nvt = new OnvifDevice(ip, user, password);
            profiles = nvt.getDevices().getProfiles();
//------------------------------------------------------ 设备标识 ------------------------------------------------------
            profileToken = profiles.get(0).getToken();
//---------------------------------------------------- 获取PTZ设备 -----------------------------------------------------
            PtzDevices ptzDevices = new PtzDevices(nvt);
//--------------------------------------------- 检验是否支持云台操作 ---------------------------------------------------
            node = ptzDevices.getNode(profileToken);

            map.put(ip + "_node", node);
            map.put(ip+ "_ptz",ptzDevices);
            map.put(ip + "_profileToken", profileToken);

        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    /***
     * 需要ip 用户名密码获取设备信息和转动
     */
    public static boolean fixedIp(String ip, float x, float y, float zoom) {
        try {
            PTZNode node = (PTZNode) map.get(ip + "_node");
            String profileToken = (String) map.get(ip + "_profileToken");
//--------------------------------------------------- 绝对运动的方法 ---------------------------------------------------
            boolean absoluteMove = nvt.getPtz().absoluteMove(node, profileToken, x, y, zoom);
            return absoluteMove;
        } catch (SOAPException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, Angle> conserve(String ip) {
        Map<String, Angle> ptz = new HashMap<>();
        PtzDevices ptzDevices = (PtzDevices) map.get(ip + "_ptz");
        PTZVector position = ptzDevices.getStatus(profileToken).getPosition();
//--------------------------------------------------- 绝对运动的方法 ---------------------------------------------------
        float y = new BigDecimal(position.getPanTilt().getY()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float x = new BigDecimal(position.getPanTilt().getX()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float z = new BigDecimal(position.getZoom().getX()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        Angle angle = new Angle(x,y,z);
        ptz.put("ptz",angle);
        return ptz;
    }

    public static void relationship(String ip, String user, String password){
        try {
            nvt = new OnvifDevice(ip, user, password);
            profiles = nvt.getDevices().getProfiles();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

}
