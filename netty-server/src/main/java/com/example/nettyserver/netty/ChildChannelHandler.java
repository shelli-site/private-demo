package com.example.nettyserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author shen_xi
 * @create 2020/10/13 8:47
 */
@Component
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
    @Resource
    private DiscardServerHandler discardServerHandler;

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(discardServerHandler);
    }
}
