package com.ems.szy.frame;

import com.ems.szy.protocol.ProtocolMsg;
import com.ems.szy.protocol.constant.CmdType;
import com.ems.szy.protocol.constant.ProtocolStructure;
import com.ems.szy.util.ByteArrayConveter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;

public class SzyProtocolToByteEncoder extends MessageToByteEncoder<ProtocolMsg> {



    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolMsg msg, ByteBuf out)
            throws Exception {
        // 对于超过指定长度的消息，这里直接抛出异常
        if (msg == null || msg.getCmdType() == null) {
            throw new UnsupportedMessageTypeException("data err ...");
        }

        byte[] resultData = encode(msg);
        if (resultData != null) {
            ctx.writeAndFlush(Unpooled.wrappedBuffer(resultData));
        }
    }

    private byte[] encode(ProtocolMsg msg) {
        if (msg.getCmdType() == CmdType.DATA_RESULT_ACK || msg.getCmdType() == CmdType.DATA_RESEND || msg.getCmdType() == CmdType.ERR_CODE) {
            byte[] result = encodeCommonlyMsg(msg);
            return result;
        } else if (msg.getCmdType() == CmdType.SET_DEVICE_TIME) {
            byte[] result = encodeSetDeviceTimeMsg(msg);
            return result;
        } else if (msg.getCmdType() == CmdType.READ_DEVICE_TIME) {
            byte[] result = encodeCommonlyMsg(msg);
            return result;
        }
        return null;
    }



    private byte[] encodeSetDeviceTimeMsg(ProtocolMsg msg) {
        byte[] result = new byte[13];
        result[0] = ProtocolStructure.STX;
        result[1] = msg.getCmdType().getValue();
        result[2] = 0x09;
        result[3] = 0x00;
        byte[] deviceTime = ByteArrayConveter.dateToByteM(msg.getTime());
        System.arraycopy(deviceTime,0,result,4,6);
        short crcCode = ByteArrayConveter.crc16_xmodem(result,1,9);
        result[10] = (byte)(crcCode >> 8 & 0xFF);
        result[11] = (byte)(crcCode & 0xFF);
        result[12] = ProtocolStructure.EXT;
        return result;
    }

    private byte[] encodeCommonlyMsg(ProtocolMsg msg) {
        byte[] result = new byte[7];
        result[0] = ProtocolStructure.STX;
        result[1] = msg.getCmdType().getValue();
        result[2] = 0x03;
        result[3] = 0x00;
        short crcCode = ByteArrayConveter.crc16_xmodem(result,1,3);
        result[4] = (byte)(crcCode >> 8 & 0xFF);
        result[5] = (byte)(crcCode & 0xFF);
        result[6] = ProtocolStructure.EXT;
        return result;
    }

}
