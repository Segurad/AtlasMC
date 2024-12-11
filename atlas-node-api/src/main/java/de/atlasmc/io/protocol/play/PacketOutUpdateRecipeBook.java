package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_RECIPE_BOOK, definition = "recipe")
public class PacketOutUpdateRecipeBook extends AbstractPacket implements PacketPlayOut {
	
	public RecipesAction action;
	public boolean craftingOpen;
	public boolean craftingFilter;
	public boolean smeltingOpen;
	public boolean smeltingFilter; 
	public boolean blastFurnaceOpen; 
	public boolean blastFurnaceFilter;
	public boolean smokerOpen;
	public boolean smokerFilter;
	public List<NamespacedKey> tagged;
	public List<NamespacedKey> untagged;
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_RECIPE_BOOK;
	}
	
	public enum RecipesAction {
		INIT,
		ADD,
		REMOVE;
		
		public static RecipesAction getByID(int id) {
			if (id == 0)
				return INIT;
			if (id == 1)
				return ADD;
			if (id == 2)
				return REMOVE;
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		public int getID() {
			return ordinal();
		}
		
	}

}
