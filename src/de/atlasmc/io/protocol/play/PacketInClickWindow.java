package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CLICK_WINDOW)
public interface PacketInClickWindow extends PacketPlay, PacketInbound {

	public byte getWindowID();
	public short getSlot();
	public byte getButton();
	public short getActionNumber();
	public int getMode();
	public ItemStack getClickedItem();
	
	@Override
	default int getDefaultID() {
		return IN_CLICK_WINDOW;
	}
}
