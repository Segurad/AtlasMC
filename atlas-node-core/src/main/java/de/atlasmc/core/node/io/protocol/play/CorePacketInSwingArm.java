package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.event.player.PlayerAnimationEvent.PlayerAnimationType;
import de.atlasmc.node.io.protocol.play.PacketInSwingArm;
import io.netty.buffer.ByteBuf;

public class CorePacketInSwingArm implements PacketIO<PacketInSwingArm> {
	
	@Override
	public void read(PacketInSwingArm packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.hand = PlayerAnimationType.getByID(readVarInt(in));
	}

	@Override
	public void write(PacketInSwingArm packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.hand.getID(), out);
	}
	
	@Override
	public PacketInSwingArm createPacketData() {
		return new PacketInSwingArm();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSwingArm.class);
	}

}
