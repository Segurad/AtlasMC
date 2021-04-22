package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInSetDisplayedRecipe extends Packet {
	
	public String getRecipeID();
	
	@Override
	default int getDefaultID() {
		return 0x1F;
	}

}
