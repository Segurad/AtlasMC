package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.WaterloggedDirectional;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWaterloggedDirectional4Faces extends CoreDirectional4Faces implements WaterloggedDirectional {
	
	private boolean waterlogged;
	
	public CoreWaterloggedDirectional4Faces(Material material) {
		super(material);
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
		return getMaterial().getBlockStateID()+
				(waterlogged?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}

}
