package com.water.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.water.util.upload.scoopit.weedfs.client.AssignParams;
import com.water.util.upload.scoopit.weedfs.client.Assignation;
import com.water.util.upload.scoopit.weedfs.client.Location;
import com.water.util.upload.scoopit.weedfs.client.ReplicationStrategy;
import com.water.util.upload.scoopit.weedfs.client.WeedFSClient;
import com.water.util.upload.scoopit.weedfs.client.WeedFSClientBuilder;
import com.water.util.upload.scoopit.weedfs.client.WeedFSFile;
import com.water.util.upload.scoopit.weedfs.exception.WeedFSException;

/***
 * 
 * 本软件版权归哈尔滨福特威尔科技有限公司享有，未经许可，
 * 禁止任何单位或者个人用作复制、修改、抄录、传播、研发、投入生产、管理等方式使用，
 * 或与其它产品捆绑使用、销售。凡有上述侵权行为的单位和个人，
 * 必须立即停止侵权行为并对侵权行为产生的一切不良后果承担法律责任。
 * 
 * @类名称: SeaWeedfsUtils
 * @类描述: 文件上传工具类
 * @作者:张凯强
 * @创建时间:2018年3月17日 下午4:13:09
 */
public class SeaWeedfsUtils
{
    
    public static WeedFSClient client;
    
    public static String ip;
    public static String port;
    
    public static class SeaWeedfsUtilsHolder
    {
        
        public final static SeaWeedfsUtils instatnce = new SeaWeedfsUtils();
    }
    
    public static SeaWeedfsUtils getInstance(String ip,String port)
    {
        
        SeaWeedfsUtils.ip = ip;
        SeaWeedfsUtils.port = port;
        return SeaWeedfsUtilsHolder.instatnce;
    }
    
    private SeaWeedfsUtils()
    {
        client = WeedFSClientBuilder.createBuilder().build(ip, port);
    }
    
    
    public static WeedFSClient getClient() {
		return client;
	}

	public static void setClient(WeedFSClient client) {
		SeaWeedfsUtils.client = client;
	}


	public static final Logger LOG = LoggerFactory.getLogger(SeaWeedfsUtils.class);
    
    /***
     * 上传文件到文件服务器
     * 
     * @param filePath 文件路径
     * @return
     * @throws WeedFSException
     * @throws IOException
     */
    public static String uploadFile2Server(String filePath) throws WeedFSException, IOException
    {
        
        client = getClient();
        String httpUrl = null;
        if (StringUtils.isEmpty(filePath))
        {
            return httpUrl;
        }
        File uploadFile = new File(filePath);
        Assignation assignKey;
        int writtentSize = 0;
        assignKey = client.assign(new AssignParams("java-cms-file", ReplicationStrategy.None));
        writtentSize = client.write(assignKey.getWeedFSFile(), assignKey.getLocation(), uploadFile);
        if (writtentSize <= 0)
        {
            return null;
        }
        httpUrl = toHttpUrl(assignKey.getWeedFSFile(), assignKey.getLocation());
        return httpUrl;
    }
    
    /**
     * 上传文件到服务器
     * 
     * @param file
     * @return
     * @throws WeedFSException
     * @throws IOException
     */
    public String uploadFile2Server(File file,String collection)
    {
        
        String httpUrl = null;
        if (file == null)
        {
            return httpUrl;
        }
        File uploadFile = file;
        Assignation assignKey = null;
        int writtentSize = 0;
        
        try
        {
            assignKey = client.assign(new AssignParams(collection, ReplicationStrategy.None));
        }
        catch (Exception e1)
        {
            LOG.debug("【SeaWeedfsUtils uploadFile2Server error】" , e1);
        }
        try
        {
            writtentSize = client.write(assignKey.getWeedFSFile(), assignKey.getLocation(), uploadFile);
        }
        catch (Exception e)
        {
            LOG.debug("【SeaWeedfsUtils uploadFile2Server error】" + e.getMessage());
        }
        if (writtentSize <= 0)
        {
            return null;
        }
        httpUrl = toHttpUrl(assignKey.getWeedFSFile(), assignKey.getLocation());
        return httpUrl;
    }
    
    /**
     * 替换文件服务器上的对应fid文件
     * 
     * @param weedfile
     * @param location
     * @param file
     * @return
     * @throws IOException
     * @throws WeedFSException
     */
    public static String replaceServerWeedFile(WeedFSFile weedFSFile, File file)
    {
        
        int writeSize = 0;
        Location location = getLocation();
        try
        {
            writeSize = client.write(weedFSFile, location, file);
        }
        catch (Exception e)
        {
            LOG.debug("【 SeadWeedfsUtils replaceServerWeedFile 】" + e.getMessage());
        }
        
        if (writeSize <= 0)
        {
            return null;
        }
        
        return toHttpUrl(weedFSFile, location);
    }
    
    /***
     * 
     * @方法名称: getFileByWeedfsUrl
     * @功能描述: 通过weedfsUrl 获取文件
     * @作者:张凯强
     * @创建时间:2018年10月24日 下午4:31:26
     * @param fileName 文件名
     * @param path 路径
     * @param weedfsUrl 文件服务器URL //http://192.168.1.11:9081/5,106c213127be
     * @return File
     */
    public File getFileByWeedfsUrl(String fileName, String path, String weedfsUrl)
    {
        
        URL url = null;
        try
        {
            url = new URL(weedfsUrl);
        }
        catch (MalformedURLException e1)
        {
            e1.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            InputStream inputStream = conn.getInputStream();
            
            // 生成文件并判断文件编码格式
            int index;
            byte[] bytes = new byte[1024];
            fileOutputStream = new FileOutputStream(String.format("%s/%s", path, fileName));
            while ((index = inputStream.read(bytes)) != -1)
            {
                fileOutputStream.write(bytes, 0, index);
                fileOutputStream.flush();
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return new File(String.format("%s/%s", path, fileName));
    }
    
    /***
     * 
     * @方法名称: delServerWeedFile
     * @功能描述: 删除 指定fid文件
     * @作者:张凯强
     * @创建时间:2018年8月7日 下午3:04:57
     * @param file
     * @throws WeedFSException
     * @throws IOException void
     * @throws PlatformBusinessException 
     */
    public static void delServerWeedFile(WeedFSFile file) throws WeedFSException, IOException
    {
        
        if(StringUtils.isEmpty(file.fid))
        {
          //throw BusinessExceptionFactory.getSingleTon().genException(SeaWeedfsUtils.class.getSimpleName(), "delServerWeedFile", "参数为空:publicUrl");  
        }
        try
        {
            client.delete(file, getLocation());
        }
        catch(Exception e)
        {
           // PlatformExceptionFactory.instance().genException(SeaWeedfsUtils.class, ExceptionHelper.getCurMethodName(), 
               // "删除文件失败，删除文件不存在！", ResultConstants.ERROR_CODE, e, GsonUtil.generateGson().toJson(file), CommonConstants.ADMIN);
        }
    }
    
    /***
     * @方法名称: delServerWeedFile
     * @功能描述: 
     * @作者：张凯强
     * @创建时间:2018年12月17日 下午3:27:39
     * @param wFid
     * @throws WeedFSException
     * @throws IOException void
     * @throws PlatformBusinessException 
     */
    public static void delServerWeedFile(String wFid) throws WeedFSException, IOException
    {
        if(StringUtils.isEmpty(wFid))
        {
          //throw BusinessExceptionFactory.getSingleTon().genException(SeaWeedfsUtils.class.getSimpleName(), "delServerWeedFile", "参数为空:publicUrl");  
        }
        System.out.println(wFid.replace("https", "http"));
        try
        {
            client.delete(wFid.replace("https", "http"));
        }catch(Exception e)
        {
           // PlatformExceptionFactory.instance().genException(SeaWeedfsUtils.class, ExceptionHelper.getCurClssName(), 
             //   "删除文件报错!", ResultConstants.ERROR_CODE, e, String.format("wfid=%s",wFid ), CommonConstants.ADMIN);
        }
        
    }
    
    /**
     * 获取location
     * 
     * @return
     */
    public static Location getLocation()
    {
        
        Assignation assignKey = null;
        try
        {
            assignKey = client.assign(new AssignParams("java-cms-file", ReplicationStrategy.None));
        }
        catch (IOException e)
        {
            LOG.debug("【SeadWeedfsUtils 获取 location 失败】" + e.getMessage());
        }
        return assignKey.getLocation();
        
    }
    
//    public static void main(String[] args)
//    {
//        
//        Assignation assignKey = null;
//        try
//        {
//            assignKey = client.assign(new AssignParams("java-cms-file", ReplicationStrategy.None));
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        
//        // WeedFSFile wdfile = new WeedFSFile("14,0130d120080d");
//        System.out.print(SeaWeedfsUtils.toHttpUrl(assignKey.getWeedFSFile(), assignKey.getLocation()));
//        // SeaWeedfsUtils.replaceServerWeedFile(wdfile, new File("D://workspace//CMS2.0//src//main//webapp//upload/templet/templet1.html"));
//        
//    }
    
    /****
     * 根据文件id和服务器位置返回图片的访问路径
     * 
     * @param file
     * @param location
     * @return
     */
    public static String toHttpUrl(WeedFSFile file, Location location)
    {
        
        StringBuilder url = new StringBuilder();
        if (!location.publicUrl.contains("http"))
        {
            url.append("http://");
        }
        url.append(location.publicUrl);
        url.append('/');
        url.append(file.fid);
        
        if (file.version > 0)
        {
            url.append('_');
            url.append(file.version);
        }
        return url.toString();
    }
    

}
