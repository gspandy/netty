package com.example.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jessie on 17/8/19.
 */
@ChannelHandler.Sharable
public class EchoClientServiceHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoClientServiceHandler.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当从Channel中读数据时被调用
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte [] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);

        String message = new String(req,"UTF-8");
        LOGGER.debug("client receive server response: {}", message);
    }

    /**
     * 当Channel变成活跃状态时被调用；Channel是连接/绑定、就绪的
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date();

        LOGGER.debug("client 连接到 server");
        ctx.writeAndFlush(Unpooled.copiedBuffer(sdf.format(date), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
