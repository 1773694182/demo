package com.example.demo.Service.Impl;

import com.example.demo.Pojo.User;
import com.example.demo.Service.CookieService;
import com.example.demo.Service.LoginService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CookieService cookieService;
    @Override
    public boolean login(String account,String password) {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5Password);
        User user=userService.getUserInfoByAccount(account);
        System.out.println(user);
        return user != null && user.getPwd().equals(md5Password);
    }

    @Override
    public boolean login(String email, int VerificationCode) {
        System.out.println(email+","+VerificationCode);
        Map VCode= (Map)redisService.HashGet(email+"login");
        System.out.println(VCode);
        if (VCode.get("login")==null)
            return false;
        int Code= Integer.parseInt((String) VCode.get("login"));
        try {
            System.out.println(Code == VerificationCode);
            return Code == VerificationCode;
        }catch (Exception e){
            return false;
        }
    }
}
