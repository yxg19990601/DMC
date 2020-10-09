package com.ems.szy.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * record
 * @author 
 */
@Data
public class Record implements Serializable {
    private Integer recordId;

    private String buildingId;

    private String gatewayId;

    private String meterId;

    private String functionId;

    private Date time;

    private BigDecimal value;

    private static final long serialVersionUID = 1L;
}