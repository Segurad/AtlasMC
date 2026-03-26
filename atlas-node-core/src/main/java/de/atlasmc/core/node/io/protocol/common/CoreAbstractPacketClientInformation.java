package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.MainHand;
import de.atlasmc.node.io.protocol.common.AbstractPacketClientInformation;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketClientInformation<T extends AbstractPacketClientInformation> implements PacketCodec<T> {

	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.local = StringCodec.readString(in, 16);
		packet.viewDistance = in.readByte();
		packet.chatMode = ChatMode.getByID(readVarInt(in));
		packet.chatColors = in.readBoolean();
		packet.skinParts = in.readUnsignedByte();
		packet.mainHand = MainHand.getByID(readVarInt(in));
		packet.enableTextFiltering = in.readBoolean();
		packet.allowServerListing = in.readBoolean();
		packet.particleStatus = readVarInt(in);
	}

	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		StringCodec.writeString(packet.local, out);
		out.writeByte(packet.viewDistance);
		writeVarInt(packet.chatMode.getID(), out);
		out.writeBoolean(packet.chatColors);
		out.writeByte(packet.skinParts);
		writeVarInt(packet.mainHand.getID(), out);
		out.writeBoolean(packet.enableTextFiltering);
		out.writeBoolean(packet.allowServerListing);
		writeVarInt(packet.particleStatus, out);
	}

}
