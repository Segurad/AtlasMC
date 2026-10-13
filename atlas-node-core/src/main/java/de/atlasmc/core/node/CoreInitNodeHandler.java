package de.atlasmc.core.node;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

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
import de.atlasmc.node.io.protocol.ProtocolAdapterManager;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftHandshake;
import de.atlasmc.node.io.protocol.handshake.PacketMinecraftLegacyHandshake;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.INIT_NODE })
class CoreInitNodeHandler implements StartupStageHandler {
	
	@Override
	public void handleStage(StartupContext context) {
		var keypair = getKeyPair(context);
		if (keypair == null)
			return;
		AtlasNodeBuilder builder = context.getContext("builder");
		builder.setServerManager(new CoreNodeServerManager(Atlas.getWorkdir()))
			.setUUID(AtlasNetwork.getNodeUUID())
			.setFactory(new CoreLocalAtlasNodeFactory())
			.setSocketManager(new CoreSocketManager())
			.setPlayerAuthKey(keypair)
			.setProtocolAdapterManager(new ProtocolAdapterManager());
		var adapter = new ProtocolAdapter(
				new CoreProtocolStatus(),
				new CoreProtocolLogin(),
				new CoreProtocolPlay(),
				new CoreProtocolConfiguration());
		builder.getProtocolAdapterManager().setProtocol(adapter);
		builder.getProtocolAdapterManager().setDefaultProtocol(adapter);
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(
				Packet.getDefaultPacketID(PacketMinecraftHandshake.class), 
				new CorePacketMinecraftHandshake());
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(
				Packet.getDefaultPacketID(PacketMinecraftLegacyHandshake.class), 
				new CorePacketMinecraftLegacyHandshake());
	}
	
	private static KeyPair getKeyPair(StartupContext ctx) {
		KeyPairGenerator gen;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			ctx.getLogger().error("Failed to generate player auth keypair!");
			ctx.fail(e);
			return null;
		}
		gen.initialize(1024);
		KeyPair pair = gen.generateKeyPair();
		ctx.getLogger().debug("Keypair generated.");
		return pair;
	}

}
