package de.atlasmc.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(PacketPlay.OUT_BLOCK_ENTITY_DATA)
public interface PacketOutBlockEntityData extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	
	public TileUpdateAction getAction();
	
	public NBT getNBT();
	
	public NBTReader getNBTReader() throws IOException;
	
	public byte[] getRawNBT();
	
	@Override
	default int getDefaultID() {
		return OUT_BLOCK_ENTITY_DATA;
	}
	
	public enum TileUpdateAction {
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
		
		private TileUpdateAction(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public static TileUpdateAction getByID(int id) {
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
