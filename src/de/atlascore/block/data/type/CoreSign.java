package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreRotatable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Sign;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSign extends CoreRotatable implements Sign {

	private boolean waterlogged;
	
	public CoreSign(Material material) {
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
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				getRotationValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) writer.writeByteTag(CoreWaterlogged.WATERLOGGED, true);
	}

}
