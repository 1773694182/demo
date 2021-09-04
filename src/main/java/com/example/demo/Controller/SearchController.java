package com.example.demo.Controller;

import com.example.demo.Pojo.Blog;
import com.example.demo.Service.Impl.BlogServiceImpl;
import com.example.demo.Service.Impl.SearchServiceImpl;
import com.example.demo.Service.SearchService;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService;
    @Autowired
    private BlogServiceImpl blogService;
    @RequestMapping("/search")
    public String search(@RequestParam("KeyWord")String KeyWord, Model model, HttpServletResponse response) throws IOException {//已得到匹配分词的博客id和显示位置
        int blog_id=2;
//        searchService.Update(str,blog_id);
        Map<String, List> KeyMap=searchService.GetBlogList(KeyWord);
        List<String> WordList=KeyMap.get("FindWordList");
        List<String> BlogList=KeyMap.get("FindBlogList");
        List<String> PositionList=KeyMap.get("FindPositionList");
        List<String[]> ResultBolgList=new ArrayList<>();
        for (int i=0;i<BlogList.size();i++)
            ResultBolgList.add(BlogList.get(i).split(","));
        Map<String,Map> AllMap=new HashMap<>();
        Set set=new HashSet<>();
        for (int j=0;j<ResultBolgList.size();j++){
            List temp=new ArrayList();
            Map<String, String> map = new HashMap<>();
            for (int i=0;i<ResultBolgList.get(j).length;i++){
//                System.out.println(ResultBolgList.get(j)[i]);
//                System.out.println(PositionList.get(j).split(",")[i]);
                set.add(ResultBolgList.get(j)[i]);
                temp.add(PositionList.get(j).split(",")[i]);
                map.put(ResultBolgList.get(j)[i],PositionList.get(j).split(",")[i].replace("[","").replace("]",""));
            }
            AllMap.put(WordList.get(j),map);
//            System.out.println(map.get("1"));
//            System.out.println("Wordlist"+WordList.get(j));
//            System.out.println("temp:"+temp);
//            System.out.println("map:"+map);
        }
        JSONObject jsonObject = JSONObject.fromObject(AllMap);
//        System.out.println(AllMap);
//        System.out.println(KeyMap);
//        System.out.println(jsonObject);
        List<Blog> Blog=new ArrayList<>();
        Iterator iterator=set.iterator();
        while (iterator.hasNext()){
            Blog.add(blogService.getBlogByID(Integer.parseInt(iterator.next().toString())));
        }
        model.addAttribute("blog",Blog);
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<input value='"+jsonObject+"' id='show' hidden='hidden'>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "test2";
    }
}
