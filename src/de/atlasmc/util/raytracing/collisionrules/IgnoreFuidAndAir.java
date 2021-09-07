package de.atlasmc.util.raytracing.collisionrules;

import de.atlasmc.Material;
import de.atlasmc.util.raytracing.BlockRayCollisionRule;

public class IgnoreFuidAndAir extends BlockRayCollisionRule {

	@Override
	public boolean isValidMaterial(Material material) {
		return !(material == Material.AIR || material == Material.WATER 
				|| material == Material.LAVA
				|| material == Material.CAVE_AIR || material == Material.VOID_AIR);
	}

}
