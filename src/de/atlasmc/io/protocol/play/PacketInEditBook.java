package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_EDIT_BOOK)
public interface PacketInEditBook extends PacketPlay, PacketInbound {
	
	public ItemStack getBook();
	public boolean isSigning();
	public int getHand();
	
	@Override
	default int getDefaultID() {
		return IN_EDIT_BOOK;
	}

}
