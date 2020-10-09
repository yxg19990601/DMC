package com.ems.springcloud.service;

import com.ems.springcloud.entities.Payment;

public interface PaymentService {
    public int insert(Payment record);
    public Payment selectByPrimaryKey(Long id);
}
