package de.atlasmc.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.world.World;

@RegistryHolder(key="atlas:entity_type")
public class EntityType implements Namespaced {
	
	private static Registry<EntityType> registry;
	
	public static EntityType 
	ACACIA_BOAT,
	ACACIA_CHEST_BOAT,
	ALLAY,
	AREA_EFFECT_CLOUD,
	ARMADILLO,
	ARMOR_STAND,
	ARROW,
	AXOLOTL,
	BAMBOO_CHEST_RAFT,
	BAMBOO_RAFT,
	BAT,
	BEE,
	BIRCH_BOAT,
	BIRCH_CHEST_BOAT,
	BLAZE,
	BLOCK_DISPLAY,
	BOGGED,
	BREEZE,
	BREEZE_WIND_CHARGE,
	CAMEL,
	CAT,
	CAVE_SPIDER,
	CHERRY_BOAT,
	CHERRY_CHEST_BOAT,
	CHEST_MINECART,
	CHICKEN,
	COD,
	COMMAND_BLOCK_MINECART,
	COW,
	CREAKING,
	CREEPER,
	DARK_OAK_BOAT,
	DARK_OAK_CHEST_BOAT,
	DOLPHIN,
	DONKEY,
	DRAGON_FIREBALL,
	DROWNED,
	EGG,
	ELDER_GUARDIAN,
	ENDERMAN,
	ENDERMITE,
	ENDER_DRAGON,
	ENDER_PEARL,
	END_CRYSTAL,
	EVOKER,
	EVOKER_FANGS,
	EXPERIENCE_BOTTLE,
	EXPERIENCE_ORB,
	EYE_OF_ENDER,
	FALLING_BLOCK,
	FIREBALL,
	FIREWORK_ROCKET,
	FISHING_BOBBER,
	FOX,
	FROG,
	FURNACE_MINECART,
	GHAST,
	GIANT,
	GLOW_ITEM_FRAME,
	GLOW_SQUID,
	GOAT,
	GUARDIAN,
	HOGLIN,
	HOPPER_MINECART,
	HORSE,
	HUSK,
	ILLUSIONER,
	INTERACTION,
	IRON_GOLEM,
	ITEM,
	ITEM_DISPLAY,
	ITEM_FRAME,
	JUNGLE_BOAT,
	JUNGLE_CHEST_BOAT,
	LEASH_KNOT,
	LIGHTNING_BOLT,
	LLAMA,
	LLAMA_SPIT,
	MAGMA_CUBE,
	MANGROVE_BOAT,
	MANGROVE_CHEST_BOAT,
	MARKER,
	MINECART,
	MOOSHROOM,
	MULE,
	OAK_BOAT,
	OAK_CHEST_BOAT,
	OCELOT,
	OMINOUS_ITEM_SPAWNER,
	PAINTING,
	PALE_OAK_BOAT,
	PALE_OAK_CHEST_BOAT,
	PANDA,
	PARROT,
	PHANTOM,
	PIG,
	PIGLIN,
	PIGLIN_BRUTE,
	PILLAGER,
	PLAYER,
	POLAR_BEAR,
	POTION,
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
	SNOWBALL,
	SNOW_GOLEM,
	SPAWNER_MINECART,
	SPECTRAL_ARROW,
	SPIDER,
	SPRUCE_BOAT,
	SPRUCE_CHEST_BOAT,
	SQUID,
	STRAY,
	STRIDER,
	TADPOLE,
	TEXT_DISPLAY,
	TNT,
	TNT_MINECART,
	TRADER_LLAMA,
	TRIDENT,
	TROPICAL_FISH,
	TURTLE,
	VEX,
	VILLAGER,
	VINDICATOR,
	WANDERING_TRADER,
	WARDEN,
	WIND_CHARGE,
	WITCH,
	WITHER,
	WITHER_SKELETON,
	WITHER_SKULL,
	WOLF,
	ZOGLIN,
	ZOMBIE,
	ZOMBIE_HORSE,
	ZOMBIE_VILLAGER,
	ZOMBIFIED_PIGLIN;
	
	private final NamespacedKey key;
	private final Class<? extends Entity> clazz;
	private final int id;
	
	/**
	 * Creates a new EntityType
	 * @param key of this type
	 * @param id the entityTypeID of this type
	 * @param clazz the entity class needs to have a constructor({@link EntityType}, {@link UUID}, {@link World})
	 */
	public EntityType(PluginHandle handle, NamespacedKey key, int id, Class<? extends Entity> clazz) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (clazz == null) 
			throw new IllegalArgumentException("Class can not be null!");
		this.key = key;
		this.id = id;
		this.clazz = clazz;
		getRegistry().register(handle, key, this);
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
		for (EntityType type : getRegistry().values()) {
			if (type.getTypeID() == id) return type;
		}
		return PIG;
	}
	
	public static EntityType getByName(String name) {
		Registry<EntityType> registry = getRegistry();
		EntityType ent = registry.get(name);
		if (ent == null)
			throw new IllegalArgumentException("No value found with name: " + name);
		return ent;
	}
	
	public static Collection<EntityType> values() {
		return getRegistry().values();
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public static Registry<EntityType> getRegistry() {
		Registry<EntityType> r = registry;
		if (r == null) {
			synchronized (EntityType.class) {
				r = registry;
				if (r == null)
					r = registry = Registries.getInstanceRegistry(EntityType.class);
			}
		}
		return r;
	}

}
