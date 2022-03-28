package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.constant.MessageConstant;
import com.example.entity.Result;
import com.example.service.MemberService;
import com.example.service.SetMealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * @author 唐孝顺
 * @date 2022/3/27 17:38
 * 统计
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetMealService setMealService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            //实例日历
            Calendar calendar = Calendar.getInstance();
            //获取当前月份之前的12个月
            calendar.add(Calendar.MONTH, -12);
            List<String> months = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                months.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
            }
            Map map = new HashMap();
            map.put("months", months);
            List<Integer> memberCount = memberService.findMemberByMonth(months);
            map.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try{
            //存放饼图数据
            Map<String, Object> date = new HashMap<>();
            //饼图名称
            List<String> setmealNames = new ArrayList<>();

            //占比数量
            List<Map<String, Object>> setmealCount = setMealService.findSetmealCount();
            date.put("setmealCount", setmealCount);
            for (Map<String, Object> map : setmealCount) {
                String name = (String) map.get("name");
                setmealNames.add(name);
            }
            date.put("setmealNames", setmealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, date);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
}