package de.atlasmc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.codec.CodecContext;

public class NamespacedKey implements CharSequence {

	public static final NBTCodec<NamespacedKey> NBT_CODEC = new NBTCodec<NamespacedKey>() {
		
		@Override
		public Class<? extends NamespacedKey> getType() {
			return NamespacedKey.class;
		}
		
		@Override
		public NamespacedKey deserialize(NamespacedKey value, NBTReader input, CodecContext context) throws IOException {
			return input.readNamespacedKey();
		}
		
		@Override
		public boolean serialize(CharSequence key, NamespacedKey value, NBTWriter output, CodecContext context) throws IOException {
			output.writeNamespacedKey(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.STRING;
		}
	};
	
	/**
	 * Key for {@link Namespaced} classes that have no unique key
	 */
	public static final NamespacedKey INLINE_DEFINITION;
	
	public static final Pattern NAMESPACE_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_\\/]+$");
	public static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_\\/:]+$");
	public static final Pattern NAMESPACED_KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_\\/]+:[a-z0-9\\.\\-_\\/:]+$");
	
	public static final String MINECRAFT = "minecraft";
	public static final String ATLAS = "atlas";
	
	private static final Map<String, NamespacedKey> CACHE;
	
	static {
		String rawCacheSize = System.getProperty("de.atlasmc.namespacedkey.literalCacheSize");
		int cacheSize = NumberConversion.toInt(rawCacheSize, 512);
		CACHE = new ConcurrentHashMap<>(cacheSize);
		INLINE_DEFINITION = literal("atlas:inline-definition");
	}
	
	private final String key;
	private final String namespace;
	private final String combination;
	
	private NamespacedKey(String namespace, String key, String namespacedKey) {
		this.key = key;
		this.namespace = namespace.intern();
		this.combination = namespacedKey;
	}
	
	@NotNull
	public String key() {
		return key;
	}
	
	@NotNull
	public String namespace() {
		return namespace;
	}
	
	@Override
	public String toString() {
		return combination;
	}
	
	/**
	 * Returns whether or no {@link #key()} can be represented as valid {@link NamespacedKey}
	 * @return true if child key
	 */
	public boolean hasChildKey() {
		return key.indexOf(':') != -1;
	}
	
	/**
	 * Returns the {@link NamespacedKey} representation of {@link #key()} or null if not valid NamespacedKey
	 * @return child or null
	 */
	@Nullable
	public NamespacedKey getChildKey() {
		return hasChildKey() ? NamespacedKey.of(key) : null;
	}
	
	/**
	 * Represents a object that has a {@link NamespacedKey}
	 */
	public static interface Namespaced {
		
		/**
		 * Returns the {@link NamespacedKey} of this Object
		 * @return key
		 */
		@NotNull
		NamespacedKey getNamespacedKey();
		
		/**
		 * Returns the {@link NamespacedKey} used client side of this Object.
		 * If no other client key is defined {@link #getNamespacedKey()} should be returned.
		 * @return key
		 */
		@NotNull
		default NamespacedKey getClientKey() {
			return getNamespacedKey();
		}
		
	}

	@Override
	public int hashCode() {
		return combination.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamespacedKey other = (NamespacedKey) obj;
		if (combination == null) {
			if (other.combination != null)
				return false;
		} else if (!combination.equals(other.combination))
			return false;
		return true;
	}

	@NotNull
	public static NamespacedKey of(CharSequence namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		if (namespacedKey instanceof NamespacedKey key)
			return key;
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		String rawKey = namespacedKey.toString();
		int first = rawKey.indexOf(':');
		String namespace;
		String key;
		if (first == -1) {
			namespace = MINECRAFT;
			key = namespacedKey.toString();
			rawKey = namespace + ":" + key;
			k = CACHE.get(rawKey);
			if (k != null)
				return k;
			if (!KEY_PATTERN.matcher(key).matches())
				throw new IllegalArgumentException("Invalid key: " + rawKey);
		} else if (NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches()) {
			namespace = rawKey.substring(0, first);
			key = rawKey.substring(first+1);
		} else {
			throw new IllegalArgumentException("Invalid namespaced key: " + rawKey);
		}
		return new NamespacedKey(namespace, key, rawKey);
	}
	
	@NotNull
	public static NamespacedKey of(String namespace, String key) {
		if (namespace == null) 
			throw new IllegalArgumentException("Namespace can not be null!");
		if (key == null) 
			throw new IllegalArgumentException("Name can not be null!");
		String namespacedKey = namespace + ":" + key;
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		if (!NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches())
			throw new IllegalArgumentException("Invalid namespaced key: " + namespacedKey);
		return new NamespacedKey(namespace, key, namespacedKey);
	}
	
	@NotNull
	public static NamespacedKey literal(String namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		k = of(namespacedKey);
		NamespacedKey cacheKey = CACHE.putIfAbsent(namespacedKey, k);
		if (cacheKey != null)
			k = cacheKey;
		return k;
	}
	
	public static void clearLiteralCache() {
		CACHE.clear();
	}

	@Override
	public int length() {
		return combination.length();
	}

	@Override
	public char charAt(int index) {
		return combination.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return combination.subSequence(start, end);
	}

}
