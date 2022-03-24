package com.example;

import com.example.utils.SendMailUtils;
import org.junit.jupiter.api.Test;

/**
 * @author 唐孝顺
 * @date 2022/3/22 18:42
 */
public class EmailTest {
    @Test
    public void test1() throws Exception {
        SendMailUtils sendMailUtils = new SendMailUtils();
        sendMailUtils.Send("1298840110@qq.com","1234");

    }
}
