package com.ems.szy.vo;

import lombok.Data;

@Data
public class DeviceVo {
    // 状态
    private Integer deviceId;
    private String unitName;
    private Integer status;
    private String name;
    private String ipAddr;
}
