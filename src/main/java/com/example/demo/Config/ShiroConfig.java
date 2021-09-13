package com.example.demo.Config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        /*
        * anon:无需认证就可以访问
        * authc:必须认证了才能访问
        * user：必须拥有记住我才能访问
        * perms：拥有对某个资源的权限才能访问
        * role：拥有某个角色的权限才能访问
        * */
        Map<String, String> filterMap=new HashMap<>();
        //无需登录
        filterMap.put("/loginByShiro","anon");
        filterMap.put("/login","anon");
        filterMap.put("/index2","anon");
        filterMap.put("/GetBlogByLabel","anon");
        filterMap.put("/GetAllBlog","anon");
        filterMap.put("/GetBlogByClassification","anon");
        filterMap.put("/GetBlogByID","anon");
        filterMap.put("/GetBlogByUser","anon");
        filterMap.put("/LoginOut","anon");
        filterMap.put("/Verification","anon");
        filterMap.put("/CreateVerificationCode","anon");
        filterMap.put("/LoginByEmail","anon");

//需要登录
        filterMap.put("/chat","authc");
        filterMap.put("/GetFriendsList","authc");
        filterMap.put("/GetUserInfo","authc");
        filterMap.put("/UserPostBlog","authc");
        filterMap.put("/AddFriend","authc");
        filterMap.put("/CancelFollow","authc");
        filterMap.put("/DeleteSafeQuestion","authc");
        filterMap.put("/UpdateUserInfo","authc");
        filterMap.put("/UpdateUserPassWord","authc");
        filterMap.put("/UpdateSafeQuestion","authc");
        filterMap.put("/GetUserComment","authc");
        filterMap.put("/GetUserReplay","authc");
        filterMap.put("/GetSafeQuestion","authc");
        filterMap.put("/PostBlog","authc");
        filterMap.put("/PostComment","authc");
        filterMap.put("/PostReplay","authc");
        filterMap.put("/DeleteBlog","authc");
        filterMap.put("/DeleteComment","authc");
        filterMap.put("/DeleteReplay","authc");
        filterMap.put("/LikeBlog","authc");
        filterMap.put("/PostBlogFromDraft","authc");
        filterMap.put("/CollectionBlog","authc");
        filterMap.put("/HideBlog","authc");
        filterMap.put("/CancelHide","authc");
        filterMap.put("/GetBlogToUpdate","authc");
        filterMap.put("/GetDraftBlog","authc");
        filterMap.put("/GetDeleteBlog","authc");



//需要权限
        filterMap.put("/UserList","perms[DeleteAllBlog]");
        filterMap.put("/DeleteUser","perms[DeleteUser]");
        filterMap.put("/UpdateUserState","perms[UpdateUserState]");
//        filterMap.put("/index2","perms[Test2:chat]");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/noAuth");
        return bean;
    }

    //DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
