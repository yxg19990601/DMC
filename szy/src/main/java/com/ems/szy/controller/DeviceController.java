package com.ems.szy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.szy.domain.Device;
import com.ems.szy.dto.CmdObject;
import com.ems.szy.dto.PageData;
import com.ems.szy.dto.ResponseDto;
import com.ems.szy.handle.SzyHandler;
import com.ems.szy.protocol.ProtocolMsg;
import com.ems.szy.protocol.constant.CmdType;
import com.ems.szy.service.DeviceService;
import com.ems.szy.syn.SyncManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
///@CrossOrigin(origins = "*",maxAge = 3600)
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @PostMapping("/api/device/setDeviceTime")
    public Object setDeviceTime(@RequestBody CmdObject cmdObject) {
        ResponseDto responseMsg = new ResponseDto();
        responseMsg.setCode(500);
        responseMsg.setMessage("设置失败");
        ProtocolMsg msg = new ProtocolMsg();
        msg.setCmdType(CmdType.SET_DEVICE_TIME);
        msg.setTime(cmdObject.getDeviceTime());
        ProtocolMsg result = SyncManager.sendCmd(cmdObject.getIpAddr(), msg);

        if (result!=null) {
            if(result.getCmdType() == CmdType.SET_DEVICE_TIME) {
                responseMsg.setCode(20000);
                responseMsg.setMessage("设置成功!");
            }
        }
        return responseMsg;
    }


    @PostMapping("/api/device/readDeviceTime")
    public Object readDeviceTime(@RequestBody CmdObject cmdObject) {
        ResponseDto responseMsg = new ResponseDto();
        responseMsg.setCode(500);
        responseMsg.setMessage("读取失败");
        ProtocolMsg msg = new ProtocolMsg();
        msg.setCmdType(CmdType.READ_DEVICE_TIME);
        ProtocolMsg result = SyncManager.sendCmd(cmdObject.getIpAddr(), msg);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (result!=null) {
            if(result.getCmdType() == CmdType.READ_DEVICE_TIME) {
                responseMsg.setCode(20000);
                responseMsg.setMessage("读取成功!");
                responseMsg.setData(sdf.format(result.getTime()));
            }
        }
        return responseMsg;
    }


    @GetMapping("/getClients")
    public Set<String> getClients() {
        return SyncManager.getClientList().keySet();
    }


    @GetMapping("/api/deviceList")
    public ResponseDto deviceList(@RequestParam(value = "page") int page
            ,@RequestParam(value = "limit") int limit
            ,@RequestParam(value = "sort",required = true,defaultValue = "1") int sort
            ,@RequestParam(value = "deviceName",required = false) String deviceName
            ,@RequestParam(value = "unitId",required = false) String unitId) {

        // 条件查询
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.orderBy(true,sort==1,"device_id");
        deviceQueryWrapper.eq(unitId!=null,"unit.unit_id",unitId);
        deviceQueryWrapper.like(deviceName!=null,"device_name",deviceName);
        Page<Device> devicePage = new Page<>(page,limit);
        IPage<Device> deviceIPage = deviceService.selectList(devicePage,deviceQueryWrapper);

        // 设置在线状态
        List<Device> records = deviceIPage.getRecords();
        for (Device record : records) {
            SzyHandler client = SyncManager.getClient(record.getIpAddr());
            if (client != null){
                if (client.getCtx().channel().isActive()) {
                    record.setStatus(1);
                    continue;
                }
            }
            record.setStatus(0);
        }


        ResponseDto dto = new ResponseDto();
        PageData<Device> pageData = new PageData<>();
        pageData.setTotal(deviceIPage.getTotal());
        pageData.setItems(deviceIPage.getRecords());
        dto.setCode(20000);
        dto.setData(pageData);
        return dto;
    }

    @GetMapping("/vue-admin-template/table/list")
    public ResponseDto tableList() {
        ResponseDto dto = new ResponseDto();
        ArrayList<Map<String,Object>> list = new ArrayList<>();

        for (int i = 0;i <10;i++) {

        }
        dto.setCode(20000);
        dto.setData(list);
        return dto;
    }





}
