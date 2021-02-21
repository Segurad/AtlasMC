package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Fire;
import de.atlasmc.util.Validate;

public class CoreFire extends CoreMultipleFacing implements Fire {

	private int age;
	private int maxage;
	
	public CoreFire(Material material) {
		super(material, 5);
		this.age = 0;
		this.maxage = 15;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getMaxAge() {
		return maxage;
	}

	@Override
	public void setAge(int age) {
		Validate.isTrue(age <= maxage && age >= 0, "Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.of(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.UP)?0:4)+
				(hasFace(BlockFace.SOUTH)?0:8)+
				(hasFace(BlockFace.NORTH)?0:16)+
				(hasFace(BlockFace.EAST)?0:32)+
				age*64;
	}

	@Override
	public boolean isValid(BlockFace face) {
		Validate.notNull(face, "BlockFace can not be null!");
		return face != BlockFace.DOWN;
	}


}
