package com.example.jobs;

import com.example.constant.RedisConstant;
import com.example.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;


/**
 * @author 唐孝顺
 * @date 2022/3/15 19:10
 * 自定义Job,实现定时清理垃圾图片
 */
public class ClearImgJob {
     @Autowired
     private JedisPool jedisPool;

    public void clearImg(){
        //根据Redis中保存的两个set集合进行差值计算，获取垃圾图片名称
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set != null){
            for(String picName : set){
                //删除七牛云服务器中的垃圾图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //从Redis集合中删除图片名称
                jedisPool.getResource()
                        .srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("================");
            }
        }
    }
}
