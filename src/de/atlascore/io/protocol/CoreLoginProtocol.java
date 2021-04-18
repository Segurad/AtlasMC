package de.atlascore.io.protocol;

import de.atlascore.io.protocol.login.CorePacketInLoginStart;
import de.atlascore.io.protocol.login.CorePacketOutDisconnect;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;

public class CoreLoginProtocol implements Protocol {

	@Override
	public Packet createPacketIn(int id) {
		if (id == 0) {
			return new CorePacketInLoginStart();
		}
		return null;
	}

	@Override
	public Packet createPacketOut(int id) {
		if (id == 0) {
			return new CorePacketOutDisconnect();
		}
		return null;
	}

	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createPacketListener(Object o) {
		return new CorePacketListenerLogin((ConnectionHandler) o);
	}

	@Override
	public Packet createCopy(Packet packet) {
		// TODO
		return null;
	}

}
