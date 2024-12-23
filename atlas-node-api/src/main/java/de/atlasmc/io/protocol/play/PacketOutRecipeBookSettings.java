package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_RECIPE_BOOK_SETTINGS, definition = "recipe_book_settings")
public class PacketOutRecipeBookSettings extends AbstractPacket implements PacketPlayOut {
	
	public boolean craftingOpen;
	public boolean craftingFilter;
	public boolean smeltingOpen;
	public boolean smeltingFilter; 
	public boolean blastFurnaceOpen; 
	public boolean blastFurnaceFilter;
	public boolean smokerOpen;
	public boolean smokerFilter;
	
	@Override
	public int getDefaultID() {
		return OUT_RECIPE_BOOK_SETTINGS;
	}

}
