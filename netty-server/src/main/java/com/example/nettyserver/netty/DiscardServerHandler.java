package com.example.nettyserver.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

/**
 * @author shen_xi
 * @create 2020/10/13 8:48
 */
@Component
@ChannelHandler.Sharable
public class DiscardServerHandler extends ChannelHandlerAdapter {
    //    @Resource
//    private BaseService baseService;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        try {
            ByteBuf in = (ByteBuf) msg;
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            System.out.println("传输内容是");
            System.out.println(in.writerIndex());
            System.out.println(bytes);
            System.out.println(in.toString(CharsetUtil.UTF_8));
            ctx.writeAndFlush(Unpooled.copiedBuffer("123".getBytes()));
            //这里调用service服务
//            baseService.test();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭
        cause.printStackTrace();
        ctx.close();
    }
}
