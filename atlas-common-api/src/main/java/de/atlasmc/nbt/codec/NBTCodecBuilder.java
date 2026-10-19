package de.atlasmc.nbt.codec;

import de.atlasmc.nbt.codec.field.NBTCompoundField;
import de.atlasmc.nbt.codec.field.NBTCompoundFieldBuilder;
import de.atlasmc.nbt.codec.field.NBTField;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.Builder;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.function.ToBooleanFunction;

public class NBTCodecBuilder<T> extends AbstractCodecBuilder<T, NBTCodecBuilder<T>> implements AbstractNBTCompoundFieldBuilder<T, NBTCodecBuilder<T>>, Builder<NBTCodec<T>> {
	
	private Boolean isField;
	
	protected final NBTCompoundFieldBuilder<T> root = new NBTCompoundFieldBuilder<T>().setKey("root");
	protected NBTCompoundFieldBuilder<T> builder = root;
	
	protected NBTCodecBuilder(@NotNull Class<T> clazz) {
		super(clazz);
	}
	
	public boolean isField() {
		return isField != null ? isField : constructor != null ? constructor.requiredField() : false;
	}
	
	public Boolean getIsField() {
		return isField;
	}
	
	public NBTCodecBuilder<T> setField(Boolean isField) {
		this.isField = isField;
		return this;
	}
	
	@Override
	public NBTCodecBuilder<T> addField(NBTField<T> field) {
		builder.addField(field);
		return getThis();
	}
	
	public NBTCompoundField<T> buildFields() {
		return root.build();
	}
	
	@Override
	public NBTCodec<T> build() {
		return new NBTCodecImpl<>(getThis());
	}
	
	@SuppressWarnings("unchecked")
	public NBTCodecBuilder<T> include(@NotNull NBTCodec<? super T> include) {
		if (!(include instanceof NBTCodecImpl codec))
			throw new IllegalArgumentException("Codec must be instanceof NBTCodecImpl!");
		AtlasUtil.requireAssignable(codec.getType(), type);
		codec.addToBuilder(this);
		return this;
	}
	
	@Override
	public NBTCodecBuilder<T> beginComponent(CharSequence key, ToBooleanFunction<T> has, boolean serverOnly) {
		builder = builder.beginComponent(key, has, serverOnly);
		return getThis();
	}
	
	@Override
	public NBTCodecBuilder<T> endComponent() {
		builder = builder.endComponent();
		return getThis();
	}

	@Override
	public NBTCodecBuilder<T> getThis() {
		return this;
	}

	@Override
	public NBTCodecBuilder<T> setHasData(ToBooleanFunction<T> has) {
		builder.setHasData(has);
		return this;
	}

	@Override
	public ToBooleanFunction<T> getHasData() {
		return builder.getHasData();
	}

	@Override
	public NBTCodecBuilder<T> setAllowNoData(boolean allow) {
		builder.setAllowNoData(allow);
		return this;
	}

	@Override
	public boolean isAllowNoData() {
		return builder.isAllowNoData();
	}

}
