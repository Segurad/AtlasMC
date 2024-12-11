package de.atlascore.io.protocol.configuration;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutRegistryData;
import de.atlasmc.io.protocol.configuration.PacketOutRegistryData.RegistryEntry;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRegistryData implements PacketIO<PacketOutRegistryData> {
	
	@Override
	public void read(PacketOutRegistryData packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.registryID = readIdentifier(in);
		final int count = readVarInt(in);
		if (count == 0)
			return;
		NBTNIOReader reader = null;
		List<RegistryEntry> entries = packet.entries = new ArrayList<>(count); 
		for (int i = 0; i < count; i++) {
			NamespacedKey id = readIdentifier(in);
			NBT nbt = null;
			if (in.readBoolean()) {
				if (reader == null)
					reader = new NBTNIOReader(in);
				nbt = reader.readNBT();
			}
			entries.add(new RegistryEntry(id, nbt));
		}
		if (reader != null)
			reader.close();
	}

	@Override
	public void write(PacketOutRegistryData packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeIdentifier(packet.registryID, out);
		List<RegistryEntry> entries = packet.entries;
		if (entries == null) {
			writeVarInt(0, out);
			return;
		}
		final int count = entries.size();
		writeVarInt(count, out);
		if (count == 0)
			return;
		NBTWriter writer = new NBTNIOWriter(out);
		for (int i = 0; i < count; i++) {
			RegistryEntry entry = entries.get(i);
			writeIdentifier(entry.entryID, out);
			NBT nbt = entry.data;
			if (nbt == null) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				if (writer == null)
					writer = new NBTNIOWriter(out);
				writer.writeNBT(nbt);
			}
		}
		if (writer != null)
			writer.close();
	}

	@Override
	public PacketOutRegistryData createPacketData() {
		return new PacketOutRegistryData();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRegistryData.class);
	}

}
