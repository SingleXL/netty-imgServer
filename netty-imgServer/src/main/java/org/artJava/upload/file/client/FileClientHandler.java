package org.artJava.upload.file.client;

import org.artJava.upload.pojo.FileRequest;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;

/**
 * @author artJava
 * @date 2016年6月13日
 * @version 1.0
 */
public class FileClientHandler extends ChannelHandlerAdapter {

	private FileRequest fileRequest;

	public FileClientHandler(FileRequest fileRequest) {
		this.fileRequest = fileRequest;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(fileRequest).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
