package com.ems.springcloud.dao;

import com.ems.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface PaymentDao {
    int deleteByPrimaryKey(Long id);

    int insert(Payment record);

    int insertSelective(Payment record);

    Payment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Payment record);

    int updateByPrimaryKey(Payment record);
}