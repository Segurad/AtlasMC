package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketInClickContainer;
import io.netty.buffer.ByteBuf;

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
			Map<Integer, ItemStack> changed = new HashMap<>(changeSize);
			for (int i = 0; i < changeSize; i++) {
				int slot = in.readShort();
				ItemStack item = readSlot(in);
				changed.put(slot, item);
			}
			packet.slotsChanged = changed;
		}
		packet.carriedItem = readSlot(in);
	}

	@Override
	public void write(PacketInClickContainer packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.stateID, out);
		out.writeShort(packet.slot);
		out.writeByte(packet.button);
		writeVarInt(packet.mode, out);
		Map<Integer, ItemStack> changed = packet.slotsChanged;
		if (changed != null && !changed.isEmpty()) {
			writeVarInt(changed.size(), out);
			for (Integer key : changed.keySet()) {
				out.writeShort(key);
				writeSlot(changed.get(key), out);
			}
		}
		writeSlot(packet.carriedItem, out);
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
