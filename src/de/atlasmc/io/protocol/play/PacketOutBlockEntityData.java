package de.atlasmc.io.protocol.play;

import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.io.NBTReader;

public interface PacketOutBlockEntityData {
	
	public long getPosition();
	
	public Action getAction();
	
	public NBT getNBT();
	
	public NBTReader getNBTReader();
	
	public byte[] getRawNBT();
	
	public enum Action {
		MOB_SPAWNER_DATA(1),
		COMMAND_BLOCK_TEXT(2),
		BEACON_LEVEL_PRIMARY_SECONDARY(3),
		MOB_HEAD_SKIN_ROTATION(4),
		DECLARE_CONDUIT(5),
		BANNER_COLOR_AND_PATTERNS(6),
		STRUCTURE_TILE_DATA(7),
		END_GATEWAY_DESTINATION(8),
		SIGN_TEXT(9),
		DECLARE_BED(11),
		JIGSAW_DATA(12),
		CAMPFIRE_ITEMS(13),
		BEEHIVE_INFORMATION(14);
		
		private int id;
		
		private Action(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public static Action getByID(int id) {
			switch (id) {
			case 1: return MOB_SPAWNER_DATA;
			case 2: return COMMAND_BLOCK_TEXT;
			case 3: return BEACON_LEVEL_PRIMARY_SECONDARY;
			case 4: return MOB_HEAD_SKIN_ROTATION;
			case 5: return DECLARE_CONDUIT;
			case 6: return BANNER_COLOR_AND_PATTERNS;
			case 7: return STRUCTURE_TILE_DATA;
			case 8: return END_GATEWAY_DESTINATION;
			case 9: return SIGN_TEXT;
			case 11: return DECLARE_BED;
			case 12: return JIGSAW_DATA;
			case 13: return CAMPFIRE_ITEMS;
			case 14: return BEEHIVE_INFORMATION;
			default: return MOB_SPAWNER_DATA;
			}
		}
	}
}
