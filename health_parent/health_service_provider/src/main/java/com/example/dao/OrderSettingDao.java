package com.example.dao;


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
    //更新可预约人数
    public void editNumberByOrderDate(Map map);
    public long findCountByOrderDate(String orderDate);
    //根据日期范围查询预约设置信息
    public List<OrderSetting> getOrderSettingByMonth(Map map);
    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(String orderDate);
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
