package com.example.dao;

import com.example.pojo.Order;

import java.util.List;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:30
 */
public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
}
