package de.atlasmc.core.node;

import de.atlasmc.Atlas;
import de.atlasmc.core.node.io.handshake.CorePacketMinecraftHandshake;
import de.atlasmc.core.node.io.handshake.CorePacketMinecraftLegacyHandshake;
import de.atlasmc.core.node.io.protocol.CoreProtocolConfiguration;
import de.atlasmc.core.node.io.protocol.CoreProtocolLogin;
import de.atlasmc.core.node.io.protocol.CoreProtocolPlay;
import de.atlasmc.core.node.io.protocol.CoreProtocolStatus;
import de.atlasmc.core.node.io.socket.CoreSocketManager;
import de.atlasmc.core.node.server.CoreNodeServerManager;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.handshake.HandshakeProtocol;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.node.AtlasNodeBuilder;
import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.node.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftHandshake;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftLegacyHandshake;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.INIT_NODE })
class CoreInitNodeHandler implements StartupStageHandler {

	@Override
	public void handleStage(StartupContext context) {
		AtlasNodeBuilder builder = context.getContext("builder");
		builder.setServerManager(new CoreNodeServerManager(Atlas.getWorkdir()))
			.setUUID(AtlasNetwork.getNodeUUID())
			.setFactory(new CoreLocalAtlasNodeFactory())
			.setProxyManager(new CoreSocketManager())
			.setProtocolAdapterHandler(new ProtocolAdapterHandler());
		builder.getProtocolAdapterHandler().setProtocol(
				new ProtocolAdapter(
						new CoreProtocolStatus(),
						new CoreProtocolLogin(),
						new CoreProtocolPlay(),
						new CoreProtocolConfiguration()));
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(Packet.getDefaultPacketID(PacketMinecraftHandshake.class), new CorePacketMinecraftHandshake());
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(Packet.getDefaultPacketID(PacketMinecraftLegacyHandshake.class), new CorePacketMinecraftLegacyHandshake());
	}

}
