package de.atlascore.event.player;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.Player;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.player.PlayerChatSettingsEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.play.PacketOutSetHeldItem;

public class CorePlayerListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		SimpleLocation clientLoc = player.getConnection().getClientLocation();
		if (event.isCancelled() 
				|| !clientLoc.matches(event.getTo())) {
			Location to = event.getTo();
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
		packet.setSlot(event.getOldSlot());
		con.sendPacked(packet);
	}
	
	@EventHandler
	public void onPluginChannelUnknonw(PlayerPluginChannelUnknownEvent event) {
		if (event.isIgnore())
			return;
		event.getPlayer().disconnect("Message on not registered plugin channel: " + event.getChannel());
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
