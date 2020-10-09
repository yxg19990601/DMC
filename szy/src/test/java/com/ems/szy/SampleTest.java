package com.ems.szy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.szy.dao.DeviceMapper;
import com.ems.szy.dao.RecordMapper;
import com.ems.szy.dao.UserMapper;
import com.ems.szy.domain.Device;
import com.ems.szy.domain.Record;
import com.ems.szy.util.DataSourceUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {


    @Resource
    private UserMapper userMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private RecordMapper recordMapper;

    @Test
    public void test5() {
        System.out.println(recordMapper.deleteTop());
    }

    @Test
    public void test4() {
        System.out.println(recordMapper.selectTop());
    }
    @Test
    public void test3() throws Exception {
        for (int i = 0; i < 2000; i++) {
            Record record = new Record();
            record.setBuildingId("G0011");
            record.setFunctionId("5");
            record.setGatewayId("04");
            record.setTime(new Date());
            record.setValue(new BigDecimal(i));
            record.setMeterId("1");
            System.out.println(recordMapper.insert(record));

        }

    }



    @Test
    public void test2() {

        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.gt("device_id",2);
        Page<Device> devicePage = new Page<>(2,1);
        deviceMapper.selectList(devicePage,deviceQueryWrapper).getRecords().forEach(v -> System.out.println("v = " + v));
    }

    @Test
    public void test6() {
        System.out.println(DataSourceUtil.deviceMapper.selectByIp("127.0.0.1"));
    }
}
