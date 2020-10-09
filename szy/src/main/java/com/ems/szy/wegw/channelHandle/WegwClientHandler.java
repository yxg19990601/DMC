package com.ems.szy.wegw.channelHandle;

import com.ems.szy.dao.RecordMapper;
import com.ems.szy.wegw.client.WegwClient;
import com.ems.szy.wegw.entity.Dto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class WegwClientHandler extends SimpleChannelInboundHandler<Dto> {

    @Autowired
    private WegwClient client;


    @Resource
    private RecordMapper recordMapper;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("服务端断开:" + ctx.channel().remoteAddress());
        //TODO 断开后的重连接
        client.doConnect();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.ctx = ctx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Dto msg) throws Exception {
        if (msg != null) {
            if (msg.getCommon().getType().equals("record_arrive")) {
                int count = recordMapper.deleteTop();
                if (count != 0) {
                    client.sendMsg();
                }
            }
        }
        System.out.println(msg);
    }
}
