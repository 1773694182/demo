package com.example.demo.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int user_id;
    private String name;
    private String pwd;
    private String role;
    private String email;
    private int state;
    private int sex;
    private Date birth;
    private String account;
}
