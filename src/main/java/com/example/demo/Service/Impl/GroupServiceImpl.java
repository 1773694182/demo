package com.example.demo.Service.Impl;

import com.example.demo.Mapper.GroupMapper;
import com.example.demo.Pojo.Group;
import com.example.demo.Pojo.MyMessage;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private RedisService redisService;
    @Override
    public Group SearchGroup(int group_id) {
        return groupMapper.SearchGroup(group_id);
    }

    @Override
    public void SendMessage(String to_user_id,String user_id, String message) {
        String MessageId=null;
        if (Integer.valueOf(to_user_id)>Integer.valueOf(user_id))
            MessageId=user_id+to_user_id;
        else MessageId=to_user_id+user_id;

        Map map=new HashMap();
        MyMessage myMessage=new MyMessage();
        Date date=new Date();
        myMessage.setMessage(message);
        myMessage.setUser_id(user_id);
        myMessage.setDate(date);
        List<Object> list=new ArrayList<>();
        list.add(myMessage);
        redisService.ListSetAll("MessageOf"+MessageId,list);
    }

    @Override
    public void CreateGroup(int user_id,String group_name) {
        Group group=new Group();
        group.setGroup_name(group_name);
        group.setUser_id(user_id);
        int group_account;
        while (true)
        {
            group_account=(int)(Math.random()*1000000000);
            if (groupMapper.SearchGroup(group_account)==null)
                break;
        }
        group.setGroup_account(group_account);
//        System.out.println(group_account);
        group.setGroup_id(0);
//        System.out.println(group);
        groupMapper.CreateGroup(group);
        redisService.SetSet(String.valueOf(group_account),user_id);
    }

    @Override
    public List<Object> GetMessage(int account,int user_id) {
        String MessageId=null;
        if (account>user_id)
            MessageId=user_id+""+account;
        else MessageId=account+""+user_id;
        List<Object> list=redisService.ListGet("MessageOf"+MessageId,0,-1);
//        System.out.println(list);
        return list;
    }

    @Override
    public void DeleteGroup(int group_account) {
        groupMapper.DeleteGroup(group_account);
        redisService.del(String.valueOf(group_account));
    }
}
