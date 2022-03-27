package com.example.controller;

import com.example.constant.MessageConstant;
import com.example.entity.Result;
import org.aspectj.bridge.Message;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 唐孝顺
 * @date 2022/3/27 16:07
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //获取当前用户名
    @GetMapping("/getUsername")
    public Result getUserName(){
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
