package com.example.demo.Service;

import com.example.demo.Pojo.test;

import java.util.Random;


public class Thread1 implements Runnable{
    private test r;
    public Thread1(test r){
        this.r = r;
    }
    @Override
    public void run() {
        synchronized (r){
            Random ran = new Random();
            int i = ran.nextInt(r.arr1.length);
            System.out.println(Thread.currentThread().getName()+"中了"+r.arr1[i]+"元大奖");
            r.flag=0;
        }
    }
}

