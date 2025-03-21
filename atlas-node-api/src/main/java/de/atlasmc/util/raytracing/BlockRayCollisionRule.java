package de.atlasmc.util.raytracing;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.BlockTypeTags;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.tag.Tags;

@FunctionalInterface
public interface BlockRayCollisionRule {

	public static BlockRayCollisionRule
	IGNORE_AIR = (data) -> {
		BlockType type = data.getType();
		return Tags.isTagged(BlockTypeTags.AIR, type);
	},
	IGNORE_FUID_AND_AIR = (data) -> {
		BlockType type = data.getType();
		return Tags.isTagged(BlockTypeTags.AIR, type) || Tags.isTagged(BlockTypeTags.FLUID, type);
	};
	
	public boolean isValid(BlockData data);

}
