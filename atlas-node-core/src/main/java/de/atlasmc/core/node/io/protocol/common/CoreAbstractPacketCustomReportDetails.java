package de.atlasmc.core.node.io.protocol.common;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketCustomReportDetails;
import de.atlasmc.node.io.protocol.common.AbstractPacketCustomReportDetails.Detail;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketCustomReportDetails<T extends AbstractPacketCustomReportDetails> implements PacketIO<T> {

	@Override
	public void read(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		ArrayList<Detail> details = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			String title = StringCodec.readString(in, 128);
			String description = StringCodec.readString(in, 4096);
			details.add(new Detail(title, description));
		}
	}
	
	@Override
	public void write(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<Detail> details = packet.details;
		final int count = details.size();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			Detail detail = details.get(i);
			StringCodec.writeString(detail.title, out);
			StringCodec.writeString(detail.description, out);
		}
	}
	
}
