package com.ems.szy.wegw.encode;

import com.ems.szy.wegw.decode.StringToDtoDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class StringToByteBufEncode extends MessageToByteEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {

        if (StringToDtoDecoder.isLog) {
            System.out.println("调试发送出去的数据："+s);
        }
        byteBuf.writeBytes(s.getBytes(CharsetUtil.UTF_8));
        byteBuf.writeByte(0x00);
    }




} 
