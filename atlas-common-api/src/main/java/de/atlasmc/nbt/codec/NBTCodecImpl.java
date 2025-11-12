package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.constructor.Constructor;
import de.atlasmc.nbt.codec.field.NBTCompoundField;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class NBTCodecImpl<T> implements NBTCodec<T> {

	private final Class<T> type;
	private final boolean redirectAfterConstruction;
	private final Constructor<T> constructor;
	private final Supplier<T> defaultConstrcutor;
	private final NBTCompoundField<T> fields;
	private final boolean isField;
	
	public NBTCodecImpl(NBTCodecBuilder<T> builder) {
		this.type = Objects.requireNonNull(builder.getType());
		this.redirectAfterConstruction = builder.isRedirectAfterConstruction();
		this.constructor = builder.getConstructor();
		this.defaultConstrcutor = builder.getDefaultConstructor();
		this.fields = builder.buildFields();
		this.isField = builder.isField();
	}
	
	@Override
	public boolean isField() {
		return isField;
	}
	
	@Override
	public List<TagType> getTags() {
		return CodecTags.COMPOUND;
	}
	
	@Override
	public boolean serialize(CharSequence key, T value, NBTWriter output, CodecContext context) throws IOException {
		output.writeCompoundTag(key);
		serializePartial(value, output, context);
		output.writeEndTag();
		return true;
	}
	
	@Override
	public boolean serializePartial(T value, NBTWriter output, CodecContext context) throws IOException {
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.serialize(value, output, context);
			}
		}
		if (constructor != null)
			constructor.serialize(value, output, context);
		if (fields != null)
			return fields.serializePartial(value, output, context);
		return true;
	}

	@Override
	public T deserialize(T value, NBTReader input, CodecContext context) throws IOException {
		input.readNextEntry();
		return deserializePartial(value, input, context);
	}
	
	@Override
	public T deserializePartial(T value, NBTReader input, CodecContext context) throws IOException {
		if (value == null) { // initialize value using constructor or default constructor
			if (constructor != null)
				value = constructor.construct(input, context);
			if (value == null && defaultConstrcutor != null) {
				value = defaultConstrcutor.get();
				if (value == null)
					throw new NBTException("Unable to construct type!");
			}
		}
		// redirect if required for correct deserialization of child implementations
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.deserializePartial(value, input, context);
			}
		}
		fields.deserializePartial(value, input, context);
		return value;
	}

	@Override
	public Class<T> getType() {
		return type;
	}
	
	void addToBuilder(NBTCodecBuilder<T> builder) {
		if (constructor != null)
			builder.setConstructor(constructor);
		if (defaultConstrcutor != null)
			builder.defaultConstructor(defaultConstrcutor);
		for (var field : fields.getFields()) {
			builder.addField(field);
		}
	}

}
