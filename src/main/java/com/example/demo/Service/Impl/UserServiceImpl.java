package com.example.demo.Service.Impl;

import com.example.demo.Mapper.BlogMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Pojo.*;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private RedisService redisService;
    //增加
    @Override
    public int addUser(User user) {
        User user1=userMapper.getUserInfoByAccount(user.getAccount());
        if (user1!=null)
            return 0;
        else if (user.getEmail()!=null)
            if (userMapper.getEmail(user.getEmail())!=null)
                userMapper.addUser(user);
            else return -1;
        return 1;
    }

    @Override
    public void addSafeQuestion(SafeQuestion safeQuestion) {
        userMapper.addSafeQuestion(safeQuestion);
    }

    @Override
    public void addCollectionBlog(Collection collection) {
        userMapper.addCollectionBlog(collection);
    }

    @Override
    public void login(HttpServletResponse response, String username, String password) {

    }

    //删除
    @Override
    public void deleteSafeQuestion(int user_id) {
        userMapper.deleteSafeQuestion(user_id);
    }

    @Override
    public void deleteUser(int user_id) {
        userMapper.deleteUser(user_id);
    }

    @Override
    public void deleteCollectionBlog(int user_id, int blog_id) {
        userMapper.deleteCollectionBlog(user_id,blog_id);
    }

    //修改
    @Override
    public void updateUserInfo(String name, String email, int user_id) {
        userMapper.updateUserInfo(name, email, user_id);
    }

    @Override
    public void updateUserPassWord(String pwd, int user_id) {
        userMapper.updateUserPassWord(pwd, user_id);
    }

    @Override
    public void updateUserState(int state, int user_id) {
        userMapper.updateUserState(state, user_id);
    }

    @Override
    public void updateSafeQuestion(String question, String answer, int user_id) {
        userMapper.updateSafeQuestion(question, answer, user_id);
    }

    //查询
    @Override
    public User getUserInfo(int user_id){
        return userMapper.getUserInfo(user_id);
    }

    @Override
    public User getUserInfoByEmail(String email) {
        return userMapper.getUserInfoByEmail(email);
    }

    @Override
    public SafeQuestion getSafeQuestion(int user_id) { return userMapper.getSafeQuestion(user_id); }

    @Override
    public UserBlogInfo getUserBlogInfo(int user_id) {
        return userMapper.getUserBlogInfo(user_id);
    }

    @Override
    public List<Comment> getUserComment(int user_id) {
        return userMapper.getUserComment(user_id);
    }

    @Override
    public List<Replay> getUserReplay(int user_id) {
        return userMapper.getUserReplay(user_id);
    }

    @Override
    public Set getCollectionBlog(int user_id) {
        Set set=redisService.SetGet(user_id+"CollectionBlog");
        return set;
    }

    @Override
    public User getUserInfoByAccount(String account) {
        return userMapper.getUserInfoByAccount(account);
    }

    public List<Blog> getCollectionBlog(Set collectionList){
        List<Blog>blogList=new ArrayList<>();
        for (Object collection : collectionList) {
            System.out.println(collection);
            blogList.add(blogMapper.getBlogByID(Integer.parseInt(String.valueOf(collection))));
        }
        return blogList;
    }
}
