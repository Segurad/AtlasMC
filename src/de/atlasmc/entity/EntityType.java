package de.atlasmc.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Location;
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
	}
	
	private final String name;
	private final Class<? extends Entity> clazz;
	private final int id;
	
	public EntityType(String name, Class<? extends Entity> clazz) {
		this(name, eid++, clazz);
	}
	
	/**
	 * Creates a new EntityType
	 * @param name of this type
	 * @param id the entityTypeID of this type
	 * @param clazz the entity class needs to have a constructor({@link Integer}, {@link EntityType}, {@link Location}, {@link UUID})
	 */
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
	
	/**
	 * Creates and returns a Entity based on the class by {@link #getEntityClass()}
	 * @param id the entityID or -1 if not or later defined (entities with a id of -1 are not send to the player)
	 * @param loc the Location of this Entity
	 * @param uuid the UUID of this Entity
	 * @return the new Entity
	 */
	public Entity create(int id, Location loc, UUID uuid) {
		try {
			return clazz.getConstructor(int.class, EntityType.class, Location.class, UUID.class)
			.newInstance(id, this, loc, uuid);
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
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
