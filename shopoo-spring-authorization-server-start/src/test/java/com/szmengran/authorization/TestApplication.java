package com.szmengran.authorization;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import org.junit.Test;
import java.util.Properties;

public class TestApplication {

    @Test
    public static void main(String[] args) throws NacosException {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", "www.szmengran.com:8848?username=nacos&password=@");
        properties.setProperty("namespace", "szmengran-dev");
        properties.put("username","");
        properties.put("password","@");
        NamingService naming = NamingFactory.createNamingService(properties);
    
        naming.registerInstance("shopoo-wechat-start", "11.11.11.11", 8888, "TEST1");
    
        naming.registerInstance("shopoo-wechat-start", "2.2.2.2", 9999, "DEFAULT");
    
        System.out.println(naming.getAllInstances("shopoo-wechat-start"));
    
        naming.deregisterInstance("shopoo-wechat-start", "2.2.2.2", 9999, "DEFAULT");
    
        System.out.println(naming.getAllInstances("shopoo-wechat-start"));
    
        naming.subscribe("shopoo-wechat-start", new EventListener() {
            @Override
            public void onEvent(Event event) {
                System.out.println(((NamingEvent)event).getServiceName());
                System.out.println(((NamingEvent)event).getInstances());
            }
        });
    }
}
