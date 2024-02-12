package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAgeable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CaveVines;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCaveVines extends CoreAgeable implements CaveVines {

	private boolean berries;
	
	public CoreCaveVines(Material material) {
		super(material, 25);
	}

	@Override
	public boolean hasBerries() {
		return berries;
	}

	@Override
	public void setBerries(boolean berries) {
		this.berries = berries;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+(berries?0:1)+getAge()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (berries)
			writer.writeByteTag(CoreCaveVinesPlant.NBT_BERRIES, true);
	}

}
