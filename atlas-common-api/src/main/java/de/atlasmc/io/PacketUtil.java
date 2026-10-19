package de.atlasmc.io;

import java.util.BitSet;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import io.netty.buffer.ByteBuf;

public class PacketUtil {
	
	public static final int 
	/**
	 * Maximum length a packet may have when read or write
	 */
	MAX_PACKET_LENGTH = 2097151,
	/**
	 * Maximum length a packet may have in uncompressed state
	 */
	MAX_UNCOMPRESSED_LENGTH = 8388608;

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
			throw new ProtocolException("Invalid array length: " + length + " expected: " + max);
		if (length < 0)
			throw new ProtocolException("Invalid array length: " + length);
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
			int maxLongs = Math.ceilDiv(maxBits, 64);
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
	
}
