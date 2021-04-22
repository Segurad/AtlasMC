package de.atlasmc;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;

public enum Effect {
	
	DISPENSER_DISPENSES(1000, Type.SOUND),
	DISPENSER_FAILS_TO_DISPENSE(1001, Type.SOUND),
	DISPENSER_SHOOTS(1002, Type.SOUND),
	ENDER_EYE_LAUNCHED(1003, Type.SOUND),
	FIREWORK_SHOT(1004, Type.SOUND),
	IRON_DOOR_OPENED(1005, Type.SOUND),
	WOODEN_DOOR_OPENED(1006, Type.SOUND),
	WOODEN_TRAPDOOR_OPENED(1007, Type.SOUND),
	FENCE_GATE_OPENED(1008, Type.SOUND),
	FIRE_EXTINGUISHED(1009, Type.SOUND),
	PLAY_RECORD(1010, Type.SOUND, Material.class),
	IRON_DOOR_CLOSED(1011, Type.SOUND),
	WOODEN_DOOR_CLOSED(1012, Type.SOUND),
	WOODEN_TRAPDOOR_CLOSED(1013, Type.SOUND),
	FENCE_GATE_CLOSED(1014, Type.SOUND),
	GHAST_WARNS(1015, Type.SOUND),
	GHAST_SHOOTS(1016, Type.SOUND),
	ENDERDRAGON_SHOOTS(1017, Type.SOUND),
	BLAZE_SHOOTS(1018, Type.SOUND),
	ZOMBIE_ATTACHS_WOOD_DOOR(1019, Type.SOUND),
	ZOMBIE_ATTACKS_IRON_DOOR(1020, Type.SOUND),
	ZOMBIE_BREAKS_WOOD_DOOR(1021, Type.SOUND),
	WITHER_BREAKS_BLOCK(1022, Type.SOUND),
	WITHER_SPAWNED(1023, Type.SOUND),
	WITHER_SHOOTS(1024, Type.SOUND),
	BAT_TAKES_OFF(1025, Type.SOUND),
	ZOMBIE_INFECTS(1026, Type.SOUND),
	ZOMBIE_VILLAGER_CONVERTED(1027, Type.SOUND),
	ENDER_DRAGON_DEATH(1028, Type.SOUND),
	ANVIL_DESTROYED(1029, Type.SOUND),
	ANVIL_USED(1030, Type.SOUND),
	ANVIL_LANDED(1031, Type.SOUND),
	PORTAL_TRAVEL(1032, Type.SOUND),
	CHORUS_FLOWER_GROWN(1033, Type.SOUND),
	CHORUS_FLOWER_DIED(1034, Type.SOUND),
	BREWING_STAND_BREWED(1035, Type.SOUND),
	IRON_TRAPDOOR_OPENED(1036, Type.SOUND),
	IRON_TRAPDOOR_CLOSED(1037, Type.SOUND),
	END_PORTAL_CREATED_IN_OVERWORLD(1038, Type.SOUND),
	PHANTOM_BITES(1039, Type.SOUND),
	ZOMBIE_COVERTS_TO_DROWNED(1040, Type.SOUND),
	HUSK_CONVERTS_TO_ZOMBIE_BY_DROWNING(1041, Type.SOUND),
	GRINDSTONE_USED(1042, Type.SOUND),
	BOOK_PAGE_TURNED(1043, Type.SOUND),
	//-------------------------------------------------------------------
	COMPOSTER_COMPOSTS(1500, Type.PARTICLE),
	LAVA_CONVERTS_BLOCK(1501, Type.PARTICLE),
	REDSTONE_TORCH_BURNS_OUT(1502, Type.PARTICLE),
	ENDER_EYE_PLACED(1503, Type.PARTICLE),
	SPAWNS_SMOKE(2000, Type.PARTICLE, BlockFace.class),
	BLOCK_BREAK(2001, Type.PARTICLE, BlockData.class),
	SPLASH_POTION_BREAK(2002, Type.PARTICLE, Integer.class),
	EYE_OF_ENDER_BREAK(2003, Type.PARTICLE),
	MOB_SPAWN_PARTICLE(2004, Type.PARTICLE),
	BONEMEAL_PARTICLES(2005, Type.PARTICLE, Integer.class),
	DRAGON_BREATH(2006, Type.PARTICLE),
	INSTANT_SPASH_POTION_BREAK(2007, Type.PARTICLE, Integer.class),
	ENDER_DRAGON_DESTROYS_BLOCK(2008, Type.PARTICLE),
	WET_SPONGE_VAPORIZES_IN_NETHER(2009, Type.PARTICLE),
	END_GATEWAY_SPAWN(3000, Type.PARTICLE),
	ENDER_DRAGON_GROWL(3001, Type.PARTICLE);
	
	private int id;
	private Class<?> clazz;
	private Type type;
	
	private Effect(int id, Type type) {
		this(id, type, null);
	}
	
	private Effect(int id, Type type, Class<?> clazz) {
		this.id = id;
		this.clazz = clazz;
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}

	public int getID() {
		return id;
	}
	
	public Class<?> getData() {
		return clazz;
	}

	public static Effect getByID(int id) {
		for (Effect e : values()) {
			if (e.getID() == id) return e;
		}
		return null;
	}
	
	public static enum Type {
		SOUND,
		PARTICLE
	}
	
	public int getDataByObject(Object data) {
		if (data != null) {
			if (!getData().isInstance(data)) throw new IllegalArgumentException("Wrong data for this effect!");
		} else {
			if (getData() != null) throw new IllegalArgumentException("Wrong data for this effect!");
		}
		if (data == null) return 0;
		switch (this) {
		case SPLASH_POTION_BREAK: return (int) data & 0xFFFFFF;
		case INSTANT_SPASH_POTION_BREAK: return (int) data & 0xFFFFFF;
		case BONEMEAL_PARTICLES: {
			int count = (int) data;
			if (count > 15 || count < 0) return 0;
			return count;
		}
		case BLOCK_BREAK: return ((BlockData) data).getStateID();
		case SPAWNS_SMOKE: {
			BlockFace face = (BlockFace) data;
			switch (face) {
			case DOWN: return 0;
			case UP: return 1;
			case NORTH: return 2;
			case SOUTH: return 3;
			case WEST: return 4;
			case EAST: return 5;
			default: return 0;
			}
		}
		case PLAY_RECORD: {
			// TODO match with records
			return 0;
		}
		default: return 0;
		}
	}

}
