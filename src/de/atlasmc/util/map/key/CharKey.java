package de.atlasmc.util.map.key;

/**
 * Implementation of {@link CharSequence} for where all implementations should be equal if the contents is the same
 * e.g. generating the same {@link #hashCode()} and {@link #equals(Object)} should return true if the contents of its chars is equal
 * {@link #hashCode()} should return the same as {@link String#hashCode()}
 * every {@link CharSequence} will be treated as equal if all chars are the same
 */
public abstract class CharKey implements CharSequence {
	
	/**
	 * Stores the current hash of this char key
	 * if a implementation is not immutable it should set this to 0 when changed
	 */
	protected int hash = 0;
	
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
	 * @return new immutable CharKey
	 */
	public static CharKey of(CharSequence sequence) {
		return new ImmutableCharKey(sequence);
	}

}
