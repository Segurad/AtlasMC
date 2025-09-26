package de.atlasmc.core.node.io.protocol;

import de.atlasmc.core.node.io.protocol.login.CorePacketInCookieResponse;
import de.atlasmc.core.node.io.protocol.login.CorePacketInEncryptionResponse;
import de.atlasmc.core.node.io.protocol.login.CorePacketInLoginAcknowledged;
import de.atlasmc.core.node.io.protocol.login.CorePacketInLoginPluginResponse;
import de.atlasmc.core.node.io.protocol.login.CorePacketInLoginStart;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutCookieRequest;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutDisconnect;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutEncryptionRequest;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutLoginPluginRequest;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutLoginSuccess;
import de.atlasmc.core.node.io.protocol.login.CorePacketOutSetCompression;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.ProtocolLogin;

public class CoreProtocolLogin extends CoreAbstractProtocol<PacketInbound, PacketOutbound> implements ProtocolLogin {
	
	@SuppressWarnings("unchecked")
	public CoreProtocolLogin() {
		super(new PacketIO[] {
				new CorePacketInLoginStart(),
				new CorePacketInEncryptionResponse(),
				new CorePacketInLoginPluginResponse(),
				new CorePacketInLoginAcknowledged(),
				new CorePacketInCookieResponse()
		}, new PacketIO[] {
				new CorePacketOutDisconnect(),
				new CorePacketOutEncryptionRequest(),
				new CorePacketOutLoginSuccess(),
				new CorePacketOutSetCompression(),
				new CorePacketOutLoginPluginRequest(),
				new CorePacketOutCookieRequest()
		});
	}

	@Override
	public PacketListener createDefaultPacketListenerIn(Object o) {
		return new CorePacketListenerLoginIn((ConnectionHandler) o);
	}

	@Override
	public PacketListener createDefaultPacketListenerOut(Object o) {
		return null;
	}

}
