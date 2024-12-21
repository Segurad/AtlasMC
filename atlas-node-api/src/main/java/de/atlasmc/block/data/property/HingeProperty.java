package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.block.data.type.Door.Hinge;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class HingeProperty extends AbstractEnumProperty<Hinge> {

	public HingeProperty() {
		super("hinge");
	}

	@Override
	public Hinge fromNBT(NBTReader reader) throws IOException {
		return Hinge.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Hinge value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Hinge value) {
		if (data instanceof Door door)
			door.setHinge(value);
	}

	@Override
	public Hinge get(BlockData data) {
		if (data instanceof Door door)
			return door.getHinge();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Door;
	}

}
