package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCraftRecipeResponse extends Packet {
	
	public int getWindowID();
	public String getRecipe();
	
	@Override
	default int getDefaultID() {
		return 0x2F;
	}

}
