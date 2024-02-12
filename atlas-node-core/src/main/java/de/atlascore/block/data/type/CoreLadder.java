package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Ladder;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLadder extends CoreDirectional4Faces implements Ladder {

	private boolean waterlogged;
	
	public CoreLadder(Material material) {
		super(material);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+getFaceValue()*2+(waterlogged?0:1);
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}
	
}
