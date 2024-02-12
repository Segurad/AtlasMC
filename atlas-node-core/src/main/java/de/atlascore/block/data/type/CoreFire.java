package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlascore.block.data.CoreAgeable;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Fire;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFire extends CoreAbstractMultipleFacing implements Fire {

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
		if (age > maxage && age < 0) throw new IllegalArgumentException("Age is not between 0 and " + maxage + ": " + age);
		this.age = age;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.of(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST);
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
	public boolean isValid(BlockFace face) {
		if (face == null) throw new IllegalArgumentException("BlockFace can not be null!");
		return face != BlockFace.DOWN && face.ordinal() < 6;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getAge() > 0) writer.writeIntTag(CoreAgeable.NBT_AGE, getAge());
	}

}
