package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTObjectReader;
import de.atlasmc.util.nbt.io.NBTObjectWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

/**
 * Represents a Object which can be converted to NBT or from NBT
 */
public interface NBTHolder {
	
	/**
	 * 
	 * @return the NBT without system data
	 * @throws IOException 
	 */
	default NBT toNBT() throws IOException {
		return toNBT(false);
	}
	
	/**
	 * 
	 * @param systemData whether or not the system data should
	 * @return NBT 
	 * @throws IOException 
	 */
	default NBT toNBT(boolean systemData) throws IOException {
		NBTObjectWriter writer = new NBTObjectWriter();
		NBT nbt = writer.toNBT();
		toNBT(writer, systemData);
		writer.close();
		return nbt;
	}
	
	/**
	 * Write the NBT of the Holder
	 * Does not create a new CompoundTag
	 * @param writer
	 * @param systemData true if it is used system internal false while send to client
	 * @throws IOException
	 */
	void toNBT(NBTWriter writer, boolean systemData) throws IOException;
	
	/**
	 * Reads until a EndTag is reached and goes to the next entry by {@link NBTReader#readNextEntry()}
	 * @param reader
	 * @throws IOException
	 */
	void fromNBT(NBTReader reader) throws IOException;
	
	default void fromNBT(NBT nbt) throws IOException {
		NBTObjectReader reader = new NBTObjectReader(nbt);
		fromNBT(reader);
		reader.close();
	}
}
