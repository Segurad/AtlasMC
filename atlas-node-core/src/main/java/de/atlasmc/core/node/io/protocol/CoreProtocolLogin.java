package de.atlasmc.core.node.io.protocol;

import de.atlasmc.core.node.io.protocol.login.CoreServerboundCookieResponse;
import de.atlasmc.core.node.io.protocol.login.CoreServerboundEncryptionResponse;
import de.atlasmc.core.node.io.protocol.login.CoreServerboundLoginAcknowledged;
import de.atlasmc.core.node.io.protocol.login.CoreServerboundLoginPluginResponse;
import de.atlasmc.core.node.io.protocol.login.CoreServerboundLoginStart;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundCookieRequest;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundDisconnect;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundEncryptionRequest;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundLoginPluginRequest;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundLoginSuccess;
import de.atlasmc.core.node.io.protocol.login.CoreClientboundSetCompression;
import de.atlasmc.io.AbstractProtocol;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketServerbound;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketClientbound;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.ProtocolLogin;

public class CoreProtocolLogin extends AbstractProtocol<PacketServerbound, PacketClientbound> implements ProtocolLogin {
	
	@SuppressWarnings("unchecked")
	public CoreProtocolLogin() {
		super(new PacketIO[] {
				new CoreServerboundLoginStart(),
				new CoreServerboundEncryptionResponse(),
				new CoreServerboundLoginPluginResponse(),
				new CoreServerboundLoginAcknowledged(),
				new CoreServerboundCookieResponse()
		}, new PacketIO[] {
				new CoreClientboundDisconnect(),
				new CoreClientboundEncryptionRequest(),
				new CoreClientboundLoginSuccess(),
				new CoreClientboundSetCompression(),
				new CoreClientboundLoginPluginRequest(),
				new CoreClientboundCookieRequest()
		});
	}
	
	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createDefaultPacketListenerServerbound(Object o) {
		return new CorePacketListenerLoginIn((ConnectionHandler) o);
	}

	@Override
	public PacketListener createDefaultPacketListenerClientbound(Object o) {
		return null;
	}

}
