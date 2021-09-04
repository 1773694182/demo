package com.example.demo.Service;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    boolean login(String account,String password);//登录
    boolean login(String email,int VerificationCode);
}
