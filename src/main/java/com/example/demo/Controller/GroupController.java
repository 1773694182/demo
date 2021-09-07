package com.example.demo.Controller;

import com.example.demo.Pojo.MyMessage;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

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
//    @RequestMapping("/GetMessage")
//    public List GetMessage(){
//        List<Map> list=groupService.GetMessage(1);
//        return list;
//    }
    @ResponseBody
    @RequestMapping("/GetMessageByID")
    public List GetMessageByID(@CookieValue("account")int account,@RequestParam("user_id")String user_id){
        List<Object> list=groupService.GetMessage(account,Integer.parseInt(user_id));
//        List<Map> list1=groupService.GetMessage();
        List<Map> MapList=new ArrayList<>();
//        System.out.println("List:"+list);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object map1:list){
            Map map=(Map) map1;
            String dateString = formatter.format(map.get("date"));
            map.put("date",dateString);
            MapList.add(map);
        }
        return MapList;
    }
    @ResponseBody
    @RequestMapping("/GetDate")
    public String GetDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(System.currentTimeMillis());
//        System.out.println((String) dateString);
        return dateString;
    }
}
