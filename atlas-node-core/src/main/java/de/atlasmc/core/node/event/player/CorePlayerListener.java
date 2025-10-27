package de.atlasmc.core.node.event.player;

import de.atlasmc.chat.ChatUtil;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.Location;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.player.PlayerChatSettingsEvent;
import de.atlasmc.node.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.node.event.player.PlayerMoveEvent;
import de.atlasmc.node.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.PlayerSettings;
import de.atlasmc.node.io.protocol.play.PacketOutSetHeldItem;

public class CorePlayerListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location clientLoc = player.getConnection().getClientLocation();
		if (event.isCancelled() 
				|| !clientLoc.matches(event.getTo())) {
			WorldLocation to = event.getTo();
			player.teleport(to.x, to.y, to.z, to.yaw, to.pitch);		
		}
	}
	
	@EventHandler
	public void onHeldItemChange(PlayerHeldItemChangeEvent event) {
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			player.getInventory().setHeldItemSlot(event.getNewSlot());
			return;
		}
		PlayerConnection con = player.getConnection();
		PacketOutSetHeldItem packet = new PacketOutSetHeldItem();
		packet.slot = event.getOldSlot();
		con.sendPacked(packet);
	}
	
	@EventHandler
	public void onPluginChannelUnknonw(PlayerPluginChannelUnknownEvent event) {
		if (event.isIgnore())
			return;
		event.getPlayer().disconnect(ChatUtil.toChat("Message on not registered plugin channel: " + event.getChannel()));
	}
	
	@EventHandler
	public void onSettingsChange(PlayerChatSettingsEvent event) {
		PlayerSettings settings = event.getPlayer().getConnection().getSettings();
		settings.setChatColor(event.getChatColor());
		settings.setChatMode(event.getChatMode());
		settings.setAllowServerListing(event.getAllowServerListing());
		settings.setTextFiltering(event.hasTextFiltering());
	}

}
