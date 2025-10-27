package de.atlasmc.io.codec;

import java.io.IOException;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntArrays;

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

}
