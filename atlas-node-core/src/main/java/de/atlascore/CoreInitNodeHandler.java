package de.atlascore;

import de.atlascore.io.handshake.CorePacketMinecraftHandshake;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlascore.proxy.CoreProxyManager;
import de.atlascore.server.CoreNodeServerManager;
import de.atlasmc.Atlas;
import de.atlasmc.AtlasNodeBuilder;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
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
			.setProxyManager(new CoreProxyManager())
			.setProtocolAdapterHandler(new ProtocolAdapterHandler());
		builder.getProtocolAdapterHandler().setProtocol(new CoreProtocolAdapter());
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(0x00, new CorePacketMinecraftHandshake());
	}

}
