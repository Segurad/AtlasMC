package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAgeable;
import de.atlascore.block.data.CoreBisected;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.PitcherCrop;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePitcherCrop extends CoreAgeable implements PitcherCrop {

	private Half half;
	
	public CorePitcherCrop(Material material) {
		super(material, 4);
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
	public int getStateID() {
		return getMaterial().getBlockStateID() + half.getID() + getAge()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (half != Half.LOWER)
			writer.writeStringTag(CoreBisected.NBT_HALF, half.getNameID());
	}

}
