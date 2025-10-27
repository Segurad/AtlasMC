package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketInClickContainer;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CorePacketInClickWindow implements PacketIO<PacketInClickContainer> {

	@Override
	public void read(PacketInClickContainer packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
		packet.stateID = readVarInt(in);
		packet.slot = in.readShort();
		packet.button = in.readByte();
		packet.mode = readVarInt(in);
		int changeSize = readVarInt(in);
		if (changeSize > 0) {
			Int2ObjectMap<ItemStack> changed = new Int2ObjectOpenHashMap<>(changeSize);
			for (int i = 0; i < changeSize; i++) {
				int slot = in.readShort();
				ItemStack item = ItemStack.STREAM_CODEC.deserialize(in, con.getCodecContext());
				changed.put(slot, item);
			}
			packet.slotsChanged = changed;
		}
		packet.carriedItem = ItemStack.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void write(PacketInClickContainer packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.stateID, out);
		out.writeShort(packet.slot);
		out.writeByte(packet.button);
		writeVarInt(packet.mode, out);
		Int2ObjectMap<ItemStack> changed = packet.slotsChanged;
		if (changed != null && !changed.isEmpty()) {
			writeVarInt(changed.size(), out);
			for (Entry<ItemStack> entry : changed.int2ObjectEntrySet()) {
				out.writeShort(entry.getIntKey());
				ItemStack.STREAM_CODEC.serialize(entry.getValue(), out, con.getCodecContext());
			}
		}
		ItemStack.STREAM_CODEC.serialize(packet.carriedItem, out, con.getCodecContext());
	}

	@Override
	public PacketInClickContainer createPacketData() {
		return new PacketInClickContainer();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClickContainer.class);
	}

}
