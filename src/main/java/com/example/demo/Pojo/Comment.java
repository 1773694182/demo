package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int user_id;
    private int blog_id;
    private int comment_id;
    private String content;
    private Timestamp date;
    private int state;
    private String user_name;
}
