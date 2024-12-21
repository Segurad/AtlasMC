package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.Bisected.Half;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class HalfProperty extends AbstractEnumProperty<Half> {

	public HalfProperty() {
		super("half");
	}

	@Override
	public Half fromNBT(NBTReader reader) throws IOException {
		return Half.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Half value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Half value) {
		if (data instanceof Bisected bisected)
			bisected.setHalf(value);
	}

	@Override
	public Half get(BlockData data) {
		if (data instanceof Bisected bisected)
			return bisected.getHalf();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bisected;
	}

}
