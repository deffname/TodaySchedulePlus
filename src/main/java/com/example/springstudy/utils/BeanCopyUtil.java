package com.example.springstudy.utils;

import org.springframework.beans.BeanUtils;

public class BeanCopyUtil {

    /**
     * 一个简单的复制对象的方法
     * 其实就是对BeanUtils.copyProperties的一个简单封装。
     * @param src 要复制的对象
     * @param clazz 复制出来的类
     * @return 复制出来的对象
     * @param <V> 调用时不需要写这个泛型
     */
    public static <V> V copyBean(Object src,Class<V> clazz){
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(src, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
