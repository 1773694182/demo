package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int blog_id;
    private int user_id;
    private String content;
    private String label;
    private String classification;
    private int collection_number;
    private int like_number;
    private int view_number;
    private int comment_number;
    private Timestamp date;
    private String title;
    private int state;
}
