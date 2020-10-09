package com.ems.szy.protocol;


import com.ems.szy.protocol.constant.CmdType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 协议消息定义
 * @author yxg
 */
@Data
public class ProtocolMsg {
    private CmdType cmdType;
    private short length;
    private byte count;
    private List<ColData> resultData;
    private byte[] crcData;
    private int RF_ID;
    private byte dataType;
    private Date time;
    private short crcCode;
    private int errCode;
}
