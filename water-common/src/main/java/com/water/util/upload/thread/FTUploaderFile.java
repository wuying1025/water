
package com.water.util.upload.thread;

import java.io.File;

import com.water.util.upload.scoopit.weedfs.client.Location;
import com.water.util.upload.scoopit.weedfs.client.WeedFSFile;

/***
 * 
 * 本软件版权归哈尔滨福特威尔科技有限公司享有，未经许可，
 * 禁止任何单位或者个人用作复制、修改、抄录、传播、研发、投入生产、管理等方式使用，
 * 或与其它产品捆绑使用、销售。凡有上述侵权行为的单位和个人，
 * 必须立即停止侵权行为并对侵权行为产生的一切不良后果承担法律责任。
 * 
 * @类名称: FTUploaderFile
 * @类描述:上传文件实体
 * @作者:张凯强
 * @创建时间:2018年3月17日 下午5:11:22
 */
public class FTUploaderFile implements Comparable<FTUploaderFile>
{
    
    private WeedFSFile weedfsFile;
    
    private Location location;
    
    private File file;
    
    public WeedFSFile getWeedfsFile()
    {
        
        return weedfsFile;
    }
    
    public void setWeedfsFile(WeedFSFile weedfsFile)
    {
        
        this.weedfsFile = weedfsFile;
    }
    
    public File getFile()
    {
        
        return file;
    }
    
    public void setFile(File file)
    {
        
        this.file = file;
    }
    
    public Location getLocation()
    {
        
        return location;
    }
    
    public void setLocation(Location location)
    {
        
        this.location = location;
    }
    
    /***
     * 
     * @方法名称: compareTo
     * @功能描述: 越大的文件越延后上传
     * @作者:张凯强
     * @创建时间:2018年3月19日 上午10:30:55
     * @param o
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(FTUploaderFile o)
    {
        
        if (this.getFile().length() > o.getFile().length())
        {
            return -1;
        }
        else if (this.getFile().length() == o.getFile().length())
        {
            return 0;
        }
        return 1;
        
    }
    
}
