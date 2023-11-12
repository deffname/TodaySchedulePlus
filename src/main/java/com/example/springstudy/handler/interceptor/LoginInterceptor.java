package com.example.springstudy.handler.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.example.springstudy.domain.ResponseResult;
import com.example.springstudy.domain.enums.AppHttpCodeEnum;
import com.example.springstudy.entity.User;
import com.example.springstudy.service.UserService;
import com.example.springstudy.utils.UserThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    /**
     * 请求处理前被调用。也就是调用Controller前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("token");

        if(StringUtils.isEmpty(token)){
            throw new Exception(String.valueOf(AppHttpCodeEnum.NEED_LOGIN));
        }
        User user = userService.checkToken(token);
        if(user==null){
            throw new Exception(String.valueOf(AppHttpCodeEnum.NEED_LOGIN));
        }
        UserThreadLocal.put(user);

        return true;
    }

    /**
     * 在preHandle,postHandle之后，响应请求之前执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
