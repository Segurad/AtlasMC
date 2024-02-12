package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBisected;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SmallDripleaf;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSmallDripleaf extends CoreDirectional4Faces implements SmallDripleaf {

	private Half half;
	private boolean waterlogged;
	
	public CoreSmallDripleaf(Material material) {
		super(material);
		half = Half.LOWER;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null)
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.TOP || half == Half.BOTTOM)
			throw new IllegalArgumentException("Invalid half: " + half.name());
		this.half = half;
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + half.getID()*2 + getFaceValue()*4;
	}
	
	@Override
	public CoreSmallDripleaf clone() {
		return (CoreSmallDripleaf) super.clone();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (waterlogged)
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (half != Half.LOWER)
			writer.writeStringTag(CoreBisected.NBT_HALF, half.getNameID());
	}

}
