package de.atlasmc.node.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.factory.ClassFactory;
import de.atlasmc.util.factory.FactoryException;

@RegistryHolder(key="atlas:entity_type", target = Target.PROTOCOL)
public class EntityType extends ProtocolRegistryValueBase implements ProtocolRegistryValue {
	
	public static final RegistryKey<EntityType> REGISTRY_KEY = Registries.getRegistryKey(EntityType.class);
	
	public static final RegistryValueKey<EntityType>
	ACACIA_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:acacia_boat")),
	ACACIA_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:acacia_chest_boat")),
	ALLAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:allay")),
	AREA_EFFECT_CLOUD = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:area_effect_cloud")),
	ARMADILLO = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:armadillo")),
	ARMOR_STAND = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:armor_stand")),
	ARROW = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:arrow")),
	AXOLOTL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:axolotl")),
	BAMBOO_CHEST_RAFT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bamboo_chest_raft")),
	BAMBOO_RAFT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bamboo_raft")),
	BAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bat")),
	BEE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bee")),
	BIRCH_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:birch_boat")),
	BIRCH_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:birch_chest_boat")),
	BLAZE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:blaze")),
	BLOCK_DISPLAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:block_display")),
	BOGGED = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bogged")),
	BREEZE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:breeze")),
	BREEZE_WIND_CHARGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:breeze_wind_charge")),
	CAMEL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:camel")),
	CAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cat")),
	CAVE_SPIDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cave_spider")),
	CHERRY_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cherry_boat")),
	CHERRY_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cherry_chest_boat")),
	CHEST_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:chest_minecart")),
	CHICKEN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:chicken")),
	COD = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cod")),
	COMMAND_BLOCK_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:command_block_minecart")),
	COW = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:cow")),
	CREAKING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:creaking")),
	CREEPER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:creeper")),
	DARK_OAK_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dark_oak_boat")),
	DARK_OAK_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dark_oak_chest_boat")),
	DOLPHIN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dolphin")),
	DONKEY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:donkey")),
	DRAGON_FIREBALL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dragon_fireball")),
	DROWNED = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:drowned")),
	EGG = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:egg")),
	ELDER_GUARDIAN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:elder_guardian")),
	ENDERMAN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:enderman")),
	ENDERMITE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:endermite")),
	ENDER_DRAGON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ender_dragon")),
	ENDER_PEARL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ender_pearl")),
	END_CRYSTAL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:end_crystal")),
	EVOKER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:evoker")),
	EVOKER_FANGS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:evoker_fangs")),
	EXPERIENCE_BOTTLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:experience_bottle")),
	EXPERIENCE_ORB = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:experience_orb")),
	EYE_OF_ENDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:eye_of_ender")),
	FALLING_BLOCK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:falling_block")),
	FIREBALL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:fireball")),
	FIREWORK_ROCKET = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:firework_rocket")),
	FISHING_BOBBER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:fishing_bobber")),
	FOX = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:fox")),
	FROG = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:frog")),
	FURNACE_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:furnace_minecart")),
	GHAST = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ghast")),
	GIANT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:giant")),
	GLOW_ITEM_FRAME = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:glow_item_frame")),
	GLOW_SQUID = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:glow_squid")),
	GOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:goat")),
	GUARDIAN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:guardian")),
	HOGLIN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:hoglin")),
	HOPPER_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:hopper_minecart")),
	HORSE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:horse")),
	HUSK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:husk")),
	ILLUSIONER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:illusioner")),
	INTERACTION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:interaction")),
	IRON_GOLEM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:iron_golem")),
	ITEM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:item")),
	ITEM_DISPLAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:item_display")),
	ITEM_FRAME = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:item_frame")),
	JUNGLE_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:jungle_boat")),
	JUNGLE_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:jungle_chest_boat")),
	LEASH_KNOT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:leash_knot")),
	LIGHTNING_BOLT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:lightning_bolt")),
	LLAMA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:llama")),
	LLAMA_SPIT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:llama_spit")),
	MAGMA_CUBE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:magma_cube")),
	MANGROVE_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:mangrove_boat")),
	MANGROVE_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:mangrove_chest_boat")),
	MARKER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:marker")),
	MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:minecart")),
	MOOSHROOM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:mooshroom")),
	MULE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:mule")),
	OAK_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:oak_boat")),
	OAK_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:oak_chest_boat")),
	OCELOT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ocelot")),
	OMINOUS_ITEM_SPAWNER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ominous_item_spawner")),
	PAINTING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:painting")),
	PALE_OAK_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pale_oak_boat")),
	PALE_OAK_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pale_oak_chest_boat")),
	PANDA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:panda")),
	PARROT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:parrot")),
	PHANTOM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:phantom")),
	PIG = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pig")),
	PIGLIN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:piglin")),
	PIGLIN_BRUTE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:piglin_brute")),
	PILLAGER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pillager")),
	PLAYER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:player")),
	POLAR_BEAR = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:polar_bear")),
	POTION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:potion")),
	PUFFERFISH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pufferfish")),
	RABBIT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:rabbit")),
	RAVAGER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ravager")),
	SALMON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:salmon")),
	SHEEP = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:sheep")),
	SHULKER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:shulker")),
	SHULKER_BULLET = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:shulker_bullet")),
	SILVERFISH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:silverfish")),
	SKELETON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:skeleton")),
	SKELETON_HORSE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:skeleton_horse")),
	SLIME = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:slime")),
	SMALL_FIREBALL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:small_fireball")),
	SNIFFER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:sniffer")),
	SNOWBALL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:snowball")),
	SNOW_GOLEM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:snow_golem")),
	SPAWNER_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:spawner_minecart")),
	SPECTRAL_ARROW = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:spectral_arrow")),
	SPIDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:spider")),
	SPRUCE_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:spruce_boat")),
	SPRUCE_CHEST_BOAT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:spruce_chest_boat")),
	SQUID = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:squid")),
	STRAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:stray")),
	STRIDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:strider")),
	TADPOLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tadpole")),
	TEXT_DISPLAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:text_display")),
	TNT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tnt")),
	TNT_MINECART = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tnt_minecart")),
	TRADER_LLAMA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:trader_llama")),
	TRIDENT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:trident")),
	TROPICAL_FISH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tropical_fish")),
	TURTLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:turtle")),
	VEX = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:vex")),
	VILLAGER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:villager")),
	VINDICATOR = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:vindicator")),
	WANDERING_TRADER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wandering_trader")),
	WARDEN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:warden")),
	WIND_CHARGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wind_charge")),
	WITCH = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:witch")),
	WITHER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wither")),
	WITHER_SKELETON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wither_skeleton")),
	WITHER_SKULL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wither_skull")),
	WOLF = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:wolf")),
	ZOGLIN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:zoglin")),
	ZOMBIE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:zombie")),
	ZOMBIE_HORSE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:zombie_horse")),
	ZOMBIE_VILLAGER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:zombie_villager")),
	ZOMBIFIED_PIGLIN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:zombified_piglin"));
	
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
		this.constructor = ClassFactory.getConstructor(clazz, EntityType.class);
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
		return getRegistry().getByID(id);
	}
	
	public static EntityType getByName(String name) {
		EntityType ent = REGISTRY_KEY.getValue(name);
		if (ent == null)
			throw new IllegalArgumentException("No value found with name: " + name);
		return ent;
	}
	
	public static EntityType get(NamespacedKey key) {
		return REGISTRY_KEY.getValue(key);
	}
	
	public static ProtocolRegistry<EntityType> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}

}
