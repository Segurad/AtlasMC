package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSetExperiance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetExperience extends CoreAbstractHandler<PacketOutSetExperiance> {

	@Override
	public void read(PacketOutSetExperiance packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setExperienceBar(in.readFloat());
		packet.setLevel(readVarInt(in));
		packet.setTotalExperience(readVarInt(in));
	}

	@Override
	public void write(PacketOutSetExperiance packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.getExperienceBar());
		writeVarInt(packet.getLevel(), out);
		writeVarInt(packet.getTotalExperience(), out);
	}
	
	@Override
	public PacketOutSetExperiance createPacketData() {
		return new PacketOutSetExperiance();
	}

}
