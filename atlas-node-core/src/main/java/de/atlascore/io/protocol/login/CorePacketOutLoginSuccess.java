package de.atlascore.io.protocol.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.atlasnetwork.ProfileProperty;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLoginSuccess implements PacketIO<PacketOutLoginSuccess> {

	@Override
	public void read(PacketOutLoginSuccess packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.uuid = readUUID(in);
		packet.username = readString(in, 16);
		final int propCount = readVarInt(in);
		if (propCount > 0) {
			List<ProfileProperty> props = packet.properties = new ArrayList<>(propCount);
			for (int i = 0; i < propCount; i++) {
				String name = readString(in, 32767);
				String value = readString(in, 32767);
				String signature = in.readBoolean() ? readString(in, 32767) : null;
				props.add(new ProfileProperty(name, value, signature));
			}
		}
	}

	@Override
	public void write(PacketOutLoginSuccess packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeUUID(packet.uuid, out);
		writeString(packet.username, out);
		List<ProfileProperty> props = packet.properties;
		if (props == null || props.isEmpty()) {
			writeVarInt(0, out);
		} else {
			writeVarInt(props.size(), out);
			for (ProfileProperty prop : props) {
				writeString(prop.getName(), out);
				writeString(prop.getValue(), out);
				String signature = prop.getSignature();
				if (signature == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					writeString(signature, out);
				}
			}
		}
	}

	@Override
	public PacketOutLoginSuccess createPacketData() {
		return new PacketOutLoginSuccess();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutLoginSuccess.class);
	}

}
