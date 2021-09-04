package com.example.demo.Config;

import com.example.demo.Pojo.User;
import com.example.demo.Service.Impl.UserServiceImpl;
import com.example.demo.Service.RedisService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserServiceImpl userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//        info.addStringPermission("Test2:chat");
        return null;
    }
//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken userToken=(UsernamePasswordToken) token;
        Map<String,String>map= (Map<String, String>) redisService.HashGet("user");
        System.out.println(map);
        String account="";
        String password="";
        if(map.size()!=0){
            account=map.get("account");
            password=map.get("password");
        }else {
            User user=userService.getUserInfoByAccount(userToken.getUsername());
            if (user==null)
                return null;
            else {
                account=user.getAccount();
                password=user.getPwd();
            }
        }
        if (!userToken.getUsername().equals(account))
            return null;
        return new SimpleAuthenticationInfo("",password,"");
    }
}
