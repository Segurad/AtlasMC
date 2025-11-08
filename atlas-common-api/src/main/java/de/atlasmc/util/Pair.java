package de.atlasmc.util;

import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final record Pair<A, B>(@Nullable A value1, @Nullable B value2) {

	private static final Pair<?, ?> NULL_PAIR = new Pair<>(null, null);
	
	public static <A, B> Pair<A, B> of() {
		@SuppressWarnings("unchecked")
		Pair<A, B> value = (Pair<A, B>) NULL_PAIR;
		return value;
	}
	
	public static <A, B> Pair<A, B> of(A value1, B value2) {
		if (value1 == null && value2 == null)
			return of();
		return new Pair<>(value1, value2);
	}
	
}
