package de.atlasmc.util;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final record Pair<A, B>(A value1, B value2) {

	@NotNull
	public static final Pair<?, ?> NULL_PAIR = new Pair<>(null, null);
	
	@NotNull
	public static <A, B> Pair<A, B> of() {
		@SuppressWarnings("unchecked")
		Pair<A, B> value = (Pair<A, B>) NULL_PAIR;
		return value;
	}
	
	@NotNull
	public static <A, B> Pair<A, B> of(A value1, B value2) {
		if (value1 == null && value2 == null)
			return of();
		return new Pair<>(value1, value2);
	}
	
}
