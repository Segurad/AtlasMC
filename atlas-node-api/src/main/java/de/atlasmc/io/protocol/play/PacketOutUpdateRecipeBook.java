package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_RECIPE_BOOK)
public class PacketOutUpdateRecipeBook extends AbstractPacket implements PacketPlayOut {
	
	private RecipesAction action;
	private boolean craftingOpen;
	private boolean craftingFilter;
	private boolean smeltingOpen;
	private boolean smeltingFilter; 
	private boolean blastFurnaceOpen; 
	private boolean blastFurnaceFilter;
	private boolean smokerOpen;
	private boolean smokerFilter;
	private List<NamespacedKey> tagged;
	private List<NamespacedKey> untagged;
	
	public RecipesAction getAction() {
		return action;
	}

	public List<NamespacedKey> getTagged() {
		return tagged;
	}

	public List<NamespacedKey> getUntagged() {
		return untagged;
	}

	public void setTagged(List<NamespacedKey> tagged) {
		this.tagged = tagged;
	}

	public void setUntagged(List<NamespacedKey> untagged) {
		this.untagged = untagged;
	}

	public void setAction(RecipesAction action) {
		this.action = action;
	}

	public boolean isCraftingBookOpen() {
		return craftingOpen;
	}

	public boolean isCraftingBookFiltered() {
		return craftingFilter;
	}

	public boolean isSmeltingBookOpen() {
		return smeltingOpen;
	}

	public boolean isSmeltingBookFiltered() {
		return smeltingFilter;
	}

	public boolean isBlastingBookOpen() {
		return blastFurnaceOpen;
	}
	
	public boolean isBlastingBookFiltered() {
		return blastFurnaceFilter;
	}

	public boolean isSmokingBookOpen() {
		return smokerOpen;
	}

	public boolean isSmokingBookFilered() {
		return smokerFilter;
	}

	public void setCraftingBookOpen(boolean open) {
		this.craftingOpen = open;
	}

	public void setCraftingBookFiltered(boolean filtered) {
		this.craftingFilter = filtered;
	}

	public void setSmeltingBookOpen(boolean open) {
		this.smeltingOpen = open;
	}

	public void setSmeltingBookFiltered(boolean filtered) {
		this.smeltingFilter = filtered;
	}

	public void setBlastingBookOpen(boolean open) {
		this.blastFurnaceOpen = open;
	}

	public void setBlastingBookFiltered(boolean filtered) {
		this.blastFurnaceFilter = filtered;
	}

	public void setSmokingBookOpen(boolean open) {
		this.smokerOpen = open;
	}

	public void setSmokingBookFiltered(boolean filtered) {
		this.smokerFilter = filtered;
	}
	
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
