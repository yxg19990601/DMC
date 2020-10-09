package com.ems.szy.wegw.encode;

import com.ems.szy.wegw.entity.Dto;
import com.ems.szy.wegw.util.XmlCastUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class DtoToStringEncoder extends MessageToMessageEncoder<Dto> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Dto dto, List<Object> list) throws Exception {
        list.add(XmlCastUtil.convertToXml(dto));
    }


}
 