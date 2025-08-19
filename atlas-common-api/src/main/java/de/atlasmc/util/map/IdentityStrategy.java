package de.atlasmc.util.map;

import it.unimi.dsi.fastutil.Hash.Strategy;

public final class IdentityStrategy<K> implements Strategy<K> {

	private static final IdentityStrategy<?> STRATEGY = new IdentityStrategy<>();
	
	@SuppressWarnings("unchecked")
	public static <K> IdentityStrategy<K> getInstance() {
		return (IdentityStrategy<K>) STRATEGY;
	}
	
	private IdentityStrategy() {
		// internal
	}
	
	@Override
	public int hashCode(K o) {
		return System.identityHashCode(o);
	}

	@Override
	public boolean equals(K a, K b) {
		return a == b;
	}

}
