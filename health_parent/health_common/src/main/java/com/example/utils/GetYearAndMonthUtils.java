package com.example.utils;

import java.time.Year;

/**
 * @author 唐孝顺
 * @date 2022/3/16 21:17
 * //获取每年、每月多少天
 */
public class GetYearAndMonthUtils {
    public static Boolean getYear(String year){
        //判断闰年
        Integer y = Integer.valueOf(year);
        if(y % 4 == 0 && y % 100 != 0 || y % 400 == 0){
            return true;
        }else{
            return false;
        }
    }

    public static Integer getMonth(String year,String month){
        Integer m = Integer.valueOf(month);//月份
        int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(getYear(year)){
            days[1] = 29;
        }
        return days[m-1];
    }
}