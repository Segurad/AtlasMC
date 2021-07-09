package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockData extends AbstractNBTBase implements BlockData {
	
	protected static final NBTFieldContainer NBT_FIELDS = new NBTFieldContainer();
	private final Material material;
	
	public CoreBlockData(Material material) {
		Validate.notNull(material, "Material can not be null!");
		Validate.isTrue(material.isBlock(), "Material is not a Block: " + material.getNamespacedName());
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
	public boolean matches(BlockData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStateID() {
		return material.getBlockID();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
