package de.atlasmc.io.netty.channel;

import de.atlasmc.log.Log;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundErrorHandler extends ChannelInboundHandlerAdapter {
	
	private final Log log;
	
	public InboundErrorHandler(Log log) {
		this.log = log;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("Error while handling inbound packet!", cause);
	}

}
