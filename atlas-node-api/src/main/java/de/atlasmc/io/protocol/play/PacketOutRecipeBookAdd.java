package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.recipe.Ingredient;
import de.atlasmc.recipe.RecipeCategory;
import de.atlasmc.recipe.display.RecipeDisplay;

@DefaultPacketID(packetID = PacketPlay.OUT_RECIPE_BOOK_ADD, definition = "recipe_book_add")
public class PacketOutRecipeBookAdd extends AbstractPacket implements PacketPlayOut {

	public List<RecipeData> recipes;
	public boolean replace;
	
	public static class RecipeData {
		
		public int id;
		public RecipeDisplay display;
		public int groupID;
		public RecipeCategory category;
		public List<Ingredient> ingredients;
		/**
		 * <ul>
		 * <li>0x01 = show notification</li>
		 * <li>0x02 = highlight new</li>
		 * </ul>
		 */
		public int flags;
		
	}
	
	@Override
	public int getDefaultID() {
		return OUT_RECIPE_BOOK_ADD;
	}

}
