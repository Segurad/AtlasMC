package de.atlasmc.core.node.io.protocol;

import de.atlasmc.Atlas;
import de.atlasmc.chat.ChatMode;
import de.atlasmc.event.HandlerList;
import de.atlasmc.io.Packet;
import de.atlasmc.node.event.player.PlayerChangeDisplayedSkinPartsEvent;
import de.atlasmc.node.event.player.PlayerChatSettingsEvent;
import de.atlasmc.node.event.player.PlayerLocaleChangeEvent;
import de.atlasmc.node.event.player.PlayerMainHandChangeEvent;
import de.atlasmc.node.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.node.event.player.PlayerViewDistanceChangeEvent;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.PlayerSettings;
import de.atlasmc.node.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.node.io.protocol.configuration.PacketConfigurationServerbound;
import de.atlasmc.node.io.protocol.configuration.ServerboundClientInformation;
import de.atlasmc.node.io.protocol.configuration.ServerboundCookieResponse;
import de.atlasmc.node.io.protocol.configuration.ServerboundFinishConfiguration;
import de.atlasmc.node.io.protocol.configuration.ServerboundKeepAlive;
import de.atlasmc.node.io.protocol.configuration.ServerboundKnownPacks;
import de.atlasmc.node.io.protocol.configuration.ServerboundPluginMessage;
import de.atlasmc.node.io.protocol.configuration.ServerboundPong;
import de.atlasmc.node.io.protocol.configuration.ServerboundResourcePack;
import de.atlasmc.plugin.channel.PluginChannel;

public class CorePacketListenerConfigurationIn extends CoreAbstractPacketListener<PlayerConnection, PacketConfigurationServerbound> {

	private static final PacketHandler<?,?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLE_ASYNC = new boolean[PacketConfiguration.PACKET_COUNT_IN];
		HANDLERS = new PacketHandler[PacketConfiguration.PACKET_COUNT_IN];
		initHandler(ServerboundClientInformation.class, (con, packet) -> {
			PlayerSettings settings = con.getSettings();
			// Chat settings
			boolean chatColors = packet.chatColors;
			ChatMode chatmode = packet.chatMode;
			boolean allowServerListing = packet.allowServerListing;
			boolean enableTextFiltering = packet.enableTextFiltering;
			if (chatColors != settings.getChatColor() || 
					chatmode != settings.getChatMode() || 
					allowServerListing != settings.getAllowServerListing() ||
					enableTextFiltering != settings.hasTextFiltering()) {
				HandlerList.callEvent(new PlayerChatSettingsEvent(con.getPlayer(), chatColors, chatmode, enableTextFiltering, allowServerListing));
			}
			// Skin Parts
			int skinparts = packet.skinParts;
			if (skinparts != settings.getSkinParts()) {
				int oldSkinParts = settings.getSkinParts();
				settings.setSkinParts(skinparts);
				HandlerList.callEvent(new PlayerChangeDisplayedSkinPartsEvent(con.getPlayer(), skinparts, oldSkinParts));
			}
			// Main Hand
			int mainHand = packet.mainHand;
			if (mainHand != settings.getMainHand()) {
				settings.setMainHand(mainHand);
				HandlerList.callEvent(new PlayerMainHandChangeEvent(con.getPlayer(), mainHand));
			}
			// View Distance
			int viewDistance = packet.viewDistance;
			if (viewDistance != settings.getViewDistance()) {
				int oldDistance = settings.getViewDistance();
				settings.setViewDistance(viewDistance);
				HandlerList.callEvent(new PlayerViewDistanceChangeEvent(con.getPlayer(), viewDistance, oldDistance));
			}
			// Local
			String clientLocale = packet.local;
			if (!clientLocale.equals(settings.getLocal())) {
				settings.setLocal(clientLocale);
				HandlerList.callEvent(new PlayerLocaleChangeEvent(con.getPlayer(), clientLocale));
			}
		});
		initHandler(ServerboundFinishConfiguration.class, (con, packet) -> {
			con.protocolChangeAcknowledged();
		});
		initHandler(ServerboundKeepAlive.class, (con, packet) -> {
			// TODO handle keep alive
		});
		initHandler(ServerboundPluginMessage.class, (con, packet) -> {
			System.out.println(packet.channel.toString());
			PluginChannel channel = con.getPluginChannelHandler().getChannel(packet.channel);
			if (channel == null) {
				Atlas.getPluginManager().callEvent(new PlayerPluginChannelUnknownEvent(false, con.getPlayer(), packet.channel, packet.data));
			} else {
				channel.handleMessage(packet.data);
			}
		});
		initHandler(ServerboundPong.class, (con, packet) -> {
			// TODO handle in pong
		});
		initHandler(ServerboundResourcePack.class, (con, packet) -> {
			// TODO handle in resource pack
		});
		initHandler(ServerboundCookieResponse.class, (con, packet) -> {
			// TODO handle in cookie response
		});
		initHandler(ServerboundKnownPacks.class, (con, packet) -> {
			// TODO handle in known packs
		});
	}
	
	private static <T extends PacketConfigurationServerbound> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler) {
		initHandler(clazz, handler, false);
	}
		
	private static <T extends PacketConfigurationServerbound> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler, boolean async) {
		int id = Packet.getDefaultPacketID(clazz);
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}
	
	public CorePacketListenerConfigurationIn(PlayerConnection holder) {
		super(holder, PacketConfiguration.PACKET_COUNT_IN);
	}

	@Override
	protected boolean handleAsync(int packetID) {
		return HANDLE_ASYNC[packetID];
	}

	@Override
	protected void handle(Packet packet) {
		if (holder.isWaitingForProtocolChange() && !(packet instanceof ServerboundFinishConfiguration)) {
			return;
		}
		@SuppressWarnings("unchecked")
		PacketHandler<PlayerConnection, Packet> handler = (PacketHandler<PlayerConnection, Packet>) HANDLERS[packet.getID()];
		handler.handle(holder, packet);
	}

}
