package de.atlascore.io.protocol.login;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLoginSuccess extends AbstractPacket implements PacketOutLoginSuccess {

	private UUID uuid;
	private String name;
	
	public CorePacketOutLoginSuccess() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutLoginSuccess(UUID uuid, String name) {
		this();
		this.name = name;
		this.uuid = uuid;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		uuid = new UUID(most, least);
		name = readString(in, 16);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeString(name, out);
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getUsername() {
		return name;
	}

}
