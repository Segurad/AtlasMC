package de.atlasmc.core.node.io.protocol;

import de.atlasmc.core.node.io.protocol.status.CorePacketInPing;
import de.atlasmc.core.node.io.protocol.status.CorePacketInRequest;
import de.atlasmc.core.node.io.protocol.status.CorePacketOutPong;
import de.atlasmc.core.node.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.ProtocolStatus;

public class CoreProtocolStatus extends CoreAbstractProtocol<PacketInbound, PacketOutbound> implements ProtocolStatus {
	
	@SuppressWarnings("unchecked")
	public CoreProtocolStatus() {
		super(new PacketIO[] {
			new CorePacketInRequest(),
			new CorePacketInPing()
		}, new PacketIO[] {
			new CorePacketOutResponse(),
			new CorePacketOutPong()
		});
	}

	@Override
	public PacketListener createDefaultPacketListenerIn(Object o) {
		return new CorePacketListenerStatus((ConnectionHandler) o);
	}
	
	@Override
	public PacketListener createDefaultPacketListenerOut(Object o) {
		return null;
	}

}
