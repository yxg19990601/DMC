package com.ems.szy.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * unit
 * @author 
 */
@Data
public class Unit implements Serializable {
    private Integer unitId;

    private String unitName;

    private static final long serialVersionUID = 1L;
}