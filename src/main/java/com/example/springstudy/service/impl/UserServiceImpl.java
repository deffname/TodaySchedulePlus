package com.example.springstudy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.dto.LoginUserDto;
import com.example.springstudy.entity.dto.LoginUserResponseDto;
import com.example.springstudy.entity.dto.RegistryUserDto;
import com.example.springstudy.entity.User;
import com.example.springstudy.mapper.UserMapper;
import com.example.springstudy.service.UserService;
import com.example.springstudy.utils.BeanCopyUtil;
import com.example.springstudy.utils.JwtUtils;
import com.example.springstudy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private RedisCache redisCache;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisCache redisCache) {
        this.userMapper = userMapper;
        this.redisCache = redisCache;
    }

    //用户注册
    @Override
    public ResponseResult registryUser(RegistryUserDto registryUserDto) {

        String password = registryUserDto.getPassword();
        String salt = registryUserDto.getSalt();

        //对密码进行md5加密:原始密码+盐转换成md5.
        String md5Password = DigestUtils.md5DigestAsHex((password+registryUserDto.getSalt()).getBytes());

        registryUserDto.setPassword(md5Password);

        registryUserDto.setCreate_time(new Timestamp(System.currentTimeMillis()));
        registryUserDto.setUpdate_time(new Timestamp(System.currentTimeMillis()));

        //根据注册信息复制出来一个新的User
        User user = BeanCopyUtil.copyBean(registryUserDto,User.class);

        userMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<LoginUserResponseDto> login(LoginUserDto loginUserDto) {
        String usrName = loginUserDto.getUsername();
        String psw = loginUserDto.getPassword();

        //选择一个用户名相同的User
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,usrName);
        User user = userMapper.selectOne(queryWrapper);
        if(null == user) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }


        String md5psw = DigestUtils.md5DigestAsHex((psw+user.getSalt()).getBytes());
        if(!md5psw.equals(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }

        String token = JwtUtils.createToken(user.getUid());//创建一个token
        //将token插入redis,1天后过期
        redisCache.setCacheObject("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        return ResponseResult.okResult(new LoginUserResponseDto(token));
    }

    /**
     * 验证token的逻辑
     * @param token
     * @return
     */
    @Override
    public User checkToken(String token) {
        System.out.println("check token:"+token);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        Map<String,Object> map = JwtUtils.checkToken(token);
        if(map==null){
            return null;
        }
        String userJson =  redisCache.getCacheObject("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
