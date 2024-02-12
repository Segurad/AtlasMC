package de.atlasmc.io;

/**
 * Object that has a known serialized size
 */
public interface IOSerializedSizePredictable {
	
	/**
	 * Returns the number of bytes to represent this structure serialized
	 * @return byte count
	 */
	long getSerializedSize();

}
