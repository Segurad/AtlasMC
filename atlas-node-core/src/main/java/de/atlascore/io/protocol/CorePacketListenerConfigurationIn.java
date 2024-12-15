package de.atlascore.io.protocol;

import de.atlasmc.Atlas;
import de.atlasmc.chat.ChatMode;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.player.PlayerChangeDisplayedSkinPartsEvent;
import de.atlasmc.event.player.PlayerChatSettingsEvent;
import de.atlasmc.event.player.PlayerLocaleChangeEvent;
import de.atlasmc.event.player.PlayerMainHandChangeEvent;
import de.atlasmc.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.event.player.PlayerViewDistanceChangeEvent;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.io.protocol.configuration.PacketConfigurationIn;
import de.atlasmc.io.protocol.configuration.PacketInClientInformation;
import de.atlasmc.io.protocol.configuration.PacketInCookieResponse;
import de.atlasmc.io.protocol.configuration.PacketInFinishConfiguration;
import de.atlasmc.io.protocol.configuration.PacketInKeepAlive;
import de.atlasmc.io.protocol.configuration.PacketInKnownPacks;
import de.atlasmc.io.protocol.configuration.PacketInPluginMessage;
import de.atlasmc.io.protocol.configuration.PacketInPong;
import de.atlasmc.io.protocol.configuration.PacketInResourcePack;
import de.atlasmc.plugin.channel.PluginChannel;

public class CorePacketListenerConfigurationIn extends CoreAbstractPacketListener<PlayerConnection, PacketConfigurationIn> {

	private static final PacketHandler<?,?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLE_ASYNC = new boolean[PacketConfiguration.PACKET_COUNT_IN];
		HANDLERS = new PacketHandler[PacketConfiguration.PACKET_COUNT_IN];
		initHandler(PacketInClientInformation.class, (con, packet) -> {
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
		initHandler(PacketInFinishConfiguration.class, (con, packet) -> {
			con.protocolChangeAcknowledged();
		});
		initHandler(PacketInKeepAlive.class, (con, packet) -> {
			// TODO handle keep alive
		});
		initHandler(PacketInPluginMessage.class, (con, packet) -> {
			System.out.println(packet.channel.toString());
			PluginChannel channel = con.getPluginChannelHandler().getChannel(packet.channel);
			if (channel == null) {
				Atlas.getPluginManager().callEvent(new PlayerPluginChannelUnknownEvent(false, con.getPlayer(), packet.channel, packet.data));
			} else {
				channel.handleMessage(packet.data);
			}
		});
		initHandler(PacketInPong.class, (con, packet) -> {
			// TODO handle in pong
		});
		initHandler(PacketInResourcePack.class, (con, packet) -> {
			// TODO handle in resource pack
		});
		initHandler(PacketInCookieResponse.class, (con, packet) -> {
			// TODO handle in cookie response
		});
		initHandler(PacketInKnownPacks.class, (con, packet) -> {
			// TODO handle in known packs
		});
	}
	
	private static <T extends PacketConfigurationIn> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler) {
		initHandler(clazz, handler, false);
	}
		
	private static <T extends PacketConfigurationIn> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler, boolean async) {
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
		if (holder.isWaitingForProtocolChange() && !(packet instanceof PacketInFinishConfiguration)) {
			return;
		}
		@SuppressWarnings("unchecked")
		PacketHandler<PlayerConnection, Packet> handler = (PacketHandler<PlayerConnection, Packet>) HANDLERS[packet.getID()];
		handler.handle(holder, packet);
	}

}
