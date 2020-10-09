package com.ems.szy.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * device
 * @author 
 */
@Data
public class Device implements Serializable {
    private Integer deviceId;

    /**
     * 建筑ID
     */
    private String buildingId;

    /**
     * 网关ID
     */
    private String gatewayId;

    /**
     * 采集器名称
     */
    private String deviceName;

    /**
     * 单位ID
     */
    private Integer unitId;

    /**
     * ip地址
     */
    private String ipAddr;

    private String unitName;


    /**
     * 状态
     */
    private Integer status;
    private static final long serialVersionUID = 1L;
}