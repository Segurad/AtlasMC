package de.atlasmc.node.block.data.property;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.CloneException;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.PacketUtil.*;

/**
 * Property value that stores the key of the property and a exact or min/max value
 */
public class PropertyValuePredicate implements StreamSerializable, Cloneable {
	
	public static final NBTCodec<List<PropertyValuePredicate>> LIST_NBT_CODEC = new NBTCodec<List<PropertyValuePredicate>>() {
		
		@Override
		public Class<?> getType() {
			return List.class;
		}

		@Override
		public boolean serialize(CharSequence key, List<PropertyValuePredicate> value, NBTWriter writer, CodecContext context) throws IOException {
			writer.writeCompoundTag(key);
			for (PropertyValuePredicate predicate : value) {
				if (predicate.isExact()) {
					writer.writeStringTag(predicate.getKey(), predicate.getValue());
				} else {
					writer.writeCompoundTag(predicate.getKey());
					writer.writeStringTag("min", predicate.getValue());
					writer.writeStringTag("max", predicate.getMaxValue());
					writer.writeEndTag();
				}
			}
			writer.writeEndTag();
			return true;
		}

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public List<PropertyValuePredicate> deserialize(List<PropertyValuePredicate> value, NBTReader reader, CodecContext context) throws IOException {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				final TagType type = reader.getType();
				PropertyValuePredicate predicate;
				if (type == TagType.COMPOUND) {
					predicate = new PropertyValuePredicate(reader.getFieldName().toString());
					reader.readNextEntry();
					while (reader.getType() != TagType.TAG_END) {
						CharKey key = reader.getFieldName();
						if (key.equals("min")) {
							predicate.setValue(reader.readStringTag());
						} else if (key.equals("max")) {
							predicate.setMaxValue(reader.readStringTag());
						} else {
							reader.skipTag();
						}
					}
					reader.readNextEntry();
				} else if (type == TagType.STRING) {
					predicate = new PropertyValuePredicate(reader.getFieldName().toString());
					predicate.setValue(reader.readStringTag());
				} else {
					throw new NBTException("Unexpected type: " + type);
				}
				value.add(predicate);
			}
			reader.readNextEntry();
			return value;
		}

		@Override
		public List<TagType> getTags() {
			return CodecTags.COMPOUND;
		}
		
	};
	
	public static final StreamCodec<PropertyValuePredicate>
	STREAM_CODEC = new StreamCodec<PropertyValuePredicate>() {
		
		@Override
		public boolean serialize(PropertyValuePredicate value, ByteBuf output, CodecContext context) throws IOException {
			writeString(value.key, output);
			if (value.maxValue == null) {
				output.writeBoolean(true);
				writeString(value.value, output);
			} else {
				output.writeBoolean(false);
				writeString(value.value, output);
				writeString(value.maxValue, output);
			}
			return true;
		}
		
		@Override
		public Class<? extends PropertyValuePredicate> getType() {
			return PropertyValuePredicate.class;
		}
		
		@Override
		public PropertyValuePredicate deserialize(PropertyValuePredicate value, ByteBuf input, CodecContext context) throws IOException {
			PropertyValuePredicate predicate = new PropertyValuePredicate(readString(input));
			if (input.readBoolean()) {
				predicate.value = readString(input);
			} else {
				predicate.value = readString(input);
				predicate.value = readString(input);
			}
			return predicate;
		}
	};
	
	private final String key;
	private String value;
	private String maxValue;
	
	public PropertyValuePredicate(String key) {
		this.key = Objects.requireNonNull(key);
	}
	
	public boolean isExact() {
		return maxValue != null;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getMaxValue() {
		return maxValue;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
	@Override
	public StreamCodec<? extends PropertyValuePredicate> getStreamCodec() {
		return STREAM_CODEC;
	}
	
	@Override
	public PropertyValuePredicate clone() {
		try {
			return (PropertyValuePredicate) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

}
