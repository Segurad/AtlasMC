package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Directional;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class FacingProperty extends AbstractEnumProperty<BlockFace> {

	public FacingProperty() {
		super("facing");
	}

	@Override
	public BlockFace fromNBT(NBTReader reader) throws IOException {
		return BlockFace.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(BlockFace value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, BlockFace value) {
		if (data instanceof Directional directional)
			directional.setFacing(value);
	}

	@Override
	public BlockFace get(BlockData data) {
		if (data instanceof Directional directional)
			directional.getFacing();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Directional;
	}

}
