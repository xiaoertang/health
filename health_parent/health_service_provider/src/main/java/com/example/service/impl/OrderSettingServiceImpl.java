package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.OrderSettingDao;
import com.example.pojo.OrderSetting;
import com.example.service.OrderSettingService;
import com.example.utils.DateUtils;
import com.example.utils.GetYearAndMonthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 唐孝顺
 * @date 2022/3/16 18:30
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
@SuppressWarnings({"all"})
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量添加
    @Override
    public void add(List<OrderSetting> list) throws Exception {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {

                //将Date格式的日期，置成“yyyy-MM--dd"格式与数据库中的日期进行比较
//                SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
//                String date = sDate.format(orderSetting.getOrderDate());
                String date = DateUtils.parseDate2String(orderSetting.getOrderDate());
                //检查此数据（日期）是否存在
                long count = orderSettingDao.findCountByOrderDate(date);
                if (count > 0) {
                    //将预约时间和预约人数添加到map，通过map进行更新数据
                    Integer number = orderSetting.getNumber();
                    Map map = new HashMap();
                    map.put("number", number);
                    map.put("date", date);
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(map);
                } else {
                    //不存在，执行添加
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {  //格式date:yyyy-mm-dd
        String year = date.substring(0, 4);  //获取年份
        String month = date.substring(date.lastIndexOf("-") + 1);//获取月份
        Integer days = GetYearAndMonthUtils.getMonth(year, month); //获取个月多少天
        String dateBegin = date + "-1";  //每月的1号
        String dateEnd = date + "-" + days;  //每月的最后一天
        Map map = new HashMap();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Object> m = new HashMap<>();
            m.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            m.put("number", orderSetting.getNumber());//预约人数
            m.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(m);
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws Exception {
//        Date orderDate = orderSetting.getOrderDate();
//        //将Date格式的日期，置成“yyyy-MM--dd"格式与数据库中的日期进行比较
//        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sDate.format(orderDate);
        String date = DateUtils.parseDate2String(orderSetting.getOrderDate());
        long count = orderSettingDao.findCountByOrderDate(date);
        if (count > 0) {
            //将预约时间和预约人数添加到map，通过map进行更新数据
            Integer number = orderSetting.getNumber();
            Map map = new HashMap();
            map.put("number", number);
            map.put("date", date);
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(map);
        } else {
            //当前日期没有设置预约人数，进行添加
            orderSettingDao.add(orderSetting);
        }
    }
}
