package de.atlasmc.util.nbt.serialization.constructor;

import de.atlasmc.util.nbt.io.NBTReader;

public interface Constructor<T> {
	
	public T invoke(NBTReader reader);

}
