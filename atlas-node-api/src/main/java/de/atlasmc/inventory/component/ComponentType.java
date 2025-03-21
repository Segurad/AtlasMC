package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

/**
 * Represents all component types known to the client custom ones are not included.
 */
public enum ComponentType implements EnumID, EnumValueCache, EnumName {
	
	CUSTOM_DATA("minecraft:custom_data"),
	MAX_STACK_SIZE("minecraft:max_stack_size"),
	MAX_DAMAGE("minecraft:max_damage"),
	DAMAGE("minecraft:damage"),
	UNBREAKABLE("minecraft:unbreakable"),
	CUSTOM_NAME("minecraft:custom_name"),
	ITEM_NAME("minecraft:item_name"),
	ITEM_MODEL("minecraft:item_model"),
	LORE("minecraft:lore"),
	RARITY("minecraft:rarity"),
	ENCHANTMENTS("minecraft:enchantments"),
	CAN_PLACE_ON("minecraft:can_place_on"),
	CAN_BREAK("minecraft:can_break"),
	ATTRIBUTE_MODIFIERS("minecraft:attribute_modifiers"),
	CUSTOM_MODEL_DATA("minecraft:custom_model_data"),
	HIDE_ADDITIONAL_TOOLTIP("minecraft:hide_additional_tooltip"),
	HIDE_TOOLTIP("minecraft:hide_tooltip"),
	REPAIR_COST("minecraft:repair_cost"),
	CREATIVE_SLOT_LOCK("minecraft:creative_slot_lock"),
	ENCHANTMENT_GLINT_OVERRIDE("minecraft:enchantment_glint_override"),
	INTANGIBLE_PROJECTILE("minecraft:intangible_projectile"),
	FOOD("minecraft:food"),
	CONSUMABLE("minecraft:consumable"),
	USE_REMAINDER("minecraft:use_remainder"),
	USE_COOLDOWN("minecraft:use_cooldown"),
	DAMAGE_RESISTANT("minecraft:damage_resistant"),
	TOOL("minecraft:tool"),
	ENCHANTABLE("minecraft:enchantable"),
	EQUIPPABLE("minecraft:equippable"),
	REPAIRABLE("minecraft:repairable"),
	GLIDER("minecraft:glider"),
	TOOLTIP_STYLE("minecraft:tooltip_style"),
	DEATH_PROTECTION("minecraft:death_protection"),
	STORED_ENCHANTMENTS("minecraft:stored_enchantments"),
	DYED_COLOR("minecraft:dyed_color"),
	MAP_COLOR("minecraft:map_color"),
	MAP_ID("minecraft:map_id"),
	MAP_DECORATIONS("minecraft:map_decorations"),
	MAP_POST_PROCESSING("minecraft:map_post_processing"),
	CHARGED_PROJECTILES("minecraft:charged_projectiles"),
	BUNDLE_CONTENTS("minecraft:bundle_contents"),
	POTION_CONTENTS("minecraft:potion_contents"),
	SUSPICIOUS_STEW_EFFECTS("minecraft:suspicious_stew_effects"),
	WRITABLE_BOOK_CONTENT("minecraft:writable_book_content"),
	WRITTEN_BOOK_CONTENT("minecraft:written_book_content"),
	TRIM("minecraft:trim"),
	DEBUG_STICK_STATE("minecraft:debug_stick_state"),
	ENTITY_DATA("minecraft:entity_data"),
	BUCKET_ENTITY_DATA("minecraft:bucket_entity_data"),
	BLOCK_ENTITY_DATA("minecraft:block_entity_data"),
	INSTRUMENT("minecraft:instrument"),
	OMINOUS_BOTTLE_AMPLIFIER("minecraft:ominous_bottle_amplifier"),
	JUKEBOX_PLAYABLE("minecraft:jukebox_playable"),
	RECIPES("minecraft:recipes"),
	LODESTONE_TRACKER("minecraft:lodestone_tracker"),
	FIREWORK_EXPLOSION("minecraft:firework_explosion"),
	FIREWORKS("minecraft:fireworks"),
	PROFILE("minecraft:profile"),
	NOTE_BLOCK_SOUND("minecraft:note_block_sound"),
	BANNER_PATTERNS("minecraft:banner_patterns"),
	BASE_COLOR("minecraft:base_color"),
	POT_DECORATIONS("minecraft:pot_decorations"),
	CONTAINER("minecraft:container"),
	BLOCK_STATE("minecraft:block_state"),
	BEES("minecraft:bees"),
	LOCK("minecraft:lock"),
	CONTAINER_LOOT("minecraft:container_loot");
	
	private static List<ComponentType> VALUES;
	
	private NamespacedKey key;
	
	private ComponentType(String key) {
		this.key = NamespacedKey.literal(key);
	}
	
	public NamespacedKey getKey() {
		return key;
	}
	
	@Override
	public int getID() {
		return ordinal();
	}
	
	public static ComponentType getByID(int id) {
		return getValues().get(id);
	}
	
	@Override
	public String getName() {
		return key.toString();
	}
	
	/**
	 * Returns the value represented by the name or null if no matching value has been found
	 * @param name the name of the value
	 * @return value or null
	 */
	public static ComponentType getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		List<ComponentType> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			ComponentType value = values.get(i);
			if (value.key.toString().equals(name)) 
				return value;
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ComponentType> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}


}
