package com.example.service;

import com.example.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/16 18:29
 */
public interface OrderSettingService {
    public void add(List<OrderSetting> list) throws Exception;
    public List<Map> getOrderSettingByMonth(String date);
    public void editNumberByDate(OrderSetting orderSetting) throws Exception;
}
