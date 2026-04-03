package de.atlasmc.io;

import java.util.Arrays;

public abstract class AbstractProtocol<I extends PacketServerbound, O extends PacketClientbound> implements Protocol {
	
	private final PacketCodec<?>[] packetIn;
	private final PacketCodec<?>[] packetOut;
	private final int COUNT_IN;
	private final int COUNT_OUT;
	
	protected AbstractProtocol(PacketCodec<? extends I>[] in, PacketCodec<? extends O>[] out) {
		sort(in);
		sort(out);
		COUNT_IN = in.length;
		COUNT_OUT = out.length;
		packetIn = in.clone();
		packetOut = out.clone();
	}
	
	private void sort(PacketCodec<?>[] ary) {
		Arrays.sort(ary, (a, b) -> {
			if (a.getPacketID() == b.getPacketID())
				throw new IllegalStateException("Packet id duplication \"" + a.getClass().getName() + "\" and \"" + b.getClass().getName() + "\"");
			return a.getPacketID() > b.getPacketID() ? 1 : -1;
		});
	}
	
	@Override
	public PacketCodec<? extends I> getCodecServerbound(int id) {
		if (id >= COUNT_IN || id < 0)
			return null;
		@SuppressWarnings("unchecked")
		PacketCodec<? extends I> packet = (PacketCodec<? extends I>) packetIn[id];
		return packet;
	}

	@Override
	public PacketCodec<? extends O> getCodecClientbound(int id) {
		if (id >= COUNT_OUT || id < 0)
			return null;
		@SuppressWarnings("unchecked")
		PacketCodec<? extends O> packet = (PacketCodec<? extends O>) packetOut[id];
		return packet;
	}
	
	@Override
	public PacketCodec<? extends Packet> getCodecClientboundByDefault(int id) {
		return getCodecClientbound(id);
	}
	
	@Override
	public PacketCodec<? extends Packet> getCodecServerboundByDefault(int id) {
		return getCodecServerbound(id);
	}

}
