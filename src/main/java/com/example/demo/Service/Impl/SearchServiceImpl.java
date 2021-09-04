package com.example.demo.Service.Impl;

import com.example.demo.Mapper.SearchMapper;
import com.example.demo.Service.SearchService;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;


@Service
@Transactional
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchMapper searchMapper;
    @Override
    public MultiValuedMap<String, Integer> GetSearchKeyWord(String str) throws IOException {//对输入的字符串进行分词
        StringReader sr = new StringReader(str);
        IKSegmenter ik = new IKSegmenter(sr, true);
        Lexeme lex = null;
        MultiValuedMap<String, Integer> KeyMap = new ArrayListValuedHashMap<>();
        while(true){
            try {
                if (!((lex = ik.next()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            KeyMap.put(lex.getLexemeText(),lex.getBeginPosition());
        }

        return KeyMap;
    }
    //更新倒排索引
    public void UpdateInvertedIndex(Map<String,List> InvertedIndexMap,MultiValuedMap<String, Integer> KeyMap,int blog_id) throws IOException {
        List<String> IDList=InvertedIndexMap.get("IDList");
        List<String> WordList=InvertedIndexMap.get("WordList");
        List<String> BlogList=InvertedIndexMap.get("BlogList");
        List<String> PositionList=InvertedIndexMap.get("PositionList");
        for(String key:KeyMap.keySet()){
            boolean flag=false;
            for (int i=0;i<WordList.size();i++){
                if (key.equals(WordList.get(i))){
                    flag=true;
                    BlogList.set(i,BlogList.get(i)+","+blog_id);
                    PositionList.set(i,PositionList.get(i)+","+KeyMap.get(key).toString().replace(",",";"));
                    break;
                }
            }
            if(flag==false){
                IDList.add(String.valueOf(WordList.size()));
                WordList.add(key);
                BlogList.add(String.valueOf(blog_id));
                PositionList.add(KeyMap.get(key).toString().replace(",",";"));
            }
        }
        File file=new File("D:\\博客项目\\demo1\\src\\main\\resources\\InvertedIndex.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (int i=0;i<IDList.size();i++){
            out.write(IDList.get(i)+"   "+WordList.get(i)+"   "+BlogList.get(i)+"   "+PositionList.get(i)+"\r\n");
        }
        out.close();
    }
    @Override
    public Map<String,List> GetInvertedIndex() throws IOException {//获取倒排索引文件
        List<String> list=new ArrayList<>();
        String encoding="UTF-8";
        File file=new File("D:\\博客项目\\demo1\\src\\main\\resources\\InvertedIndex.txt");
        if(file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                list.add(lineTxt);
            }
            read.close();
        }else {
            System.out.println("该文件不存在");
        }
        List<String> IDList=new ArrayList<>();
        List<String> WordList=new ArrayList<>();
        List<String> BlogList=new ArrayList<>();
        List<String> PositionList=new ArrayList<>();
        Map<String,List> map=new HashMap<>();
        for (int i=0;i<list.size();i++){
            String str=list.get(i);
            String[] str1=str.split("   ");//0:编号，1:Word,2:Blog_ID,3:Position
            IDList.add(str1[0]);
            WordList.add(str1[1]);
            BlogList.add(str1[2]);
            PositionList.add(str1[3]);
        }
        map.put("IDList",IDList);
        map.put("WordList",WordList);
        map.put("BlogList",BlogList);
        map.put("PositionList",PositionList);
        return map;
    }

    public void Update(String str ,int blog_id) throws IOException{
        UpdateInvertedIndex(GetInvertedIndex(),GetSearchKeyWord(str),blog_id);

    }
    @Override
    public Map<String,List> GetBlogList(String str) throws IOException {
        MultiValuedMap<String, Integer> KeyMap=GetSearchKeyWord(str);
        Map<String,List> map=GetInvertedIndex();
        List<String> WordList=map.get("WordList");
        List<String> BlogList=map.get("BlogList");
        List<String> PositionList=map.get("PositionList");
        List<String> FindWordList=new ArrayList<>();
        List<String> FindBlogList=new ArrayList<>();
        List<String> FindPositionList=new ArrayList<>();
        for(String key:KeyMap.keySet()) {
            for (int i=0;i< WordList.size();i++){
//                System.out.println(KeyMap.get(key)+"    "+key+"   "+WordList.get(i));
                if (WordList.get(i).equals(key)){
                    FindWordList.add(WordList.get(i));
                    FindBlogList.add(BlogList.get(i));
                    FindPositionList.add(PositionList.get(i));
                }
            }
        }
        Map<String,List> ResultMap=new HashMap<>();
        ResultMap.put("FindWordList",FindWordList);
        ResultMap.put("FindBlogList",FindBlogList);
        ResultMap.put("FindPositionList",FindPositionList);
        return ResultMap;
    }
}
