package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.util.Builder;

public abstract class NBTFieldBuilder<T, B extends NBTFieldBuilder<T, B>> implements Builder<NBTField<T>>  {

	private CharSequence key;
	private boolean serverOnly;
	
	public CharSequence getKey() {
		return key;
	}
	
	public abstract List<TagType> getTypes();
	
	public B setKey(CharSequence key) {
		this.key = key;
		return getThis();
	}
	
	public boolean isServerOnly() {
		return serverOnly;
	}
	
	public B setServerOnly(boolean serverOnly) {
		this.serverOnly = serverOnly;
		return getThis();
	}
	
	protected abstract B getThis();

}
