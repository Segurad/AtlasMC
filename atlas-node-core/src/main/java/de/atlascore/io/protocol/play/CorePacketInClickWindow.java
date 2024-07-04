package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static de.atlasmc.io.AbstractPacket.*;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClickContainer;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickWindow implements PacketIO<PacketInClickContainer> {

	@Override
	public void read(PacketInClickContainer packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setStateID(readVarInt(in));
		packet.setSlot(in.readShort());
		packet.setButton(in.readByte());
		packet.setMode(readVarInt(in));
		int changeSize = readVarInt(in);
		if (changeSize > 0) {
			Map<Integer, ItemStack> changed = new HashMap<>(changeSize);
			for (int i = 0; i < changeSize; i++) {
				int slot = in.readShort();
				ItemStack item = readSlot(in);
				changed.put(slot, item);
			}
			packet.setSlotsChanged(changed);
		}
		packet.setCarriedItem(readSlot(in));
	}

	@Override
	public void write(PacketInClickContainer packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		writeVarInt(packet.getStateID(), out);
		out.writeShort(packet.getSlot());
		out.writeByte(packet.getButton());
		writeVarInt(packet.getMode(), out);
		Map<Integer, ItemStack> changed = packet.getSlotsChanged();
		NBTNIOWriter writer = new NBTNIOWriter(out, true);
		if (changed != null && !changed.isEmpty()) {
			writeVarInt(changed.size(), out);
			for (Integer key : changed.keySet()) {
				out.writeShort(key);
				writeSlot(changed.get(key), out, writer);
			}
		}
		writeSlot(packet.getCarriedItem(), out, writer);
		writer.close();
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
