package de.atlascore.io.protocol;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ProxyConnectionHandler;
import de.atlascore.io.protocol.status.CorePacketInPing;
import de.atlascore.io.protocol.status.CorePacketInRequest;
import de.atlascore.io.protocol.status.CorePacketOutPong;
import de.atlascore.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.protocol.ProtocolStatus;
import static de.atlasmc.io.protocol.status.PacketStatus.*;

public class CoreProtocolStatus extends CoreAbstractProtocol implements ProtocolStatus {

	private final List<PacketIO<? extends PacketInbound>> statusIn;
	private final List<PacketIO<? extends PacketOutbound>> statusOut;
	
	public CoreProtocolStatus() {
		statusIn = new ArrayList<>(PACKET_COUNT_IN);
		statusIn.add(IN_REQUEST, new CorePacketInRequest());
		statusIn.add(IN_PING, new CorePacketInPing());
		
		statusOut = new ArrayList<>(PACKET_COUNT_OUT);
		statusOut.add(OUT_RESPONSE, new CorePacketOutResponse());
		statusOut.add(OUT_PONG, new CorePacketOutPong());
	}

	@Override
	public PacketListener createDefaultPacketListener(Object o) {
		return new CorePacketListenerStatus((ProxyConnectionHandler) o);
	}

	@Override
	public PacketIO<?> getHandlerIn(int id) {
		if (id >= PACKET_COUNT_IN || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return statusIn.get(id);
	}

	@Override
	public PacketIO<?> getHandlerOut(int id) {
		if (id >= PACKET_COUNT_OUT || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return statusOut.get(id);
	}

}
