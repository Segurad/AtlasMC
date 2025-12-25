package de.atlasmc.io.codec;

import java.io.IOException;

import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;

public class StreamCodecs {
	
	public static final StreamCodec<Quaternionf> QUATERNION_F = StreamCodec
			.builder(Quaternionf.class)
			.defaultConstructor(Quaternionf::new)
			.floatValue(Quaternionf::x, (t, v) -> t.x = v)
			.floatValue(Quaternionf::y, (t, v) -> t.y = v)
			.floatValue(Quaternionf::z, (t, v) -> t.z = v)
			.floatValue(Quaternionf::w, (t, v) -> t.w = v)
			.build();
	
	public static final StreamCodec<Vector3f> VECTOR_3F = StreamCodec
			.builder(Vector3f.class)
			.defaultConstructor(Vector3f::new)
			.floatValue(Vector3f::x, (t, v) -> t.x = v)
			.floatValue(Vector3f::y, (t, v) -> t.y = v)
			.floatValue(Vector3f::z, (t, v) -> t.z = v)
			.build();
	
	public static final StreamCodec<Vector3d> VECTOR_3D = StreamCodec
			.builder(Vector3d.class)
			.defaultConstructor(Vector3d::new)
			.doubleValue(Vector3d::x, (t, v) -> t.x = v)
			.doubleValue(Vector3d::y, (t, v) -> t.y = v)
			.doubleValue(Vector3d::z, (t, v) -> t.z = v)
			.build();
	
	public static final StreamCodec<int[]> INT_ARRAY = new StreamCodec<int[]>() {
		
		@Override
		public boolean serialize(int[] value, ByteBuf output, CodecContext context) throws IOException {
			if (value == null) {
				PacketUtil.writeVarInt(0, output);
				return true;
			}
			PacketUtil.writeVarInt(value.length, output);
			for (int i : value) {
				output.writeInt(i);
			}
			return true;
		}
		
		@Override
		public Class<? extends int[]> getType() {
			return int[].class;
		}
		
		@Override
		public int[] deserialize(int[] value, ByteBuf input, CodecContext context) throws IOException {
			final int size = PacketUtil.readVarInt(input);
			if (size == 0)
				return IntArrays.EMPTY_ARRAY;
			int[] array = new int[size];
			for (int i = 0; i < size; i++) {
				array[i] = input.readInt();
			}
			return array;
		}
	};
	
	public static final StreamCodec<FloatList> FLOAT_LIST = new StreamCodec<FloatList>() {
		
		@Override
		public boolean serialize(FloatList value, ByteBuf output, CodecContext context) throws IOException {
			if (value == null || value.isEmpty()) {
				PacketUtil.writeVarInt(0, output);
				return true;
			}
			final int size = value.size();
			PacketUtil.writeVarInt(size, output);
			for (int i = 0; i < size; i++) {
				output.writeFloat(value.getFloat(i));
			}
			return true;
		}
		
		@Override
		public Class<?> getType() {
			return FloatList.class;
		}
		
		@Override
		public FloatList deserialize(FloatList value, ByteBuf input, CodecContext context) throws IOException {
			final int size = PacketUtil.readVarInt(input);
			if (size == 0)
				return value;
			FloatList list = value != null ? value : new FloatArrayList(size);
			for (int i = 0; i < size; i++) {
				list.add(input.readFloat());
			}
			return list;
		}
	};
	
	public static final StreamCodec<BooleanList> BOOLEAN_LIST = new StreamCodec<BooleanList>() {
		
		@Override
		public boolean serialize(BooleanList value, ByteBuf output, CodecContext context) throws IOException {
			if (value == null || value.isEmpty()) {
				PacketUtil.writeVarInt(0, output);
				return true;
			}
			final int size = value.size();
			PacketUtil.writeVarInt(size, output);
			for (int i = 0; i < size; i++) {
				output.writeBoolean(value.getBoolean(i));
			}
			return true;
		}
		
		@Override
		public Class<?> getType() {
			return FloatList.class;
		}
		
		@Override
		public BooleanList deserialize(BooleanList value, ByteBuf input, CodecContext context) throws IOException {
			final int size = PacketUtil.readVarInt(input);
			if (size == 0)
				return value;
			BooleanList list = value != null ? value : new BooleanArrayList(size);
			for (int i = 0; i < size; i++) {
				list.add(input.readBoolean());
			}
			return list;
		}
	};
	
	public static final StreamCodec<IntCollection> INT_COLLECTION = new StreamCodec<IntCollection>() {
		
		@Override
		public boolean serialize(IntCollection value, ByteBuf output, CodecContext context) throws IOException {
			if (value == null || value.isEmpty()) {
				PacketUtil.writeVarInt(0, output);
				return true;
			}
			final int size = value.size();
			PacketUtil.writeVarInt(size, output);
			for (int i : value) {
				output.writeInt(i);
			}
			return true;
		}
		
		@Override
		public Class<?> getType() {
			return FloatList.class;
		}
		
		@Override
		public IntCollection deserialize(IntCollection value, ByteBuf input, CodecContext context) throws IOException {
			final int size = PacketUtil.readVarInt(input);
			if (size == 0)
				return value;
			IntCollection list = value != null ? value : new IntArrayList(size);
			for (int i = 0; i < size; i++) {
				list.add(input.readInt());
			}
			return list;
		}
	};

}
