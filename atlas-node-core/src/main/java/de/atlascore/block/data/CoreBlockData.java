package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockData extends AbstractNBTBase implements BlockData {
	
	protected static final NBTFieldContainer<CoreBlockData> NBT_FIELDS = NBTFieldContainer.newContainer();
	private final Material material;
	
	public CoreBlockData(Material material) {
		if (material == null) 
			throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) 
			throw new IllegalArgumentException("Material is not a Block: " + material.getNamespacedKeyRaw());
		this.material = material;
	}
	
	public BlockData clone() {
		try {
			return (BlockData) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getStateID() {
		return material.getBlockStateID();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (systemData)
			writer.writeStringTag("id", material.getNamespacedKeyRaw());
		else
			writer.writeStringTag("id", material.getClientKey().toString());
	}

	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
