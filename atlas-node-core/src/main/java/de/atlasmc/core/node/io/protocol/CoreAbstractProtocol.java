package de.atlasmc.core.node.io.protocol;

import java.util.Arrays;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.Protocol;

public abstract class CoreAbstractProtocol<I extends PacketInbound, O extends PacketOutbound> implements Protocol {
	
	private final PacketIO<?>[] packetIn;
	private final PacketIO<?>[] packetOut;
	private final int COUNT_IN;
	private final int COUNT_OUT;
	
	protected CoreAbstractProtocol(PacketIO<? extends I>[] in, PacketIO<? extends O>[] out) {
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
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
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
			return null;
		@SuppressWarnings("unchecked")
		PacketIO<? extends I> packet = (PacketIO<? extends I>) packetIn[id];
		return packet;
	}

	public PacketIO<? extends O> getHandlerOut(int id) {
		if (id >= COUNT_OUT || id < 0)
			return null;
		@SuppressWarnings("unchecked")
		PacketIO<? extends O> packet = (PacketIO<? extends O>) packetOut[id];
		return packet;
	}

}
