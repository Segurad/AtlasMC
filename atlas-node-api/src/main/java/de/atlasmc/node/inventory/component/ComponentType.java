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
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

/**
 * Represents all component types known to the client custom ones are not included.
 */
@RegistryHolder(key = "minecraft:data_component_type", target = Target.PROTOCOL)
public class ComponentType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<ComponentType> REGISTRY_KEY = Registries.getRegistryKey(ComponentType.class);
	
	public static final RegistryValueKey<ComponentType>
	ATTRIBUTE_MODIFIERS = ofLiteral(REGISTRY_KEY, "minecraft:attribute_modifiers"),
	AXOLOTL_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:axolotl/variant"),
	BANNER_PATTERNS = ofLiteral(REGISTRY_KEY, "minecraft:banner_patterns"),
	BASE_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:base_color"),
	BEES = ofLiteral(REGISTRY_KEY, "minecraft:bees"),
	BLOCKS_ATTACKS = ofLiteral(REGISTRY_KEY, "minecraft:blocks_attacks"),
	BLOCK_ENTITY_DATA = ofLiteral(REGISTRY_KEY, "minecraft:block_entity_data"),
	BLOCK_STATE = ofLiteral(REGISTRY_KEY, "minecraft:block_state"),
	BREAK_SOUND = ofLiteral(REGISTRY_KEY, "minecraft:break_sound"),
	BUCKET_ENTITY_DATA = ofLiteral(REGISTRY_KEY, "minecraft:bucket_entity_data"),
	BUNDLE_CONTENTS = ofLiteral(REGISTRY_KEY, "minecraft:bundle_contents"),
	CAN_BREAK = ofLiteral(REGISTRY_KEY, "minecraft:can_break"),
	CAN_PLACE_ON = ofLiteral(REGISTRY_KEY, "minecraft:can_place_on"),
	CAT_COLLAR = ofLiteral(REGISTRY_KEY, "minecraft:cat/collar"),
	CAT_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:cat/variant"),
	CHARGED_PROJECTILES = ofLiteral(REGISTRY_KEY, "minecraft:charged_projectiles"),
	CHICKEN_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:chicken/variant"),
	CONSUMABLE = ofLiteral(REGISTRY_KEY, "minecraft:consumable"),
	CONTAINER = ofLiteral(REGISTRY_KEY, "minecraft:container"),
	CONTAINER_LOOT = ofLiteral(REGISTRY_KEY, "minecraft:container_loot"),
	COW_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:cow/variant"),
	CREATIVE_SLOT_LOCK = ofLiteral(REGISTRY_KEY, "minecraft:creative_slot_lock"),
	CUSTOM_DATA = ofLiteral(REGISTRY_KEY, "minecraft:custom_data"),
	CUSTOM_MODEL_DATA = ofLiteral(REGISTRY_KEY, "minecraft:custom_model_data"),
	CUSTOM_NAME = ofLiteral(REGISTRY_KEY, "minecraft:custom_name"),
	DAMAGE = ofLiteral(REGISTRY_KEY, "minecraft:damage"),
	DAMAGE_RESISTANT = ofLiteral(REGISTRY_KEY, "minecraft:damage_resistant"),
	DEATH_PROTECTION = ofLiteral(REGISTRY_KEY, "minecraft:death_protection"),
	DEBUG_STICK_STATE = ofLiteral(REGISTRY_KEY, "minecraft:debug_stick_state"),
	DYED_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:dyed_color"),
	ENCHANTABLE = ofLiteral(REGISTRY_KEY, "minecraft:enchantable"),
	ENCHANTMENTS = ofLiteral(REGISTRY_KEY, "minecraft:enchantments"),
	ENCHANTMENT_GLINT_OVERRIDE = ofLiteral(REGISTRY_KEY, "minecraft:enchantment_glint_override"),
	ENTITY_DATA = ofLiteral(REGISTRY_KEY, "minecraft:entity_data"),
	EQUIPPABLE = ofLiteral(REGISTRY_KEY, "minecraft:equippable"),
	FIREWORKS = ofLiteral(REGISTRY_KEY, "minecraft:fireworks"),
	FIREWORK_EXPLOSION = ofLiteral(REGISTRY_KEY, "minecraft:firework_explosion"),
	FOOD = ofLiteral(REGISTRY_KEY, "minecraft:food"),
	FOX_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:fox/variant"),
	FROG_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:frog/variant"),
	GLIDER = ofLiteral(REGISTRY_KEY, "minecraft:glider"),
	HORSE_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:horse/variant"),
	INSTRUMENT = ofLiteral(REGISTRY_KEY, "minecraft:instrument"),
	INTANGIBLE_PROJECTILE = ofLiteral(REGISTRY_KEY, "minecraft:intangible_projectile"),
	ITEM_MODEL = ofLiteral(REGISTRY_KEY, "minecraft:item_model"),
	ITEM_NAME = ofLiteral(REGISTRY_KEY, "minecraft:item_name"),
	JUKEBOX_PLAYABLE = ofLiteral(REGISTRY_KEY, "minecraft:jukebox_playable"),
	LLAMA_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:llama/variant"),
	LOCK = ofLiteral(REGISTRY_KEY, "minecraft:lock"),
	LODESTONE_TRACKER = ofLiteral(REGISTRY_KEY, "minecraft:lodestone_tracker"),
	LORE = ofLiteral(REGISTRY_KEY, "minecraft:lore"),
	MAP_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:map_color"),
	MAP_DECORATIONS = ofLiteral(REGISTRY_KEY, "minecraft:map_decorations"),
	MAP_ID = ofLiteral(REGISTRY_KEY, "minecraft:map_id"),
	MAP_POST_PROCESSING = ofLiteral(REGISTRY_KEY, "minecraft:map_post_processing"),
	MAX_DAMAGE = ofLiteral(REGISTRY_KEY, "minecraft:max_damage"),
	MAX_STACK_SIZE = ofLiteral(REGISTRY_KEY, "minecraft:max_stack_size"),
	MOOSHROOM_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:mooshroom/variant"),
	NOTE_BLOCK_SOUND = ofLiteral(REGISTRY_KEY, "minecraft:note_block_sound"),
	OMINOUS_BOTTLE_AMPLIFIER = ofLiteral(REGISTRY_KEY, "minecraft:ominous_bottle_amplifier"),
	PAINTING_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:painting/variant"),
	PARROT_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:parrot/variant"),
	PIG_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:pig/variant"),
	POTION_CONTENTS = ofLiteral(REGISTRY_KEY, "minecraft:potion_contents"),
	POTION_DURATION_SCALE = ofLiteral(REGISTRY_KEY, "minecraft:potion_duration_scale"),
	POT_DECORATIONS = ofLiteral(REGISTRY_KEY, "minecraft:pot_decorations"),
	PROFILE = ofLiteral(REGISTRY_KEY, "minecraft:profile"),
	PROVIDES_BANNER_PATTERNS = ofLiteral(REGISTRY_KEY, "minecraft:provides_banner_patterns"),
	PROVIDES_TRIM_MATERIAL = ofLiteral(REGISTRY_KEY, "minecraft:provides_trim_material"),
	RABBIT_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:rabbit/variant"),
	RARITY = ofLiteral(REGISTRY_KEY, "minecraft:rarity"),
	RECIPES = ofLiteral(REGISTRY_KEY, "minecraft:recipes"),
	REPAIRABLE = ofLiteral(REGISTRY_KEY, "minecraft:repairable"),
	REPAIR_COST = ofLiteral(REGISTRY_KEY, "minecraft:repair_cost"),
	SALMON_SIZE = ofLiteral(REGISTRY_KEY, "minecraft:salmon/size"),
	SHEEP_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:sheep/color"),
	SHULKER_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:shulker/color"),
	STORED_ENCHANTMENTS = ofLiteral(REGISTRY_KEY, "minecraft:stored_enchantments"),
	SUSPICIOUS_STEW_EFFECTS = ofLiteral(REGISTRY_KEY, "minecraft:suspicious_stew_effects"),
	TOOL = ofLiteral(REGISTRY_KEY, "minecraft:tool"),
	TOOLTIP_DISPLAY = ofLiteral(REGISTRY_KEY, "minecraft:tooltip_display"),
	TOOLTIP_STYLE = ofLiteral(REGISTRY_KEY, "minecraft:tooltip_style"),
	TRIM = ofLiteral(REGISTRY_KEY, "minecraft:trim"),
	TROPICAL_FISH_BASE_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:tropical_fish/base_color"),
	TROPICAL_FISH_PATTERN = ofLiteral(REGISTRY_KEY, "minecraft:tropical_fish/pattern"),
	TROPICAL_FISH_PATTERN_COLOR = ofLiteral(REGISTRY_KEY, "minecraft:tropical_fish/pattern_color"),
	UNBREAKABLE = ofLiteral(REGISTRY_KEY, "minecraft:unbreakable"),
	USE_COOLDOWN = ofLiteral(REGISTRY_KEY, "minecraft:use_cooldown"),
	USE_REMAINDER = ofLiteral(REGISTRY_KEY, "minecraft:use_remainder"),
	VILLAGER_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:villager/variant"),
	WEAPON = ofLiteral(REGISTRY_KEY, "minecraft:weapon"),
	WOLF_COLLAR = ofLiteral(REGISTRY_KEY, "minecraft:wolf/collar"),
	WOLF_SOUND_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:wolf/sound_variant"),
	WOLF_VARIANT = ofLiteral(REGISTRY_KEY, "minecraft:wolf/variant"),
	WRITABLE_BOOK_CONTENT = ofLiteral(REGISTRY_KEY, "minecraft:writable_book_content"),
	WRITTEN_BOOK_CONTENT = ofLiteral(REGISTRY_KEY, "minecraft:written_book_content");
	
	// non standard
	public static final RegistryValueKey<ComponentType>
	IDENTIFIER = ofLiteral(REGISTRY_KEY, "atlas:identifier");

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
	
	public static ComponentType get(CharSequence key) {
		return getRegistry().get(key);
	}
	
	public static ComponentType getByID(int id) {
		return getRegistry().getByID(id);
	}
	
	public static ProtocolRegistry<ComponentType> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}

}
