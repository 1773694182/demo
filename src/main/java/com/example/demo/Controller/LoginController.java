package com.example.demo.Controller;
import com.example.demo.Pojo.User;
import com.example.demo.Service.CookieService;
import com.example.demo.Service.Impl.LoginServiceImpl;
import com.example.demo.Service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.example.demo.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CookieService cookieService;
    @ResponseBody
    @RequestMapping("/loginByShiro")
    public String loginByShiro(Model model, @RequestParam("account")String account, @RequestParam("password") String password,
                                @RequestParam("op")String op, HttpServletRequest request,HttpServletResponse response){
        System.out.println("登录");
        Subject subject= SecurityUtils.getSubject();
        String md5Password=null;
        String user_id=null;
        User user=new User();
        if (op.equals("input")){
            md5Password= DigestUtils.md5DigestAsHex(password.getBytes());
            user=userService.getUserInfoByAccount(account);
            user_id=String.valueOf(user.getUser_id());
            Map<String,Object>map=new HashMap<>();
            map.put("user_id",user.getUser_id());
            map.put("account",user.getAccount());
            map.put("name",user.getName());
            redisService.HashSet("user", map );
        }
        else if(op.equals("auto")){
            md5Password=password;
            user_id=account;
        }
        UsernamePasswordToken token=new UsernamePasswordToken(user_id,md5Password);
        try{
            subject.login(token);
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "0";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "0";
        }
        cookieService.CookieCreate(response,request,user_id,md5Password);
        return "1";
    }
    @ResponseBody
    @RequestMapping("/login_check")
    public String login(@RequestParam("account")String account, @RequestParam("password")String password,
                        RedirectAttributes attributes,HttpServletResponse response,HttpServletRequest request){
        if (loginService.login(account,password))
        {
            User user=userService.getUserInfoByAccount(account);
            cookieService.CookieCreate(response,request, String.valueOf(user.getUser_id()),String.valueOf(user.getPwd()));
            return "1";
        }
        else
        {
            attributes.addFlashAttribute("msg","账号或密码错误");
            attributes.addFlashAttribute("account",account);
            attributes.addFlashAttribute("password",password);
            attributes.addFlashAttribute("login_way","account");
            return "0";
        }
    }
    @ResponseBody
    @RequestMapping("/LoginByEmail")
    public String login(@RequestParam("Email")String email, @RequestParam("VerificationCode")int VerificationCode,
                        HttpServletRequest request, HttpServletResponse response){
        if (loginService.login(email, VerificationCode))
        {
            User user=userService.getUserInfoByEmail(email);
            Map<String,Object>map=new HashMap<>();
            map.put("user_id",user.getUser_id());
            map.put("account",user.getAccount());
            map.put("name",user.getName());
//            System.out.println("login_id:"+user.getUser_id());
//            session.setAttribute("user_id",user.getUser_id());
            redisService.HashSet("user", map );
            cookieService.CookieCreate(response,request, String.valueOf(user.getUser_id()),String.valueOf(user.getPwd()));
            return "1";
        }
        else {
//            attributes.addFlashAttribute("email",email);
//            attributes.addFlashAttribute("VerificationCode",VerificationCode);
//            attributes.addFlashAttribute("login_way","email");
            return "0";
        }
    }
//    @RequestMapping("/CookieCreate")
//    public String CookieCreate(HttpServletResponse response, HttpServletRequest request){
////        cookieService.CookieCreate(response,request,"testcookie");
//        return "test";
//    }

    @RequestMapping("/CreateVerificationCode")
    public void CreateVerificationCode(@RequestParam("Email")String email,@RequestParam("operation")String operation,
                                       HttpServletResponse response) throws IOException{
        try{
            int emailServiceCode =(int) (Math.random()*10000);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("注册验证码");
            message.setText("注册验证码是：" + emailServiceCode);
            message.setFrom("1773694182@qq.com");
            message.setTo(email);
//            System.out.println(email);

            mailSender.send(message);
            HashMap map=new HashMap();
            map.put(operation, String.valueOf(emailServiceCode));
            redisService.HashSet(email+operation, map);
            redisService.expire(email+operation,60*5);
        }catch (Exception e)
        {
            response.getWriter().write("邮箱错误");
            e.printStackTrace();
        }
    }
    @RequestMapping("/Verification")
    public String Verification(@RequestParam("Email")String email,@RequestParam("operation")String operation,
                             @RequestParam("VCode")String VCode) {
        Map map= (Map) redisService.HashGet(email);
        String Code= (String) map.get(operation);
        if (VCode.equals(Code)){
            return "/index2";
        }
        else return "/register";
    }
    @ResponseBody
    @RequestMapping("/AutomaticLogon")
    public String AutomaticLogon(@RequestParam("account")String account,HttpServletRequest request,
                                 @RequestParam("pwd")String pwd){
//        String url="loginByShiro?account"+=account+"&password="+pwd;
//        return url;
        Cookie[] cookies=request.getCookies();
        boolean flag=false;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("account"))
                if(cookie.getValue().equals(account))
                    flag=true;
                else{
                    flag=false;
                    break;
                }
            else if (cookie.getName().equals("pwd"))
                if(cookie.getValue().equals(pwd))
                    flag=true;
                else {
                    flag=false;
                    break;
                }
        }
        if (flag){
            User user=userService.getUserInfo(Integer.parseInt(account));
            Map<String,Object> map=new HashMap<>();
            map.put("account",user.getAccount());
            map.put("user_id",user.getUser_id());
            redisService.HashSet(account+"login",map);
            return "1";
        }
        return "0";
    }
    @RequestMapping("/LoginOut")
    public String LoginOut(HttpServletRequest request,HttpServletResponse response){
        Cookie account = new Cookie("account", "");
        Cookie pwd = new Cookie("pwd", "");
        Cookie state = new Cookie("state", "");
        account.setMaxAge(0);
        pwd.setMaxAge(0);
        state.setMaxAge(0);
        account.setPath(request.getContextPath());
        pwd.setPath(request.getContextPath());
        state.setPath(request.getContextPath());
        response.addCookie(account);
        response.addCookie(pwd);
        response.addCookie(state);
        Subject subject= SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }
}
