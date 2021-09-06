package com.example.demo.Controller;

import com.example.demo.Pojo.Blog;
import com.example.demo.Pojo.Collection;
import com.example.demo.Pojo.SafeQuestion;
import com.example.demo.Pojo.User;
import com.example.demo.Service.Impl.BlogServiceImpl;
import com.example.demo.Service.Impl.UserServiceImpl;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private RedisService redisService;
//添加
    @RequestMapping("/addUser")
    public String addUser(User user, SafeQuestion safeQuestion, RedirectAttributes attributes){
        int flag=userService.addUser(user);
        switch (flag) {
            case -1 -> {
                attributes.addFlashAttribute("EmailError", "邮箱已注册");
                attributes.addFlashAttribute("account", user.getAccount());
                attributes.addFlashAttribute("password", user.getPwd());
                return "redirect:/register";
            }
            case 0 -> {
                attributes.addFlashAttribute("AccountError", "有户名已注册");
                attributes.addFlashAttribute("account", user.getAccount());
                attributes.addFlashAttribute("password", user.getPwd());
                return "redirect:/register";
            }
        }

        User user1=userService.getUserInfoByAccount(user.getAccount());
        safeQuestion.setQuestion_id(user1.getUser_id());
        userService.addSafeQuestion(safeQuestion);
        return "redirect:/login";
    }

    @RequestMapping("/AddFriend")
    public void AddFriend(@CookieValue("account")Integer account,@RequestParam("FriendID")String FriendID){
        redisService.SetSet("FriendOf"+account,FriendID);
    }
//删除
    @RequestMapping("/DeleteSafeQuestion")
    public void deleteSafeQuestion(@RequestParam("user_id")int user_id){
        userService.deleteSafeQuestion(user_id);
    }

    @RequestMapping("/DeleteUser")
    public void deleteUser(@RequestParam("user_id")int user_id){
        userService.deleteUser(user_id);
    }

    @RequestMapping("UpdateUserState")
    public void updateUserState(@RequestBody Map<String,Object> map){
        userService.updateUserState((int)map.get("state"),(int)map.get("user_id"));
    }
//修改
    @RequestMapping("/UpdateUserInfo")
    public void updateUserInfo(@RequestBody Map<String,Object> map){
        userService.updateUserInfo((String) map.get("name"), (String) map.get("email"), (int)map.get("user_id"));
    }

    @RequestMapping("UpdateUserPassWord")
    public void updateUserPassWord(@RequestBody Map<String,Object> map){
        userService.updateUserPassWord((String) map.get("pwd"),(int)map.get("user_id"));
    }

    @RequestMapping("UpdateSafeQuestion")
    public void updateSafeQuestion(@RequestBody Map<String,Object>map){
        userService.updateSafeQuestion((String) map.get("question"),(String)map.get("answer"),(int)map.get("user_id"));
    }
//查询
    @RequestMapping("/GetUserInfo")
    public String GetUserInfo(@CookieValue("account")Integer user_id,/*@RequestParam("user_id") int user_id,*/ Model model){
        List<Collection> collectionList=userService.getCollectionBlog(user_id);
        List<Blog> blogList=userService.getCollectionBlog(collectionList);
        
//        System.out.println(userService.getUserInfo(user_id));
//        System.out.println(userService.getUserBlogInfo(user_id));
//        System.out.println(blogService.getBlogByUser(user_id));
//        System.out.println(userService.getCollectionBlog(user_id));
//        System.out.println(blogList);

        model.addAttribute("user_info",userService.getUserInfo(user_id));
        model.addAttribute("user_blog_info",userService.getUserBlogInfo(user_id));
        model.addAttribute("user_blog",blogService.getBlogByUser(user_id));
        model.addAttribute("user_collection",blogList);
        return "UserIndex";
    }
    @RequestMapping("/GetUserInfoByID")
    public String GetUserInfo(@CookieValue("account")int account,@RequestParam("user_id") int user_id, Model model){
        List<Collection> collectionList=userService.getCollectionBlog(user_id);
        List<Blog> blogList=userService.getCollectionBlog(collectionList);
        model.addAttribute("user_info",userService.getUserInfo(user_id));
        model.addAttribute("user_blog_info",userService.getUserBlogInfo(user_id));
        model.addAttribute("user_blog",blogService.getBlogByUser(user_id));
        model.addAttribute("user_collection",blogList);
        if (account==user_id)
            return "UserIndex";
        else
            return "OtherUserIndex";
    }

    @RequestMapping("/GetUserComment")
    public String GetUserComment(@RequestParam("user_id")int user_id,Model model){
//        System.out.println(userService.getUserComment(user_id));
        model.addAttribute("user_comment",userService.getUserComment(user_id));
        return "test";
    }

    @RequestMapping("/GetUserReplay")
    public String GetUserReplay(@RequestParam("user_id")int user_id,Model model){
//        System.out.println(userService.getUserReplay(user_id));
        model.addAttribute("user_replay",userService.getUserReplay(user_id));
        return "test";
    }

    @RequestMapping("/GetSafeQuestion")
    public String GetSafeQuestion(@RequestParam("user_id")int user_id,Model model){
//        System.out.println(userService.getSafeQuestion(user_id));
        model.addAttribute("user_safe_question",userService.getSafeQuestion(user_id));
        return "test";
    }

    @RequestMapping("/GetFriendsList")
    public String GetFriendsList(@CookieValue("account")String account,Model model){
        Set<Object> set=redisService.SetGet("FriendOf"+account);
        List<User> list=new ArrayList<>();
        System.out.println(set);
        for (Object id:set){
            System.out.println(id);
            list.add(userService.getUserInfo(Integer.parseInt((String) id)));
        }
        model.addAttribute("user_list",list);
        System.out.println(list);
        return "chat";
    }
}
