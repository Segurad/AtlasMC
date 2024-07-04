package de.atlasmc.util;

import java.util.Arrays;

public class VariableValueArray implements Cloneable {
	
	private int capacity; // Number of values that can be stored
	private long[] values; // backing array
	private byte bitsPerValue; // bits required per value
	private long mask; // mask containing as many 1 bits as bits per value defines
	private byte valuesPerLong; // number of values stored per long
	
	public VariableValueArray(VariableValueArray array) {
		if (array == null)
			throw new IllegalArgumentException("Array may not be null!");
		this.capacity = array.capacity;
		this.values = array.values.clone();
		this.bitsPerValue = array.bitsPerValue;
		this.mask = array.mask;
		this.valuesPerLong = array.valuesPerLong;
	}
	
	public VariableValueArray(int capacity, int bitsPerValue) {
		this(capacity, bitsPerValue, null);
	}
	
	/**
	 * Creates a new VariableValueArray
	 * @param capacity number of values that should be stored
	 * @param bitsPerValue number of bits to represent a value
	 * @param values array of variable values that will be wrapped by this
	 */
	public VariableValueArray(int capacity, int bitsPerValue, long[] values) {
		if (capacity < 0)
			throw new IllegalArgumentException("Capacity must be at least 0: " + capacity);
		if (bitsPerValue < 1)
			throw new IllegalArgumentException("Bits per value must be greater than 0: " + bitsPerValue);
		this.capacity = capacity;
		this.bitsPerValue = (byte) bitsPerValue;
		this.valuesPerLong = valuesPerLong(bitsPerValue);
		int valuesSize = Math.ceilDiv(capacity, valuesPerLong);
		if (values != null) {
			if (values.length != valuesSize)
				throw new IllegalArgumentException("Invalid values length, expected: " + valuesSize + " but got: " + values.length);
			else
				this.values = values;
		} else
			this.values = new long[valuesSize];
		this.mask = MathUtil.createBitMask(bitsPerValue);
	}
	
	/**
	 * Resizes the array to match new number of bits
	 * @param bitsPerValue number of bits to represent a value
	 */
	public void resize(int bitsPerValue) {
		if (this.bitsPerValue == bitsPerValue)
			return;
		// save old
		final long[] oldValues = values;
		final long oldMask = mask;
		final int oldBitPerValue = this.bitsPerValue;
		// init new
		this.bitsPerValue = (byte) bitsPerValue;
		this.valuesPerLong = valuesPerLong(bitsPerValue);
		values = new long[MathUtil.upper(capacity / (double) valuesPerLong)];
		mask = MathUtil.createBitMask(bitsPerValue);
		// copy data
		int index = 0;
		for (long l : oldValues) {
			int restBits = 64;
			while (restBits >= oldBitPerValue) {
				int oldValue = (int) (l & oldMask);
				if (oldValue != 0)  {
					// assuming new array is empty so no need to set 0 values
					set(index, oldValue);
				}
				index++;
				l >>= oldBitPerValue;
				restBits -= oldBitPerValue;
			}
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
		
		longValue &= mask << bitsToShift ^ 0xFFFFFFFFFFFFFFFFL; // reset old value
		if (value == 0)
			return; // no need to set bits
		longValue |= (value & mask) << bitsToShift; // set new value
		values[longIndex] = longValue;
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
		if (oldValue == value)
			return value; // equal value no need for change
		longValue &= mask << bitsToShift ^ 0xFFFFFFFFFFFFFFFFL; // reset old value
		if (value == 0)
			return oldValue; // no need to set bits
		longValue |= (value & mask) << bitsToShift; // set new value
		values[longIndex] = longValue;
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
	 * Sets the capacity of this array.<br>
	 * If set lower than the current capacity values may be lost and new values will be set to 0.<br>
	 * @param capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
		values = Arrays.copyOf(values, (capacity + valuesPerLong - 1) / valuesPerLong);
	}
	
	/**
	 * Returns the backing long array
	 * @return long array
	 */
	public long[] array() {
		return values;
	}

	/**
	 * Copies all values to this array
	 * @param array the array copied from
	 */
	public void copy(VariableValueArray array) {
		this.capacity = array.capacity;
		this.values = array.values.clone();
		this.bitsPerValue = array.bitsPerValue;
		this.mask = array.mask;
		this.valuesPerLong = array.valuesPerLong;
	}
	
	/**
	 * Fill the array with the given value
	 * @param value
	 */
	public void fill(int value) {
		value &= mask; // trim value
		long storedLong = 0; // create long that contains the max number of values
		for (int i = 0; i < valuesPerLong; i++) {
			storedLong <<= bitsPerValue;
			storedLong |= value;
		}
		// store long to backing array
		Arrays.fill(values, storedLong);
	}
	
	public VariableValueArray clone() {
		VariableValueArray clone = null;
		try {
			clone = (VariableValueArray) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		clone.values = values.clone();
		return clone;	
	}
	
	private static byte valuesPerLong(int bitsPerValue) {
		return (byte) (64 / bitsPerValue);
	}
	
	public static VariableValueArray of(int capacity, int bitsPerValue) {
		return new Immutable(capacity, bitsPerValue, null);
	}
	
	private static final class Immutable extends VariableValueArray {

		public Immutable(int capacity, int bitsPerValue, long[] values) {
			super(capacity, bitsPerValue, values);
		}
		
		public Immutable(VariableValueArray array) {
			super(array);
		}
		
		@Override
		public VariableValueArray clone() {
			return this;
		}
		
		@Override
		public void set(int index, int value) {}
		
		@Override
		public int replace(int index, int value) {
			return -1;
		}
		
		@Override
		public void resize(int bitsPerValue) {}
		
	}

}
