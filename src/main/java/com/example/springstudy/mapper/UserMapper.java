package com.example.springstudy.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springstudy.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * mybatis-plus的mapper...
 * 里面不用写东西
 */
@TableName("user")
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
