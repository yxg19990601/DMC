package com.ems.szy.wegw.decode;

import com.ems.szy.wegw.entity.Dto;
import com.ems.szy.wegw.util.XmlCastUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class StringToDtoDecoder extends MessageToMessageDecoder<String> {

	/// 是否开启调试模式
	public static boolean isLog = true;

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
		if (isLog) {
			System.out.println("调试接受到的数据："+s);
		}
		// 容错xml
		try {
			Dto dto =  XmlCastUtil.convertToBean(Dto.class,s.trim());
			list.add(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
 