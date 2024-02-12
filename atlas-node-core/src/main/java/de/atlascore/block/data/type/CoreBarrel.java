package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlascore.block.data.CoreOpenable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Barrel;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBarrel extends CoreDirectional6Faces implements Barrel {

	private boolean open;
	
	public CoreBarrel(Material material) {
		super(material);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(open?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isOpen()) writer.writeByteTag(CoreOpenable.NBT_OPEN, true);
	}

}
