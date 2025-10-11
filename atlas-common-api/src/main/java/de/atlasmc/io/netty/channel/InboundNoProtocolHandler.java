package de.atlasmc.io.netty.channel;

import de.atlasmc.io.Packet;
import de.atlasmc.io.ProtocolException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class InboundNoProtocolHandler extends ChannelInboundHandlerAdapter {
	
	public static final InboundNoProtocolHandler INSTANCE = new InboundNoProtocolHandler();
	
	protected InboundNoProtocolHandler() {
		// hide constructor
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Packet packet = null;
		if (msg instanceof Packet p) {
			packet = p;
		}
		throw new ProtocolException("No inbound protocol set unable to process packet!", null, packet);
	}

}
