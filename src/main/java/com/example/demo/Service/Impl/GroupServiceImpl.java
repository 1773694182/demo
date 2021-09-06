package com.example.demo.Service.Impl;

import com.example.demo.Mapper.GroupMapper;
import com.example.demo.Pojo.Group;
import com.example.demo.Pojo.MyMessage;
import com.example.demo.Service.GroupService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Map map=new HashMap();
        MyMessage myMessage=new MyMessage();
        Date date=new Date();
        myMessage.setMessage(message);
        myMessage.setUser_id(user_id);
        myMessage.setDate(date);
        List<MyMessage> list=new ArrayList<>();

        map.put(to_user_id,myMessage);
        System.out.println("map:"+map);
        Map<Object, Object> map1= (Map<Object, Object>) redisService.HashGet("Message");
        if (map1.get(to_user_id)!=null)
        {
            list= (List<MyMessage>) map1.get(to_user_id);
            System.out.println("list:"+list);
        }
        list.add(myMessage);
        Map map2=new HashMap();
        map2.put(to_user_id,list);
        System.out.println("map2:"+map2);
        redisService.HashSet("Message",map2);
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
        System.out.println(group_account);
        group.setGroup_id(0);
        System.out.println(group);
        groupMapper.CreateGroup(group);
        redisService.SetSet(String.valueOf(group_account),user_id);
    }

    @Override
    public List<Map> GetMessage(int user_id) {
        Map map= (Map)redisService.HashGet("Message");
        List list= (List) map.get(String.valueOf(user_id));
//        System.out.println(list);
        return list;
    }

    @Override
    public void DeleteGroup(int group_account) {
        groupMapper.DeleteGroup(group_account);
        redisService.del(String.valueOf(group_account));
    }
}
