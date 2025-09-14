package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutMoveMinecartOnTrack;
import de.atlasmc.node.io.protocol.play.PacketOutMoveMinecartOnTrack.Step;
import de.atlasmc.node.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMoveMinecartOnTrack implements PacketIO<PacketOutMoveMinecartOnTrack> {

	@Override
	public void read(PacketOutMoveMinecartOnTrack packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		final int count = readVarInt(in);
		if (count == 0) {
			packet.steps = List.of();
			return;
		}
		List<Step> steps = new ArrayList<>(count);
		packet.steps = steps;
		for (int i = 0; i < count; i++) {
			Step step = new Step();
			step.x = in.readDouble();
			step.y = in.readDouble();
			step.z = in.readDouble();
			step.velocityX = in.readDouble();
			step.velocityY = in.readDouble();
			step.velocityZ = in.readDouble();
			step.yaw = MathUtil.fromAngle(in.readByte());
			step.pitch = MathUtil.fromAngle(in.readByte());
			step.weight = in.readFloat();
			steps.add(step);
		}
	}

	@Override
	public void write(PacketOutMoveMinecartOnTrack packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		List<Step> steps = packet.steps;
		if (steps == null || steps.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		final int count = steps.size();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			Step step = steps.get(i);
			out.writeDouble(step.x);
			out.writeDouble(step.y);
			out.writeDouble(step.z);
			out.writeDouble(step.velocityX);
			out.writeDouble(step.velocityY);
			out.writeDouble(step.velocityZ);
			out.writeByte(MathUtil.toAngle(step.yaw));
			out.writeByte(MathUtil.toAngle(step.pitch));
			out.writeFloat(step.weight);
		}
	}

	@Override
	public PacketOutMoveMinecartOnTrack createPacketData() {
		return new PacketOutMoveMinecartOnTrack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutMoveMinecartOnTrack.class);
	}

}
