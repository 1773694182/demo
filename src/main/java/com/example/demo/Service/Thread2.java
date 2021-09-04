package com.example.demo.Service;

import com.example.demo.Pojo.test;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Thread2 implements Runnable{
    private test r;
    public Thread2(test r){
        this.r = r;
    }

    @Override
    public void run() {
        synchronized (r){
            Random ran = new Random();
            int i = ran.nextInt(r.arr2.length);
            System.out.println(Thread.currentThread().getName()+"中了"+r.arr2[i]+"元大奖");
            r.flag=1;
        }
    }
}
