package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Orientable;
import de.atlasmc.block.data.Orientable.Orientation;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class OrientationProperty extends AbstractEnumProperty<Orientation> {

	public OrientationProperty() {
		super("orientation");
	}

	@Override
	public Orientation fromNBT(NBTReader reader) throws IOException {
		return Orientation.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Orientation value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Orientation value) {
		if (data instanceof Orientable orientable)
			orientable.setOrientation(value);
	}

	@Override
	public Orientation get(BlockData data) {
		if (data instanceof Orientable orientable)
			return orientable.getOrientation();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Orientable;
	}

}
