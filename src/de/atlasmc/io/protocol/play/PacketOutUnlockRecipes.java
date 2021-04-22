package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUnlockRecipes extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x35;
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
