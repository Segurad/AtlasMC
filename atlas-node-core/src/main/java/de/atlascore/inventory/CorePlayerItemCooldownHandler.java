package de.atlascore.inventory;

import java.util.Set;

import de.atlasmc.entity.Player;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSetCooldown;
import de.atlasmc.util.CooldownHandler;

public class CorePlayerItemCooldownHandler extends CooldownHandler<ItemType> {
	
	private final Player player;
	
	public CorePlayerItemCooldownHandler(Player player) {
		if (player == null)
			throw new IllegalArgumentException("Player can not be null!");
		this.player = player;
	}
	
	@Override
	protected void onSetCooldown(ItemType key, int ticks, int previousTicks) {
		PacketOutSetCooldown packet = new PacketOutSetCooldown();
		packet.itemID = key.getID();
		packet.cooldown = ticks;
		player.getConnection().sendPacked(packet);
	}
	
	@Override
	protected void onRemoveCooldown(ItemType key, int ticks) {
		PacketOutSetCooldown packet = new PacketOutSetCooldown();
		packet.itemID = key.getID();
		packet.cooldown = 0;
		player.getConnection().sendPacked(packet);
	}
	
	@Override
	protected void onClear(Set<ItemType> keys) {
		PlayerConnection con = player.getConnection();
		for (ItemType key : keys) {
			PacketOutSetCooldown packet = new PacketOutSetCooldown();
			packet.itemID = key.getID();
			packet.cooldown = 0;
			con.sendPacked(packet);
		}
	}
	
	@Override
	public void clear() {
		internalClear(true);
	}

}
