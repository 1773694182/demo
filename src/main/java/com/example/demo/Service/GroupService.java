package com.example.demo.Service;

import com.example.demo.Pojo.Group;
import com.example.demo.Pojo.MyMessage;

import java.util.List;
import java.util.Map;

public interface GroupService {
    Group SearchGroup(int group_id);
    void CreateGroup(int user_id,String group_name);
    void DeleteGroup(int group_account);
    void SendMessage(String to_user_id,String user_id,String message);
    List<Map> GetMessage(int user_id);
}
