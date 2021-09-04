package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Replay {
    private int replay_id;
    private int comment_id;
    private String content;
    private Timestamp date;
    private int user_id;
    private int state;
    private String user_name;
    private int to_user_id;
    private String to_user_name;
}
