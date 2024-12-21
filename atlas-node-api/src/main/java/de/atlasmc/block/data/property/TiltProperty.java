package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.BigDripleaf;
import de.atlasmc.block.data.type.BigDripleaf.Tilt;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class TiltProperty extends AbstractEnumProperty<Tilt> {

	public TiltProperty() {
		super("tilt");
	}

	@Override
	public Tilt fromNBT(NBTReader reader) throws IOException {
		return Tilt.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Tilt value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Tilt value) {
		if (data instanceof BigDripleaf leaf)
			leaf.setTilt(value);
	}

	@Override
	public Tilt get(BlockData data) {
		if (data instanceof BigDripleaf leaf)
			return leaf.getTilt();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof BigDripleaf;
	}

}
