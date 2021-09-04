package com.example.demo.Controller;

import com.example.demo.Pojo.Blog;
import com.example.demo.Pojo.Comment;

import com.example.demo.Pojo.Replay;
import com.example.demo.Service.Impl.BlogServiceImpl;
import com.example.demo.Service.Impl.SearchServiceImpl;
import com.example.demo.Service.Impl.UserServiceImpl;

import com.example.demo.Service.RedisService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SearchServiceImpl searchService;
    @Autowired
    private RedisService redisService;

//添加
    @ResponseBody
    @RequestMapping("/PostBlog")
    public void postBlog(@RequestBody List<Map<String,Object>> list, HttpServletResponse response) throws IOException {
//        System.out.println(list);
        blogService.postBlog(list);
    }

    @ResponseBody
    @RequestMapping("/PostComment")
    public void postComment(@RequestBody List<Map<String,Object>> list, HttpServletResponse response){
        Comment comment=blogService.postComment(list);
//        blogService.CollectionBlog(comment.getBlog_id());
        Map<String,Object>map=new HashMap<>();
        map.put("content",comment.getContent());
        map.put("user_id",comment.getUser_id());
        map.put("comment_id",comment.getComment_id());
        map.put("blog_id",comment.getBlog_id());
        map.put("date",comment.getDate());
        map.put("user_name",comment.getUser_name());
        map.put("comment_number",GetCommentNumber(comment.getBlog_id()));
        JSONObject jsonObject = JSONObject.fromObject(map);
//        System.out.println(map);
//        System.out.println(jsonObject);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/PostReplay")
    public void postReplay(@RequestBody List<Map<String,Object>> list, HttpServletResponse response){
        Replay replay=blogService.postReplay(list);
        Map<String,Object>map=new HashMap<>();
        map.put("content",replay.getContent());
        map.put("user_id",replay.getUser_id());
        map.put("user_name",replay.getUser_name());
        map.put("comment_id",replay.getComment_id());
        map.put("to_user_id",replay.getTo_user_id());
        map.put("to_user_name",replay.getTo_user_name());
        map.put("date",replay.getDate());
        JSONObject jsonObject = JSONObject.fromObject(map);
//        System.out.println(map);
//        System.out.println(jsonObject);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/ToGetCommentNumber")
    public void ToGetCommentNumber(int blog_id, HttpServletResponse response){
//        System.out.println("getCommentNumber");
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(GetCommentNumber(blog_id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int GetCommentNumber(int blog_id){
        int commentNumber=blogService.getCommentNumber(blog_id);
        System.out.println("commentNumber"+commentNumber);
        List<Integer>list=blogService.getCommentID(blog_id);
        int replay_number=blogService.getReplayNumber(list);
        blogService.CommentBlog(commentNumber+replay_number,blog_id);
        return commentNumber+replay_number;
    }


//删除
    @RequestMapping("/DeleteBlog")
    public void deleteBlog(@RequestParam("blog_id")int blog_id){
        blogService.deleteBlog(blog_id);
    }

    @RequestMapping("DeleteComment")
    public void deleteComment(@RequestParam("comment_id")int comment_id){
        blogService.deleteComment(comment_id);
    }

    @RequestMapping("DeleteReplay")
    public void deleteReplay(@RequestParam("replay_id")int replay_id){
        blogService.deleteReplay(replay_id);
    }

//修改
    @RequestMapping("UpdateBlog")
    public void updateBlog(@RequestBody Map<String,Object>map){
        blogService.updateBlog((String)map.get("content"), (String) map.get("label"),
                (String) map.get("classification"),(String) map.get("title"),(int) map.get("blog_id"));
    }
    @RequestMapping("PostBlogFromDraft")
    public String PostBlogFromDraft(@CookieValue("account")String user_id,Model model,@RequestParam("blog_id")int blog_id){
        blogService.postBlogFromDraft(blog_id);
        model.addAttribute("DeleteBlog",blogService.getDeleteBlogByUser(Integer.parseInt(user_id)));
        return "Delete";
    }

    @RequestMapping("/LikeBlog")
    @ResponseBody
    public Integer LikeBlog(@RequestParam("blog_id")int blog_id, HttpSession session){
        Map map= (Map) redisService.HashGet("user");
        blogService.LikeBlog(blog_id,String.valueOf(map.get("user_id")));
        return blogService.getLikeNumber(blog_id);
    }
    @RequestMapping("/CollectionBlog")
    @ResponseBody
    public Integer CollectionBlog(@RequestParam("blog_id")int blog_id,HttpSession session){
        Map map= (Map) redisService.HashGet("user");
        blogService.CollectionBlog(blog_id, String.valueOf(map.get("user_id")));
        return blogService.getCollection(blog_id);
    }

//查询
    @RequestMapping("/GetAllBlog")
    public String GetAllBlog(Model model,HttpSession session){
        List<Blog> blog=blogService.getAllBlog();
        List<Map<String,Object>> blog_list=blogService.Conformity_blog(blog);
        model.addAttribute("blog",blog_list);
//        System.out.println(blog_list);
//        System.out.println("GetAllBlogSession"+session.getAttribute("user"));
        return "index";
    }

    @RequestMapping("/GetBlogByClassification")
    public void GetBlogByClassification(@RequestParam("classification")String classification,Model model){
        List<Blog> blog=blogService.getBlogByClassification(classification);
        model.addAttribute("blog",blog);
    }

    @RequestMapping("/GetBlogByLabel")
    public void GetBlogByLabel(@RequestParam("label")String label,Model model){
        List<Blog> blog=blogService.getBlogByLabel(label);
        model.addAttribute("blog",blog);
    }

    @RequestMapping("/GetBlogByID")
    public String GetBlogByID(@RequestParam("blog_id")int blog_id,@RequestParam("operation")String operation, Model model){
//        System.out.println(blog_id);
        Blog blog=blogService.getBlogByID(blog_id);
//        System.out.println(blog);
        List<Comment> comment=blogService.getComment(blog_id);
        List<Map<String,List>>replay_list=blogService.Conformity_comment(comment);
        model.addAttribute("blog_info",blog);
        model.addAttribute("blog_replay",replay_list);
        model.addAttribute("user_info",userService.getUserInfo(blog.getUser_id()));
        if (operation.equals("update"))
            return "PostDraftBlog";
        return "BrowseBlog";
    }

    @RequestMapping("/GetBlogByUser")
    public String GetBlogByUser(@RequestParam("user_id")int user_id,Model model){
        model.addAttribute("blog_user_info",blogService.getBlogByUser(user_id));
        return "test";
    }

    @RequestMapping("/GetDraftBlog")
    public String GetDraftBlog(@CookieValue("account")String user_id, /*@RequestParam("user_id")int user_id,*/ Model model){
//        System.out.println(user_id);
        model.addAttribute("DraftBlog",blogService.getDraftBlogByUser(Integer.parseInt(user_id)));
//        System.out.println(blogService.getDraftBlogByUser(Integer.parseInt(user_id)));
        return "Draft";
    }

    @RequestMapping("/GetDeleteBlog")
    public String GetDeleteBlog(@CookieValue("account")String user_id,/*@RequestParam("user_id")int user_id,*/Model model){

//        System.out.println(user_id);
        model.addAttribute("DeleteBlog",blogService.getDeleteBlogByUser(Integer.parseInt(user_id)));
//        System.out.println(blogService.getDeleteBlogByUser(Integer.parseInt(user_id)));
        return "Delete";
    }
}
