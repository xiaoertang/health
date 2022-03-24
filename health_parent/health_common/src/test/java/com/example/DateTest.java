package com.example;

import com.example.utils.DateUtils;
import org.apache.poi.ss.formula.functions.Now;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author 唐孝顺
 * @date 2022/3/16 23:01
 */
public class DateTest {
    @Test
    public void test(){

        Date date = new Date();
        date.setYear(2022);
        date.setMonth(3);
        date.setDate(12);
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sDate.format(date);
        System.out.println(date1);
       // System.out.println(date);
    }
    @Test
    public void test2() throws Exception {

    }

}
