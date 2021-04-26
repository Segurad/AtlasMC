package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUpdateHealth;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateHealth extends AbstractPacket implements PacketOutUpdateHealth {

	private float health, saturation;
	private int food;
	
	public CorePacketOutUpdateHealth() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutUpdateHealth(float health, int food, float saturation) {
		this();
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		health = in.readFloat();
		food = readVarInt(in);
		saturation = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeFloat(health);
		writeVarInt(food, out);
		out.writeFloat(saturation);
	}


	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public int getFood() {
		return food;
	}

	@Override
	public float getSaturation() {
		return saturation;
	}

}
