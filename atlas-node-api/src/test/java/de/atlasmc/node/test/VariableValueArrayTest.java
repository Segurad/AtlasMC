package de.atlasmc.node.test;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.VariableValueArray;

public class VariableValueArrayTest {
	
	private static final int CAPACITY = 4096;
	private static final int BITS = 5;
	private static final long seed = 42L;
	
	private static int[] values;
	
	@BeforeAll
	public static void init() {
		int maxValue = MathUtil.createBitMask(BITS)+1;
		Random rand = new Random(seed);
		values = new int[CAPACITY];
		for (int i = 0; i < CAPACITY; i++) {
			values[i] = rand.nextInt(maxValue);
		}
	}
	
	private VariableValueArray init(VariableValueArray array) {
		for (int i = 0; i < CAPACITY; i++) {
			array.set(i, values[i]);
		}
		return array;
	}
	
	@Test
	void testVariableValueArray() {
		VariableValueArray array = new VariableValueArray(CAPACITY, BITS);
		init(array);
		for (int i = 0; i < CAPACITY; i++) {
			if (values[i] == array.get(i))
				continue;
			fail("Expected " + values[i] + " but was " + array.get(i) + " at " + i, values, array);
		}
	}
	
	@Test 
	void testResizingConsistency() {
		VariableValueArray array = new VariableValueArray(CAPACITY, BITS);
		init(array);
		array.resize(8);
		for (int i = 0; i < CAPACITY; i++) {
			if (values[i] == array.get(i))
				continue;
			fail("Expected " + values[i] + " but was " + array.get(i) + " at " + i + " after resizing", values, array);
		}
	}
	
	private void fail(String message, int[] values, VariableValueArray array) {
		StringBuilder builder = new StringBuilder(message);
		builder.append("\n--- Tested Data ---");
		builder.append("\nCapacity: ");
		builder.append(array.getCapacity());
		builder.append("\nBits per value: ");
		builder.append(array.getBitsPerValue());
		builder.append("\nValues per Long: ");
		builder.append((byte) (64 / array.getBitsPerValue()));
		builder.append("\nMask: ");
		builder.append(Integer.toBinaryString(MathUtil.createBitMask(array.getBitsPerValue())));
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
		builder.append("\nExpected: [ ");
		for (long l : init(new VariableValueArray(array.getCapacity(), array.getBitsPerValue())).array()) {
			builder.append(Long.toBinaryString(l));
			builder.append(", ");
		}
		builder.setLength(builder.length()-1);
		builder.append(']');
		Assertions.fail(builder.toString());
	}

}
