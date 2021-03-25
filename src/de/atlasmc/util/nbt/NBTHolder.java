package de.atlasmc.util.nbt;

import java.io.IOException;

/**
 * Represents a Object which can be converted to NBT or from NBT
 * @author Segurad
 *
 */
public interface NBTHolder {
	
	/**
	 * 
	 * @return the NBT content with local = default and systemData = false
	 */
	public default NBT toNBT() {
		return toNBT("default", false);
	}
	
	public default NBT toNBT(String local, boolean systemData) {
		NBTObjectWriter writer = new NBTObjectWriter();
		try {
			toNBT(writer, local, systemData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toNBT();
	}
	
	/**
	 * 
	 * @param systemData
	 * @return the NBT content with local = default
	 */
	public default NBT toNBT(boolean systemData) {
		return toNBT("default", systemData);
	}
	
	/**
	 * 
	 * @param local
	 * @return the NBT content with systemData = false
	 */
	public default NBT toNBT(String local) {
		return toNBT(local, false);
	}
	
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException;
	
	public void fromNBT(NBTReader reader);
	
	public default void fromNBT(NBT nbt) {
		fromNBT(new NBTObjectReader(nbt));
	}
}
