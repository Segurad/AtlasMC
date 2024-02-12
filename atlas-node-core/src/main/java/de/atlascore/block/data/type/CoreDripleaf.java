package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Dripleaf;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDripleaf extends CoreDirectional4Faces implements Dripleaf {

	private boolean waterlogged;
	
	public CoreDripleaf(Material material) {
		super(material);
	}
	
	@Override
	public CoreDripleaf clone() {
		return (CoreDripleaf) super.clone();
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
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (waterlogged)
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}

}
