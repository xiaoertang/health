package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dao.MemberDao;
import com.example.dao.OrderDao;
import com.example.pojo.Order;
import com.example.service.ReportService;
import com.example.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 唐孝顺
 * @date 2022/3/28 16:13
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 获得运营统计数据
     * Map数据格式：
     * todayNewMember -> number
     * totalMember -> number
     * thisWeekNewMember -> number
     * thisMonthNewMember -> number
     * todayOrderNumber -> number
     * todayVisitsNumber -> number
     * thisWeekOrderNumber -> number
     * thisWeekVisitsNumber -> number
     * thisMonthOrderNumber -> number
     * thisMonthVisitsNumber -> number
     * hotSetmeals -> List<Setmeal>
     */
    @Override
    public Map<String, Object> getBusinessReport() throws Exception {
        //获取当前日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获得本月第一天的日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //今日新增会员
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        //总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //今日到诊人数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周预约人数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //本周到诊人数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountByDate(thisWeekMonday);
        //本月预约人数
        Integer thisMonthOrderNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        //本月到诊人数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
        //热门套餐（取前4）
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        Map<String,Object> map = new HashMap<>();
        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetmeal",hotSetmeal);

        return map;
    }
}

