package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketServerLinks;
import de.atlasmc.node.io.protocol.common.AbstractPacketServerLinks.Label;
import de.atlasmc.node.io.protocol.common.AbstractPacketServerLinks.ServerLink;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import de.atlasmc.util.enums.EnumUtil.EnumData;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketServerLinks<T extends AbstractPacketServerLinks> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count == 0) {
			packet.links = List.of();
			return;
		}
		final CodecContext context = con.getCodecContext();
		final ArrayList<ServerLink> links = new ArrayList<>(count);
		packet.links = links;
		final EnumData<Label> enumData = EnumUtil.getData(Label.class);
		for (int i = 0; i < count; i++) {
			Chat customLabel = null;
			Label label = null;
			if (in.readBoolean()) {
				label = enumData.getByID(readVarInt(in));
			} else {
				customLabel = Chat.STREAM_CODEC.deserialize(in, context);
			}
			String url = readString(in);
			links.add(new ServerLink(label, customLabel, url));
		}
	}

	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<ServerLink> links = packet.links;
		final int count = links.size();
		final CodecContext context = con.getCodecContext();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			ServerLink link = links.get(i);
			if (link.label != null) {
				out.writeBoolean(true);
				writeVarInt(link.label.getID(), out);
			} else {
				Chat.STREAM_CODEC.serialize(link.customLabel, out, context);
			}
			writeString(link.url, out);
		}
	}

}
