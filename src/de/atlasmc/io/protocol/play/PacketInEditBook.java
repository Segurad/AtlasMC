package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.ItemStack;

public interface PacketInEditBook {
	
	public ItemStack getBook();
	public boolean isSigning();
	public int getHand();

}
