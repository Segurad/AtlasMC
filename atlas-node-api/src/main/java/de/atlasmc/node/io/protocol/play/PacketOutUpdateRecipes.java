package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.ItemStack;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_RECIPES, definition = "update_recipes")
public class PacketOutUpdateRecipes extends AbstractPacket implements PacketPlayOut {
	
	public List<PropertySet> properties;
	public List<StonecutterRecipe> stonecutterRecipes;
	
	public static class PropertySet {
		
		public NamespacedKey identifier;
		public int[] items;
		
	}
	
	public static class StonecutterRecipe {
		
		public int[] ingredients;
		public ItemStack display;
		
	}
	
	@Override
	public int getDefaultID() {
		return OUT_UPDATE_RECIPES;
	}

}
