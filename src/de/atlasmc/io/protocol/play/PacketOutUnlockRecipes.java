package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UNLOCK_RECIPES)
public interface PacketOutUnlockRecipes extends PacketPlay, PacketOutbound {
	
	public RecipesAction getAction();
	
	public List<NamespacedKey> getTagged();
	
	public List<NamespacedKey> getUntagged();
	
	public void setTagged(List<NamespacedKey> tagged);
	
	public void setUntagged(List<NamespacedKey> untagged);
	
	public void setAction(RecipesAction action);
	
	public boolean isCraftingBookOpen();
	
	public boolean isCraftingBookFiltered();
	
	public boolean isSmeltingBookOpen();
	
	public boolean isSmeltingBookFiltered();
	
	public boolean isBlastingBookOpen();
	
	public boolean isBlastingBookFiltered();
	
	public boolean isSmokingBookOpen();
	
	public boolean isSmokingBookFilered();
	
	public void setCraftingBookOpen(boolean open);
	
	public void setCraftingBookFiltered(boolean filtered);
	
	public void setSmeltingBookOpen(boolean open);
	
	public void setSmeltingBookFiltered(boolean filtered);
	
	public void setBlastingBookOpen(boolean open);
	
	public void setBlastingBookFiltered(boolean filtered);
	
	public void setSmokingBookOpen(boolean open);
	
	public void setSmokingBookFiltered(boolean filtered);
	
	@Override
	default int getDefaultID() {
		return OUT_UNLOCK_RECIPES;
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
