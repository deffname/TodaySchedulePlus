package com.example.springstudy.utils;

import com.example.springstudy.entity.User;

/**
 * 用于储存用户信息。具体为什么不清楚。。。
 *
 * LocalThread:
 * ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的，也就是说该变量是当前线程独有的变量。
 * ThreadLocal为变量在每个线程中都创建了一个副本,同一个 ThreadLocal 所包含的对象，在不同的 Thread 中有不同的副本。
 */
public class UserThreadLocal {

    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
