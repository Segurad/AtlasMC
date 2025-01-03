package de.atlasmc.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.UUID;
import java.util.function.ToIntFunction;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.dataset.SingleValueDataSet;
import de.atlasmc.util.dataset.TagDataSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public class PacketUtil {
	
	public static final int 
	MAX_PACKET_LENGTH = 2097151,
	CHAT_MAX_LENGTH = 262144,
    MAX_IDENTIFIER_LENGTH = 32767;

	private static final int SEGMENT_BITS = 0x7F;
	private static final int CONTINUE_BIT = 0x80;
	
	protected PacketUtil() {}

	public static int readVarInt(ByteBuf in) {
	    int value = 0;
	    int position = 0;
	    byte currentByte;

	    while (true) {
	        currentByte = in.readByte();
	        value |= (currentByte & SEGMENT_BITS) << position;

	        if ((currentByte & CONTINUE_BIT) == 0) 
	        	break;

	        position += 7;

	        if (position >= 32) 
	        	throw new RuntimeException("VarInt is too big");
	    }

	    return value;
	}
	
	public static long readVarLong(ByteBuf in) {
	    long value = 0;
	    int position = 0;
	    byte currentByte;

	    while (true) {
	        currentByte = in.readByte();
	        value |= (long) (currentByte & SEGMENT_BITS) << position;

	        if ((currentByte & CONTINUE_BIT) == 0) 
	        	break;

	        position += 7;

	        if (position >= 64) 
	        	throw new RuntimeException("VarLong is too big");
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
	        if ((value & ~SEGMENT_BITS) == 0) {
	            out.writeByte(value);
	            return;
	        }

	        out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

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
		        if ((value & ~SEGMENT_BITS) == 0) {
		            out.writeByte(value);
		            break;
		        }

		        out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);

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
	        if ((value & ~SEGMENT_BITS) == 0) {
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
	        if ((value & ~((long) SEGMENT_BITS)) == 0) {
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
		        if ((value & ~((long) SEGMENT_BITS)) == 0) {
		            out.writeByte((int) value);
		            break;
		        }

		        out.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

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
	        if ((value & ~((long) SEGMENT_BITS)) == 0) {
	            out.writeByte((int) value);
	            return;
	        }

	        out.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	    }
	}
	
	@Nullable
	public static String readString(ByteBuf in) {
		return readString(in, Integer.MAX_VALUE);
	}
	
	@NotNull
	public static NamespacedKey readIdentifier(ByteBuf in) {
		return NamespacedKey.of(readString(in, MAX_IDENTIFIER_LENGTH));
	}
	
	public static void writeIdentifier(NamespacedKey key, ByteBuf out) {
		writeString(key.toString(), out);
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
		int len = readVarInt(in);
		if (len == 0) 
			return null;
		if (len > maxLength) 
			throw new IllegalArgumentException("Invalid byte array length:" + len + " expected: " + maxLength);
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
	
	@Nullable
	public static String readString(ByteBuf in, int maxLength) {
		int len = readVarInt(in);
		if (len == 0) 
			return null;
		if (len > maxLength) 
			throw new IllegalArgumentException("Invalid String length:" + len + " expected: " + maxLength);
		String value = in.toString(in.readerIndex(), len, StandardCharsets.UTF_8);
		in.readerIndex(in.readerIndex()+len);
		return value;
	}
	
	/**
	 * Writes a String prefixed with a varint indicating the length of the following byte array.
	 * @param value the String to write
	 * @param out the buffer the data should be written too
	 * @implNote if the String is null a string with length of 0 will be written
	 */
	public static void writeString(CharSequence value, ByteBuf out) {
		if (value == null)  {
			writeVarInt(0, out);
			return;
		}
		int maxBytes = ByteBufUtil.utf8MaxBytes(value);
		ByteBuf buf = out.alloc().buffer(maxBytes);
		int bytes = ByteBufUtil.writeUtf8(buf, value);
		writeVarInt(bytes, out);
		out.writeBytes(buf);
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
	
	@NotNull
	public static UUID readUUID(ByteBuf in) {
		long most = in.readLong();
		long least = in.readLong();
		return new UUID(most, least);
	}
	
	public static void writeUUID(UUID uuid, ByteBuf out) {
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
	}
	
	public static Chat readTextComponent(ByteBuf in) throws IOException {
		int type = in.getByte(in.readerIndex());
		Chat chat;
		if (type == TagType.STRING.getID()) {
			in.readByte();
			int size = in.readShort();
			byte[] buff = new byte[size];
			in.readBytes(buff);
			chat = ChatUtil.toChat(new String(buff));
		} else {
			NBTNIOReader reader = new NBTNIOReader(in, true);
			chat = ChatUtil.fromNBT(reader);
			reader.close();
		}
		return chat;
	}
	
	public static void writeTextComponent(Chat text, ByteBuf out) throws IOException {
		NBTWriter writer = new NBTNIOWriter(out);
		ChatUtil.toNBT(text, writer);
		writer.close();
	}
	
	public static Chat readTextComponent(NBTReader reader) throws IOException {
		return ChatUtil.fromNBT(reader);
	}
	
	public static void writeTextComponent(Chat text, NBTWriter writer) throws IOException {
		ChatUtil.toNBT(text, writer);
	}
	
	public static <T extends Namespaced> void writeDataSet(DataSet<T> set, ProtocolRegistry<T> registry, ByteBuf out) {
		if (set instanceof TagDataSet tagSet) {
			writeVarInt(0, out);
			writeIdentifier(tagSet.getTag().getNamespacedKey(), out);
		} else if (set.size() == 0) {
			writeVarInt(1, out);
		} else if (set instanceof SingleValueDataSet<T> singleValue) {
			writeVarInt(2, out);
			ToIntFunction<T> toInt = registry.getIDSupplier();
			writeVarInt(toInt.applyAsInt(singleValue.getValue()), out);
		} else {
			Collection<T> values = set.values();
			final int size = values.size() + 1;
			writeVarInt(size, out);
			final ToIntFunction<T> toInt = registry.getIDSupplier();
			for (T value : values)
				writeVarInt(toInt.applyAsInt(value), out);
		}
	}
	
	@NotNull
	public static <T extends Namespaced> DataSet<T> readDataSet(ProtocolRegistry<T> registry, ByteBuf in) {
		final int id = readVarInt(in);
		if (id == 0) {
			NamespacedKey key = readIdentifier(in);
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
