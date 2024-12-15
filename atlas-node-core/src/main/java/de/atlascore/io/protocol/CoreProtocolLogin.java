package de.atlascore.io.protocol;

import de.atlascore.io.protocol.login.CorePacketInCookieResponse;
import de.atlascore.io.protocol.login.CorePacketInEncryptionResponse;
import de.atlascore.io.protocol.login.CorePacketInLoginAcknowledged;
import de.atlascore.io.protocol.login.CorePacketInLoginPluginResponse;
import de.atlascore.io.protocol.login.CorePacketInLoginStart;
import de.atlascore.io.protocol.login.CorePacketOutCookieRequest;
import de.atlascore.io.protocol.login.CorePacketOutDisconnect;
import de.atlascore.io.protocol.login.CorePacketOutEncryptionRequest;
import de.atlascore.io.protocol.login.CorePacketOutLoginPluginRequest;
import de.atlascore.io.protocol.login.CorePacketOutLoginSuccess;
import de.atlascore.io.protocol.login.CorePacketOutSetCompression;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.io.protocol.ProtocolLogin;

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
	public PacketListener createDefaultPacketListener(Object o) {
		return new CorePacketListenerLoginIn((ProxyConnectionHandler) o);
	}

}
