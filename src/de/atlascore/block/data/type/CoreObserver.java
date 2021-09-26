package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Observer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreObserver extends CoreDirectional6Faces implements Observer {

	private boolean powered;
	
	public CoreObserver(Material material) {
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
		return getMaterial().getBlockID()+
				(powered?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
	}

}
