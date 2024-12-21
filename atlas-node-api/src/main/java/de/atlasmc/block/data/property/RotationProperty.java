package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Rotatable;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class RotationProperty extends AbstractEnumProperty<BlockFace> {

	public RotationProperty() {
		super("rotation");
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
