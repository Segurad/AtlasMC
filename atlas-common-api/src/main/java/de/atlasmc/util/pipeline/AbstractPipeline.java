package de.atlasmc.util.pipeline;

import java.lang.reflect.Array;

public abstract class AbstractPipeline<E> implements Pipeline<E> {
	
	protected static final String[] EMPTY_NAMES = {};
	
	protected <T> T[] removeAt(T[] array, int i, T[] empty) {
		final int length = array.length;
		if (length == 1)
			return empty;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length - 1);
		if (i > 0)
			System.arraycopy(array, 0, newArray, 0, i);
		if (i != length - 1)
			System.arraycopy(array, i + 1, newArray, i, length - 1 - i);
		return newArray;
	}
	
	protected <T> T[] insertAt(T[] array, int i, T value) {
		final int length = array.length;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length + 1);
		if (i > 0)
			System.arraycopy(array, 0, newArray, 0, i);
		newArray[i] = value;
		if (i < length)
			System.arraycopy(array, i, newArray, i + 1, length - i);
		return newArray;
	}
	
	protected <T> int findIndex(T[] values, T value) {
		final var length = values.length;
		for (int i = 0; i < length; i++) {
			if (values[i].equals(value))
				return i;
		}
		return -1;
	}
	
	protected void ensureNotPresent(String[] names, String name) {
		if (findIndex(names, name) != -1)
			throw new IllegalArgumentException("Entry with name: " + name + " already present!");
	}

}
