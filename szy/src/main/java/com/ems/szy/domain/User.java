package com.ems.szy.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String userId;

    private String pwd;

    private String userName;

    private Date createDate;

    private static final long serialVersionUID = 1L;
}