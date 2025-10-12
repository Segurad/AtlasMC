package de.atlasmc.core.node.io.protocol.login;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.player.PlayerProfile;
import de.atlasmc.network.player.ProfileProperty;
import de.atlasmc.node.io.protocol.login.ClientboundLoginSuccess;
import io.netty.buffer.ByteBuf;

public class CoreClientboundLoginSuccess implements PacketIO<ClientboundLoginSuccess> {

	@Override
	public void read(ClientboundLoginSuccess packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		PlayerProfile profile = packet.profile;
		profile.setUUID(readUUID(in));
		profile.setName(readString(in, 16));
		final int propCount = readArrayLength(in, 16);
		if (propCount > 0) {
			for (int i = 0; i < propCount; i++) {
				String name = readString(in, 64);
				String value = readString(in, 32767);
				String signature = in.readBoolean() ? readString(in, 1024) : null;
				profile.addProperty(new ProfileProperty(name, value, signature));
			}
		}
	}

	@Override
	public void write(ClientboundLoginSuccess packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		PlayerProfile profile = packet.profile;
		writeUUID(profile.getUUID(), out);
		writeString(profile.getName(), out);
		if (!profile.hasProperties()) {
			writeVarInt(0, out);
		} else {
			List<ProfileProperty> props = profile.getProperties();
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
	public ClientboundLoginSuccess createPacketData() {
		return new ClientboundLoginSuccess();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundLoginSuccess.class);
	}

}
