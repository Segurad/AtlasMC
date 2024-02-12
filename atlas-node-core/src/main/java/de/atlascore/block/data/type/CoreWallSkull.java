package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.WallSkull;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWallSkull extends CoreDirectional4Faces implements WallSkull {

	private boolean powered;
	
	public CoreWallSkull(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered())
			writer.writeByteTag(CorePowerable.NBT_POWERED, systemData);
	}

}
