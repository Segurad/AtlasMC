package de.atlasmc.util;

public class NibbleArray {
	
	private final byte[] values;
	private final int capacity;
	
	public NibbleArray(int capacity) {
		this(capacity, null);
	}
	
	/**
	 * Creates a new nibble array
	 * @param capacity capacity of nibble array
	 * @param values array of nibbles that will be wrapped (new array will be created if null)
	 */
	public NibbleArray(int capacity, byte[] values) {
		if (capacity < 0)
			throw new IllegalArgumentException("Capacity can not be lower than 0: " + capacity);
		if (values != null) {
			if (values.length != capacity+1 >> 1) {
				throw new IllegalArgumentException("Invalid values length, expected: " + (capacity+1 >> 1) + " but got: " + values.length);
			} else {
				this.values = values;
			}
		} else {
			this.values = new byte[capacity+1 >> 1];
		}
		this.capacity = capacity;
	}
	
	public int getNibble(int index) {
		if (index < 0 || index >= capacity)
			throw new ArrayIndexOutOfBoundsException(index);
		byte bytee = values[index >> 1];
		if ((index & 0x1) == 1)
			bytee >>= 4;
		return bytee & 0xF;
	}
	
	public void setNibble(int index, int value) {
		if (index < 0 || index >= capacity)
			throw new ArrayIndexOutOfBoundsException(index);
		byte bytee = values[index >> 1];
		if ((index & 0x1) == 1)
			bytee = (byte) (bytee & 0xF | (value & 0xF << 4));
		else
			bytee = (byte) (bytee & 0xF0 | value & 0xF);
		values[index >> 1] = bytee;
	}
	
	/**
	 * Returns the number of elements that can be stored in this array
	 * @return number of elements
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Returns the backing byte array
	 * @return byte array
	 */
	public byte[] array() {
		return values;
	}

}
