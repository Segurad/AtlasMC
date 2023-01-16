package de.atlascore.io.protocol;

import java.io.IOException;

import de.atlasmc.io.PacketIO;
import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.Protocol;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractProtocol implements Protocol {
	
	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P readPacket(P packet, ByteBuf in, ConnectionHandler con) throws IOException {
		PacketIO<P> handler = null;
		if (packet instanceof PacketInbound)
			handler = (PacketIO<P>) getHandlerIn(packet.getID());
		else if (packet instanceof PacketOutbound)
			handler = (PacketIO<P>) getHandlerOut(packet.getID());
		else
			throw new IOException("Unable to read this packet! (Packet is not instance of PacketPlayIn or PacketPlayOut)");
		if (handler == null)
			throw new IOException("Unable to find handler for packet: " + packet.getClass());
		handler.read(packet, in, con);
		return packet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P writePacket(P packet, ByteBuf out, ConnectionHandler con) throws IOException {
		PacketIO<P> handler = null;
		if (packet instanceof PacketInbound)
			handler = (PacketIO<P>) getHandlerIn(packet.getID());
		else if (packet instanceof PacketOutbound)
			handler = (PacketIO<P>) getHandlerOut(packet.getID());
		else
			throw new IOException("Unable to write this packet! (Packet is not instance of PacketPlayIn or PacketPlayOut)");
		if (handler == null)
			throw new IOException("Unable to find handler for packet: " + packet.getClass());
		handler.write(packet, out, con);
		return packet;
	}
	
	@Override
	public Packet createPacketIn(int id) {
		return getHandlerIn(id).createPacketData();
	}

	@Override
	public Packet createPacketOut(int id) {
		return getHandlerOut(id).createPacketData();
	}
	
	public abstract PacketIO<?> getHandlerIn(int id);
	
	public abstract PacketIO<?> getHandlerOut(int id);

}
