package com.example.controller;

import com.example.constant.MessageConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.utils.SMSUtils;
import com.example.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.sql.ResultSet;

/**
 * @author 唐孝顺
 * @date 2022/3/22 13:31
 * 短信验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    //体检预约时发送手机验证码
    @PostMapping("/send4Order")
    public Result send4Order(String  telephone){
        //生成4位验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            //发送手机验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将手机验证码缓存到redis，保留5分钟，超过5分钟自动消失
        jedisPool.getResource().setex(
                telephone + RedisMessageConstant.SENDTYPE_ORDER,
                5*50,
                validateCode.toString()
        );
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
