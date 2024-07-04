package de.atlasmc.util.map.key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.NumberConversion;

/**
 * Implementation of {@link CharSequence} for where all implementations should be equal if the contents is the same
 * e.g. generating the same {@link #hashCode()} and {@link #equals(Object)} should return true if the contents of its chars is equal
 * {@link #hashCode()} should return the same as {@link String#hashCode()}
 * every {@link CharSequence} will be treated as equal if all chars are the same
 */
public abstract class CharKey implements CharSequence {
	
	private static final Map<CharSequence, CharKey> CACHE;
	
	static {
		String rawCacheSize = System.getProperty("de.atlasmc.util.map.key.literalCacheSize");
		int cacheSize = NumberConversion.toInt(rawCacheSize, 512);
		CACHE = new ConcurrentHashMap<>(cacheSize);
	}
	
	/**
	 * Stores the current hash of this char key
	 * if a implementation is not immutable it should set this to 0 when changed
	 */
	protected int hash = 0;
	
	protected CharKey() {}
	
	@Override
	public final int hashCode() {
		if (hash != 0)
			return hash;
		final int length = length();
		final char[] buf = getBuf();
		int h = 0;
		for (int i = 0; i < length; i++)
			h = 31 * h + buf[i];
		hash = h;
		return hash;
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof CharSequence))
			return false;
		CharSequence sequence = (CharSequence) obj;
		final int length = sequence.length();
		if (length != length())
			return false;
		final char[] buf = getBuf();
		if (obj instanceof CharKey) {
			final char[] otherBuf = ((CharKey) obj).getBuf();
			for (int i = 0; i < length; i++)
				if (buf[i] != otherBuf[i])
					return false;
		} else 
			for (int i = 0; i < length; i++)
				if (buf[i] != sequence.charAt(i))
					return false;
		return true;
	}
	
	/**
	 * Returns the buffered chars for operations like {@link #equals(Object)} and {@link #hashCode()}.
	 * The returned array should not be modified by any users of this method.
	 * The returned array might be longer than then {@link #length()}.
	 * @return char array
	 */
	protected abstract char[] getBuf();

	/**
	 * Returns the {@link CharSequence} as immutable {@link CharKey}
	 * @param sequence
	 * @return immutable CharKey
	 */
	public static CharKey of(CharSequence sequence) {
		CharKey cached = CACHE.get(sequence);
		return cached != null ? cached : new ImmutableCharKey(sequence);
	}
	
	/**
	 * Returns the {@link String} as immutable {@link CharKey} and caches it
	 * @param literal
	 * @return immutable CharKey
	 */
	public static CharKey literal(CharSequence literal) {
		CharKey cached = CACHE.get(literal);
		if (cached != null)
			return cached;
		CharKey newCached = new ImmutableCharKey(literal);
		cached = CACHE.putIfAbsent(newCached, newCached);
		return cached != null ? cached : newCached;
	}
	
	public static void clearLiteralCache() {
		CACHE.clear();
	}

}
