package com.example.demo.Config;

import com.example.demo.Pojo.Jurisdiction;
import com.example.demo.Pojo.User;
import com.example.demo.Service.Impl.UserServiceImpl;
import com.example.demo.Service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserServiceImpl userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("授权");
        Subject subject=SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        List<String> list=userService.GetUserJurisdiction(user.getRole());
        for (String jurisdiction:list)
            info.addStringPermission(jurisdiction);
//        System.out.println(info.getStringPermissions());
        return info;
    }
//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("认证");
        UsernamePasswordToken userToken=(UsernamePasswordToken) token;
        Map<String,String>map= (Map<String, String>) redisService.HashGet("user");
        String account="";
        String password="";
        User user=new User();
        if(map.size()!=0){
            account=String.valueOf(map.get("user_id"));
            user=userService.getUserInfo(Integer.parseInt(String.valueOf(map.get("user_id"))));
            password=user.getPwd();
        }else {
            user=userService.getUserInfoByAccount(userToken.getUsername());
            if (user==null)
                return null;
            else {
                account=user.getAccount();
                password=user.getPwd();
            }
        }
        if (!userToken.getUsername().equals(account))
            return null;
        return new SimpleAuthenticationInfo(user,password,"");
    }
}
