package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Represents all component types known to the client custom ones are not included.
 */
@RegistryHolder(key = "atlas:component_type", target = Target.PROTOCOL)
public class ComponentType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<ComponentType> REGISTRY_KEY = Registries.getRegistryKey(ComponentType.class);
	
	public static final RegistryValueKey<ComponentType>
	CUSTOM_DATA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:custom_data")),
	MAX_STACK_SIZE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:max_stack_size")),
	MAX_DAMAGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:max_damage")),
	DAMAGE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:damage")),
	UNBREAKABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:unbreakable")),
	CUSTOM_NAME = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:custom_name")),
	ITEM_NAME = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:item_name")),
	ITEM_MODEL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:item_model")),
	LORE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:lore")),
	RARITY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:rarity")),
	ENCHANTMENTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:enchantments")),
	CAN_PLACE_ON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:can_place_on")),
	CAN_BREAK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:can_break")),
	ATTRIBUTE_MODIFIERS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:attribute_modifiers")),
	CUSTOM_MODEL_DATA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:custom_model_data")),
	REPAIR_COST = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:repair_cost")),
	CREATIVE_SLOT_LOCK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:creative_slot_lock")),
	ENCHANTMENT_GLINT_OVERRIDE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:enchantment_glint_override")),
	INTANGIBLE_PROJECTILE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:intangible_projectile")),
	FOOD = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:food")),
	CONSUMABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:consumable")),
	USE_REMAINDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:use_remainder")),
	USE_COOLDOWN = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:use_cooldown")),
	DAMAGE_RESISTANT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:damage_resistant")),
	TOOL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tool")),
	ENCHANTABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:enchantable")),
	EQUIPPABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:equippable")),
	REPAIRABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:repairable")),
	GLIDER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:glider")),
	TOOLTIP_STYLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tooltip_style")),
	DEATH_PROTECTION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:death_protection")),
	STORED_ENCHANTMENTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:stored_enchantments")),
	DYED_COLOR = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:dyed_color")),
	MAP_COLOR = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:map_color")),
	MAP_ID = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:map_id")),
	MAP_DECORATIONS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:map_decorations")),
	MAP_POST_PROCESSING = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:map_post_processing")),
	CHARGED_PROJECTILES = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:charged_projectiles")),
	BUNDLE_CONTENTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bundle_contents")),
	POTION_CONTENTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:potion_contents")),
	SUSPICIOUS_STEW_EFFECTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:suspicious_stew_effects")),
	WRITABLE_BOOK_CONTENT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:writable_book_content")),
	WRITTEN_BOOK_CONTENT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:written_book_content")),
	TRIM = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:trim")),
	DEBUG_STICK_STATE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:debug_stick_state")),
	ENTITY_DATA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:entity_data")),
	BUCKET_ENTITY_DATA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bucket_entity_data")),
	BLOCK_ENTITY_DATA = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:block_entity_data")),
	INSTRUMENT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:instrument")),
	OMINOUS_BOTTLE_AMPLIFIER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:ominous_bottle_amplifier")),
	JUKEBOX_PLAYABLE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:jukebox_playable")),
	RECIPES = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:recipes")),
	LODESTONE_TRACKER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:lodestone_tracker")),
	FIREWORK_EXPLOSION = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:firework_explosion")),
	FIREWORKS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:fireworks")),
	PROFILE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:profile")),
	NOTE_BLOCK_SOUND = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:note_block_sound")),
	BANNER_PATTERNS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:banner_patterns")),
	BASE_COLOR = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:base_color")),
	POT_DECORATIONS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:pot_decorations")),
	CONTAINER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:container")),
	BLOCK_STATE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:block_state")),
	BEES = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:bees")),
	LOCK = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:lock")),
	CONTAINER_LOOT = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:container_loot")),
	BREAK_SOUND = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:break_sound")),
	BLOCKS_ATTACKS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:blocks_attacks")),
	IDENTIFIER = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("atlas:identifier")),
	POTION_DURATION_SCALE = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:potion_duration_scale")),
	PROVIDES_BANNER_PATTERNS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:provides_banner_patterns")),
	PROVIDES_TRIM_MATERIAL = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:provides_trim_material")),
	TOOLTIP_DISPLAY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:tooltip_display")),
	WEAPON = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:weapon"));

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
