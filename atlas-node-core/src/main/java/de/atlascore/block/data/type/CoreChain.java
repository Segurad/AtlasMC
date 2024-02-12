package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreOrientable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Chain;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChain extends CoreOrientable implements Chain {

	private boolean waterlogged;
	
	public CoreChain(Material material) {
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
				getAxis().ordinal()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}

}
