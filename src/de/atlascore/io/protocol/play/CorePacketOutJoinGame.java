package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.Gamemode;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutJoinGame;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.DimensionCodec;
import io.netty.buffer.ByteBuf;

public class CorePacketOutJoinGame extends PacketIO<PacketOutJoinGame> {

	@Override
	public void read(PacketOutJoinGame packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(in.readInt());
		packet.setHardcore(in.readBoolean());
		packet.setGamemode(Gamemode.getByID(in.readUnsignedByte()));
		int oldGamemode = in.readByte();
		if (oldGamemode != -1)
			packet.setOldGamemode(Gamemode.getByID(oldGamemode));
		int worldCount = readVarInt(in);
		String[] worlds = new String[worldCount];
		for (int i = 0; i < worldCount; i++)
			worlds[i] = readString(in);
		DimensionCodec codec = new DimensionCodec();
		NBTNIOReader reader = new NBTNIOReader(in);
		reader.readNextEntry();
		codec.fromNBT(reader);
		reader.readNextEntry();
		reader.skipToEnd(); // TODO read Dimension
		reader.close();
		packet.setWorld(readString(in));
		packet.setSeed(in.readLong());
		readVarInt(in); // ignored value previous max players
		packet.setViewDistance(readVarInt(in));
		packet.setReducedDebugInfo(in.readBoolean());
		packet.setRespawnScreen(in.readBoolean());
		packet.setDebug(in.readBoolean());
		packet.setFlat(in.readBoolean());
	}

	@Override
	public void write(PacketOutJoinGame packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getEntityID());
		out.writeBoolean(packet.isHardcore());
		out.writeByte(packet.getGamemode().getID());
		out.writeByte(packet.getOldGamemode() != null ? packet.getOldGamemode().getID() : -1);
		writeVarInt(packet.getWorlds().length, out);
		for (String s : packet.getWorlds()) {
			writeString(s, out);
		}
		NBTNIOWriter writer = new NBTNIOWriter(out);
		writer.writeCompoundTag();
		packet.getDimensionCodec().toNBT(writer, false);
		writer.writeEndTag();
		writer.writeCompoundTag();
		packet.getDimension().toNBT(writer, false);
		writer.writeEndTag();
		writer.close();
		writeString(packet.getWorld(), out);
		out.writeLong(packet.getSeed());
		writeVarInt(0, out); // ignored value was once max players (why mojang why is this value still in the packet)
		writeVarInt(packet.getViewDistance(), out);
		out.writeBoolean(packet.isReducedDebugInfo());
		out.writeBoolean(packet.isRespawnScreen());
		out.writeBoolean(packet.isDebug());
		out.writeBoolean(packet.isFlat());
	}

	@Override
	public PacketOutJoinGame createPacketData() {
		return new PacketOutJoinGame();
	}
	
}
