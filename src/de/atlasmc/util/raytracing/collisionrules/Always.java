package de.atlasmc.util.raytracing.collisionrules;

import de.atlasmc.Material;
import de.atlasmc.util.raytracing.BlockRayCollisionRule;

public class Always extends BlockRayCollisionRule {

	@Override
	public boolean isValidMaterial(Material material) {
		return true;
	}

}
