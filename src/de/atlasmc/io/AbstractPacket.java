package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class AbstractPacket {

	private final int id, version;
	private boolean cancelled;
	
	public AbstractPacket(int id, int version) {
		this.id = id;
		this.version = version;
		this.cancelled = false;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getVersion() {
		return version;
	}

	public int getID() {
		return id;
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
	
	public static SimpleLocation readPosition(ByteBuf in) throws IOException {
		long raw = in.readLong();
		int x = (int) (raw >> 38);
		int y = (int) ((raw >> 26) & 0xFFF);
		int z = (int) (raw << 38 >> 38);
		return new SimpleLocation(x, y, z);
	}
	
	public static void writePosition(SimpleLocation loc, ByteBuf out) throws IOException {
		long raw = ((loc.getBlockX() & 0x3FFFFFF) << 38) |
				((loc.getBlockZ() & 0x3FFFFFF) << 12) |
				(loc.getBlockY() & 0xFFF);
		out.writeLong(raw);
	}
	
	public static String readString(ByteBuf in) {
		int len = readVarInt(in);
		byte[] buffer = new byte[len];
		in.readBytes(buffer);
		return new String(buffer);
	}
	
	public static void writeString(String value, ByteBuf out) {
		byte[] buffer = value.getBytes();
		writeVarInt(buffer.length, out);
		out.writeBytes(buffer);
	}
	
	/**
	 * 
	 * @param in
	 * @return a ItemStack or null if empty
	 */
	public ItemStack readSlot(ByteBuf in) throws IOException {
		boolean present = in.readBoolean();
		if (!present) return null;
		int itemID = readVarInt(in);
		byte amount = in.readByte();
		Material mat = Material.getByItemID(itemID);
		ItemStack item = new ItemStack(mat, amount);
		byte comp = in.readByte();
		if (comp == 0) return item;
		ItemMeta meta = mat.createItemMeta();
		meta.fromNBT(new NBTNIOReader(in));
		item.setItemMeta(meta);
		return item;
	}
	
	public void writeSlot(ItemStack item, ByteBuf out) throws IOException {
		if (item == null) {
			out.writeBoolean(false);
			return;
		}
		out.writeBoolean(true);
		writeVarInt(item.getType().getItemID(), out);
		out.writeByte(item.getAmount());
		if (!item.hasItemMeta()) out.writeByte(0);
		else item.getItemMeta().toNBT(new NBTNIOWriter(out), null, false);
	}

}
