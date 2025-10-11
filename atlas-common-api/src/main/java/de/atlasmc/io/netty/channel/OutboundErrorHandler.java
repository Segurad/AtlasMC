package de.atlasmc.io.netty.channel;

import de.atlasmc.log.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class OutboundErrorHandler extends ChannelOutboundHandlerAdapter {

	private final Log log;
	
	public OutboundErrorHandler(Log log) {
		this.log = log;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("Error while handling outbound packet!", cause);
	}
	
}
