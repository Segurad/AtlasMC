package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Represents all component types known to the client custom ones are not included.
 */
@RegistryHolder(key = "atlas:component_type", target = Target.PROTOCOL)
public class ComponentType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<ComponentType> REGISTRY_KEY = Registries.getRegistryKey(ComponentType.class);
	
	public static final NamespacedKey
	CUSTOM_DATA = NamespacedKey.literal("minecraft:custom_data"),
	MAX_STACK_SIZE = NamespacedKey.literal("minecraft:max_stack_size"),
	MAX_DAMAGE = NamespacedKey.literal("minecraft:max_damage"),
	DAMAGE = NamespacedKey.literal("minecraft:damage"),
	UNBREAKABLE = NamespacedKey.literal("minecraft:unbreakable"),
	CUSTOM_NAME = NamespacedKey.literal("minecraft:custom_name"),
	ITEM_NAME = NamespacedKey.literal("minecraft:item_name"),
	ITEM_MODEL = NamespacedKey.literal("minecraft:item_model"),
	LORE = NamespacedKey.literal("minecraft:lore"),
	RARITY = NamespacedKey.literal("minecraft:rarity"),
	ENCHANTMENTS = NamespacedKey.literal("minecraft:enchantments"),
	CAN_PLACE_ON = NamespacedKey.literal("minecraft:can_place_on"),
	CAN_BREAK = NamespacedKey.literal("minecraft:can_break"),
	ATTRIBUTE_MODIFIERS = NamespacedKey.literal("minecraft:attribute_modifiers"),
	CUSTOM_MODEL_DATA = NamespacedKey.literal("minecraft:custom_model_data"),
	REPAIR_COST = NamespacedKey.literal("minecraft:repair_cost"),
	CREATIVE_SLOT_LOCK = NamespacedKey.literal("minecraft:creative_slot_lock"),
	ENCHANTMENT_GLINT_OVERRIDE = NamespacedKey.literal("minecraft:enchantment_glint_override"),
	INTANGIBLE_PROJECTILE = NamespacedKey.literal("minecraft:intangible_projectile"),
	FOOD = NamespacedKey.literal("minecraft:food"),
	CONSUMABLE = NamespacedKey.literal("minecraft:consumable"),
	USE_REMAINDER = NamespacedKey.literal("minecraft:use_remainder"),
	USE_COOLDOWN = NamespacedKey.literal("minecraft:use_cooldown"),
	DAMAGE_RESISTANT = NamespacedKey.literal("minecraft:damage_resistant"),
	TOOL = NamespacedKey.literal("minecraft:tool"),
	ENCHANTABLE = NamespacedKey.literal("minecraft:enchantable"),
	EQUIPPABLE = NamespacedKey.literal("minecraft:equippable"),
	REPAIRABLE = NamespacedKey.literal("minecraft:repairable"),
	GLIDER = NamespacedKey.literal("minecraft:glider"),
	TOOLTIP_STYLE = NamespacedKey.literal("minecraft:tooltip_style"),
	DEATH_PROTECTION = NamespacedKey.literal("minecraft:death_protection"),
	STORED_ENCHANTMENTS = NamespacedKey.literal("minecraft:stored_enchantments"),
	DYED_COLOR = NamespacedKey.literal("minecraft:dyed_color"),
	MAP_COLOR = NamespacedKey.literal("minecraft:map_color"),
	MAP_ID = NamespacedKey.literal("minecraft:map_id"),
	MAP_DECORATIONS = NamespacedKey.literal("minecraft:map_decorations"),
	MAP_POST_PROCESSING = NamespacedKey.literal("minecraft:map_post_processing"),
	CHARGED_PROJECTILES = NamespacedKey.literal("minecraft:charged_projectiles"),
	BUNDLE_CONTENTS = NamespacedKey.literal("minecraft:bundle_contents"),
	POTION_CONTENTS = NamespacedKey.literal("minecraft:potion_contents"),
	SUSPICIOUS_STEW_EFFECTS = NamespacedKey.literal("minecraft:suspicious_stew_effects"),
	WRITABLE_BOOK_CONTENT = NamespacedKey.literal("minecraft:writable_book_content"),
	WRITTEN_BOOK_CONTENT = NamespacedKey.literal("minecraft:written_book_content"),
	TRIM = NamespacedKey.literal("minecraft:trim"),
	DEBUG_STICK_STATE = NamespacedKey.literal("minecraft:debug_stick_state"),
	ENTITY_DATA = NamespacedKey.literal("minecraft:entity_data"),
	BUCKET_ENTITY_DATA = NamespacedKey.literal("minecraft:bucket_entity_data"),
	BLOCK_ENTITY_DATA = NamespacedKey.literal("minecraft:block_entity_data"),
	INSTRUMENT = NamespacedKey.literal("minecraft:instrument"),
	OMINOUS_BOTTLE_AMPLIFIER = NamespacedKey.literal("minecraft:ominous_bottle_amplifier"),
	JUKEBOX_PLAYABLE = NamespacedKey.literal("minecraft:jukebox_playable"),
	RECIPES = NamespacedKey.literal("minecraft:recipes"),
	LODESTONE_TRACKER = NamespacedKey.literal("minecraft:lodestone_tracker"),
	FIREWORK_EXPLOSION = NamespacedKey.literal("minecraft:firework_explosion"),
	FIREWORKS = NamespacedKey.literal("minecraft:fireworks"),
	PROFILE = NamespacedKey.literal("minecraft:profile"),
	NOTE_BLOCK_SOUND = NamespacedKey.literal("minecraft:note_block_sound"),
	BANNER_PATTERNS = NamespacedKey.literal("minecraft:banner_patterns"),
	BASE_COLOR = NamespacedKey.literal("minecraft:base_color"),
	POT_DECORATIONS = NamespacedKey.literal("minecraft:pot_decorations"),
	CONTAINER = NamespacedKey.literal("minecraft:container"),
	BLOCK_STATE = NamespacedKey.literal("minecraft:block_state"),
	BEES = NamespacedKey.literal("minecraft:bees"),
	LOCK = NamespacedKey.literal("minecraft:lock"),
	CONTAINER_LOOT = NamespacedKey.literal("minecraft:container_loot"),
	BREAK_SOUND = NamespacedKey.literal("minecraft:break_sound"),
	BLOCKS_ATTACKS = NamespacedKey.literal("minecraft:blocks_attacks"),
	IDENTIFIER = NamespacedKey.literal("atlas:identifier"),
	POTION_DURATION_SCALE = NamespacedKey.literal("minecraft:potion_duration_scale"),
	PROVIDES_BANNER_PATTERNS = NamespacedKey.literal("minecraft:provides_banner_patterns"),
	PROVIDES_TRIM_MATERIAL = NamespacedKey.literal("minecraft:provides_trim_material"),
	TOOLTIP_DISPLAY = NamespacedKey.literal("minecraft:tooltip_display"),
	WEAPON = NamespacedKey.literal("minecraft:weapon");

	private ItemComponentFactory factory;
	
	public ComponentType(ConfigurationSection cfg) {
		super(cfg);
		Registry<ItemComponentFactory> registry = Registries.getRegistry(ItemComponentFactory.class);
		String factoryKey = cfg.getString("factory", "");
		factory = registry.getOrDefault(factoryKey);
	}
	
	public ItemComponent createItemComponent() {
		return factory != null ? factory.createComponent(this) : null;
	}
	
	public ItemComponentFactory getFactory() {
		return factory;
	}
	
	public void setFactory(ItemComponentFactory factory) {
		this.factory = factory;
	}
	
	public static ComponentType get(NamespacedKey key) {
		return getRegistry().get(key);
	}
	
	public static ComponentType nget(CharSequence key) {
		return getRegistry().get(key);
	}
	
	public static ComponentType getByID(int id) {
		return getRegistry().getByID(id);
	}
	
	public static ProtocolRegistry<ComponentType> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}

}
