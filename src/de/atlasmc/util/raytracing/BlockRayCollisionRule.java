package de.atlasmc.util.raytracing;

import de.atlasmc.Material;
import de.atlasmc.util.raytracing.collisionrules.IgnoreFuidAndAir;

public abstract class BlockRayCollisionRule {

	public static BlockRayCollisionRule
	ALWAYS,
	IGNORE_AIR,
	IGNORE_FUID_AND_AIR = new IgnoreFuidAndAir()
	;
	
	public abstract boolean isValidMaterial(Material material);

}
