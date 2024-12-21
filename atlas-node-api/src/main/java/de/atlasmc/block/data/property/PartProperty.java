package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Bed;
import de.atlasmc.block.data.type.Bed.Part;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class PartProperty extends AbstractEnumProperty<Part> {

	public PartProperty() {
		super("part");
	}

	@Override
	public Part fromNBT(NBTReader reader) throws IOException {
		return Part.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Part value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Part value) {
		if (data instanceof Bed bed)
			bed.setPart(value);
	}

	@Override
	public Part get(BlockData data) {
		if (data instanceof Bed bed)
			return bed.getPart();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bed;
	}

}
