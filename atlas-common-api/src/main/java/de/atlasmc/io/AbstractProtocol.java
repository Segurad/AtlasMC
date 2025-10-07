package de.atlasmc.io;

import java.util.Arrays;

public abstract class AbstractProtocol<I extends PacketInbound, O extends PacketOutbound> implements Protocol {
	
	private final PacketIO<?>[] packetIn;
	private final PacketIO<?>[] packetOut;
	private final int COUNT_IN;
	private final int COUNT_OUT;
	
	protected AbstractProtocol(PacketIO<? extends I>[] in, PacketIO<? extends O>[] out) {
		sort(in);
		sort(out);
		COUNT_IN = in.length;
		COUNT_OUT = out.length;
		packetIn = in.clone();
		packetOut = in.clone();
	}
	
	private void sort(PacketIO<?>[] ary) {
		Arrays.sort(ary, (a, b) -> {
			if (a.getPacketID() == b.getPacketID())
				throw new IllegalStateException("Packet id duplication \"" + a.getClass().getName() + "\" and \"" + b.getClass().getName() + "\"");
			return a.getPacketID() > b.getPacketID() ? 1 : -1;
		});
	}
	
	@Override
	public I createPacketIn(int id) {
		PacketIO<? extends I> handler = getHandlerIn(id);
		return handler != null ? handler.createPacketData() : null;
	}

	@Override
	public O createPacketOut(int id) {
		PacketIO<? extends O> handler = getHandlerOut(id);
		return handler != null ? handler.createPacketData() : null;
	}
	
	@Override
	public PacketIO<? extends I> getHandlerIn(int id) {
		if (id >= COUNT_IN || id < 0)
			return null;
		@SuppressWarnings("unchecked")
		PacketIO<? extends I> packet = (PacketIO<? extends I>) packetIn[id];
		return packet;
	}

	@Override
	public PacketIO<? extends O> getHandlerOut(int id) {
		if (id >= COUNT_OUT || id < 0)
			return null;
		@SuppressWarnings("unchecked")
		PacketIO<? extends O> packet = (PacketIO<? extends O>) packetOut[id];
		return packet;
	}

}
