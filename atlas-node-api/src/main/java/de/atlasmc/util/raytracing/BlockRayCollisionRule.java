package de.atlasmc.util.raytracing;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

@FunctionalInterface
public interface BlockRayCollisionRule {

	public static BlockRayCollisionRule
	IGNORE_AIR = (data) -> {
		Material material = data.getMaterial();
		return !(material == Material.AIR
				|| material == Material.CAVE_AIR || material == Material.VOID_AIR);
	},
	IGNORE_FUID_AND_AIR = (data) -> {
		Material material = data.getMaterial();
		return !(material == Material.AIR || material == Material.WATER 
				|| material == Material.LAVA
				|| material == Material.CAVE_AIR || material == Material.VOID_AIR);
	};
	
	public boolean isValid(BlockData data);

}
