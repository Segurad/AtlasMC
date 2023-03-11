package de.atlasmc.util;

public final class Pair<A, B> {

	private final A value1;
	private final B value2;
	
	public Pair(A value1,B value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	public A getValue1() {
		return value1;
	}
	
	public B getValue2() {
		return value2;
	}

	public static <A, B> Pair<A, B> of(A value1, B value2) {
		return new Pair<>(value1, value2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value1 == null) ? 0 : value1.hashCode());
		result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
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
		Pair<?, ?> other = (Pair<?, ?>) obj;
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
		return true;
	}
}
