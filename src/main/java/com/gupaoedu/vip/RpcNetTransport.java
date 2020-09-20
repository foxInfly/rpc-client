package com.gupaoedu.vip;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 网络传输
 *
 * @author : lipu
 * @since : 2020-08-19 21:47
 */
public class RpcNetTransport extends SimpleChannelInboundHandler<Object> {

//    private String host;
//    private int port;
    private String serviceAddress;

    public RpcNetTransport(String serviceAddress) {
//        this.host = host;
//        this.port = port;
        this.serviceAddress = serviceAddress;
    }
    private final Object lock=new Object();
    private Object result;


    /**
     * 进行远程通信，调用另外一个地方的方法，并拿到返回结果。
//     * @param rpcRequest 被代理的类的对象信息
     * @return 代理后的对象
     */
    /*public Object send(RpcRequest rpcRequest) {
        Socket socket = null;
        Object result = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            socket = new Socket(host, port);//建立连接
            //写出
            outputStream = new ObjectOutputStream(socket.getOutputStream());//网络socket
            outputStream.writeObject(rpcRequest);//把对象序列化，准备传输
            outputStream.flush();


            //读入
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result=msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常：");
        ctx.close();
    }

    /**
     * 发送
     * @param request
     * @return
     */

    public Object send(RpcRequest request){
        NioEventLoopGroup eventLoogGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoogGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().
                        addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))).
                        addLast(new ObjectEncoder()).
                        addLast(RpcNetTransport.this);
            }
        }).option(ChannelOption.TCP_NODELAY,true);
        try {
            String urls[]=serviceAddress.split(":");
            ChannelFuture future=bootstrap.connect(urls[0],Integer.parseInt(urls[1])).sync();
            future.channel().writeAndFlush(request).sync();

            if(request!=null){
                future.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoogGroup.shutdownGracefully();
        }
        return result;
    }
}
