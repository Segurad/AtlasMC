package de.atlascore.io.protocol;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.Protocol;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractProtocol<I extends PacketInbound, O extends PacketOutbound> implements Protocol {
	
	private final List<PacketIO<? extends I>> packetIn;
	private final List<PacketIO<? extends O>> packetOut;
	private final int COUNT_IN;
	private final int COUNT_OUT;
	
	protected CoreAbstractProtocol(PacketIO<? extends I>[] in, PacketIO<? extends O>[] out) {
		sort(in);
		sort(out);
		COUNT_IN = in.length;
		COUNT_OUT = out.length;
		packetIn = List.of(in);
		packetOut = List.of(out);
	}
	
	private void sort(PacketIO<?>[] ary) {
		Arrays.sort(ary, (a, b) -> {
			if (a.getPacketID() == b.getPacketID())
				throw new IllegalStateException("Packet id duplication \"" + a.getClass().getName() + "\" and \"" + b.getClass().getName() + "\"");
			return a.getPacketID() > b.getPacketID() ? 1 : -1;
		});
	}
	
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
	
	public PacketIO<? extends I> getHandlerIn(int id) {
		if (id >= COUNT_IN || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return packetIn.get(id);
	}

	public PacketIO<? extends O> getHandlerOut(int id) {
		if (id >= COUNT_OUT || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return packetOut.get(id);
	}

}
