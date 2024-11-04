package de.atlascore;

import java.util.List;

import de.atlascore.event.command.CoreCommandListener;
import de.atlascore.event.inventory.CoreInventoryListener;
import de.atlascore.event.player.CorePlayerListener;
import de.atlascore.io.handshake.CorePacketMinecraftHandshake;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlascore.proxy.CoreProxyManager;
import de.atlascore.server.CoreNodeServerManager;
import de.atlasmc.Atlas;
import de.atlasmc.AtlasNodeBuilder;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.INIT_NODE })
class CoreInitNodeHandler implements StartupStageHandler {

	@Override
	public void handleStage(StartupContext context) {
		AtlasNodeBuilder builder = context.getContext("builder");
		builder.setServerManager(new CoreNodeServerManager(Atlas.getWorkdir()))
			.setProxyManager(new CoreProxyManager())
			.setProtocolAdapterHandler(new ProtocolAdapterHandler());
		builder.getProtocolAdapterHandler().setProtocol(new CoreProtocolAdapter());
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(0x00, new CorePacketMinecraftHandshake());
		Log log = context.getLogger();
		initDefaultExecutor(log, new CorePlayerListener());
		initDefaultExecutor(log, new CoreInventoryListener());
		initDefaultExecutor(log, new CoreCommandListener());
	}
	
	private static void initDefaultExecutor(Log log, Listener listener) {
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(Atlas.getSystem(), listener);
		for (EventExecutor exe : exes) {
			Class<? extends Event> clazz = exe.getEventClass();
			log.debug("Set default executor for event: {}", clazz.getSimpleName());
			HandlerList handlers = HandlerList.getHandlerListOf(clazz);
			handlers.setDefaultExecutor(exe);
		}
	}

}
