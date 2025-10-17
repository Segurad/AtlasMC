package de.atlasmc.util.nbt.codec.field;

import java.util.Objects;

public abstract class AbstractPrimitiveField<T, G, S> extends NBTField<T> {

	protected final G getter;
	protected final S setter;
	protected final boolean useDefault;
	
	public AbstractPrimitiveField(PrimitiveFieldBuilder<T, G, S> builder) {
		super(builder);
		this.getter = Objects.requireNonNull(builder.getGetter());
		this.setter = Objects.requireNonNull(builder.getSetter());
		this.useDefault = builder.isUseDefault();
	}

}
