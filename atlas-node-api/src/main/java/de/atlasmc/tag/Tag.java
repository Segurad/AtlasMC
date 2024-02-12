package de.atlasmc.tag;

import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.entity.EntityType;
import de.atlasmc.io.IOWriteable;

public abstract class Tag<E> implements Namespaced, IOWriteable {
	
	public static Tag<Material>
		ACACIA_LOGS,
		ANVIL,
		ITEM_ARROWS,
		BAMBOO_PLANTABLE_ON,
		BANNERS,
		BEACON_PAYMENT_ITEMS,
		BOATS,
		BASE_STONE_NETHER,
		BASE_STONE_OVERWORLD,
		BEACON_BASE_BLOCKS,
		BEDS,
		BEEHIVES,
		BEE_GROWABLES,
		BIRCH_GROWABLES,
		BIRCH_LOGS,
		BUTTONS,
		CAMPFIRES,
		CARPETS,
		CLIMBABLE,
		CORALS,
		CORAL_BLOCKS,
		CORAL_PLANTS,
		CREEPER_DROP_MUSIC_DISCS,
		FISHES,
		LECTERN_BOOKS,
		MUSIC_DISCS,
		CRIMSON_STEMS,
		CROPS,
		DARK_OAK_LOGS,
		DOORS,
		DRAGON_IMMUNE,
		ENDER_HOLDABLE,
		FENCE_GATES,
		FENCES,
		FIRE,
		FLOWERS,
		FLOWER_POTS,
		GOLD_ORES,
		GUARDED_BY_PIGLINS,
		HOGLIN_REPELLENTS,
		ICE,
		IMPERMEABLE,
		INFINIBURN_END,
		INFINIBURN_NETHER,
		INFINIBURN_OVERWORLD,
		JUNGLE_LOGS,
		LEAVES,
		LOGS,
		LOGS_THAT_BURN,
		MUSHROOM_GROW_BLOCK,
		NON_FLAMEABLE_WOOD,
		NYLIUM,
		OAK_LOGS,
		PIGLIN_REPELLENTS,
		PLANKS,
		PORTALS,
		PRESSURE_PLATES,
		PREVENT_MOB_SPAWNING_INSIDE,
		RAILS,
		SAND,
		SAPLINGS,
		SHULKER_BOXES,
		SIGNS,
		SLABS,
		SMALL_FLOWERS,
		SOUL_FIRE_BASE_BLOCKS,
		SOUL_SPEED_BLOCKS,
		SPRUCE_LOGS,
		STAIRS,
		STANDING_SIGNS,
		STONE_BRICKS,
		STONE_PRESSURE_PLATES,
		STRIDER_WARM_BLOCKS,
		TALL_FLOWERS,
		TRAPOORS,
		UNDERWATER_BONEMEALS,
		UNSTABLE_BOTTOM_CENTER,
		VALID_SPAWN,
		WALLS,
		WALL_CORALS,
		WALL_POST_OVERRIDE,
		WALL_SIGNS,
		WARPED_STEMS,
		WART_BLOCKS,
		WITHER_IMMUNE,
		WITHER_SUMMON_BASE_BLOCKS,
		WOODEN_BUTTONS,
		WOODEN_DOORS,
		WOODEN_FENCES,
		WOODEN_SLABS,
		WOODEN_STAIRS,
		WOODEN_TRAPDOORS,
		WOOL;
		
	public static Tag<EntityType>
		ARROWS,
		BEEHIVE_INHABITORS,
		IMPACT_PROJECTILES,
		RAIDERS,
		SKELETONS;
	
	private final NamespacedKey type;
	private final NamespacedKey identifier;
	
	public Tag(NamespacedKey type, NamespacedKey identifier) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (identifier == null)
			throw new IllegalArgumentException("Identifier can not be null!");
		this.type = type;
		this.identifier = identifier;
	}
	
	public final NamespacedKey getType() {
		return type;
	}
	
	@Override
	public final NamespacedKey getNamespacedKey() {
		return identifier;
	}
	
	public abstract boolean isTaged(E element);
	
	public abstract boolean isTaged(int elementID);
	
	public abstract Set<E> getValues();
	
	public abstract boolean tag(E element);
	
	public abstract boolean tag(int elementID);
	
}
