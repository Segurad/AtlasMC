package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Bamboo;
import de.atlasmc.block.data.type.Bamboo.Leaves;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class LeavesProperty extends AbstractEnumProperty<Leaves> {

	public LeavesProperty() {
		super("leaves");
	}

	@Override
	public Leaves fromNBT(NBTReader reader) throws IOException {
		return Leaves.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Leaves value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Leaves value) {
		if (data instanceof Bamboo bamboo)
			bamboo.setLeaves(value);
	}

	@Override
	public Leaves get(BlockData data) {
		if (data instanceof Bamboo bamboo)
			return bamboo.getLeaves();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bamboo;
	}

}
