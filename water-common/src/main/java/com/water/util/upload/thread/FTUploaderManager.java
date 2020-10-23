
package com.water.util.upload.thread;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.water.util.SeaWeedfsUtils;
import com.water.util.upload.scoopit.weedfs.client.AssignParams;
import com.water.util.upload.scoopit.weedfs.client.Assignation;
import com.water.util.upload.scoopit.weedfs.client.Location;
import com.water.util.upload.scoopit.weedfs.client.ReplicationStrategy;
import com.water.util.upload.scoopit.weedfs.client.WeedFSClient;
import com.water.util.upload.scoopit.weedfs.client.WeedFSClientBuilder;
import com.water.util.upload.scoopit.weedfs.client.WeedFSFile;

/****
 * 
 * 本软件版权归哈尔滨福特威尔科技有限公司享有，未经许可，
 * 禁止任何单位或者个人用作复制、修改、抄录、传播、研发、投入生产、管理等方式使用，
 * 或与其它产品捆绑使用、销售。凡有上述侵权行为的单位和个人，
 * 必须立即停止侵权行为并对侵权行为产生的一切不良后果承担法律责任。
 * 
 * @类名称: FTUploaderManager
 * @类描述:上传管理者
 * @作者:张凯强
 * @创建时间:2018年3月17日 下午4:16:13
 */
public class FTUploaderManager
{
    
    public static final Logger LOG = LoggerFactory.getLogger(FTUploaderManager.class);
    
    public static final ExecutorService executor = Executors.newFixedThreadPool(15);
    
    // 上传文件客户端
    public static WeedFSClient client;
    
    public static FTUploaderQueue<FTUploaderFile> queue;
    
    public static FTUploaderManager instance ;
    
    public static  String ip;
    private static String port;
    
    private static class FTUploaderManagerHolder 
    {
        public final  static FTUploaderManager instance = new FTUploaderManager();
    }
    
    public synchronized static FTUploaderManager getInstance(String ip,String port)
    {
        FTUploaderManager.ip = ip;
        FTUploaderManager.port = port;
    
        return FTUploaderManagerHolder.instance;
    }
    
    
    private FTUploaderManager()
    {
        queue = new FTUploaderQueue<FTUploaderFile>();
        client = WeedFSClientBuilder.createBuilder().build(ip,port); 
        new Thread( new FTUploader(queue, client)).start();
        new Thread( new FTUploader(queue, client)).start();
        new Thread( new FTUploader(queue, client)).start();
    }
    
  
    
    
    /***
     * 
     * @方法名称: uploadWeedfs
     * @功能描述: 上传weedfs -1.首先返回URL
     * @作者:张凯强
     * @创建时间:2018年3月17日 下午4:48:28
     * @param file
     * @return String
     */
    public static String uploadWeedfs(File file)
    {
        
        String httpUrl = null;
        if (file == null)
        {
            return httpUrl;
        }
        FTUploaderFile uploadFile = new FTUploaderFile();
        Assignation assignKey = null;
        try
        {
            assignKey = client.assign(new AssignParams("java-cms-file", ReplicationStrategy.None));
            System.out.println(assignKey);
            httpUrl = SeaWeedfsUtils.toHttpUrl(assignKey.getWeedFSFile(), assignKey.getLocation());
            uploadFile.setWeedfsFile(assignKey.getWeedFSFile());
            uploadFile.setLocation(assignKey.getLocation());
            uploadFile.setFile(file);
            System.out.println(file.length());
            queue.add(uploadFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.debug("【SeaWeedfsUtils uploadFile2Server error】" + e.getMessage());
        }
        
        
        return httpUrl;
    }
    /***
     * 
     * @方法名称: syncFileToWeedfs
     * @功能描述: 同步文件到 weedfs 
     * @作者:张凯强
     * @创建时间:2018年9月13日 上午11:56:18
     * @param file void
     */
    public  String syncFileToWeedfs(FTUploaderFile file)
    {
        queue.add(file);
        SeaWeedfsUtils.setClient(client);
        return SeaWeedfsUtils.toHttpUrl(file.getWeedfsFile(), file.getLocation());
    }
    
    /***
     * 
     * @方法名称: replaceFileToWeedfs
     * @功能描述: 替换文件到weedfs
     * @作者:张凯强
     * @创建时间:2018年11月26日 上午10:12:58
     * @param file
     * @param fid void
     */
    public void replaceFileToWeedfs(FTUploaderFile file)
    {
        queue.add(file);
        SeaWeedfsUtils.setClient(client);
        SeaWeedfsUtils.replaceServerWeedFile(file.getWeedfsFile(), file.getFile());
    }
    
    /***
     * 
     * @方法名称: buildFile
     * @功能描述: 构建一个上传文件的 
     * @作者:张凯强
     * @创建时间:2018年9月13日 上午11:59:41
     * @param file
     * @param assignKey
     * @return FTUploaderFile
     */
    public static FTUploaderFile buildFile(File file,Assignation assignKey )
    {
        FTUploaderFile uploadFile = new FTUploaderFile();
        uploadFile.setWeedfsFile(assignKey.getWeedFSFile());
        uploadFile.setLocation(assignKey.getLocation());
        uploadFile.setFile(file);
        return uploadFile;
        
    }
    
    public static FTUploaderFile buildFile(File file,String publicUrl,String fid)
    {
        FTUploaderFile uploadFile = new FTUploaderFile();
        Location location = new Location();
        location.publicUrl = publicUrl;
        WeedFSFile weedfsFile = new WeedFSFile(fid);
        uploadFile.setWeedfsFile(weedfsFile);
        uploadFile.setLocation(location);
        uploadFile.setFile(file);
        return uploadFile;
    }
    
    /***
     * 
     * @方法名称: assignFakeWeedfsUrl
     * @功能描述: 临时分配 weedfs 地址空间
     * @作者:张凯强
     * @创建时间:2018年9月13日 上午11:46:59
     * @param groupName  文件分类
     * @return
     * @throws Exception String
     */
    public  Assignation assign(String groupName) throws Exception
    {
        if(StringUtils.isEmpty(groupName))
        {
            return null;
        }
        Assignation assignKey = client.assign(new AssignParams(groupName, ReplicationStrategy.None));
       
        return assignKey;
    }


	public static String getPort() {
		return port;
	}


	public static void setPort(String port) {
		FTUploaderManager.port = port;
	}
    
    
}
