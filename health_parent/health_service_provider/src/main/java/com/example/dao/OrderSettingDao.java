package com.example.dao;

import com.example.pojo.Order;
import com.example.pojo.OrderSetting;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/16 18:21
 */
public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editNumberByOrderDate(Map map);
    public long findCountByOrderDate(String orderDate);
    public List<OrderSetting> getOrderSettingByMonth(Map map);
//    public long findByOrderDate(Map map);
}
