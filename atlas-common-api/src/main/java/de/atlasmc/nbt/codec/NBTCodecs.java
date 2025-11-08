package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

import de.atlasmc.Color;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;

public class NBTCodecs {
	
	public static final NBTCodec<UUID> UUID_CODEC = new NBTCodec<>() {
		
		@Override
		public Class<?> getType() {
			return UUID.class;
		}
		
		@Override
		public UUID deserialize(UUID value, NBTReader input, CodecContext context) throws IOException {
			switch (input.getType()) {
			case INT_ARRAY:
				return input.readUUID();
			case STRING:
				return UUID.fromString(input.readStringTag());
			// list of int will be ignored
			default:
				throw new NBTException("Unexpected tag type: " + input.getType());
			}
		}
		
		@Override
		public boolean serialize(CharSequence key, UUID value, NBTWriter output, CodecContext context) throws IOException {
			output.writeUUID(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.INT_ARRAY_STRING;
		}
	};
	
	public static final NBTCodec<String> STRING = new NBTCodec<>() {
		
		@Override
		public Class<?> getType() {
			return String.class;
		}
		
		@Override
		public String deserialize(String value, NBTReader input, CodecContext context) throws IOException {
			return input.readStringTag();
		}
		
		@Override
		public boolean serialize(CharSequence key, String value, NBTWriter output, CodecContext context) throws IOException {
			output.writeStringTag(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.STRING;
		}
	};
	
	public static final NBTCodec<Integer> INT = new NBTCodec<Integer>() {
		
		@Override
		public Class<?> getType() {
			return Integer.class;
		}
		
		@Override
		public Integer deserialize(Integer value, NBTReader input, CodecContext context) throws IOException {
			return input.readIntTag();
		}
		
		@Override
		public boolean serialize(CharSequence key, Integer value, NBTWriter output, CodecContext context) throws IOException {
			output.writeIntTag(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.INT;
		}
	};
	
	public static final NBTCodec<Long> LONG = new NBTCodec<Long>() {
		
		@Override
		public Class<?> getType() {
			return Long.class;
		}
		
		@Override
		public Long deserialize(Long value, NBTReader input, CodecContext context) throws IOException {
			return input.readLongTag();
		}
		
		@Override
		public boolean serialize(CharSequence key, Long value, NBTWriter output, CodecContext context) throws IOException {
			output.writeLongTag(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LONG;
		}
	};
	
	public static final NBTCodec<Vector3d> VECTOR_3D = new NBTCodec<Vector3d>() {
		
		@Override
		public Class<?> getType() {
			return Vector3d.class;
		}
		
		@Override
		public boolean isReuseValue() {
			return true;
		}
		
		@Override
		public Vector3d deserialize(Vector3d value, NBTReader input, CodecContext context) throws IOException {
			final TagType listType = input.getListType();
			if (listType != TagType.DOUBLE)
				throw new NBTException("Expected list of type DOUBLE but was: " + listType);
			Vector3d vec = value != null ? value : new Vector3d();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return vec;
			}
			input.readNextEntry();
			vec.x = input.readDoubleTag();
			vec.y = input.readDoubleTag();
			vec.z = input.readDoubleTag();
			input.readNextEntry();
			return vec;
		}
		
		@Override
		public boolean serialize(CharSequence key, Vector3d value, NBTWriter output, CodecContext context) throws IOException {
			output.writeListTag(key, TagType.DOUBLE, 3);
			output.writeDoubleTag(null, value.x);
			output.writeDoubleTag(null, value.y);
			output.writeDoubleTag(null, value.z);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<Vector3f> VECTOR_3F = new NBTCodec<Vector3f>() {
		
		@Override
		public Class<?> getType() {
			return Vector3f.class;
		}
		
		@Override
		public boolean isReuseValue() {
			return true;
		}
		
		@Override
		public Vector3f deserialize(Vector3f value, NBTReader input, CodecContext context) throws IOException {
			final TagType listType = input.getListType();
			if (listType != TagType.FLOAT)
				throw new NBTException("Expected list of type FLOAT but was: " + listType);
			Vector3f vec = value != null ? value : new Vector3f();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return vec;
			}
			input.readNextEntry();
			vec.x = input.readFloatTag();
			vec.y = input.readFloatTag();
			vec.z = input.readFloatTag();
			input.readNextEntry();
			return vec;
		}
		
		@Override
		public boolean serialize(CharSequence key, Vector3f value, NBTWriter output, CodecContext context) throws IOException {
			output.writeListTag(key, TagType.FLOAT, 3);
			output.writeFloatTag(null, value.x);
			output.writeFloatTag(null, value.y);
			output.writeFloatTag(null, value.z);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<Vector3i> VECTOR_3I = new NBTCodec<Vector3i>() {
		
		@Override
		public Class<?> getType() {
			return Vector3i.class;
		}
		
		@Override
		public boolean isReuseValue() {
			return true;
		}
		
		@Override
		public Vector3i deserialize(Vector3i value, NBTReader input, CodecContext context) throws IOException {
			final Vector3i vec = value != null ? value : new Vector3i();
			int[] data = input.readIntArrayTag();
			vec.x = data[0];
			vec.y = data[1];
			vec.z = data[2];
			return vec;
		}
		
		@Override
		public boolean serialize(CharSequence key, Vector3i value, NBTWriter output, CodecContext context) throws IOException {
			output.writeIntArrayTag(key, new int[]{ value.x, value.y, value.z });
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.INT_ARRAY;
		}
	};
	
	public static final NBTCodec<int[]> INT_ARRAY = new NBTCodec<int[]>() {
		
		@Override
		public Class<?> getType() {
			return int[].class;
		}
		
		@Override
		public int[] deserialize(int[] value, NBTReader input, CodecContext context) throws IOException {
			return input.readIntArrayTag();
		}
		
		@Override
		public boolean serialize(CharSequence key, int[] value, NBTWriter output, CodecContext context) throws IOException {
			output.writeIntArrayTag(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.INT_ARRAY;
		}
	};
	
	public static final NBTCodec<Quaternionf> QUATERNION_F = new NBTCodec<Quaternionf>() {
		
		@Override
		public Class<?> getType() {
			return Quaternionf.class;
		}
		
		@Override
		public boolean isReuseValue() {
			return true;
		}
		
		@Override
		public Quaternionf deserialize(Quaternionf value, NBTReader input, CodecContext context) throws IOException {
			final TagType listType = input.getListType();
			if (listType != TagType.FLOAT)
				throw new NBTException("Expected list of type FLOAT but was: " + listType);
			final Quaternionf vec = value != null ? value : new Quaternionf();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return vec;
			}
			input.readNextEntry();
			vec.w = input.readFloatTag();
			vec.x = input.readFloatTag();
			vec.y = input.readFloatTag();
			vec.z = input.readFloatTag();
			input.readNextEntry();
			return vec;
		}
		
		@Override
		public boolean serialize(CharSequence key, Quaternionf value, NBTWriter output, CodecContext context) throws IOException {
			output.writeListTag(key, TagType.FLOAT, 4);
			output.writeFloatTag(null, value.w);
			output.writeFloatTag(null, value.x);
			output.writeFloatTag(null, value.y);
			output.writeFloatTag(null, value.z);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<FloatList> FLOAT_LIST = new NBTCodec<FloatList>() {
		
		@Override
		public Class<?> getType() {
			return FloatList.class;
		}
		
		@Override
		public FloatList deserialize(FloatList value, NBTReader input, CodecContext context) throws IOException {
			final TagType listType = input.getListType();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return value;
			}
			if (listType != TagType.FLOAT)
				throw new NBTException("Expected list of type FLOAT but was: " + listType);
			input.readNextEntry();
			while (input.getRestPayload() > 0) {
				value.add(input.readFloatTag());
			}
			input.readNextEntry();
			return value;
		}
		
		@Override
		public boolean serialize(CharSequence key, FloatList value, NBTWriter output, CodecContext context) throws IOException {
			final int size = value.size();
			output.writeListTag(key, TagType.FLOAT, size);
			for (int i = 0; i < size; i++) {
				output.writeFloatTag(null, value.getFloat(i));
			}
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<byte[]> BYTE_ARRAY = new NBTCodec<byte[]>() {
		
		@Override
		public Class<?> getType() {
			return byte[].class;
		}
		
		@Override
		public byte[] deserialize(byte[] value, NBTReader input, CodecContext context) throws IOException {
			return input.readByteArrayTag();
		}
		
		@Override
		public boolean serialize(CharSequence key, byte[] value, NBTWriter output, CodecContext context) throws IOException {
			if (value.length == 0)
				return true;
			output.writeByteArrayTag(key, value);
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.BYTE_ARRAY;
		}
	};
	
	public static final NBTCodec<BooleanList> BOOLEAN_LIST = new NBTCodec<BooleanList>() {
		
		@Override
		public Class<?> getType() {
			return BooleanList.class;
		}
		
		@Override
		public BooleanList deserialize(BooleanList value, NBTReader input, CodecContext context) throws IOException {
			TagType listType = input.getListType();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return value;
			}
			if (listType != TagType.BYTE)
				throw new NBTException("Expected list of type BYTE but was: " + listType);
			input.readNextEntry();
			while (input.getRestPayload() > 0) {
				value.add(input.readBoolean());
			}
			input.readNextEntry();
			return value;
		}
		
		@Override
		public boolean serialize(CharSequence key, BooleanList value, NBTWriter output, CodecContext context) throws IOException {
			final int size = value.size();
			output.writeListTag(key, TagType.BYTE, size);
			for (int i = 0; i < size; i++) {
				output.writeByteTag(null, value.getBoolean(i));
			}
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<IntCollection> INT_COLLECTION = new NBTCodec<IntCollection>() {
		
		private static final List<TagType> TYPES = List.of(TagType.INT_ARRAY, TagType.LIST);
		
		@Override
		public Class<?> getType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public IntCollection deserialize(IntCollection value, NBTReader input, CodecContext context) throws IOException {
			switch(input.getType()) {
			case INT_ARRAY:
				int[] array = input.readIntArrayTag();
				if (value instanceof IntList list) {
					list.addElements(list.size(), array);
				} else {
					for (int i : array)
						value.add(i);
				}
				return value;
			case LIST: {
				final TagType listType = input.getListType();
				if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
					input.readNextEntry();
					input.readNextEntry();
					return value;
				}
				switch(listType) {
				case INT:
					input.readNextEntry();
					while (input.getRestPayload() > 0) {
						value.add(input.readIntTag());
					}
					input.readNextEntry();
					break;
				case LIST:
					input.readNextEntry(); // enter outer list
					if (input.getListType() != TagType.FLOAT)
						throw new NBTException("Invalid list type: " + input.getListType());
					input.readNextEntry(); // enter float list
					float r = input.readFloatTag();
					float g = input.readFloatTag();
					float b = input.readFloatTag();
					value.add(Color.asRGB(r, g, b));
					input.readNextEntry(); // leaf inner list
					input.readNextEntry(); // leaf outer list
					break;
				default:
					throw new NBTException("Unxecpected list type: " + listType);
				}
				return value;
			}
			default:
				throw new NBTException("Unexpected tag type: " + input.getType());
			}
		}
		
		@Override
		public boolean serialize(CharSequence key, IntCollection value, NBTWriter output, CodecContext context) throws IOException {
			output.writeIntArrayTag(key, value.toIntArray());
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return TYPES;
		}
	};

}
