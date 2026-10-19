package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.field.NBTCompoundField;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class NBTCodecImpl<T> extends AbstractCodec<T> {

	private final NBTCompoundField<T> fields;
	private final boolean isField;
	
	public NBTCodecImpl(NBTCodecBuilder<T> builder) {
		super(builder);
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
				return handler.serializePartial(value, output, context);
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
	public T deserializePartial(T value, NBTReader reader, CodecContext context) throws IOException {
		if (value == null)
			value = construct(reader, context);
		// redirect if required for correct deserialization of child implementations
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.deserializePartial(value, reader, context);
			}
		}
		fields.deserializePartial(value, reader, context);
		return value;
	}
	
	void addToBuilder(NBTCodecBuilder<T> builder) {
		if (constructor != null)
			builder.constructor(constructor);
		if (defaultConstructor != null)
			builder.defaultConstructor(defaultConstructor);
		for (var field : fields.getFields()) {
			builder.addField(field);
		}
	}

}
