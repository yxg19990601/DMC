package com.ems.szy.util;

import com.ems.szy.dao.DeviceMapper;
import com.ems.szy.dao.RecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DataSourceUtil {
    public static RecordMapper recordMapper;
    public static DeviceMapper deviceMapper;


    @Resource
    public void setDeviceMapper(DeviceMapper deviceMapper) {
        DataSourceUtil.deviceMapper = deviceMapper;
    }

    @Resource
    public void setRecordMapper(RecordMapper recordMapper) {
        DataSourceUtil.recordMapper = recordMapper;
    }

}
