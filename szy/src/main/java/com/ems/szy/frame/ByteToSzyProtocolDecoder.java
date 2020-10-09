package com.ems.szy.frame;

import com.ems.szy.protocol.ColData;
import com.ems.szy.protocol.ProtocolMsg;
import com.ems.szy.protocol.constant.CmdType;
import com.ems.szy.protocol.constant.ProtocolStructure;
import com.ems.szy.util.ByteArrayConveter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ByteToSzyProtocolDecoder extends ByteToMessageDecoder {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

    protected  void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }


    private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        byte stx = in.readByte();
        if (stx != ProtocolStructure.STX) {
            System.out.println("非法协议头");
            in.clear();
            ctx.close();
            return null;
        }

        in.markReaderIndex(); //标记需要CRC校验的数据部分

        // 读取指令
        byte cmd = in.readByte();
        CmdType type = CmdType.getType(cmd);
        //读取数据长度
        short length = ByteBufUtil.swapShort(in.readShort());

        //验证是否支持该指令
        if (type == null) {
            System.out.println("非法，或不支持的指令类型");
            in.clear();
            ctx.close();
            return null;
        }


        ProtocolMsg protocolMsg = new ProtocolMsg();
        // 判断指令做出相应的解析
        switch (type) {
            case DATA_RESULT :
                handlerResultData(in, protocolMsg);
                break;
            case DATA_RESEND :
                System.out.println(-1);
                break;
            case SET_DEVICE_IP :
                System.out.println(0);
                break;
            case SET_DEVICE_MAC :
                System.out.println(1);
                break;
            case READ_DEVICE_TIME :
                handlerReadDeviceTime(in,protocolMsg);
                System.out.println(3);
                break;
            case SET_DEVICE_TIME :
                System.out.println(4);
                break;
        }


        // 设置crc校验数据以及结果
        byte[] crcEncodeData = new byte[length];
        in.resetReaderIndex();
        in.readBytes(crcEncodeData);

        // crc校验结果
        short crc = in.readShort();

        //校验协议尾部
        byte ext = in.readByte();
        if ( ext != ProtocolStructure.EXT) {
            System.out.println("非法的结束符");
            in.clear();
            ctx.close();
            return null;
        }



        protocolMsg.setLength(length);
        protocolMsg.setCmdType(type);
        protocolMsg.setCrcData(crcEncodeData);
        protocolMsg.setCrcCode(crc);
        return protocolMsg;

    }


    private void handlerReadDeviceTime(ByteBuf in, ProtocolMsg protocolMsg) {
        byte[] deviceTimeByte = new byte[6];
        in.readBytes(deviceTimeByte);
        Date date = ByteArrayConveter.byteToDateM(deviceTimeByte);
        protocolMsg.setTime(date);
    }

    private void handlerResultData(ByteBuf in, ProtocolMsg protocolMsg) {
        // 读取数据个数
        byte count = in.readByte();
        int intCount = count & 0xFF;

        // 封装数据
        ArrayList<ColData> resultData = new ArrayList<>();
        for (int i = 1; i <= intCount; i++) {
            byte[] dateByte = new byte[6];
            in.readBytes(dateByte);
            Date colDate = ByteArrayConveter.byteToDateM(dateByte);
            int value = ByteBufUtil.swapShort(in.readShort()) & 0xFFFF;
            int simulationVoltage = in.readShort() & 0xFFFF;
            int batteryVoltage = in.readShort() & 0xFFFF;
            resultData.add(new ColData(colDate,value,simulationVoltage,batteryVoltage));
        }
        protocolMsg.setResultData(resultData);

        // 读取设备ID
        int RF_ID = in.readShort() & 0xFFFF;
        protocolMsg.setRF_ID(RF_ID);

        // 读取数据类型
        byte dataType = in.readByte();
        protocolMsg.setDataType(dataType);
    }
}
