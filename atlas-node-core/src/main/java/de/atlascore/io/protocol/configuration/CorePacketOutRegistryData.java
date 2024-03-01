package de.atlascore.io.protocol.configuration;

import java.io.IOException;
import java.util.List;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketOutRegistryData;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRegistryData implements PacketIO<PacketOutRegistryData> {
	
	@Override
	public void read(PacketOutRegistryData packet, ByteBuf in, ConnectionHandler con) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(in, true);
		packet.rawData = reader.readNBT();
		reader.close();
	}

	@Override
	public void write(PacketOutRegistryData packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<Registry<?>> data = packet.data;
		if (data == null || data.isEmpty()) {
			out.writeByte(TagType.TAG_END.getID());
		} else {
			NBTNIOWriter writer = new NBTNIOWriter(out, true);
			writer.writeCompoundTag();
			for (Registry<?> registry : data) {
				writer.writeCompoundTag(registry.getNamespacedKeyRaw());
				writer.writeStringTag("type", registry.getNamespacedKeyRaw());
				writer.writeListTag("value", TagType.COMPOUND, registry.size());
				
				writer.writeEndTag();
			}
			writer.writeEndTag();
			writer.close();
		}
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
