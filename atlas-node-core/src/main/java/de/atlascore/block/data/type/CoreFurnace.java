package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreLightable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Furnace;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFurnace extends CoreDirectional4Faces implements Furnace {

	private boolean lit;
	
	public CoreFurnace(Material material) {
		super(material);
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(lit?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isLit()) writer.writeByteTag(CoreLightable.NBT_LIT, true);
	}

}
