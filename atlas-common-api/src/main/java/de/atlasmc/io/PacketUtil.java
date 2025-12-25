package de.atlasmc.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.dataset.SingleValueDataSet;
import de.atlasmc.util.dataset.TagDataSet;
import io.netty.buffer.ByteBuf;

public class PacketUtil {
	
	public static final int 
	MAX_PACKET_LENGTH = 2097151;

	public static final int VAR_SEGMENT_BITS = 0x7F;
	public static final int VAR_CONTINUE_BIT = 0x80;
	
	protected PacketUtil() {}

	public static int readVarInt(ByteBuf in) {
	    int value = 0;
	    int position = 0;
	    byte currentByte;

	    while (true) {
	        currentByte = in.readByte();
	        value |= (currentByte & VAR_SEGMENT_BITS) << position;

	        if ((currentByte & VAR_CONTINUE_BIT) == 0) 
	        	break;

	        position += 7;

	        if (position >= 32) 
	        	throw new ProtocolException("VarInt is too big");
	    }

	    return value;
	}
	
	public static long readVarLong(ByteBuf in) {
	    long value = 0;
	    int position = 0;
	    byte currentByte;

	    while (true) {
	        currentByte = in.readByte();
	        value |= (long) (currentByte & VAR_SEGMENT_BITS) << position;

	        if ((currentByte & VAR_CONTINUE_BIT) == 0) 
	        	break;

	        position += 7;

	        if (position >= 64) 
	        	throw new ProtocolException("VarLong is too big");
	    }

	    return value;
	}
	
	/**
	 * Writes the given value as LEB128 value to the given {@link ByteBuf}
	 * @param value the value to write
	 * @param out the ByteBuf the value is written to
	 */
	public static void writeVarInt(int value, ByteBuf out) {
	    while (true) {
	        if ((value & ~VAR_SEGMENT_BITS) == 0) {
	            out.writeByte(value);
	            return;
	        }

	        out.writeByte((value & VAR_SEGMENT_BITS) | VAR_CONTINUE_BIT);

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	    }
	}
	
	/**
	 * Writes the given values as LEB128 value to the given {@link ByteBuf}
	 * @param values the values to write
	 * @param out the ByteBuf the value is written to
	 */
	public static void writeVarIntArray(int[] values, ByteBuf out) {
		for (int value : values) {
			while (true) {
		        if ((value & ~VAR_SEGMENT_BITS) == 0) {
		            out.writeByte(value);
		            break;
		        }

		        out.writeByte((value & VAR_SEGMENT_BITS) | VAR_CONTINUE_BIT);

		        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
		        value >>>= 7;
		    }
		}
	}
	
	/**
	 * Returns the number of bytes to represent the given value as LEB128 var int
	 * @param value
	 * @return number of bytes
	 */
	public static int getVarIntLength(int value) {
		int i = 0;
		while (true) {
	        if ((value & ~VAR_SEGMENT_BITS) == 0) {
	            i++;
	            return i;
	        }

	        i++;

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	    }
	}
	
	/**
	 * Returns the number of bytes to represent the given value as LEB128 var long
	 * @param value
	 * @return number of bytes
	 */
	public static int getVarLongLength(long value) {
		int length = 0;
		while (true) {
	        if ((value & ~((long) VAR_SEGMENT_BITS)) == 0) {
	            length++;
	            return length;
	        }

	        length++;

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	    }
	}
	
	/**
	 * Writes the given values as LEB128 value to the given {@link ByteBuf}
	 * @param values the values to write
	 * @param out the ByteBuf the value is written to
	 */
	public static void writeVarLongArray(long[] values, ByteBuf out) {
		for (long value : values) {
			while (true) {
		        if ((value & ~((long) VAR_SEGMENT_BITS)) == 0) {
		            out.writeByte((int) value);
		            break;
		        }

		        out.writeByte((int) ((value & VAR_SEGMENT_BITS) | VAR_CONTINUE_BIT));

		        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
		        value >>>= 7;
		    }
		}
	}
	
	/**
	 * Writes the given value as LEB128 value to the given {@link ByteBuf}
	 * @param value the value to write
	 * @param out the ByteBuf the value is written to
	 */
	public static void writeVarLong(long value, ByteBuf out) {
	    while (true) {
	        if ((value & ~((long) VAR_SEGMENT_BITS)) == 0) {
	            out.writeByte((int) value);
	            return;
	        }

	        out.writeByte((int) ((value & VAR_SEGMENT_BITS) | VAR_CONTINUE_BIT));

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	    }
	}
	
	public static int readArrayLength(ByteBuf in, int max) {
		int length = readVarInt(in);
		if (length > max)
			throw new ProtocolException("Invalid array length:" + length + " expected: " + max);
		return length;
	}
	
	/**
	 * Reads a byte array of {@link #readVarInt(ByteBuf)} length
	 * @param in
	 * @param maxLength
	 * @return array
	 * @throws IllegalArgumentException if length > maxLength
	 */
	@Nullable
	public static byte[] readByteArray(ByteBuf in, int maxLength) {
		int len = readArrayLength(in, maxLength);
		if (len == 0) 
			return null;
		byte[] buff = new byte[len];
		in.readBytes(buff);
		return buff;
	}
	
	/**
	 * Writes the a varint indicating the length of the given array followed byte the array
	 * @param out
	 * @param data to write
	 */
	public static void writeByteArray(ByteBuf out, byte[] data) {
		writeVarInt(data.length, out);
		out.writeBytes(data);
	}
	
	@NotNull
	public static BitSet readBitSet(ByteBuf in) {
		return readBitSet(in, 0);
	}
	
	@NotNull
	public static BitSet readBitSet(ByteBuf in, int maxBits) {
		int numberOfLongs = readVarInt(in);
		if (maxBits > 0) {
			int maxLongs = Math.max(maxBits / 64, 1);
			if (numberOfLongs > maxLongs)
				throw new IllegalArgumentException("Invalid number of longs for BitSet:" + numberOfLongs + " expected: " + maxLongs + " (" + maxBits + " bits)");
		}
		long[] data = new long[numberOfLongs];
		for (int i = 0; i < numberOfLongs; i++)
			data[i] = in.readLong();
		return BitSet.valueOf(data);
	}
	
	public static void writeBitSet(BitSet set, ByteBuf out) {
		long[] data = set.toLongArray();
		writeVarInt(data.length, out);
		for (long l : data) {
			out.writeLong(l);
		}
	}
	
	public static <T extends ProtocolRegistryValue> void writeDataSet(DataSet<T> set, ProtocolRegistry<T> registry, ByteBuf out) throws IOException {
		if (set == null) {
			writeVarInt(1, out);
			return;
		}
		if (set instanceof TagDataSet tagSet) {
			writeVarInt(0, out);
			NamespacedKey.STREAM_CODEC.serialize(tagSet.getTag().getNamespacedKey(), out);
		} else if (set.size() == 0) {
			writeVarInt(1, out);
		} else if (set instanceof SingleValueDataSet<T> singleValue) {
			writeVarInt(2, out);
			writeVarInt(singleValue.getValue().getID(), out);
		} else {
			Collection<T> values = set.values();
			final int size = values.size() + 1;
			writeVarInt(size, out);
			for (T value : values)
				writeVarInt(value.getID(), out);
		}
	}
	
	@NotNull
	public static <T extends ProtocolRegistryValue> DataSet<T> readDataSet(ProtocolRegistry<T> registry, ByteBuf in) throws IOException {
		final int id = readVarInt(in);
		if (id == 0) {
			NamespacedKey key = NamespacedKey.STREAM_CODEC.deserialize(in);
			Tag<T> tag = Tags.getTag(key);
			if (tag == null)
				throw new ProtocolException("Missing tag while reading data set: " + key);
			return DataSet.of(tag);
		}
		final int count = id - 1;
		if (count == 0)
			return DataSet.of();
		if (count == 1) {
			T value = registry.getByID(id);
			return DataSet.of(value);
		}
		ArrayList<T> values = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			T value = registry.getByID(readVarInt(in));
			if (value == null)
				continue;
			values.add(value);
		}
		return DataSet.of(values);
	}
	
}
