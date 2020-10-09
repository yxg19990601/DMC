package com.ems.szy.config;

import com.ems.szy.frame.ByteToSzyProtocolDecoder;
import com.ems.szy.frame.SzyProtocolToByteEncoder;
import com.ems.szy.handle.SzyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.ByteOrder;

@Configuration
public class SzyServerConfig {

    Logger logger = LoggerFactory.getLogger(SzyServerConfig.class);
    @Value("${szy.server.port}")
    private int port;


    private Channel channel = null;
    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //拆包, 长度为小端模式，偏移量为2，长度字段为2字节，不增加数据，也不减少数据
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,2048,2,2,0,0,true));
                            ch.pipeline().addLast(new ByteToSzyProtocolDecoder());
                            ch.pipeline().addLast(new SzyProtocolToByteEncoder());
                            // 最终的数据处理
                            ch.pipeline().addLast(new SzyHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            logger.info("启动完成");
            this.channel = future.channel();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



    /**
     * 关闭项目的时候，销毁NettyServer
     */
    @PreDestroy
    public void destroy() {
        logger.info("SzyServerConfig.destroy");
        this.channel.close();
    }


    /**
     * 启动项目的时候，带起nettyServer
     */
    @PostConstruct
    public void startServer() {
        SzyServerConfig szyServerConfig = this;
        new Thread(){
            public void run() {
                try {
                    logger.info("SzyServerConfig.run");
                    szyServerConfig.bind(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



}
