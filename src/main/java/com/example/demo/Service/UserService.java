package com.example.demo.Service;

import com.example.demo.Pojo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public interface UserService {
//    增加

    //添加用户
    int addUser(User user);
    //添加安全问题
    void addSafeQuestion(SafeQuestion safeQuestion);
    //添加收藏记录
    void addCollectionBlog(Collection collection);
    //登录
    void login(HttpServletResponse response, String username, String password);

//    删除

    //删除安全问题
    void deleteSafeQuestion(int user_id);
    //删除用户
    void deleteUser(int user_id);
    //删除收藏记录
    void deleteCollectionBlog(int user_id,int blog_id);

//      修改

    //    修改用户基本信息
    void updateUserInfo(String name,String email,int user_id);
    //    修改用户密码
    void updateUserPassWord(String pwd,int user_id);
    //    修改用户状态
    void updateUserState(int state,int user_id);
    //    重置安全问题
    void updateSafeQuestion(String question,String answer,int user_id);

    //查询

    //    用户基本信息
    User getUserInfo(int user_id);
    User getUserInfoByEmail(String email);

    //    用户博客信息
    UserBlogInfo getUserBlogInfo(int user_id);
    //    用户评论信息
    List<Comment> getUserComment(int user_id);
    //    用户回复信息
    List<Replay> getUserReplay(int user_id);
    //    用户安全问题
    SafeQuestion getSafeQuestion(int user_id);
    //用户收藏博客
    Set getCollectionBlog(int user_id);
    User getUserInfoByAccount(String account);
    //得到收藏列表
    List<User> getFollowList(String account);
    //查询权限
    List<String> GetUserJurisdiction(String role);
}
