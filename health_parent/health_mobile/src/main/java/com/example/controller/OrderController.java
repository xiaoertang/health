package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.constant.RedisMessageConstant;
import com.example.entity.Result;
import com.example.pojo.Order;
import com.example.service.OrderService;
import com.example.utils.SMSUtils;
import com.example.utils.SendMailUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.sql.ResultSet;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:11
 * 体验预约
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        //从map中获取邮箱地址
        String email = (String) map.get("email");
        //从缓存中取得验证码
        String codeInRedis = jedisPool.getResource().get(
                email + RedisMessageConstant.SENDTYPE_LOGIN);
        //从map中获取验证码
        String validateCode = (String) map.get("validateCode");
        //校验验证码
        if(codeInRedis == null || validateCode == null || !codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        //调用体验预约服务
        try{
            map.put("orderType", Order.ORDERTYPE_WEIXIN);//预约方式：微信
           result =  orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            //预约失败
            return result;
        }
        if(result.isFlag()){
            //预约成功
            String orderData = (String) map.get("orderData");
            try{

                //发送预约成功短信
                //SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderData);
                SendMailUtils.Send(email,email + " 用户预约成功！");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @PostMapping("/findById")
    public Result findById(Integer id){
        try{
           Map map = orderService.findById(id);
           return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
