package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBlogInfo {
    private int blog_number;
    private int collection_number;
    private int view_number;
    private int like_number;
    private int comment_number;
}
