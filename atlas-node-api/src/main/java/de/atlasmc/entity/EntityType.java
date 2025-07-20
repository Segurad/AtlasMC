package de.atlasmc.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.factory.ClassFactory;
import de.atlasmc.util.factory.FactoryException;
import de.atlasmc.world.World;

@RegistryHolder(key="atlas:entity_type", target = Target.PROTOCOL)
public class EntityType extends ProtocolRegistryValueBase implements ProtocolRegistryValue {
	
	private static final ProtocolRegistry<EntityType> REGISTRY;
	
	static {
		REGISTRY = Registries.createRegistry(EntityType.class);
	}
	
	public static final NamespacedKey
	ACACIA_BOAT = NamespacedKey.literal("minecraft:acacia_boat"),
	ACACIA_CHEST_BOAT = NamespacedKey.literal("minecraft:acacia_chest_boat"),
	ALLAY = NamespacedKey.literal("minecraft:allay"),
	AREA_EFFECT_CLOUD = NamespacedKey.literal("minecraft:area_effect_cloud"),
	ARMADILLO = NamespacedKey.literal("minecraft:armadillo"),
	ARMOR_STAND = NamespacedKey.literal("minecraft:armor_stand"),
	ARROW = NamespacedKey.literal("minecraft:arrow"),
	AXOLOTL = NamespacedKey.literal("minecraft:axolotl"),
	BAMBOO_CHEST_RAFT = NamespacedKey.literal("minecraft:bamboo_chest_raft"),
	BAMBOO_RAFT = NamespacedKey.literal("minecraft:bamboo_raft"),
	BAT = NamespacedKey.literal("minecraft:bat"),
	BEE = NamespacedKey.literal("minecraft:bee"),
	BIRCH_BOAT = NamespacedKey.literal("minecraft:birch_boat"),
	BIRCH_CHEST_BOAT = NamespacedKey.literal("minecraft:birch_chest_boat"),
	BLAZE = NamespacedKey.literal("minecraft:blaze"),
	BLOCK_DISPLAY = NamespacedKey.literal("minecraft:block_display"),
	BOGGED = NamespacedKey.literal("minecraft:bogged"),
	BREEZE = NamespacedKey.literal("minecraft:breeze"),
	BREEZE_WIND_CHARGE = NamespacedKey.literal("minecraft:breeze_wind_charge"),
	CAMEL = NamespacedKey.literal("minecraft:camel"),
	CAT = NamespacedKey.literal("minecraft:cat"),
	CAVE_SPIDER = NamespacedKey.literal("minecraft:cave_spider"),
	CHERRY_BOAT = NamespacedKey.literal("minecraft:cherry_boat"),
	CHERRY_CHEST_BOAT = NamespacedKey.literal("minecraft:cherry_chest_boat"),
	CHEST_MINECART = NamespacedKey.literal("minecraft:chest_minecart"),
	CHICKEN = NamespacedKey.literal("minecraft:chicken"),
	COD = NamespacedKey.literal("minecraft:cod"),
	COMMAND_BLOCK_MINECART = NamespacedKey.literal("minecraft:command_block_minecart"),
	COW = NamespacedKey.literal("minecraft:cow"),
	CREAKING = NamespacedKey.literal("minecraft:creaking"),
	CREEPER = NamespacedKey.literal("minecraft:creeper"),
	DARK_OAK_BOAT = NamespacedKey.literal("minecraft:dark_oak_boat"),
	DARK_OAK_CHEST_BOAT = NamespacedKey.literal("minecraft:dark_oak_chest_boat"),
	DOLPHIN = NamespacedKey.literal("minecraft:dolphin"),
	DONKEY = NamespacedKey.literal("minecraft:donkey"),
	DRAGON_FIREBALL = NamespacedKey.literal("minecraft:dragon_fireball"),
	DROWNED = NamespacedKey.literal("minecraft:drowned"),
	EGG = NamespacedKey.literal("minecraft:egg"),
	ELDER_GUARDIAN = NamespacedKey.literal("minecraft:elder_guardian"),
	ENDERMAN = NamespacedKey.literal("minecraft:enderman"),
	ENDERMITE = NamespacedKey.literal("minecraft:endermite"),
	ENDER_DRAGON = NamespacedKey.literal("minecraft:ender_dragon"),
	ENDER_PEARL = NamespacedKey.literal("minecraft:ender_pearl"),
	END_CRYSTAL = NamespacedKey.literal("minecraft:end_crystal"),
	EVOKER = NamespacedKey.literal("minecraft:evoker"),
	EVOKER_FANGS = NamespacedKey.literal("minecraft:evoker_fangs"),
	EXPERIENCE_BOTTLE = NamespacedKey.literal("minecraft:experience_bottle"),
	EXPERIENCE_ORB = NamespacedKey.literal("minecraft:experience_orb"),
	EYE_OF_ENDER = NamespacedKey.literal("minecraft:eye_of_ender"),
	FALLING_BLOCK = NamespacedKey.literal("minecraft:falling_block"),
	FIREBALL = NamespacedKey.literal("minecraft:fireball"),
	FIREWORK_ROCKET = NamespacedKey.literal("minecraft:firework_rocket"),
	FISHING_BOBBER = NamespacedKey.literal("minecraft:fishing_bobber"),
	FOX = NamespacedKey.literal("minecraft:fox"),
	FROG = NamespacedKey.literal("minecraft:frog"),
	FURNACE_MINECART = NamespacedKey.literal("minecraft:furnace_minecart"),
	GHAST = NamespacedKey.literal("minecraft:ghast"),
	GIANT = NamespacedKey.literal("minecraft:giant"),
	GLOW_ITEM_FRAME = NamespacedKey.literal("minecraft:glow_item_frame"),
	GLOW_SQUID = NamespacedKey.literal("minecraft:glow_squid"),
	GOAT = NamespacedKey.literal("minecraft:goat"),
	GUARDIAN = NamespacedKey.literal("minecraft:guardian"),
	HOGLIN = NamespacedKey.literal("minecraft:hoglin"),
	HOPPER_MINECART = NamespacedKey.literal("minecraft:hopper_minecart"),
	HORSE = NamespacedKey.literal("minecraft:horse"),
	HUSK = NamespacedKey.literal("minecraft:husk"),
	ILLUSIONER = NamespacedKey.literal("minecraft:illusioner"),
	INTERACTION = NamespacedKey.literal("minecraft:interaction"),
	IRON_GOLEM = NamespacedKey.literal("minecraft:iron_golem"),
	ITEM = NamespacedKey.literal("minecraft:item"),
	ITEM_DISPLAY = NamespacedKey.literal("minecraft:item_display"),
	ITEM_FRAME = NamespacedKey.literal("minecraft:item_frame"),
	JUNGLE_BOAT = NamespacedKey.literal("minecraft:jungle_boat"),
	JUNGLE_CHEST_BOAT = NamespacedKey.literal("minecraft:jungle_chest_boat"),
	LEASH_KNOT = NamespacedKey.literal("minecraft:leash_knot"),
	LIGHTNING_BOLT = NamespacedKey.literal("minecraft:lightning_bolt"),
	LLAMA = NamespacedKey.literal("minecraft:llama"),
	LLAMA_SPIT = NamespacedKey.literal("minecraft:llama_spit"),
	MAGMA_CUBE = NamespacedKey.literal("minecraft:magma_cube"),
	MANGROVE_BOAT = NamespacedKey.literal("minecraft:mangrove_boat"),
	MANGROVE_CHEST_BOAT = NamespacedKey.literal("minecraft:mangrove_chest_boat"),
	MARKER = NamespacedKey.literal("minecraft:marker"),
	MINECART = NamespacedKey.literal("minecraft:minecart"),
	MOOSHROOM = NamespacedKey.literal("minecraft:mooshroom"),
	MULE = NamespacedKey.literal("minecraft:mule"),
	OAK_BOAT = NamespacedKey.literal("minecraft:oak_boat"),
	OAK_CHEST_BOAT = NamespacedKey.literal("minecraft:oak_chest_boat"),
	OCELOT = NamespacedKey.literal("minecraft:ocelot"),
	OMINOUS_ITEM_SPAWNER = NamespacedKey.literal("minecraft:ominous_item_spawner"),
	PAINTING = NamespacedKey.literal("minecraft:painting"),
	PALE_OAK_BOAT = NamespacedKey.literal("minecraft:pale_oak_boat"),
	PALE_OAK_CHEST_BOAT = NamespacedKey.literal("minecraft:pale_oak_chest_boat"),
	PANDA = NamespacedKey.literal("minecraft:panda"),
	PARROT = NamespacedKey.literal("minecraft:parrot"),
	PHANTOM = NamespacedKey.literal("minecraft:phantom"),
	PIG = NamespacedKey.literal("minecraft:pig"),
	PIGLIN = NamespacedKey.literal("minecraft:piglin"),
	PIGLIN_BRUTE = NamespacedKey.literal("minecraft:piglin_brute"),
	PILLAGER = NamespacedKey.literal("minecraft:pillager"),
	PLAYER = NamespacedKey.literal("minecraft:player"),
	POLAR_BEAR = NamespacedKey.literal("minecraft:polar_bear"),
	POTION = NamespacedKey.literal("minecraft:potion"),
	PUFFERFISH = NamespacedKey.literal("minecraft:pufferfish"),
	RABBIT = NamespacedKey.literal("minecraft:rabbit"),
	RAVAGER = NamespacedKey.literal("minecraft:ravager"),
	SALMON = NamespacedKey.literal("minecraft:salmon"),
	SHEEP = NamespacedKey.literal("minecraft:sheep"),
	SHULKER = NamespacedKey.literal("minecraft:shulker"),
	SHULKER_BULLET = NamespacedKey.literal("minecraft:shulker_bullet"),
	SILVERFISH = NamespacedKey.literal("minecraft:silverfish"),
	SKELETON = NamespacedKey.literal("minecraft:skeleton"),
	SKELETON_HORSE = NamespacedKey.literal("minecraft:skeleton_horse"),
	SLIME = NamespacedKey.literal("minecraft:slime"),
	SMALL_FIREBALL = NamespacedKey.literal("minecraft:small_fireball"),
	SNIFFER = NamespacedKey.literal("minecraft:sniffer"),
	SNOWBALL = NamespacedKey.literal("minecraft:snowball"),
	SNOW_GOLEM = NamespacedKey.literal("minecraft:snow_golem"),
	SPAWNER_MINECART = NamespacedKey.literal("minecraft:spawner_minecart"),
	SPECTRAL_ARROW = NamespacedKey.literal("minecraft:spectral_arrow"),
	SPIDER = NamespacedKey.literal("minecraft:spider"),
	SPRUCE_BOAT = NamespacedKey.literal("minecraft:spruce_boat"),
	SPRUCE_CHEST_BOAT = NamespacedKey.literal("minecraft:spruce_chest_boat"),
	SQUID = NamespacedKey.literal("minecraft:squid"),
	STRAY = NamespacedKey.literal("minecraft:stray"),
	STRIDER = NamespacedKey.literal("minecraft:strider"),
	TADPOLE = NamespacedKey.literal("minecraft:tadpole"),
	TEXT_DISPLAY = NamespacedKey.literal("minecraft:text_display"),
	TNT = NamespacedKey.literal("minecraft:tnt"),
	TNT_MINECART = NamespacedKey.literal("minecraft:tnt_minecart"),
	TRADER_LLAMA = NamespacedKey.literal("minecraft:trader_llama"),
	TRIDENT = NamespacedKey.literal("minecraft:trident"),
	TROPICAL_FISH = NamespacedKey.literal("minecraft:tropical_fish"),
	TURTLE = NamespacedKey.literal("minecraft:turtle"),
	VEX = NamespacedKey.literal("minecraft:vex"),
	VILLAGER = NamespacedKey.literal("minecraft:villager"),
	VINDICATOR = NamespacedKey.literal("minecraft:vindicator"),
	WANDERING_TRADER = NamespacedKey.literal("minecraft:wandering_trader"),
	WARDEN = NamespacedKey.literal("minecraft:warden"),
	WIND_CHARGE = NamespacedKey.literal("minecraft:wind_charge"),
	WITCH = NamespacedKey.literal("minecraft:witch"),
	WITHER = NamespacedKey.literal("minecraft:wither"),
	WITHER_SKELETON = NamespacedKey.literal("minecraft:wither_skeleton"),
	WITHER_SKULL = NamespacedKey.literal("minecraft:wither_skull"),
	WOLF = NamespacedKey.literal("minecraft:wolf"),
	ZOGLIN = NamespacedKey.literal("minecraft:zoglin"),
	ZOMBIE = NamespacedKey.literal("minecraft:zombie"),
	ZOMBIE_HORSE = NamespacedKey.literal("minecraft:zombie_horse"),
	ZOMBIE_VILLAGER = NamespacedKey.literal("minecraft:zombie_villager"),
	ZOMBIFIED_PIGLIN = NamespacedKey.literal("minecraft:zombified_piglin");
	
	private final Class<? extends Entity> clazz;
	private final Constructor<? extends Entity> constructor;
	
	/**
	 * Creates a new EntityType
	 * @param key of this type
	 * @param id the entityTypeID of this type
	 * @param clazz the entity class needs to have a constructor({@link EntityType}, {@link UUID}, {@link World})
	 */
	public EntityType(NamespacedKey key, int id, Class<? extends Entity> clazz) {
		super(key, id);
		if (clazz == null) 
			throw new IllegalArgumentException("Class can not be null!");
		this.clazz = clazz;
		this.constructor = ClassFactory.getConstructor(clazz, EntityType.class);
	}
	
	public EntityType(ConfigurationSection cfg) {
		super(cfg);
		this.clazz = ClassFactory.getClass(cfg.getString("entityClass"));
		this.constructor = ClassFactory.getConstructor(clazz, EntityType.class, UUID.class);
	}
	
	public Class<? extends Entity> getEntityClass() {
		return clazz;
	}
	
	/**
	 * Creates and returns a Entity based on the class by {@link #getEntityClass()}.<br>
	 * The created Entity is marked as removed and need to be spawned. 
	 * @return the new Entity
	 */
	public Entity createEntity() {
		try {
			return constructor.newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating Entity", e);
		}
	}
	
	public static EntityType getByID(int id) {
		return REGISTRY.getByID(id);
	}
	
	public static EntityType getByName(String name) {
		EntityType ent = REGISTRY.get(name);
		if (ent == null)
			throw new IllegalArgumentException("No value found with name: " + name);
		return ent;
	}
	
	public static EntityType get(NamespacedKey key) {
		return REGISTRY.get(key);
	}
	
	public static ProtocolRegistry<EntityType> getRegistry() {
		return REGISTRY;
	}
	
	public static Collection<EntityType> values() {
		return REGISTRY.values();
	}

}
