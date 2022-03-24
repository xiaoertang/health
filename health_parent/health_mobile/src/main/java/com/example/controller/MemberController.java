package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.constant.MessageConstant;
import com.example.constant.RedisConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.pojo.Member;
import com.example.service.MemberService;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/24 14:22
 * 会员登录
 */
@RestController
@RequestMapping("/login")
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map map) throws Exception {
        //获得Email地址
        String email = (String) map.get("email");
        //获得验证码
        String validateCode = (String) map.get("validateCode");
        //从Reids中获取验证码
        String codeInRedis = jedisPool.getResource().get(email + RedisMessageConstant.SENDTYPE_LOGIN);
        //1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        if(validateCode != null && codeInRedis != null && validateCode.equals(codeInRedis)){
            //2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
            //验证码输入正确
            //判断当前是否为会员
            Member member = memberService.findByEmail(email);
            if(member == null){
                //当前不是会员，进行注册
                member = new Member();
                member.setEmail(email);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            //登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_email",email);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60*60*24*30);//有效期30天
            response.addCookie(cookie);
            //保存会员信息到Redis中
            String json = (String) JSON.toJSON(member.toString());
            jedisPool.getResource().setex(email,60*30,json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }else{
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

}
