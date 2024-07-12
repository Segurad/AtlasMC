package de.atlasmc.util.nbt.tag;

import java.util.Collection;
import java.util.Iterator;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractCollectionTag<T extends AbstractCollectionTag<T, C>, C extends Collection<NBT>> extends AbstractTag implements Iterable<NBT> {

	protected C data;
	
	public AbstractCollectionTag(String name) {
		this.name = name;
		this.data = createCollection();
	}
	
	public AbstractCollectionTag() {
		this.data = createCollection();
	}

	protected abstract C createCollection();
	
	@Override
	public C getData() {
		return data;
	}

	/**
	 * 
	 * @param tag
	 * @return this
	 */
	public T addTag(NBT tag) {
		add(tag);
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addByteArrayTag(String name, byte[] value) {
		add(new ByteArrayTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addByteTag(String name, byte value) {
		add(new ByteTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addDoubleTag(String name, double value) {
		add(new DoubleTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addFloatTag(String name, float value) {
		add(new FloatTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addIntArrayTag(String name, int[] value) {
		add(new IntArrayTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addIntTag(String name, int value) {
		add(new IntTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @return the created list
	 */
	public ListTag addListTag(String name, TagType type) {
		ListTag tag = new ListTag(name, type);
		add(tag);
		return tag;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addLongArrayTag(String name, long[] value) {
		add(new LongArrayTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addLongTag(String name, long value) {
		add(new LongTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addShortTag(String name, short value) {
		add(new ShortTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T addStringTag(String name, String value) {
		add(new StringTag(name, value));
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return this
	 */
	public T removeTag(NBT tag) {
		data.remove(tag);
		return getThis();
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 * @return the created compound
	 */
	public CompoundTag addCompoundTag(String name) {
		CompoundTag tag = new CompoundTag(name);
		add(tag);
		return tag;
	}

	@Override
	public Iterator<NBT> iterator() {
		return data.iterator();
	}
	
	@Override
	public T clone() {
		@SuppressWarnings("unchecked")
		T clone = (T) super.clone();
		if (clone == null)
			return null;
		if (data != null) {
			clone.data = clone.createCollection();
			clone.setData(data);
		}
		return clone;
	}
	
	@Override
	public void setData(Object data) {
		this.data.clear();
		if (data instanceof Collection list) {
			for (Object obj : list) {
				if (obj instanceof NBT nbt)
					add(nbt);
			}
		} else if (data instanceof NBT nbt) {
			add(nbt);
		}
	}
	
	protected void remove(NBT data) {
		this.data.remove(data);
	}
	
	protected void add(NBT data) {
		this.data.add(data);
	}
	
	protected abstract T getThis();
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		AbstractCollectionTag<?, ?> other = (AbstractCollectionTag<?, ?>) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

}
