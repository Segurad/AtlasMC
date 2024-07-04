package de.atlasmc.util;

public final class Pair<V1, V2> {

	private static final Pair<?, ?> NULL_PAIR = new Pair<>(null, null);
	
	private final V1 value1;
	private final V2 value2;
	
	private Pair(V1 value1,V2 value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@SuppressWarnings("unchecked")
	public static <V1, V2> Pair<V1, V2> of() {
		return (Pair<V1, V2>) NULL_PAIR;
	}
	
	@SuppressWarnings("unchecked")
	public static <V1, V2> Pair<V1, V2> of(V1 value1, V2 value2) {
		if (value1 == null && value2 == null)
			return (Pair<V1, V2>) NULL_PAIR;
		return new Pair<>(value1, value2);
	}
	
	public V1 getValue1() {
		return value1;
	}
	
	public V2 getValue2() {
		return value2;
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
