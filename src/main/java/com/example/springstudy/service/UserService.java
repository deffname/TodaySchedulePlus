package com.example.springstudy.service;

import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.entity.dto.LoginUserDto;
import com.example.springstudy.entity.dto.LoginUserResponseDto;
import com.example.springstudy.entity.dto.RegistryUserDto;
import com.example.springstudy.entity.User;

public interface UserService {
    ResponseResult registryUser(RegistryUserDto registryUserDto);
    ResponseResult<LoginUserResponseDto> login(LoginUserDto loginUserDto);
    User checkToken(String token);

}
