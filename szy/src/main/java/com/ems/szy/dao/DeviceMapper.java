package com.ems.szy.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.szy.domain.Device;
import org.apache.ibatis.annotations.Param;

public interface DeviceMapper  {
    int deleteByPrimaryKey(Integer deviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);


    Device selectByIp(String ip);

    IPage<Device>  selectList(IPage<Device> page, @Param("ew") Wrapper<Device> wrapper);
}