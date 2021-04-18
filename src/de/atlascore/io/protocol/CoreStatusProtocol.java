package de.atlascore.io.protocol;

import de.atlascore.io.protocol.status.CorePacketInPing;
import de.atlascore.io.protocol.status.CorePacketInRequest;
import de.atlascore.io.protocol.status.CorePacketOutPong;
import de.atlascore.io.protocol.status.CorePacketOutResponse;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;

public class CoreStatusProtocol implements Protocol {

	@Override
	public Packet createPacketIn(int id) {
		if (id == 0) {
			return new CorePacketInRequest();
		} else if (id == 1) {
			return new CorePacketInPing();
		}
		return null;
	}

	@Override
	public Packet createPacketOut(int id) {
		if (id == 0) {
			return new CorePacketOutResponse();
		} else if (id == 1) {
			return new CorePacketOutPong();
		}
		return null;
	}

	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createPacketListener(Object o) {
		return new CorePacketListenerStatus((ConnectionHandler) o);
	}

	@Override
	public Packet createCopy(Packet packet) {
		// TODO Auto-generated method stub
		return null;
	}

}
