package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.PointedDripstone;
import de.atlasmc.block.data.type.PointedDripstone.Thickness;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class ThicknessProperty extends AbstractEnumProperty<Thickness> {

	public ThicknessProperty() {
		super("thickness");
	}

	@Override
	public Thickness fromNBT(NBTReader reader) throws IOException {
		return Thickness.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Thickness value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Thickness value) {
		if (data instanceof PointedDripstone dripstone)
			dripstone.setThickness(value);
	}

	@Override
	public Thickness get(BlockData data) {
		if (data instanceof PointedDripstone dripstone)
			return dripstone.getThickness();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PointedDripstone;
	}

}
