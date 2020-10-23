package com.water.module.video.config;

import com.water.module.video.util.OnvifUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author：my
 * @date：2019/11/14 14:23
 * @describe：监听器类
 */
@Component
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent e) {

        OnvifUtil.connect("192.168.1.40", "admin","sifu12345");

    }

}
