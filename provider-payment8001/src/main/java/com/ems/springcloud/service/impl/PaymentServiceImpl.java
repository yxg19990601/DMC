package com.ems.springcloud.service.impl;

import com.ems.springcloud.dao.PaymentDao;
import com.ems.springcloud.entities.Payment;
import com.ems.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int insert(Payment record) {
        return paymentDao.insert(record);
    }

    @Override
    public Payment selectByPrimaryKey(Long id) {
        return paymentDao.selectByPrimaryKey(id);
    }
}
