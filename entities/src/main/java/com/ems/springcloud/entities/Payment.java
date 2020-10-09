package com.ems.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * payment
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 支付流水号
     */
        private String serial;

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        System.out.println("Payment.main");
    }
}