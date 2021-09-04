package com.example.demo;

import com.example.demo.Pojo.test;
import com.example.demo.Service.RedisService;
import com.example.demo.Service.Thread1;
import com.example.demo.Service.Thread2;
import com.example.demo.Service.Thread3;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class Demo1ApplicationTests {
    @Autowired
    private RedisService redisService;
    @Autowired
    JavaMailSenderImpl mailSender;
    private String emailServiceCode;
    @Test
    public void test(){
        for (int i=0;i<10;i++)
            redisService.SetSet("test",i);
//        emailServiceCode = "1234";
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("注册验证码");
//        message.setText("注册验证码是：" + emailServiceCode);
//        message.setFrom("1773694182@qq.com");
//        message.setTo("3512788259@qq.com");
//        mailSender.send(message);
    }
}
