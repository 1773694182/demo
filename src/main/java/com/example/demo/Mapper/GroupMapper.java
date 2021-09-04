package com.example.demo.Mapper;

import com.example.demo.Pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupMapper {
    Group SearchGroup(int group_id);
    void CreateGroup(Group group);
    void DeleteGroup(int group_account);
}
