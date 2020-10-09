package com.ems.szy.wegw.client;

import com.ems.szy.dao.RecordMapper;
import com.ems.szy.domain.Record;
import com.ems.szy.wegw.channelHandle.WegwClientHandler;
import com.ems.szy.wegw.decode.StringToDtoDecoder;
import com.ems.szy.wegw.encode.DtoToStringEncoder;
import com.ems.szy.wegw.encode.StringToByteBufEncode;
import com.ems.szy.wegw.entity.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class WegwClient {
    private static Logger logger = LoggerFactory.getLogger(WegwClient.class);

    @Autowired
    private WegwClientHandler wegwClientHandler;

    @Resource
    private RecordMapper recordMapper;

    @Value("${ems.host}")
    private String ip;
    @Value("${ems.port}")
    private int port;
    public ChannelHandlerContext ctx;
    private ChannelFuture cf;
    private Channel channel;
    private Bootstrap bootstrap = new Bootstrap();
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    // 是否在线
    public boolean isActive() {
        if (ctx != null) {
            return ctx.channel().isActive();
        }
        return false;
    }

    public void sendMsg() {
        Record record = recordMapper.selectTop();
        if (record != null) {
            if (isActive()) {
                Dto dto = recordToDto(record);
                ctx.writeAndFlush(dto);
            }
        }

    }

    private Dto recordToDto(Record record) {
        Dto dto = new Dto();
        Common common = new Common();
        common.setBuildingId(record.getBuildingId());
        common.setGetewayId(record.getGatewayId());
        common.setType("report");
        dto.setCommon(common);
        Data data = new Data();
        data.setSequence("1");
        data.setParse("yes");
        data.setTime(record.getTime());
        data.setTotal(1);
        data.setCurrent(1);
        Meter meter = new Meter();
        meter.setId(record.getMeterId());
        meter.setConn("conn");
        Function function = new Function();
        function.setError("192");
        function.setSampleTime(new Date());
        function.setId(record.getFunctionId());
        function.setCoding("coding");
        function.setValue(record.getValue());
        ArrayList<Function> functions = new ArrayList<>();
        functions.add(function);
        meter.setList(functions);
        ArrayList<Meter> meters = new ArrayList<>();
        meters.add(meter);
        data.setMeters(meters);
        dto.setData(data);
        return  dto;
    }

    public void destroy() {
        channel.close();
        bossGroup.shutdownGracefully();
    }
    public void create() throws Exception{

        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //用于拆分xml 的解码器
                        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024*1024,false,
                                Unpooled.wrappedBuffer(new byte[] { '<','/','r','o','o','t','>' })));
                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new StringToDtoDecoder());
                        ch.pipeline().addLast(new StringToByteBufEncode());
                        ch.pipeline().addLast(new DtoToStringEncoder());
                        // 处理服务端的消息
                        ch.pipeline().addLast(wegwClientHandler);
                    }
                });

        try {

            // 客户端开启
            doConnect();
            // 等待直到连接中断
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //bossGroup.shutdownGracefully();
        }
    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        cf = bootstrap.connect(ip,port);
        logger.info("Wegw server start-port:{}  host:{}",ip,port);
        cf.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    logger.info("Connect to server successfully!");
                } else {
                    logger.info("Failed to connect to server, try connect after 10s");

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }
}
