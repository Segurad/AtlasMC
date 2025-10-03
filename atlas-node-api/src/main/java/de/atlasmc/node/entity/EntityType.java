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
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

@RegistryHolder(key="atlas:entity_type", target = Target.PROTOCOL)
public class EntityType extends ProtocolRegistryValueBase implements ProtocolRegistryValue {
	
	public static final RegistryKey<EntityType> REGISTRY_KEY = Registries.getRegistryKey(EntityType.class);
	
	public static final RegistryValueKey<EntityType>
	ACACIA_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:acacia_boat"),
	ACACIA_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:acacia_chest_boat"),
	ALLAY = ofLiteral(REGISTRY_KEY, "minecraft:allay"),
	AREA_EFFECT_CLOUD = ofLiteral(REGISTRY_KEY, "minecraft:area_effect_cloud"),
	ARMADILLO = ofLiteral(REGISTRY_KEY, "minecraft:armadillo"),
	ARMOR_STAND = ofLiteral(REGISTRY_KEY, "minecraft:armor_stand"),
	ARROW = ofLiteral(REGISTRY_KEY, "minecraft:arrow"),
	AXOLOTL = ofLiteral(REGISTRY_KEY, "minecraft:axolotl"),
	BAMBOO_CHEST_RAFT = ofLiteral(REGISTRY_KEY, "minecraft:bamboo_chest_raft"),
	BAMBOO_RAFT = ofLiteral(REGISTRY_KEY, "minecraft:bamboo_raft"),
	BAT = ofLiteral(REGISTRY_KEY, "minecraft:bat"),
	BEE = ofLiteral(REGISTRY_KEY, "minecraft:bee"),
	BIRCH_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:birch_boat"),
	BIRCH_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:birch_chest_boat"),
	BLAZE = ofLiteral(REGISTRY_KEY, "minecraft:blaze"),
	BLOCK_DISPLAY = ofLiteral(REGISTRY_KEY, "minecraft:block_display"),
	BOGGED = ofLiteral(REGISTRY_KEY, "minecraft:bogged"),
	BREEZE = ofLiteral(REGISTRY_KEY, "minecraft:breeze"),
	BREEZE_WIND_CHARGE = ofLiteral(REGISTRY_KEY, "minecraft:breeze_wind_charge"),
	CAMEL = ofLiteral(REGISTRY_KEY, "minecraft:camel"),
	CAT = ofLiteral(REGISTRY_KEY, "minecraft:cat"),
	CAVE_SPIDER = ofLiteral(REGISTRY_KEY, "minecraft:cave_spider"),
	CHERRY_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:cherry_boat"),
	CHERRY_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:cherry_chest_boat"),
	CHEST_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:chest_minecart"),
	CHICKEN = ofLiteral(REGISTRY_KEY, "minecraft:chicken"),
	COD = ofLiteral(REGISTRY_KEY, "minecraft:cod"),
	COMMAND_BLOCK_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:command_block_minecart"),
	COW = ofLiteral(REGISTRY_KEY, "minecraft:cow"),
	CREAKING = ofLiteral(REGISTRY_KEY, "minecraft:creaking"),
	CREEPER = ofLiteral(REGISTRY_KEY, "minecraft:creeper"),
	DARK_OAK_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:dark_oak_boat"),
	DARK_OAK_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:dark_oak_chest_boat"),
	DOLPHIN = ofLiteral(REGISTRY_KEY, "minecraft:dolphin"),
	DONKEY = ofLiteral(REGISTRY_KEY, "minecraft:donkey"),
	DRAGON_FIREBALL = ofLiteral(REGISTRY_KEY, "minecraft:dragon_fireball"),
	DROWNED = ofLiteral(REGISTRY_KEY, "minecraft:drowned"),
	EGG = ofLiteral(REGISTRY_KEY, "minecraft:egg"),
	ELDER_GUARDIAN = ofLiteral(REGISTRY_KEY, "minecraft:elder_guardian"),
	ENDERMAN = ofLiteral(REGISTRY_KEY, "minecraft:enderman"),
	ENDERMITE = ofLiteral(REGISTRY_KEY, "minecraft:endermite"),
	ENDER_DRAGON = ofLiteral(REGISTRY_KEY, "minecraft:ender_dragon"),
	ENDER_PEARL = ofLiteral(REGISTRY_KEY, "minecraft:ender_pearl"),
	END_CRYSTAL = ofLiteral(REGISTRY_KEY, "minecraft:end_crystal"),
	EVOKER = ofLiteral(REGISTRY_KEY, "minecraft:evoker"),
	EVOKER_FANGS = ofLiteral(REGISTRY_KEY, "minecraft:evoker_fangs"),
	EXPERIENCE_BOTTLE = ofLiteral(REGISTRY_KEY, "minecraft:experience_bottle"),
	EXPERIENCE_ORB = ofLiteral(REGISTRY_KEY, "minecraft:experience_orb"),
	EYE_OF_ENDER = ofLiteral(REGISTRY_KEY, "minecraft:eye_of_ender"),
	FALLING_BLOCK = ofLiteral(REGISTRY_KEY, "minecraft:falling_block"),
	FIREBALL = ofLiteral(REGISTRY_KEY, "minecraft:fireball"),
	FIREWORK_ROCKET = ofLiteral(REGISTRY_KEY, "minecraft:firework_rocket"),
	FISHING_BOBBER = ofLiteral(REGISTRY_KEY, "minecraft:fishing_bobber"),
	FOX = ofLiteral(REGISTRY_KEY, "minecraft:fox"),
	FROG = ofLiteral(REGISTRY_KEY, "minecraft:frog"),
	FURNACE_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:furnace_minecart"),
	GHAST = ofLiteral(REGISTRY_KEY, "minecraft:ghast"),
	GIANT = ofLiteral(REGISTRY_KEY, "minecraft:giant"),
	GLOW_ITEM_FRAME = ofLiteral(REGISTRY_KEY, "minecraft:glow_item_frame"),
	GLOW_SQUID = ofLiteral(REGISTRY_KEY, "minecraft:glow_squid"),
	GOAT = ofLiteral(REGISTRY_KEY, "minecraft:goat"),
	GUARDIAN = ofLiteral(REGISTRY_KEY, "minecraft:guardian"),
	HAPPY_GHAST = ofLiteral(REGISTRY_KEY, "minecraft:happy_ghast"),
	HOGLIN = ofLiteral(REGISTRY_KEY, "minecraft:hoglin"),
	HOPPER_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:hopper_minecart"),
	HORSE = ofLiteral(REGISTRY_KEY, "minecraft:horse"),
	HUSK = ofLiteral(REGISTRY_KEY, "minecraft:husk"),
	ILLUSIONER = ofLiteral(REGISTRY_KEY, "minecraft:illusioner"),
	INTERACTION = ofLiteral(REGISTRY_KEY, "minecraft:interaction"),
	IRON_GOLEM = ofLiteral(REGISTRY_KEY, "minecraft:iron_golem"),
	ITEM = ofLiteral(REGISTRY_KEY, "minecraft:item"),
	ITEM_DISPLAY = ofLiteral(REGISTRY_KEY, "minecraft:item_display"),
	ITEM_FRAME = ofLiteral(REGISTRY_KEY, "minecraft:item_frame"),
	JUNGLE_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:jungle_boat"),
	JUNGLE_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:jungle_chest_boat"),
	LEASH_KNOT = ofLiteral(REGISTRY_KEY, "minecraft:leash_knot"),
	LIGHTNING_BOLT = ofLiteral(REGISTRY_KEY, "minecraft:lightning_bolt"),
	LINGERING_POTION = ofLiteral(REGISTRY_KEY, "minecraft:lingering_potion"),
	LLAMA = ofLiteral(REGISTRY_KEY, "minecraft:llama"),
	LLAMA_SPIT = ofLiteral(REGISTRY_KEY, "minecraft:llama_spit"),
	MAGMA_CUBE = ofLiteral(REGISTRY_KEY, "minecraft:magma_cube"),
	MANGROVE_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:mangrove_boat"),
	MANGROVE_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:mangrove_chest_boat"),
	MARKER = ofLiteral(REGISTRY_KEY, "minecraft:marker"),
	MINECART = ofLiteral(REGISTRY_KEY, "minecraft:minecart"),
	MOOSHROOM = ofLiteral(REGISTRY_KEY, "minecraft:mooshroom"),
	MULE = ofLiteral(REGISTRY_KEY, "minecraft:mule"),
	OAK_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:oak_boat"),
	OAK_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:oak_chest_boat"),
	OCELOT = ofLiteral(REGISTRY_KEY, "minecraft:ocelot"),
	OMINOUS_ITEM_SPAWNER = ofLiteral(REGISTRY_KEY, "minecraft:ominous_item_spawner"),
	PAINTING = ofLiteral(REGISTRY_KEY, "minecraft:painting"),
	PALE_OAK_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:pale_oak_boat"),
	PALE_OAK_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:pale_oak_chest_boat"),
	PANDA = ofLiteral(REGISTRY_KEY, "minecraft:panda"),
	PARROT = ofLiteral(REGISTRY_KEY, "minecraft:parrot"),
	PHANTOM = ofLiteral(REGISTRY_KEY, "minecraft:phantom"),
	PIG = ofLiteral(REGISTRY_KEY, "minecraft:pig"),
	PIGLIN = ofLiteral(REGISTRY_KEY, "minecraft:piglin"),
	PIGLIN_BRUTE = ofLiteral(REGISTRY_KEY, "minecraft:piglin_brute"),
	PILLAGER = ofLiteral(REGISTRY_KEY, "minecraft:pillager"),
	PLAYER = ofLiteral(REGISTRY_KEY, "minecraft:player"),
	POLAR_BEAR = ofLiteral(REGISTRY_KEY, "minecraft:polar_bear"),
	PUFFERFISH = ofLiteral(REGISTRY_KEY, "minecraft:pufferfish"),
	RABBIT = ofLiteral(REGISTRY_KEY, "minecraft:rabbit"),
	RAVAGER = ofLiteral(REGISTRY_KEY, "minecraft:ravager"),
	SALMON = ofLiteral(REGISTRY_KEY, "minecraft:salmon"),
	SHEEP = ofLiteral(REGISTRY_KEY, "minecraft:sheep"),
	SHULKER = ofLiteral(REGISTRY_KEY, "minecraft:shulker"),
	SHULKER_BULLET = ofLiteral(REGISTRY_KEY, "minecraft:shulker_bullet"),
	SILVERFISH = ofLiteral(REGISTRY_KEY, "minecraft:silverfish"),
	SKELETON = ofLiteral(REGISTRY_KEY, "minecraft:skeleton"),
	SKELETON_HORSE = ofLiteral(REGISTRY_KEY, "minecraft:skeleton_horse"),
	SLIME = ofLiteral(REGISTRY_KEY, "minecraft:slime"),
	SMALL_FIREBALL = ofLiteral(REGISTRY_KEY, "minecraft:small_fireball"),
	SNIFFER = ofLiteral(REGISTRY_KEY, "minecraft:sniffer"),
	SNOWBALL = ofLiteral(REGISTRY_KEY, "minecraft:snowball"),
	SNOW_GOLEM = ofLiteral(REGISTRY_KEY, "minecraft:snow_golem"),
	SPAWNER_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:spawner_minecart"),
	SPECTRAL_ARROW = ofLiteral(REGISTRY_KEY, "minecraft:spectral_arrow"),
	SPIDER = ofLiteral(REGISTRY_KEY, "minecraft:spider"),
	SPLASH_POTION = ofLiteral(REGISTRY_KEY, "minecraft:splash_potion"),
	SPRUCE_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:spruce_boat"),
	SPRUCE_CHEST_BOAT = ofLiteral(REGISTRY_KEY, "minecraft:spruce_chest_boat"),
	SQUID = ofLiteral(REGISTRY_KEY, "minecraft:squid"),
	STRAY = ofLiteral(REGISTRY_KEY, "minecraft:stray"),
	STRIDER = ofLiteral(REGISTRY_KEY, "minecraft:strider"),
	TADPOLE = ofLiteral(REGISTRY_KEY, "minecraft:tadpole"),
	TEXT_DISPLAY = ofLiteral(REGISTRY_KEY, "minecraft:text_display"),
	TNT = ofLiteral(REGISTRY_KEY, "minecraft:tnt"),
	TNT_MINECART = ofLiteral(REGISTRY_KEY, "minecraft:tnt_minecart"),
	TRADER_LLAMA = ofLiteral(REGISTRY_KEY, "minecraft:trader_llama"),
	TRIDENT = ofLiteral(REGISTRY_KEY, "minecraft:trident"),
	TROPICAL_FISH = ofLiteral(REGISTRY_KEY, "minecraft:tropical_fish"),
	TURTLE = ofLiteral(REGISTRY_KEY, "minecraft:turtle"),
	VEX = ofLiteral(REGISTRY_KEY, "minecraft:vex"),
	VILLAGER = ofLiteral(REGISTRY_KEY, "minecraft:villager"),
	VINDICATOR = ofLiteral(REGISTRY_KEY, "minecraft:vindicator"),
	WANDERING_TRADER = ofLiteral(REGISTRY_KEY, "minecraft:wandering_trader"),
	WARDEN = ofLiteral(REGISTRY_KEY, "minecraft:warden"),
	WIND_CHARGE = ofLiteral(REGISTRY_KEY, "minecraft:wind_charge"),
	WITCH = ofLiteral(REGISTRY_KEY, "minecraft:witch"),
	WITHER = ofLiteral(REGISTRY_KEY, "minecraft:wither"),
	WITHER_SKELETON = ofLiteral(REGISTRY_KEY, "minecraft:wither_skeleton"),
	WITHER_SKULL = ofLiteral(REGISTRY_KEY, "minecraft:wither_skull"),
	WOLF = ofLiteral(REGISTRY_KEY, "minecraft:wolf"),
	ZOGLIN = ofLiteral(REGISTRY_KEY, "minecraft:zoglin"),
	ZOMBIE = ofLiteral(REGISTRY_KEY, "minecraft:zombie"),
	ZOMBIE_HORSE = ofLiteral(REGISTRY_KEY, "minecraft:zombie_horse"),
	ZOMBIE_VILLAGER = ofLiteral(REGISTRY_KEY, "minecraft:zombie_villager"),
	ZOMBIFIED_PIGLIN = ofLiteral(REGISTRY_KEY, "minecraft:zombified_piglin");
	
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
