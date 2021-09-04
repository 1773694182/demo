package com.example.demo.Service;

import org.apache.commons.collections4.MultiValuedMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    //搜索的关键词
    MultiValuedMap<String, Integer> GetSearchKeyWord(String str) throws IOException;
    //创建倒排索引
    Map<String,List> GetInvertedIndex() throws IOException;
    //得到包含匹配的关键词的博客
    Map<String,List> GetBlogList(String str) throws IOException;
}
