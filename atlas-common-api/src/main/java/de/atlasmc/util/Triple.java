package de.atlasmc.util;

import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public final record Triple<A, B, C>(@Nullable A value1, @Nullable B value2, @Nullable C value3) {
	
	private static final Triple<?, ?, ?> NULL_TRIPLE = new Triple<>(null, null, null);
	
	public static <A, B, C> Triple<A, B, C> of() {
		@SuppressWarnings("unchecked")
		Triple<A, B, C> value = (Triple<A, B, C>) NULL_TRIPLE;
		return value;
	}
	
	public static <A, B, C> Triple<A, B, C> of(A value1, B value2, C value3) {
		if (value1 == null && value2 == null && value3 == null)
			return of();
		return new Triple<>(value1, value2, value3);
	}

}
