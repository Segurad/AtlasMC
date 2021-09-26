package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CoralWallFan;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCoralWallFan extends CoreDirectional4Faces implements CoralWallFan {

	private boolean waterlogged;
	
	public CoreCoralWallFan(Material material) {
		super(material);
		this.waterlogged = true;
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
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) writer.writeByteTag(CoreWaterlogged.WATERLOGGED, true);
	}

}
