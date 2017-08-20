package com.example.netty.server.service;

import com.example.netty.server.handler.EchoServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * Created by jessie on 17/8/19.
 */
@Service
public class EchoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoService.class);
    private static final int port = 8080;

    public void start() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        // 通过nio方式来接收连接和处理连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // reactor 单线程模式
            b.group(group);
            // 通过nio方式来接收连接和处理连接
            b.channel(NioServerSocketChannel.class);
            // 绑定端口
            b.localAddress(new InetSocketAddress(port));
            //有连接到达时会创建一个channel
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // pipeline管理channel中的Handler，在channel队列中添加一个handler来处理业务
                    ch.pipeline().addLast("myHandler", new EchoServiceHandler());
                }
            });
            // 配置完成，绑定server，通过调用sync同步方法阻塞直到绑定成功
            ChannelFuture f = b.bind().sync();
            LOGGER.info("[{}] started and listen on [{}]", this.getClass().getSimpleName(), port);
            // 应用程序会一直等待，直到channel关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            group.shutdownGracefully().sync();
        }
    }
}
