
package com.water.util.upload.thread;

import java.io.File;
import java.io.Serializable;


/**
 * 
 * 本软件版权归哈尔滨福特威尔科技有限公司享有，未经许可，
 * 禁止任何单位或者个人用作复制、修改、抄录、传播、研发、投入生产、管理等方式使用，
 * 或与其它产品捆绑使用、销售。凡有上述侵权行为的单位和个人，
 * 必须立即停止侵权行为并对侵权行为产生的一切不良后果承担法律责任。
 *
 * @类名称: FTFile
 * @类描述: 文件实体类
 * @作者:李巍
 * @创建时间:2018年1月18日 下午7:56:04
 */
public class FTFile  implements Serializable
{
    
    
    private static final long serialVersionUID = 1L;
    
    /** 上传文件的名字*/
    private String fileName;
    
    private File srcFile;
    
    private String fileSize;
    
    private String fileByte;
    
    /**文件的来源*/
    private String fileSource;
    
    
    /** 上传图片的字节流 */
    private byte[] fileBytes;
    
    private String fileContent;
    
    private String url;
    
    public FTFile()
    {
        
    }
    
    public FTFile(String fileName, String fileContent)
    {
        this.fileName = fileName;
        this.fileContent  = fileContent;
    }
    
    public File getSrcFile()
    {
        
        
        return srcFile;
    }
    
    public void setSrcFile(File srcFile)
    {
        
        
        this.srcFile = srcFile;
    }
    
    public String getFileName()
    {
        
        
        return fileName;
    }
    
    public String getFileContent()
    {
        
        
        return fileContent;
    }
    
    public void setFileContent(String fileContent)
    {
        
        
        this.fileContent = fileContent;
    }
    
    public void setFileName(String fileName)
    {
        
        
        this.fileName = fileName;
    }
    
    public byte[] getFileBytes()
    {
        
        
        return fileBytes;
    }
    
    public void setFileBytes(byte[] fileBytes)
    {
        
        
        this.fileBytes = fileBytes;
    }
    
    public String getUrl()
    {
        
        
        return url;
    }
    
    public void setUrl(String url)
    {
        
        
        this.url = url;
    }

    
    public String getFileSource()
    {
        
        return fileSource;
    }

    
    public void setFileSource(String fileSource)
    {
        
        this.fileSource = fileSource;
    }

    
    public String getFileSize()
    {
        
        return fileSize;
    }

    
    public void setFileSize(String fileSize)
    {
        
        this.fileSize = fileSize;
    }

    
    public String getFileByte()
    {
        
        return fileByte;
    }

    
    public void setFileByte(String fileByte)
    {
        
        this.fileByte = fileByte;
    }
    
    
    
}
