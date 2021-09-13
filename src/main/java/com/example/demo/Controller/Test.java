package com.example.demo.Controller;

import com.example.demo.Service.Impl.UserServiceImpl;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class Test {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("/login2")
    public String Test(){
        System.out.println("login2");
        return "login";
    }
    @RequestMapping("/Test/chat")
    public String Group(){
        System.out.println("/Test/chat");
        return "Test/chat";
    }
    @ResponseBody
    @RequestMapping("/noAuth")
    public String noauth(){
        System.out.println("未授权");
        return "未授权";
    }
    @ResponseBody
    @RequestMapping("/TestSession")
    public int TestSession(HttpSession session){
        Map map= (Map) redisService.HashGet("user");
        session.setAttribute("Test","OK");
        return (int)map.get("user_id") ;
    }
    @ResponseBody
    @RequestMapping("/GetUserJurisdiction")
    public void GetUserJurisdiction(){
        System.out.println(userService.GetUserJurisdiction("admin"));

    }
}
