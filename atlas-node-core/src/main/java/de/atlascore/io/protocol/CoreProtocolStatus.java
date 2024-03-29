package de.atlascore.io.protocol;

import de.atlascore.io.protocol.status.CorePacketInPing;
import de.atlascore.io.protocol.status.CorePacketInRequest;
import de.atlascore.io.protocol.status.CorePacketOutPong;
import de.atlascore.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.io.protocol.ProtocolStatus;

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
	public PacketListener createDefaultPacketListener(Object o) {
		return new CorePacketListenerStatus((ProxyConnectionHandler) o);
	}

}
