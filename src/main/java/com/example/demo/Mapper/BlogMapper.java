package com.example.demo.Mapper;

import com.example.demo.Pojo.Blog;
import com.example.demo.Pojo.Comment;
import com.example.demo.Pojo.Replay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper {
//添加
//发布博客
    void postBlog(Blog blog);
//评论
    void postComment(Comment comment);
//回复
    void postReplay(Replay replay);

//删除
//删除博客
    void deleteBlog(int blog_id);
//删除评论
    void deleteComment(int comment_id);
//删除回复
    void deleteReplay(int replay_id);

//修改
//修改博客内容
    void updateBlog(Blog blog);
//从草稿箱发布博客
    void postBlogFromDraft(int blog_id);
//点赞
    void LikeBlog(int blog_id);
//收藏
    void CollectionBlog(int blog_id);
    //取消收藏
    void CancelCollectionBlog(int blog_id);
    //隐藏博客
    void HideBlog(int blog_id);
    //取消隐藏博客
    void CancelHideBlog(int blog_id);
//评论
    void CommentBlog(int comment_number,int blog_id);
//查询
//查询所有博客
    List<Blog> getAllBlog();
//查询博客通过分类
    List<Blog> getBlogByClassification(String classification);
//查询博客通过分类
    List<Blog> getBlogByLabel(String label);
//查询博客信息通过ID
    Blog getBlogByID(int blog_id);
//查询博客信息通过用户
    List<Blog> getBlogByUser(int user_id);
//查询博客的评论信息
    List<Comment> getComment(int blog_id);
//查询评论的回复信息
    List<Replay> getReplay(int comment_id);
//查询草稿箱
    List<Blog> getDraftBlogByUser(int user_id);
//查询回收站
    List<Blog> getDeleteBlogByUser(int user_id);
//查询点赞数
    Integer getLikeNumber(int blog_id);
//查询收藏数
    Integer getCollection(int blog_id);
//查询评论ID
    List<Integer> getCommentID(int blog_id);
//查询评论数
    int getCommentNumber(int blog_id);
//查询回复数
    int GetReplayNumber(int comment_id);
}
