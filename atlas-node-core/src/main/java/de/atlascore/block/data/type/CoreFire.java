package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlascore.block.data.CoreMultipleFacing5;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Fire;

public class CoreFire extends CoreMultipleFacing5 implements Fire {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAbstractMultipleFacing.PROPERTIES, BlockDataProperty.AGE);
	}
	
	private int age;
	private int maxage;
	
	public CoreFire(Material material) {
		super(material);
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
		if (age > maxage && age < 0) 
			throw new IllegalArgumentException("Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.UP)?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16)+
				age*32;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
