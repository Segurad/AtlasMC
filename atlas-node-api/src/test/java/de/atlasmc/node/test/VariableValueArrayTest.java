package de.atlasmc.node.test;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.VariableValueArray;

public class VariableValueArrayTest {
	
	private static final int CAPACITY = 32;
	private static final int BITS = 4;
	private static final long seed = 42L;
	
	private int[] values;
	
	private void init(VariableValueArray array) {
		if (values == null) {
			int maxValue = MathUtil.getRequiredBitCount(BITS)+1;
			Random rand = new Random(seed);
			values = new int[CAPACITY];
			for (int i = 0; i < CAPACITY; i++) {
				values[i] = rand.nextInt(maxValue);
			}
		}
		for (int i = 0; i < CAPACITY; i++) {
			array.set(i, values[i]);
		}
	}
	
	@Test
	void testVariableValueArray() {
		VariableValueArray array = new VariableValueArray(CAPACITY, BITS);
		init(array);
		for (int i = 0; i < CAPACITY; i++) {
			if (values[i] == array.get(i))
				continue;
			fail("Expected " + values[i] + " but was " + array.get(i) + " at " + i, values, BITS, array);
		}
	}
	
	@Test 
	void testResizingConsistency() {
		VariableValueArray array = new VariableValueArray(CAPACITY, BITS);
		init(array);
		array.resize(BITS+1);
		for (int i = 0; i < CAPACITY; i++) {
			if (values[i] == array.get(i))
				continue;
			fail("Expected " + values[i] + " but was " + array.get(i) + " at " + i + " after resizing", values, BITS+1, array);
		}
	}
	
	private void fail(String message, int[] values, int bits, VariableValueArray array) {
		StringBuilder builder = new StringBuilder(message);
		builder.append("\n--- Tested Data ---");
		builder.append("\nCapacity: ");
		builder.append(CAPACITY);
		builder.append("\nBits per value: ");
		builder.append(bits);
		builder.append("\nMask: ");
		builder.append(Integer.toBinaryString(MathUtil.createBitMask(bits)));
		builder.append("\nValues:  [ ");
		for (int i : values) {
			builder.append(i);
			builder.append(" ,");
		}
		builder.setLength(builder.length()-1);
		builder.append(']');
		builder.append("\nVarValues: [ ");
		for (long l : array.array()) {
			builder.append(Long.toBinaryString(l));
			builder.append(", ");
		}
		builder.setLength(builder.length()-1);
		builder.append(']');
		Assertions.fail(builder.toString());
	}

}
