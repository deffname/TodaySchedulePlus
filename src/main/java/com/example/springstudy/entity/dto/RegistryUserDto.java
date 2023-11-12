package com.example.springstudy.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;


/**
 * 接受用户注册信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryUserDto {
    private long uid;
    private String username;
    private String password;
    @JsonIgnore
    private String salt = UUID.randomUUID().toString().replaceAll("-","");
    private String phone;
    private String role;
    @JsonIgnore
    private Timestamp create_time;
    @JsonIgnore
    private Timestamp update_time;


}
