package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;

public interface NBTField {
	
	public static final NBTField TRASH = (holder, reader) -> {
		reader.readNBT();
	};
	
	public void setField(NBTHolder holder, NBTReader reader) throws IOException;

}
