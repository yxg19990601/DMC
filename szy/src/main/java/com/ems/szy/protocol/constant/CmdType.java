package com.ems.szy.protocol.constant;

/**
 * 命令类型
 */
public enum CmdType {

    //错误代码
    ERR_CODE((byte) 0x00),
    //数据返回
    DATA_RESULT((byte) 0x10),
    //数据重发
    DATA_RESEND((byte) 0x11),
    //数据确认
    DATA_RESULT_ACK((byte) 0x12),
    //设置采集器IP，或者确认
    SET_DEVICE_IP((byte) 0x21),
    //设置采集器MAC，或者确认
    SET_DEVICE_MAC((byte) 0x22),
    //读取采集器时间，或响应
    READ_DEVICE_TIME((byte) 0x30),
    //设置采集器时间，或响应
    SET_DEVICE_TIME((byte) 0x31);


    //指令值
    final byte value;
    CmdType(byte value){
        this.value = value;
    }

    public byte getValue(){
        return this.value;
    }

    // 依据value 获取命令类型
    public static CmdType getType(byte value){
        CmdType[] values = CmdType.values();
        for (CmdType cmdType : values) {
            if (cmdType.value == value) {
                return cmdType;
            }
        }
        return null;
    }
}
