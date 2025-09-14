package de.atlasmc.node.util.raytracing;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.BlockTypeTags;
import de.atlasmc.node.block.data.BlockData;
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
