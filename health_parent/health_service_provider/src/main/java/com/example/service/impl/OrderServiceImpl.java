package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.constant.MessageConstant;
import com.example.dao.MemberDao;
import com.example.dao.OrderDao;
import com.example.dao.OrderSettingDao;
import com.example.dao.SetMealDao;
import com.example.entity.Result;
import com.example.pojo.Member;
import com.example.pojo.Order;
import com.example.pojo.OrderSetting;
import com.example.service.OrderService;
import com.example.service.VO.OrderInfo;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/22 14:27
 * 体检预约服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;


    @Override
    public Result order(Map map) throws Exception {
//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber(); //可预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if (number <= reservations) {
            //预约人数已满，无法预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String email = (String) map.get("email");
        Member member = memberDao.findByEmail(email);

        //防止重复预约
        if (member != null) {
            //Integer memberId = member.getId();
            //Integer setmealId = Integer.parseInt((String) map.get("setmealId"));

            //Order order = new Order(memberId, orderDate, null, null, setmealId);
            List<Order> orderList = orderDao.findByDate(orderDate);
            if (orderList != null && orderList.size() > 0) {
                //已经预预约成功，无法重新预约
                return new Result(false, MessageConstant.HAS_ORDERED);
            }

        }
        //可以预约，设置可预约人数加一
        //orderSetting.setReservations(orderSetting.getReservations() + 1);
        //orderSettingDao.editReservationsByOrderDate(orderSetting);
        //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        if (member == null) {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setEmail(email);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            //SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            //String date = sdf.format(new Date());
            member.setRegTime(DateUtils.parseString2Date(orderDate));
            memberDao.add(member);
        }
//        5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception {
        OrderInfo orderInfo = orderDao.findById4Detail(id);
        Map map = new HashMap();;
        if(orderInfo != null){
            map.put("member",orderInfo.getMember());
            map.put("setmeal",orderInfo.getSetmeal());
            map.put("orderDate",orderInfo.getOrderDate());
            map.put("orderType",orderInfo.getOrderType());
        }
//        Map map = orderDao.findById4Detail(id);
////        if(map != null){
////            //处理日期格式
////            Date orderDate = (Date) map.get("orderDate");
////            map.put("orderDate",DateUtils.parseDate2String(orderDate));
////        }
        return map;
    }
}
