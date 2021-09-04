package com.example.demo.Controller;

import com.example.demo.Pojo.MyMessage;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/SearchGroup")
    public void SearchGroup(@RequestParam("group_id")int group_id){
        groupService.SearchGroup(group_id);
    }
    @RequestMapping("/JoinGroup")
    public void JoinGroup(@RequestParam("user_id")int user_id,@RequestParam("group_account")int group_account){
        redisService.SetSet(String.valueOf(group_account),user_id);
    }
    @RequestMapping("/CreateGroup")
    public void CreateGroup(@RequestParam("user_id")int user_id,@RequestParam("group_name")String group_name){
        groupService.CreateGroup(user_id,group_name);
    }
    @RequestMapping("/SignOutGroup")
    public void SignOutGroup(@RequestParam("user_id")int user_id,@RequestParam("group_account")int group_account){
        redisService.SetRemove(String.valueOf(group_account),user_id);
    }
    @RequestMapping("/DeleteGroup")
    public void DeleteGroup(@RequestParam("group_account")int group_account){
        groupService.DeleteGroup(group_account);
    }
    @RequestMapping("/SendMessage")
    public void SendMessage(){
        groupService.SendMessage(String.valueOf(1),String.valueOf(0),"hello world");
    }
    @RequestMapping("/GetMessage")
    public void GetMessage(){
        List list=groupService.GetMessage(1);
        System.out.println(list);
//        System.out.println(map.get("message"));
//        System.out.println(map.get("date"));
//        System.out.println(map.get("user_id"));
    }
}
