package de.atlascore.event.player;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.entity.Player;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutHeldItemChange;

public class CorePlayerListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		SimpleLocation clientLoc = player.getConnection().getClientLocation();
		if (event.isCancelled() 
				|| !clientLoc.matches(event.getTo())) {
			Location to = event.getTo();
			player.teleport(to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());		
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
		PacketOutHeldItemChange packet = new PacketOutHeldItemChange();
		packet.setSlot(event.getOldSlot());
		con.sendPacked(packet);
	}

}
