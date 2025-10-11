package de.atlasmc.io.netty.channel;

import de.atlasmc.io.Packet;
import de.atlasmc.io.ProtocolException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class OutboundNoProtocolHandler extends ChannelOutboundHandlerAdapter {
	
	public static final OutboundNoProtocolHandler INSTANCE = new OutboundNoProtocolHandler();
	
	protected OutboundNoProtocolHandler() {
		// hide constructor
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		Packet packet = null;
		if (msg instanceof Packet p) {
			packet = p;
		}
		throw new ProtocolException("No outbound protocol set unable to process packet!", null, packet);
	}

}
