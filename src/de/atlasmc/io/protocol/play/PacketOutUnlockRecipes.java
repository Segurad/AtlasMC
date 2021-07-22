package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_UNLOCK_RECIPES)
public interface PacketOutUnlockRecipes extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_UNLOCK_RECIPES;
	}
	
	public enum RecipesAction {
		INIT,
		ADD,
		REMOVE;
		
		public static RecipesAction getByID(int id) {
			return values()[id];
		}

		public int getID() {
			return ordinal();
		}
	}

}
