package de.atlascore.io.protocol;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlascore.io.protocol.login.CorePacketInLoginStart;
import de.atlascore.io.protocol.login.CorePacketOutDisconnect;
import de.atlascore.io.protocol.login.CorePacketOutEncryptionRequest;
import de.atlascore.io.protocol.login.CorePacketOutLoginSuccess;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.ProtocolLogin;
import de.atlasmc.io.protocol.login.PacketLogin;
import static de.atlasmc.io.protocol.login.PacketLogin.*;

public class CoreProtocolLogin extends CoreAbstractProtocol implements ProtocolLogin {

	private final List<PacketIO<? extends PacketLogin>> loginIn;
	private final List<PacketIO<? extends PacketLogin>> loginOut;
	
	public CoreProtocolLogin() {
		loginIn = new ArrayList<>(PACKET_COUNT_IN);
		loginIn.add(IN_LOGIN_START, new CorePacketInLoginStart());
		loginIn.add(IN_ENCRYPTION_RESPONSE, null);
		loginIn.add(IN_LOGIN_PLUGIN_RESPONSE, null);
		loginOut = new ArrayList<>(PACKET_COUNT_OUT);
		loginOut.add(OUT_DISCONNECT, new CorePacketOutDisconnect());
		loginOut.add(OUT_ENCRYPTION_REQUEST, new CorePacketOutEncryptionRequest());
		loginOut.add(OUT_LOGIN_SUCCESS, new CorePacketOutLoginSuccess());
		loginOut.add(OUT_SET_COMPRESSION, null);
		loginOut.add(OUT_LOGIN_PLUGIN_REQUEST, null);
	}

	@Override
	public PacketListener createDefaultPacketListener(Object o) {
		return new CorePacketListenerLogin((ConnectionHandler) o);
	}

	@Override
	public PacketIO<?> getHandlerIn(int id) {
		if (id >= PACKET_COUNT_IN || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return loginIn.get(id);
	}

	@Override
	public PacketIO<?> getHandlerOut(int id) {
		if (id >= PACKET_COUNT_OUT || id < 0)
			throw new IllegalArgumentException("Invalid packet id: " + id);
		return loginOut.get(id);
	}

}
