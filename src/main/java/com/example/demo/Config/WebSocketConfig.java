package com.example.demo.Config;

import com.example.demo.Service.GroupService;
import com.example.demo.Service.WebSocketService;
import com.example.demo.Service.WebSocketTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.net.http.WebSocket;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Autowired
    public void GetMessage(GroupService groupService){
        WebSocketService.groupService=groupService;
        WebSocketTestService.groupService=groupService;
    }

}
