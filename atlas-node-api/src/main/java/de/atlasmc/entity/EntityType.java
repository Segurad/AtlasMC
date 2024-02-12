package de.atlasmc.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.world.World;

public class EntityType implements Namespaced {
	
	private static final List<EntityType> REGISTRI;
	
	public static EntityType 
	AREA_EFFECT_CLOUD,
	ARMOR_STAND,
	ARROW,
	ALLAY,
	AXOLOTL,
	BLOCK_DISPLAY,
	BAT,
	BEE,
	BLAZE,
	BOAT,
	CAMEL,
	CAT,
	CAVE_SPIDER,
	CHICKEN,
	COD,
	CHEST_BOAT,
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
	FROG,
	GHAST,
	GIANT,
	GUARDIAN,
	GLOW_ITEM_FRAME,
	GLOW_SQUID,
	GOAT,
	HORSE,
	HOGLIN,
	HUSK,
	ILLUSIONER,
	IRON_GOLEM,
	ITEM,
	INTERACTION,
	ITEM_DISPLAY,
	ITEM_FRAME,
	FIREBALL,
	LEASH_KNOT,
	LIGHTNING_BOLT,
	LLAMA,
	LLAMA_SPIT,
	MAGMA_CUBE,
	MINECART,
	MARKER,
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
	TEXT_DISPLAY,
	TADPOLE,
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
	SNIFFER,
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
	WARDEN,
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
		REGISTRI = new ArrayList<>();
	}
	
	private final NamespacedKey key;
	private final Class<? extends Entity> clazz;
	private final int id;
	
	/**
	 * Creates a new EntityType
	 * @param key of this type
	 * @param id the entityTypeID of this type
	 * @param clazz the entity class needs to have a constructor({@link EntityType}, {@link UUID}, {@link World})
	 */
	public EntityType(NamespacedKey key, int id, Class<? extends Entity> clazz) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (clazz == null) 
			throw new IllegalArgumentException("Class can not be null!");
		this.key = key;
		this.id = id;
		this.clazz = clazz;
		REGISTRI.add(this);
	}
	
	public Class<? extends Entity> getEntityClass() {
		return clazz;
	}
	
	/**
	 * Creates and returns a Entity based on the class by {@link #getEntityClass()}.<br>
	 * The created Entity is marked as removed and need to be spawned. 
	 * @param uuid the UUID of this Entity
	 * @return the new Entity
	 */
	public Entity create(World world, UUID uuid) {
		try {
			return clazz.getConstructor(EntityType.class, UUID.class)
			.newInstance(this, uuid);
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException("Error while creating Entity", e);
		}
	}
	
	public Entity create(World world) {
		return create(world, UUID.randomUUID());
	}
	
	public int getTypeID() {
		return id;
	}
	
	public static EntityType getByID(int id) {
		for (EntityType type : REGISTRI) {
			if (type.getTypeID() == id) return type;
		}
		return PIG;
	}
	
	public static EntityType getByName(String name) {
		for (EntityType type : REGISTRI) {
			if (type.getNamespacedKeyRaw().equals(name))
				return type;
		}
		throw new IllegalArgumentException("No value found with name: " + name);
	}
	
	public static List<EntityType> values() {
		return new ArrayList<>(REGISTRI);
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

}
