package de.atlasmc.io.netty.channel;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;
import de.atlasmc.io.connection.SocketConnectionHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Hands decoded packets to the {@link PacketListener} of the {@link ConnectionHandler} and writes all queued packets
 */
public class PacketProcessor extends ChannelInboundHandlerAdapter {
	
	private final SocketConnectionHandler handler;
	
	public PacketProcessor(SocketConnectionHandler handler) {
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
		handler.handlePacket(packet);
	}

}
