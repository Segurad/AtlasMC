package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.WaterloggedRotatable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWaterloggedRotatable extends CoreRotatable implements WaterloggedRotatable {
	
	private boolean waterlogged;
	
	public CoreWaterloggedRotatable(Material material) {
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
				getRotationValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}

}
