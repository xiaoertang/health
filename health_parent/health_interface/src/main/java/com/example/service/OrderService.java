package com.example.service;



import com.example.entity.Result;

import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:12
 * 体验预约接口
 */
public interface OrderService {
    public Result order(Map map) throws Exception;
    public Map findById(Integer id) throws Exception;
}
