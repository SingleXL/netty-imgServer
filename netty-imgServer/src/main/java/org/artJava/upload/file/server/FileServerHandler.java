package org.artJava.upload.file.server;

import java.io.FileOutputStream;
import java.util.concurrent.SynchronousQueue;

import org.artJava.upload.pojo.FileRequest;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
@Sharable
public class FileServerHandler extends ChannelHandlerAdapter {
	
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		FileRequest fileRequest = (FileRequest) msg;
		byte[] content = fileRequest.getFileContent();
		
        FileOutputStream out=new FileOutputStream("C:\\Users\\artJava\\Desktop\\temp\\" + fileRequest.getFileName());
        out.write(content);
        out.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();// 发生异常，关闭链路
	}
}
