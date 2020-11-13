package de.atlasmc.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.atlasmc.util.Validate;

public class EntityType {
	
	private static final HashMap<String, EntityType> BY_NAME;
	private static int eid = 0;
	
	public static EntityType 
	AREA_EFFECT_CLOUD,
	ARMOR_STAND,
	ARROW,
	BAT,
	BEE,
	BLAZE,
	BOAT,
	CAT,
	CAVE_SPIDER,
	CHICKEN,
	COD,
	COW,
	CREEPER,
	DOLPHIN,
	DONKEY,
	DRAGON_FIREBALL,
	DROWNED,
	ELDER_GUARDIAN,
	END_CRYSTAL,
	ENDER_DRAGON,
	ENDERMAN,
	ENDERMITE,
	EVOKER,
	EVOKER_FANGS,
	EXPERIENCE_ORB,
	EYE_OF_ENDER,
	FALLING_BLOCK,
	FIREWORK_ROCKET,
	FOX,
	GHAST,
	GIANT,
	GUARDIAN,
	HORSE,
	HOGLIN,
	HUSK,
	ILLUSIONER,
	IRON_GOLEM,
	ITEM,
	ITEM_FRAME,
	FIREBALL,
	LEASH_KNOT,
	LIGHTNING_BOLT,
	LLAMA,
	LLAMA_SPIT,
	MAGMA_CUBE,
	MINECART,
	CHEST_MINECART,
	COMMAND_BLOCK_MINECART,
	FURNACE_MINECART,
	HOPPER_MINECART,
	SPAWNER_MINECART,
	TNT_MINECART,
	MULE,
	MOOSHROOM,
	OCELOT,
	PAINTING,
	PANDA,
	PARROT,
	PHANTOM,
	PIG,
	PIGLIN,
	PIGLIN_BRUTE,
	PILLAGER,
	POLAR_BEAR,
	TNT,
	PUFFERFISH,
	RABBIT,
	RAVAGER,
	SALMON,
	SHEEP,
	SHULKER,
	SHULKER_BULLET,
	SILVERFISH,
	SKELETON,
	SKELETON_HORSE,
	SLIME,
	SMALL_FIREBALL,
	SNOW_GOLEM,
	SNOWBALL,
	SPECTRAL_ARROW,
	SPIDER,
	SQUID,
	STRAY,
	STRIDER,
	EGG,
	ENDER_PEARL,
	EXPERIENCE_BOTTLE,
	POTION,
	TRIDENT,
	TRADER_LLAMA,
	TROPICAL_FISH,
	TURTLE,
	VEX,
	VILLAGER,
	VINDICATOR,
	WANDERING_TRADER,
	WITCH,
	WITHER,
	WITHER_SKELETON,
	WITHER_SKULL,
	WOLF,
	ZOGLIN,
	ZOMBIE,
	ZOMBIE_HORSE,
	ZOMBIE_VILLAGER,
	ZOMBIFIED_PIGLIN,
	PLAYER,
	FISHING_BOBBER;
	
	static {
		BY_NAME = new HashMap<String, EntityType>();
		
		AREA_EFFECT_CLOUD = new EntityType("AREA_EFFECT_CLOUD", null);
		ARMOR_STAND = new EntityType("ArmorStand", null);
		ARROW = new EntityType("ARROW", null);
		BAT = new EntityType("BAT", null); 
		BEE = new EntityType("BEE", null);
		BLAZE = new EntityType("BLAZE", null);
		BOAT = new EntityType("BOAT", null);
		CAT = new EntityType("CAT", null);
		CAVE_SPIDER = new EntityType("CAVE_SPIDER", null);
		CHICKEN = new EntityType("CHICKEN", null);
		COD = new EntityType("COD", null);
		COW = new EntityType("COW", null);
		CREEPER = new EntityType("CREEPER", null);
		DOLPHIN = new EntityType("DOLPHIN", null);
		DONKEY = new EntityType("DONKEY", null);
		DRAGON_FIREBALL = new EntityType("DRAGON_FIREBALL", null);
		DROWNED = new EntityType("DROWNED", null);
		ELDER_GUARDIAN = new EntityType("ELDER_GUARDIAN", null);
		END_CRYSTAL = new EntityType("END_CRYSTAL", null);
		ENDER_DRAGON = new EntityType("ENDER_DRAGON", null);
		ENDERMAN = new EntityType("ENDERMAN", null);
		ENDERMITE = new EntityType("ENDERMITE", null);
		EVOKER = new EntityType("EVOKER", null);
		EVOKER_FANGS = new EntityType("EVOKER_FANGS", null);
		EXPERIENCE_ORB = new EntityType("EXPERIENCE_ORB", null);
		EYE_OF_ENDER = new EntityType("EYE_OF_ENDER", null);
		FALLING_BLOCK = new EntityType("FALLING_BLOCK", null);
		FIREWORK_ROCKET = new EntityType("FIREWORK_ROCKET", null);
		FOX = new EntityType("FOX", null);
		GHAST = new EntityType("GHAST", null);
		GIANT = new EntityType("GIANT", null);
		GUARDIAN = new EntityType("GUARDIAN", null);
		HORSE = new EntityType("HORSE", null);
		HOGLIN = new EntityType("HOGLIN", null);
		HUSK = new EntityType("HUSK", null);
		ILLUSIONER = new EntityType("ILLUSIONER", null);
		IRON_GOLEM = new EntityType("IRON_GOLEM", null);
		ITEM = new EntityType("ITEM", null);
		ITEM_FRAME = new EntityType("ITEM_FRAME", null);
		FIREBALL = new EntityType("FIREBALL", null);
		LEASH_KNOT = new EntityType("LEASH_KNOT", null);
		LIGHTNING_BOLT = new EntityType("LIGHTNING_BOLT", null);
		LLAMA = new EntityType("LLAMA", null);
		LLAMA_SPIT = new EntityType("LLAMA_SPIT", null);
		MAGMA_CUBE = new EntityType("MAGMA_CUBE", null);
		MINECART = new EntityType("MINECART", null);
		CHEST_MINECART = new EntityType("CHEST_MINECART", null);
		COMMAND_BLOCK_MINECART = new EntityType("COMMAND_BLOCK_MINECART", null);
		FURNACE_MINECART = new EntityType("FURNACE_MINECART", null);
		HOPPER_MINECART = new EntityType("HOPPER_MINECART", null);
		SPAWNER_MINECART = new EntityType("SPAWNER_MINECART", null);
		TNT_MINECART = new EntityType("TNT_MINECART", null);
		MULE = new EntityType("MULE", null);
		MOOSHROOM = new EntityType("MOOSHROOM", null);
		OCELOT = new EntityType("OCELOT", null);
		PAINTING = new EntityType("PAINTING", null);
		PANDA = new EntityType("PANDA", null);
		PARROT = new EntityType("PARROT", null);
		PHANTOM = new EntityType("PHANTOM", null);
		PIG = new EntityType("PIG", null);
		PIGLIN = new EntityType("PIGLIN", null);
		PIGLIN_BRUTE = new EntityType("PIGLIN_BRUTE", null);
		PILLAGER = new EntityType("PILLAGER", null);
		POLAR_BEAR = new EntityType("POLAR_BEAR", null);
		TNT = new EntityType("TNT", null);
		PUFFERFISH = new EntityType("PUFFERFISH", null);
		RABBIT = new EntityType("RABBIT", null);
		RAVAGER = new EntityType("RAVAGER", null);
		SALMON = new EntityType("SALMON", null);
		SHEEP = new EntityType("SHEEP", null);
		SHULKER = new EntityType("SHULKER", null);
		SHULKER_BULLET = new EntityType("SHULKER_BULLET", null);
		SILVERFISH = new EntityType("SILVERFISH", null);
		SKELETON = new EntityType("SKELETON", null);
		SKELETON_HORSE = new EntityType("SKELETON_HORNE", null);
		SLIME = new EntityType("SLIME", null);
		SMALL_FIREBALL = new EntityType("SMALL_FIREBALL", null);
		SNOW_GOLEM = new EntityType("SNOW_GOLEM", null);
		SNOWBALL = new EntityType("SNOWBALL", null);
		SPECTRAL_ARROW = new EntityType("SPECTRAL_ARROW", null);
		SPIDER = new EntityType("SPIDER", null);
		SQUID = new EntityType("SQUID", null);
		STRAY = new EntityType("STRAY", null);
		STRIDER = new EntityType("STRIDER", null);
		EGG = new EntityType("EGG", null);
		ENDER_PEARL = new EntityType("ENDER_PEARL", null);
		EXPERIENCE_BOTTLE = new EntityType("EXPERIENCE_BOTTLE", null);
		POTION = new EntityType("POTION", null);
		TRIDENT = new EntityType("TRIDENT", null);
		TRADER_LLAMA = new EntityType("TRADER_LLAMA", null);
		TROPICAL_FISH = new EntityType("TROPICAL_FISH", null);
		TURTLE = new EntityType("TRUTLE", null);
		VEX = new EntityType("VEX", null);
		VILLAGER = new EntityType("VILLAGER", null);
		VINDICATOR = new EntityType("VINDICATOR", null);
		WANDERING_TRADER = new EntityType("WANDERING_TRADER", null);
		WITCH = new EntityType("WITCH", null);
		WITHER = new EntityType("WITHER", null);
		WITHER_SKELETON = new EntityType("WITHER_SKELETON", null);
		WITHER_SKULL = new EntityType("WITHER_SKULL", null);
		WOLF = new EntityType("WOLF", null);
		ZOGLIN = new EntityType("ZOGLIN", null);
		ZOMBIE = new EntityType("ZOMBIE", null);
		ZOMBIE_HORSE = new EntityType("ZOMBIE_HORSE", null);
		ZOMBIE_VILLAGER = new EntityType("ZOMBIFIED_VILLAGER", null);
		ZOMBIFIED_PIGLIN = new EntityType("ZOMBIEFIED", null);
		PLAYER = new EntityType("PLAYER", null);
		FISHING_BOBBER = new EntityType("FISHING_BOBBER", null);
	}
	
	private final String name;
	private final Class<? extends Entity> clazz;
	private final int id;
	
	public EntityType(String name, Class<? extends Entity> clazz) {
		this(name, eid++, clazz);
	}
	
	public EntityType(String name, int id, Class<? extends Entity> clazz) {
		Validate.notNull(name, "Name can not be null!");
		Validate.notNull(clazz, "Class can not be null!");
		if (BY_NAME.containsKey(name)) throw new IllegalArgumentException("EntityType with the name (" + name + ") already exists!");
		this.name = name;
		this.id = id;
		this.clazz = clazz;
		BY_NAME.put(name, this);
	}
	
	public String name() {
		return name;
	}
	
	public Class<? extends Entity> getEntityClass() {
		return clazz;
	}
	
	public int getTypeID() {
		return id;
	}
	
	public static EntityType getByID(int id) {
		for (EntityType type : BY_NAME.values()) {
			if (type.getTypeID() == id) return type;
		}
		return PIG;
	}
	
	public static EntityType getByName(String name) {
		return BY_NAME.get(name);
	}
	
	public List<EntityType> values() {
		return new ArrayList<EntityType>(BY_NAME.values());
	}
	
	
	
	
	

}
