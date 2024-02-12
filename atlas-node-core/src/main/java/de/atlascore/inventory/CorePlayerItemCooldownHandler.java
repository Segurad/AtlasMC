package de.atlascore.inventory;

import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSetCooldown;
import de.atlasmc.util.CooldownHandler;

public class CorePlayerItemCooldownHandler extends CooldownHandler<Material> {
	
	private final Player player;
	
	public CorePlayerItemCooldownHandler(Player player) {
		if (player == null)
			throw new IllegalArgumentException("Player can not be null!");
		this.player = player;
	}
	
	@Override
	protected void onSetCooldown(Material key, int ticks, int previousTicks) {
		PacketOutSetCooldown packet = new PacketOutSetCooldown();
		packet.setItemID(key.getItemID());
		packet.setCooldown(ticks);
		player.getConnection().sendPacked(packet);
	}
	
	@Override
	protected void onRemoveCooldown(Material key, int ticks) {
		PacketOutSetCooldown packet = new PacketOutSetCooldown();
		packet.setItemID(key.getItemID());
		packet.setCooldown(0);
		player.getConnection().sendPacked(packet);
	}
	
	@Override
	protected void onClear(Set<Material> keys) {
		PlayerConnection con = player.getConnection();
		for (Material key : keys) {
			PacketOutSetCooldown packet = new PacketOutSetCooldown();
			packet.setItemID(key.getItemID());
			packet.setCooldown(0);
			con.sendPacked(packet);
		}
	}
	
	@Override
	public void clear() {
		internalClear(true);
	}

}
