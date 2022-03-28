package com.example.dao;

import com.example.pojo.Order;
import com.example.service.VO.OrderInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:30
 */

public interface OrderDao {
    public void add(Order order);

    public List<Order> findByDate(String orderDate);

    //根据id查询预约信息，包括体检人信息、套餐信息
    @MapKey("id")
    public OrderInfo findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    @MapKey("")
    public List<Map> findHotSetmeal();

}
