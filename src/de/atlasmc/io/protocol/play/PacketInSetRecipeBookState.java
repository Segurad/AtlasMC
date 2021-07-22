package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SET_RECIPE_BOOK_STATE)
public interface PacketInSetRecipeBookState extends PacketPlay, PacketInbound {
	
	public int getBookID();
	public boolean getBookOpen();
	public boolean getFilterActive();
	
	@Override
	default int getDefaultID() {
		return IN_SET_RECIPE_BOOK_STATE;
	}

}
