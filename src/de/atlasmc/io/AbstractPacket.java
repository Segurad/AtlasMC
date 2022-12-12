package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket implements Packet {

	private final int id;
	private boolean cancelled;
	private long timestamp;
	
	public static final int 
		MAX_PACKET_LENGTH = 2097151,
		CHAT_MAX_LENGTH = 262144;
	
	/**
	 * 
	 * @param id the packets id
	 * @param version the protocol version
	 */
	public AbstractPacket(int id) {
		this.id = id;
		this.cancelled = false;
	}
	
	/**
	 * Creates a new AbstractPacket with packet {@link #getDefaultID()}
	 * @param version the protocol version
	 */
	public AbstractPacket() {
		this.id = getDefaultID();
		this.cancelled = false;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public static int readVarInt(ByteBuf in) {
	    int numRead = 0;
	    int result = 0;
	    byte read;
	    do {
	        read = in.readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 5) {
	            throw new RuntimeException("VarInt is too big");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	
	public static long readVarLong(ByteBuf in) {
	    int numRead = 0;
	    long result = 0;
	    byte read;
	    do {
	        read = in.readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 10) {
	            throw new RuntimeException("VarLong is too big");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	
	public static void writeVarInt(int value, ByteBuf out) {
	    do {
	        int temp = value & 0b01111111;
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        out.writeByte(temp);
	    } while (value != 0);
	}
	
	public static int getVarIntLength(int value) {
		int i = 0;
	    do {
	        @SuppressWarnings("unused")
			int temp = value & 0b01111111;
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        i++;
	    } while (value != 0);
	    return i;
	}
	
	public static int getVarLongLength(long value) {
		int length = 0;
		do {
	        @SuppressWarnings("unused")
			int temp = (int) (value & 0b01111111);
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        length++;
	    } while (value != 0);
		return length;
	}
	
	public static void writeVarLong(long value, ByteBuf out) throws IOException {
	    do {
	        int temp = (int) (value & 0b01111111);
	        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        out.writeByte(temp);
	    } while (value != 0);
	}
	
	public static String readString(ByteBuf in) {
		return readString(in, Short.MAX_VALUE);
	}
	
	public static String readString(ByteBuf in, int maxLength) {
		int len = readVarInt(in);
		if (len == 0) 
			return null;
		if (len > maxLength) 
			throw new IllegalArgumentException("Invalid String length:" + len + " expected: " + maxLength);
		byte[] buffer = new byte[len];
		in.readBytes(buffer);
		return new String(buffer);
	}
	
	/**
	 * Writes a String prefixed with a varint indicating the length of the following byte array.
	 * @implNote if the String is null a string with length of 0 will be written
	 * @param value the String to write
	 * @param out the buffer the data should be written too
	 */
	public static void writeString(String value, ByteBuf out) {
		if (value == null)  {
			writeVarInt(0, out);
			return;
		}
		byte[] buffer = value.getBytes();
		writeVarInt(buffer.length, out);
		out.writeBytes(buffer);
	}
	
	/**
	 * 
	 * @param in
	 * @return a ItemStack or null if empty
	 */
	public static ItemStack readSlot(ByteBuf in) throws IOException {
		boolean present = in.readBoolean();
		if (!present) 
			return null;
		int itemID = readVarInt(in);
		byte amount = in.readByte();
		Material mat = Material.getByItemID(itemID);
		ItemStack item = new ItemStack(mat, amount);
		byte comp = in.readByte();
		if (comp == 0) 
			return item;
		NBTNIOReader reader = new NBTNIOReader(in);
		item.getItemMeta().fromNBT(reader);
		reader.close();
		return item;
	}
	
	/**
	 * Reads a Slot to ItemStack using a existing {@link NBTNIOReader}
	 * @param in
	 * @return a ItemStack or null if empty
	 */
	public static ItemStack readSlot(ByteBuf in, NBTNIOReader reader) throws IOException {
		boolean present = in.readBoolean();
		if (!present) 
			return null;
		int itemID = readVarInt(in);
		byte amount = in.readByte();
		Material mat = Material.getByItemID(itemID);
		ItemStack item = new ItemStack(mat, amount);
		byte comp = in.readByte();
		if (comp == 0) 
			return item;
		item.getItemMeta().fromNBT(reader);
		return item;
	}
	
	public static void writeSlot(ItemStack item, ByteBuf out) throws IOException {
		if (item == null) {
			out.writeBoolean(false);
			return;
		}
		out.writeBoolean(true);
		writeVarInt(item.getType().getItemID(), out);
		out.writeByte(item.getAmount());
		if (!item.hasItemMeta())
			out.writeByte(0);
		else {
			NBTNIOWriter writer = new NBTNIOWriter(out);
			writer.writeCompoundTag(null);
			item.getItemMeta().toNBT(writer, false);
			writer.writeEndTag();
			writer.close();
		}
	}
	
	/**
	 * Writes a ItemStack as Slot using a existing {@link NBTNIOWriter}
	 * @param item
	 * @param out
	 * @param writer
	 * @throws IOException
	 */
	public static void writeSlot(ItemStack item, ByteBuf out, NBTNIOWriter writer) throws IOException {
		if (item == null) {
			out.writeBoolean(false);
			return;
		}
		out.writeBoolean(true);
		writeVarInt(item.getType().getItemID(), out);
		out.writeByte(item.getAmount());
		if (!item.hasItemMeta()) 
			out.writeByte(0);
		else {
			writer.writeCompoundTag(null);
			item.getItemMeta().toNBT(writer, false);
			writer.writeEndTag();
		}
	}

}
