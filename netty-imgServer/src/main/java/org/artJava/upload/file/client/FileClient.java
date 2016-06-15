package org.artJava.upload.file.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.artJava.upload.codec.MarshallingCodeCFactory;
import org.artJava.upload.file.api.Client;
import org.artJava.upload.pojo.FileRequest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author artJava
 * @date 2016年6月13日
 * @version 1.0
 */
public class FileClient implements Client {

	public void connect(String host, int port, final FileRequest fileRequest) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					ch.pipeline().addLast(new FileClientHandler(fileRequest));
				}
			});

			// 发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();

			// 当代客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

	@Override
	public void sendFile(FileRequest fileRequest) {
		try {
			new FileClient().connect("127.0.0.1", 8888, fileRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		
		
		File file = new File("C:\\Users\\artJava\\Desktop\\netty-file.zip");
		byte[] content = FileUtils.readFileToByteArray(file);
		
		FileRequest fileRequest = new FileRequest();
		fileRequest.setFileName("test.zip");
		fileRequest.setFileContent(content);
		
		new FileClient().connect("127.0.0.1", 8088, fileRequest);
		
     }

}
