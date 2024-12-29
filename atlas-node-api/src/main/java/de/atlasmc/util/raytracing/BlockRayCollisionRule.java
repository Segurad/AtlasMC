package de.atlasmc.util.raytracing;

import de.atlasmc.Material;
import de.atlasmc.MaterialTags;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.tag.Tags;

@FunctionalInterface
public interface BlockRayCollisionRule {

	public static BlockRayCollisionRule
	IGNORE_AIR = (data) -> {
		Material material = data.getMaterial();
		return Tags.isTagged(MaterialTags.AIR, material);
	},
	IGNORE_FUID_AND_AIR = (data) -> {
		Material material = data.getMaterial();
		return Tags.isTagged(MaterialTags.AIR, material) || Tags.isTagged(MaterialTags.FLUID, material);
	};
	
	public boolean isValid(BlockData data);

}
