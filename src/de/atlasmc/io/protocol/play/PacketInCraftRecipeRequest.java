package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInCraftRecipeRequest extends Packet {
	
	public byte getWindowID();
	public String getRecipe();
	public boolean getMakeAll();
	
	@Override
	default int getDefaultID() {
		return 0x19;
	}

}
