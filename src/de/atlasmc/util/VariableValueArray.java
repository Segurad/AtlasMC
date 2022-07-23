package de.atlasmc.util;

public class VariableValueArray {
	
	private final int capacity;
	private long[] values;
	private byte bitsPerValue;
	private int mask;
	private byte valuesPerLong;
	
	public VariableValueArray(int capacity, int bitsPerValue) {
		this(capacity, bitsPerValue, null);
	}
	
	/**
	 * Creates a new VariableValueArray
	 * @param capacity number of values that should be stored
	 * @param bitsPerValue number of bits to represent a value (set to 4 if lower than 4)
	 * @param values array of variable values that will be wrapped by this
	 */
	public VariableValueArray(int capacity, int bitsPerValue, long[] values) {
		this.capacity = capacity;
		this.bitsPerValue = (byte) bitsPerValue;
		this.valuesPerLong = (byte) (64 / bitsPerValue);
		int valuesSize = (capacity + valuesPerLong - 1) / valuesPerLong;
		if (values != null) {
			if (values.length != valuesSize)
				throw new IllegalArgumentException("Invalid values length, expected: " + valuesSize + " but got: " + values.length);
			else
				this.values = values;
		} else
			this.values = new long[(capacity + valuesPerLong - 1) / valuesPerLong];
		this.mask = MathUtil.createPaletteBitMask(bitsPerValue);
	}
	
	/**
	 * Resizes the array to math new number of bits
	 * @param bitsPerValue number of bits to represent a value (set to 4 if lower than 4)
	 */
	public void resize(int bitsPerValue) {
		if (this.bitsPerValue == bitsPerValue)
			return;
		// save old
		final long[] oldValues = values;
		final int oldMask = mask;
		final int oldBitPerValue = this.bitsPerValue;
		// init new
		this.bitsPerValue = (byte) bitsPerValue;
		this.valuesPerLong = (byte) (64 / bitsPerValue);
		values = new long[(capacity + valuesPerLong - 1) / valuesPerLong];
		mask = MathUtil.createPaletteBitMask(bitsPerValue);
		// copy data
		int restBits = 64;
		int index = 0;
		for (long l : oldValues) {
			while (restBits > oldBitPerValue) {
				set(index++, (int) (l & oldMask));
				l >>= oldBitPerValue;
			}
			restBits = 64;
		}
	}
	
	/**
	 * Sets the new value at the index
	 * @param index to set at
	 * @param value that will be set
	 */
	public void set(int index, int value) {
		if (index < 0 || index >= capacity)
			throw new ArrayIndexOutOfBoundsException(index);
		int longIndex = index / valuesPerLong;
		long longValue = values[longIndex];
		int bitsToShift = (index - longIndex * valuesPerLong) * bitsPerValue;
		values[longIndex] = longValue & (mask << bitsToShift ^ 0xFFFFFFFFFFFFFFFFL) | (value & mask) << bitsToShift;
	}
	
	/**
	 * Sets the new value at the index and returns the old value
	 * @param index to set at
	 * @param value that will be set
	 * @return the old value
	 */
	public int replace(int index, int value) {
		if (index < 0 || index >= capacity)
			throw new ArrayIndexOutOfBoundsException(index);
		int longIndex = index / valuesPerLong;
		long longValue = values[longIndex];
		int bitsToShift = (index - longIndex * valuesPerLong) * bitsPerValue;
		
		int oldValue = (int) (longValue >> bitsToShift & mask);
		values[longIndex] = longValue & (mask << bitsToShift ^ 0xFFFFFFFFFFFFFFFFL) | (value & mask) << bitsToShift;
		return oldValue;
	}
	
	/**
	 * Returns the value set at the index
	 * @param index
	 * @return value
	 */
	public int get(int index) {
		if (index < 0 || index >= capacity)
			throw new ArrayIndexOutOfBoundsException(index);
		int longIndex = index / valuesPerLong;
		long longValue = values[longIndex];
		int bitsToShift = (index - longIndex * valuesPerLong) * bitsPerValue;
		return (int) (longValue >> bitsToShift & mask);
	}
	
	/**
	 * Returns the number of bits used to represent a value
	 * @return number of bits
	 */
	public int getBitsPerValue() {
		return bitsPerValue;
	}
	
	/**
	 * Returns the capacity of values
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Returns the backing long array
	 * @return
	 */
	public long[] array() {
		return values;
	}

}
