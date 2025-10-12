package de.atlasmc.core.node.io.protocol;

import de.atlasmc.core.node.io.protocol.status.CorePacketInPing;
import de.atlasmc.core.node.io.protocol.status.CorePacketInRequest;
import de.atlasmc.core.node.io.protocol.status.CorePacketOutPong;
import de.atlasmc.core.node.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.AbstractProtocol;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketServerbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketClientbound;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.ProtocolStatus;

public class CoreProtocolStatus extends AbstractProtocol<PacketServerbound, PacketClientbound> implements ProtocolStatus {
	
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
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createDefaultPacketListenerServerbound(Object o) {
		return new CorePacketListenerStatus((ConnectionHandler) o);
	}
	
	@Override
	public PacketListener createDefaultPacketListenerClientbound(Object o) {
		return null;
	}

}
