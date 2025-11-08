package de.atlasmc.node.block.data.property;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Rotatable;

class RotationProperty extends AbstractEnumProperty<BlockFace> {

	public RotationProperty() {
		super("rotation", BlockFace.class);
	}

	@Override
	public BlockFace fromNBT(NBTReader reader) throws IOException {
		return BlockFace.getByRotation(reader.readIntTag());
	}

	@Override
	public void toNBT(BlockFace value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeIntTag(key, value.getRotation());
	}

	@Override
	public void set(BlockData data, BlockFace value) {
		if (data instanceof Rotatable rotatable)
			rotatable.setRotation(value);
	}

	@Override
	public BlockFace get(BlockData data) {
		if (data instanceof Rotatable rotatable)
			return rotatable.getRotation();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Rotatable;
	}

}
