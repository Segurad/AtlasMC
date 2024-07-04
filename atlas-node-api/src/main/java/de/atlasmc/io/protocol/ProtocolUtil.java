package de.atlasmc.io.protocol;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public final class ProtocolUtil extends AbstractPacket {
	
	private ProtocolUtil() {}

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
			NBTNIOWriter writer = new NBTNIOWriter(out, true);
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
		NBTNIOReader reader = new NBTNIOReader(in, true);
		item.getItemMeta().fromNBT(reader);
		reader.close();
		return item;
	}

	@Override
	public int getDefaultID() {
		return 0;
	}
	
}
