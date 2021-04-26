package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTimeUpdate;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTimeUpdate extends AbstractPacket implements PacketOutTimeUpdate {

	private long worldAge, timeOfDay;
	
	public CorePacketOutTimeUpdate() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTimeUpdate(long worldAge, long timeOfDay) {
		this();
		this.worldAge = worldAge;
		this.timeOfDay = timeOfDay;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		worldAge = in.readLong();
		timeOfDay = in.readLong();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(worldAge);
		out.writeLong(timeOfDay);
	}

	@Override
	public long getWorldAge() {
		return worldAge;
	}

	@Override
	public long getTimeOfDay() {
		return timeOfDay;
	}

}
