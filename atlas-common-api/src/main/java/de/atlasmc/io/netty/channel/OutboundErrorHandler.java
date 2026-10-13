package de.atlasmc.io.netty.channel;

import java.util.Objects;

import de.atlasmc.io.connection.SocketConnectionHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * Channel handler for handling errors
 */
public class OutboundErrorHandler extends ChannelOutboundHandlerAdapter {

	private final SocketConnectionHandler handler;
	
	public OutboundErrorHandler(SocketConnectionHandler handler) {
		this.handler = Objects.requireNonNull(handler, "handler");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		handler.handleException(cause);
	}
	
}
