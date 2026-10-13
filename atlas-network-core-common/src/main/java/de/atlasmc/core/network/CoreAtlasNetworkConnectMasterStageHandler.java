package de.atlasmc.core.network;

import java.nio.ByteBuffer;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import de.atlasmc.Atlas;
import de.atlasmc.core.network.io.atlasprotocol.CoreAtlasProtocol;
import de.atlasmc.core.network.node.CoreNodeManager;
import de.atlasmc.core.network.server.CoreServerManager;
import de.atlasmc.io.connection.InternalConnectionHandler;
import de.atlasmc.io.connection.SocketConnectionHandler;
import de.atlasmc.io.socket.ClientSocket;
import de.atlasmc.io.socket.SocketConfig.ChannelType;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

@StartupHandlerRegister(value = { StartupContext.CONNECT_MASTER })
class CoreAtlasNetworkConnectMasterStageHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.getLogger().info("Connecting to master...");
		ClientSocket client = new ClientSocket();
		SocketConnectionHandler conHandler = new SocketConnectionHandler(null, Atlas.getLogger());
		var builder = new CoreAtlasNetworkHandlerBuilder();
		context.setContex("builder", builder);
	}
	
	@Override
	public void handleStage(StartupContext context) {
		CoreAtlasNetworkHandlerBuilder builder = context.getContext("builder");
		builder.setNodeManager(new CoreNodeManager())
			.setServerManager(new CoreServerManager())
			.setProfileHandler(AtlasMaster.getProfileManager())
			.setPermissionManager(AtlasMaster.getPermissionManager())
			.setUUID(AtlasMaster.getID())
			.setClient(new InternalConnectionHandler(context.getLogger(), CoreAtlasProtocol.INSTANCE));
	}
	
	@Override
	public void finalizeStage(StartupContext context) {
		CoreAtlasNetworkHandlerBuilder builder = context.getContext("builder");
		AtlasNetwork.init(builder.build());
		context.getLogger().info("Connected to master successfully...");
	}

}
