
package com.water.util.upload.thread;

import java.io.IOException;
import java.util.Date;

import com.water.util.upload.scoopit.weedfs.client.WeedFSClient;
import com.water.util.upload.scoopit.weedfs.exception.WeedFSException;

import cn.hutool.core.date.DateUtil;

public class FTUploader implements Runnable
{
    
    private FTUploaderQueue<FTUploaderFile> queue;
    
    // 上传文件客户端
    private WeedFSClient client;
    
    FTUploader(FTUploaderQueue<FTUploaderFile> queue, WeedFSClient client)
    {
        this.queue = queue;
        this.client = client;
    }
    
    @Override
    public void run()
    {
        
        while (true)
        {
            FTUploaderFile file = queue.poll();
            if (file != null)
            {
                Date startTime = new Date();
                System.out.println(Thread.currentThread().getName());
                
                try
                {
                    client.write(file.getWeedfsFile(), file.getLocation(), file.getFile());
                }
                catch (WeedFSException e)
                {
                    
                    e.printStackTrace();
                    
                }
                catch (IOException e)
                {
                    
                    e.printStackTrace();
                    
                }finally {
                    Date endTime = new Date();
                    //file.getFile().delete();
                    System.out.println("上传文件完毕"+file.getFile().getName()+DateUtil.format( startTime,"yyyy-MM-dd hh:mm:dd")+"-"+DateUtil.format( endTime,"yyyy-MM-dd hh:mm:dd"));
                }
            }
            
        }
    }
}
