package com.example.demo.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface SearchMapper {
    //搜索的关键词
    List<String> GetSearchKeyWord(String str);
    //创建倒排索引
    void CreateInvertedIndex(Map<String,Integer> map);
    //得到包含匹配的关键词的博客
    List<Integer> GetBlogList(List<String> str) throws IOException;
}
