//package com.example.demo.Config;
//
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//    //ShiroFilterFactoryBean
//    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
//        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
//        bean.setSecurityManager(defaultWebSecurityManager);
//        /*
//        * anon:无需认证就可以访问
//        * authc:必须认证了才能访问
//        * user：必须拥有记住我才能访问
//        * perms：拥有对某个资源的权限才能访问
//        * role：拥有某个角色的权限才能访问
//        * */
//        Map<String, String> filterMap=new HashMap<>();
//        filterMap.put("/Test/*","authc");
//        filterMap.put("/index2","perms[Test2:chat]");
//
//        bean.setFilterChainDefinitionMap(filterMap);
//        bean.setLoginUrl("/loginTest");
//        bean.setUnauthorizedUrl("/noAuth");
//        return bean;
//    }
//
//    //DefaultWebSecurityManager
//    @Bean
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
//        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
//        securityManager.setRealm(userRealm);
//        return securityManager;
//    }
//
//    //创建realm对象
//    @Bean
//    public UserRealm userRealm(){
//        return new UserRealm();
//    }
//}
