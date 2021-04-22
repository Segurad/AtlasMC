package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;

public interface PacketInEditBook extends Packet {
	
	public ItemStack getBook();
	public boolean isSigning();
	public int getHand();
	
	@Override
	default int getDefaultID() {
		return 0x0C;
	}

}
