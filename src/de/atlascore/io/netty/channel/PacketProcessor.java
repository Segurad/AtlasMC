package de.atlascore.io.netty.channel;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Hands decoded packets to the {@link PacketListener} of the {@link ConnectionHandler} and writes all queued packets
 */
public class PacketProcessor extends ChannelInboundHandlerAdapter {
	
	private final ConnectionHandler handler;
	
	public PacketProcessor(ConnectionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		handler.writeQueuedPackets();
		ctx.fireChannelActive();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Packet packet = (Packet) msg;
		for (PacketListener listener : handler.getPacketListeners()) {
			listener.handlePacket(packet);
		}
	}

}
