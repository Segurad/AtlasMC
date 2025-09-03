package de.atlasmc.io.netty.channel;

import de.atlasmc.io.ConnectionHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ErrorHandler extends ChannelInboundHandlerAdapter {

	private final String message;
	private final ConnectionHandler handler;
	
	public ErrorHandler(String message, ConnectionHandler handler) {
		this.message = message;
		this.handler = handler;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		handler.getLogger().error(message, cause);
	}
	
}
