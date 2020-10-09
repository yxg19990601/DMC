package com.ems.springcloud;

import com.ems.springcloud.entities.Payment;
import com.ems.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest
{

    @Resource
    private PaymentService service;


    @Test
    public void shouldAnswerWithTrue()
    {

        for (int i = 0; i < 100000; i++) {
            int insert = service.insert(new Payment(null, "test插入"+i));
            log.info("影响的行数："+insert+" : "+i);
        }

    }
}
