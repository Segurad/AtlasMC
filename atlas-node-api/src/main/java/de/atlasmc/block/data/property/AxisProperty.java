package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.Axis;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.AxisOrientable;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class AxisProperty extends AbstractEnumProperty<Axis> {

	public AxisProperty() {
		super("axis");
	}

	@Override
	public Axis fromNBT(NBTReader reader) throws IOException {
		return Axis.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Axis value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Axis value) {
		if (data instanceof AxisOrientable orientable)
			orientable.setAxis(value);
	}

	@Override
	public Axis get(BlockData data) {
		if (data instanceof AxisOrientable orientable)
			return orientable.getAxis();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof AxisOrientable;
	}

}
