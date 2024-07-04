package de.atlasmc.util;

public final class Triple<V1, V2, V3> {
	
	private static final Triple<?, ?, ?> NULL_TRIPLE = new Triple<>(null, null, null);
	
	private final V1 value1;
	private final V2 value2;
	private final V3 value3;
	
	private Triple(V1 value1,V2 value2, V3 value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	
	@SuppressWarnings("unchecked")
	public static <V1, V2, V3> Triple<V1, V2, V3> of() {
		return (Triple<V1, V2, V3>) NULL_TRIPLE;
	}
	
	@SuppressWarnings("unchecked")
	public static <V1, V2, V3> Triple<V1, V2, V3> of(V1 value1, V2 value2, V3 value3) {
		if (value1 == null && value2 == null && value3 == null)
			return (Triple<V1, V2, V3>) NULL_TRIPLE;
		return new Triple<>(value1, value2, value3);
	}
	
	public V1 getValue1() {
		return value1;
	}
	
	public V2 getValue2() {
		return value2;
	}
	
	public V3 getValue3() {
		return value3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
		result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
		result = prime * result + ((value3 == null) ? 0 : value3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
		if (value1 == null) {
			if (other.value1 != null)
				return false;
		} else if (!value1.equals(other.value1))
			return false;
		if (value2 == null) {
			if (other.value2 != null)
				return false;
		} else if (!value2.equals(other.value2))
			return false;
		if (value3 == null) {
			if (other.value3 != null)
				return false;
		} else if (!value3.equals(other.value3))
			return false;
		return true;
	}

}
