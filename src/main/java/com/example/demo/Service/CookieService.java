package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class CookieService {
    public void CookieCreate(HttpServletResponse response, HttpServletRequest request, String username,String pwd){
        // Cookie 为键值对数据格式
        Cookie cookie_username = new Cookie("account", username);
        Cookie cookie_paw = new Cookie("pwd", pwd);
        // 即：过期时间，单位是：秒（s）
        cookie_username.setMaxAge(30 * 24 * 60 * 60);
        cookie_paw.setMaxAge(30 * 24 * 60 * 60);
        // 表示当前项目下都携带这个cookie
        cookie_username.setPath(request.getContextPath());
        cookie_paw.setPath(request.getContextPath());
        // 使用 HttpServletResponse 对象向客户端发送 Cookie
        response.addCookie(cookie_username);
        response.addCookie(cookie_paw);
    }
}
