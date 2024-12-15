package de.atlasmc.util;

public final class Triple<A, B, C> {
	
	private static final Triple<?, ?, ?> NULL_TRIPLE = new Triple<>(null, null, null);
	
	private final A value1;
	private final B value2;
	private final C value3;
	
	private Triple(A value1,B value2, C value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	
	@SuppressWarnings("unchecked")
	public static <A, B, C> Triple<A, B, C> of() {
		return (Triple<A, B, C>) NULL_TRIPLE;
	}
	
	@SuppressWarnings("unchecked")
	public static <A, B, C> Triple<A, B, C> of(A value1, B value2, C value3) {
		if (value1 == null && value2 == null && value3 == null)
			return (Triple<A, B, C>) NULL_TRIPLE;
		return new Triple<>(value1, value2, value3);
	}
	
	public A getValue1() {
		return value1;
	}
	
	public B getValue2() {
		return value2;
	}
	
	public C getValue3() {
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
