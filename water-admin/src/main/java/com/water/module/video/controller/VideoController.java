package com.water.module.video.controller;

import com.water.module.video.common.Result;
import com.water.module.video.pojo.Angle;
import com.water.module.video.util.OnvifUtil;
import org.springframework.web.bind.annotation.*;


/**
 * @author：my
 * @date：2019/12/1 13:39
 * @describe：
 */
@RestController
@RequestMapping("/onvif")
public class VideoController {


    /**
     * @author：my
     * @date：2019/12/19 8:29
     * @describe：调整摄像机
     */
    @PostMapping("/adjust")
    private Result adjust(@RequestBody Angle angle) {

        boolean b = OnvifUtil.fixedIp("192.168.1.40", angle.getX(), angle.getY(), angle.getZ());

        if (b){
            return new Result(true, 200, "操作成功");
        }else {
            return new Result(true, 500, "操作失败");
        }

    }

}
