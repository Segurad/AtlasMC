package de.atlasmc.util.nbt;

import java.io.IOException;
import java.util.ArrayList;

import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractNBTBuildable implements NBTHolder {

	@Override
	public abstract void toNBT(NBTWriter writer, boolean systemData) throws IOException;

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		Validate.notNull(reader, "NBTReader can not be null!");
		final int depth = reader.getDepth();
		final ArrayList<NBTFieldContainer> containers = new ArrayList<NBTFieldContainer>();
		NBTFieldContainer highestContainer = getRootFieldContainer();
		while (depth <= reader.getDepth()) {
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				if (containers.isEmpty()) break;
				highestContainer = containers.remove(containers.size()-1);
				reader.readNextEntry();
				continue;
			}
			final String key = reader.getFieldName();
			NBTFieldContainer container = highestContainer.getContainer(key);
			if (container != null) {
				containers.add(highestContainer);
				highestContainer = container;
				continue;
			}
			NBTField field = highestContainer.getField(key);
			if (field != null) {
				field.setField(this, reader);
			} else if (highestContainer.hasUnknownFieldHandler()) {
				highestContainer.getUnknownFieldHandler().setField(this, reader);
			} else if (getCustomTagContainer() != null) {
				getCustomTagContainer().addCustomTag(reader.readNBT());
			} else reader.skipNBT();
		}
		if (reader.getType() == TagType.TAG_END) reader.readNextEntry();
	}
	
	protected abstract NBTFieldContainer getRootFieldContainer();
	
	public CustomTagContainer getCustomTagContainer() {
		return null;
	}

}
