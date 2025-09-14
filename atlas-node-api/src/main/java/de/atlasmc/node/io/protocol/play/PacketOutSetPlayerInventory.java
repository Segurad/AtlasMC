package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.ItemStack;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_PLAYER_INVENTORY, definition = "set_player_inventory")
public class PacketOutSetPlayerInventory extends AbstractPacket implements PacketPlayOut {

	public int slot;
	public ItemStack item;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_PLAYER_INVENTORY;
	}

}
