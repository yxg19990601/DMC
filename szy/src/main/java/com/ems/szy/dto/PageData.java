package com.ems.szy.dto;

import lombok.Data;

import java.util.List;

/**
 * 分页对象
 */
@Data
public class PageData <T>{
    private Long total;
    private List<T> items;
}
