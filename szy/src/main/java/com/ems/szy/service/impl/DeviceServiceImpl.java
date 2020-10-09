package com.ems.szy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.szy.dao.DeviceMapper;
import com.ems.szy.domain.Device;
import com.ems.szy.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;
    @Override
    public int deleteByPrimaryKey(Integer deviceId) {
        return deviceMapper.deleteByPrimaryKey(deviceId);

    }

    @Override
    public int insert(Device record) {
        return deviceMapper.insert(record);
    }

    @Override
    public int insertSelective(Device record) {
        return deviceMapper.insertSelective(record);
    }

    @Override
    public Device selectByPrimaryKey(Integer deviceId) {
        return deviceMapper.selectByPrimaryKey(deviceId);
    }

    @Override
    public int updateByPrimaryKeySelective(Device record) {
        return deviceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Device record) {
        return deviceMapper.updateByPrimaryKey(record);
    }

    @Override
    public IPage<Device> selectList(IPage<Device> page, Wrapper<Device> wrapper) {
        return deviceMapper.selectList(page, wrapper);
    }
}
