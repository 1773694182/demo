package com.example.demo.Service.Impl;

import com.example.demo.Mapper.BlogMapper;
import com.example.demo.Mapper.SearchMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Pojo.Blog;
import com.example.demo.Pojo.Comment;
import com.example.demo.Pojo.Replay;
import com.example.demo.Service.BlogService;
import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SearchServiceImpl searchService;
    @Autowired
    private RedisService redisService;
    @Override
    public void postBlog(List<Map<String,Object>> list) throws IOException {
        Blog blog=new Blog();
        for (int i=0;i<list.size();i++){
            switch (list.get(i).get("name").toString()){
                case "title":
                    blog.setTitle(list.get(i).get("value").toString());
                    break;
                case "classification":
                    blog.setClassification(list.get(i).get("value").toString());
                    break;
                case "tip":
                    blog.setLabel(list.get(i).get("value").toString());
                    break;
                case "content":
                    blog.setContent(list.get(i).get("value").toString());
                    break;
                case "state":
                    blog.setState(Integer.parseInt(list.get(i).get("value").toString()));
            }
            java.util.Date date=new Timestamp(System.currentTimeMillis());
            Timestamp date1=new Timestamp(date.getTime());
            blog.setUser_id(1);
            blog.setCollection_number(0);
            blog.setLike_number(0);
            blog.setView_number(0);
            blog.setDate(date1);
            blog.setComment_number(0);
        }
        blogMapper.postBlog(blog);
    }

    @Override
    public Comment postComment(List<Map<String,Object>> list,String user_id) {
        Comment comment=new Comment();
        for (int i=0;i<list.size();i++){
            switch (list.get(i).get("name").toString()){
                case "user_id":
                    comment.setUser_id(Integer.parseInt(user_id));
                    break;
                case "blog_id":
                    comment.setBlog_id(Integer.parseInt(list.get(i).get("value").toString()));
                    break;
                case "content":
                    comment.setContent(list.get(i).get("value").toString());
                    break;
            }
        }
        java.util.Date date=new Timestamp(System.currentTimeMillis());
        Timestamp date1=new Timestamp(date.getTime());
        comment.setUser_name(userMapper.getUserInfo(comment.getUser_id()).getName());
        comment.setDate(date1);
        comment.setState(0);
//        System.out.println(comment);
        blogMapper.postComment(comment);
        return comment;
    }

    @Override
    public Replay postReplay(List<Map<String,Object>> list,String user_id) {
        Replay replay=new Replay();
        for (int i=0;i<list.size();i++){
            switch (list.get(i).get("name").toString()){
                case "user_id":
                    replay.setUser_id(Integer.parseInt(user_id));
                    break;
                case "comment_id":
                    replay.setComment_id(Integer.parseInt(list.get(i).get("value").toString()));
                    break;
                case "content":
                    replay.setContent(list.get(i).get("value").toString());
                    break;
                case "to_user_id":
                    replay.setTo_user_id(Integer.parseInt(list.get(i).get("value").toString()));
                    break;
            }
        }
        java.util.Date date=new Timestamp(System.currentTimeMillis());
        Timestamp date1=new Timestamp(date.getTime());
        replay.setUser_name(userMapper.getUserInfo(replay.getUser_id()).getName());
        replay.setTo_user_name(userMapper.getUserInfo(replay.getTo_user_id()).getName());
        replay.setDate(date1);
        replay.setState(0);
        blogMapper.postReplay(replay);
        return replay;
    }

    @Override
    public void deleteBlog(int blog_id) { blogMapper.deleteBlog(blog_id); }

    @Override
    public void deleteComment(int comment_id) { blogMapper.deleteComment(comment_id); }

    @Override
    public void deleteReplay(int replay_id) { blogMapper.deleteReplay(replay_id); }

    @Override
    public void updateBlog(String content, String label, String classification, String title, int blog_id) {
        blogMapper.updateBlog(content,label,classification,title,blog_id);
    }

    @Override
    public void postBlogFromDraft(int blog_id) {
        blogMapper.postBlogFromDraft(blog_id);
    }

    @Override
    public int LikeBlog(int blog_id,String user_id) {
        System.out.println(user_id);
        if (!redisService.SetHasKey(blog_id+"LikeBlog",user_id)){
            redisService.SetSet(blog_id+"LikeBlog",user_id);
            blogMapper.LikeBlog(blog_id);
            return 1;
        }
        return -1;
//        else{//取消点赞
//            redisService.SetRemove(blog_id+"LikeBlog",user_id);
//        }
    }

    @Override
    public void CancelHideBlog(int blog_id) {
        blogMapper.CancelHideBlog(blog_id);
    }

    @Override
    public void HideBlog(int blog_id) {
        blogMapper.HideBlog(blog_id);
    }

    @Override
    public int CollectionBlog(int blog_id,String user_id) {
        if(!redisService.SetHasKey(user_id+"CollectionBlog",blog_id)){
            redisService.SetSet(user_id+"CollectionBlog",blog_id);
            blogMapper.CollectionBlog(blog_id);
            return 1;
        }
        else {
            redisService.SetRemove(user_id+"CollectionBlog",blog_id);//取消收藏
            blogMapper.CancelCollectionBlog(blog_id);
            return -1;
        }
    }

    @Override
    public void CommentBlog(int comment_number,int blog_id) {
        blogMapper.CommentBlog(comment_number,blog_id);
    }

    @Override
    public Blog getBlogByID(int blog_id) {
        return blogMapper.getBlogByID(blog_id);
    }

    @Override
    public List<Blog> getBlogByUser(int user_id) {
        return blogMapper.getBlogByUser(user_id);
    }

    @Override
    public List<Comment> getComment(int blog_id) { return blogMapper.getComment(blog_id); }

    @Override
    public List<Replay> getReplay(int comment_id) {
        return blogMapper.getReplay(comment_id);
    }

    @Override
    public List<Blog> getDraftBlogByUser(int user_id) {
        return blogMapper.getDraftBlogByUser(user_id);
    }

    @Override
    public List<Blog> getDeleteBlogByUser(int user_id) {
        return blogMapper.getDeleteBlogByUser(user_id);
    }

    @Override
    public Integer getLikeNumber(int blog_id) {
        return blogMapper.getLikeNumber(blog_id);
    }


    @Override
    public Integer getCollection(int blog_id) {
        return blogMapper.getCollection(blog_id);
    }



    @Override
    public int getCommentNumber(int blog_id) {
        return blogMapper.getCommentNumber(blog_id);
    }

    @Override
    public List<Integer> getCommentID(int blog_id) {
//        System.out.println(blog_id);
        return blogMapper.getCommentID(blog_id);
    }

    @Override
    public int getReplayNumber(List<Integer> list) {
        int replay_number=0;
        for (int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
            replay_number+=blogMapper.GetReplayNumber(list.get(i));
        }
        return replay_number;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public List<Blog> getBlogByClassification(String classification) {
        return blogMapper.getBlogByClassification(classification);
    }

    @Override
    public List<Blog> getBlogByLabel(String label) {
        return blogMapper.getBlogByLabel(label);
    }

    //整合博客和用户名
    public List<Map<String,Object>> Conformity_blog(List<Blog> blog){
        List<Map<String,Object>>blog_list=new ArrayList<>();
        for(int i=0;i<blog.size();i++){
            Map<String,Object> map=new HashMap<>();
            map.put("user_name",userMapper.getUserInfo(blog.get(i).getUser_id()).getName());
            map.put("blog",blog.get(i));
            blog_list.add(map);
        }
        return blog_list;
    }

    //整合评论和回复
    public List<Map<String,List>> Conformity_comment(List<Comment> comment){
        List<Map<String,List>>replay_list=new ArrayList<>();
        for (int i=0;i<comment.size();i++)
        {
            List<Comment> temp=new ArrayList<>();
            comment.get(i).setUser_name(userMapper.getUserInfo(comment.get(i).getUser_id()).getName());
            temp.add(comment.get(i));
            List<Replay>replayList=blogMapper.getReplay(comment.get(i).getComment_id());
            Map<String,List> map=new HashMap<>();
            map.put("comment",temp);
            List<List> comment_list=new ArrayList<>();
            for(int j=0;j<replayList.size();j++){
                replayList.get(j).setUser_name(userMapper.getUserInfo(replayList.get(j).getUser_id()).getName());
                replayList.get(j).setTo_user_name(userMapper.getUserInfo(replayList.get(j).getTo_user_id()).getName());
            }
            comment_list.add(replayList);
            map.put("replay",comment_list);
            replay_list.add(map);
        }
        return replay_list;
    }
}
