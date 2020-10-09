package com.ems.szy.dto;

import lombok.Data;

@Data
public class ResponseDto {

    public ResponseDto() {
    }

    public ResponseDto(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResponseDto(int code, String msg,Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }


    private int code;
    private Object data;
    private String message;



}
