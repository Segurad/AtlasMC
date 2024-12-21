package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.PointedDripstone;
import de.atlasmc.block.data.type.PointedDripstone.VerticalDirection;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class VerticalDirectionProperty extends AbstractEnumProperty<VerticalDirection> {

	public VerticalDirectionProperty() {
		super("vertical_direction");
	}

	@Override
	public VerticalDirection fromNBT(NBTReader reader) throws IOException {
		return VerticalDirection.getByNameID(reader.readStringTag());
	}

	@Override
	public void toNBT(VerticalDirection value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getNameID());
	}

	@Override
	public void set(BlockData data, VerticalDirection value) {
		if (data instanceof PointedDripstone dripstone)
			dripstone.setDirection(value);
	}

	@Override
	public VerticalDirection get(BlockData data) {
		if (data instanceof PointedDripstone dripstone)
			return dripstone.getDirection();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PointedDripstone;
	}

}
