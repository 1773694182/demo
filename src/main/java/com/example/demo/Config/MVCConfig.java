package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/UserList").setViewName("UserList");
        registry.addViewController("/UserIndex").setViewName("UserIndex");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index2").setViewName("index2");
        registry.addViewController("/BrowseBlog").setViewName("BrowseBlog");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/UserPostBlog").setViewName("UserPostBlog");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/test2").setViewName("test2");
        registry.addViewController("/common").setViewName("common");
        registry.addViewController("/Draft").setViewName("Draft");
        registry.addViewController("/Delete").setViewName("Delete");
        registry.addViewController("/PostDraftBlog").setViewName("PostDraftBlog");
        registry.addViewController("/NoAuth").setViewName("NoAuth");
        registry.addViewController("/loginTest").setViewName("loginTest");
        registry.addViewController("/chat").setViewName("chat");

//        registry.addViewController("FilterHtml/Group").setViewName("FilterHtml/Group");

    }
}
