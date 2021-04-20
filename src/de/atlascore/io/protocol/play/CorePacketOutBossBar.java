package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarFlag;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.CoreBossBar;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBossBar extends AbstractPacket implements CoreBossBar {

	private UUID uuid;
	private int action, color, style, flags = 0;
	private String title;
	private float health;
	
	public CorePacketOutBossBar() {
		super(0x0C, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutBossBar(UUID uuid, Action action, String title, float health, BarColor color, BarStyle style, Set<BarFlag> flags) {
		this();
		this.action = action.ordinal();
		this.color = color.ordinal();
		if (flags != null) {
			if (flags.contains(BarFlag.DARKEN_SKY)) this.flags += 0x01;
			if (flags.contains(BarFlag.PLAY_BOSS_MUSIC)) this.flags += 0x02;
			if (flags.contains(BarFlag.CREATE_FOG)) this.flags += 0x04;
		}
		this.health = health;
		this.style = style.ordinal();
		this.title = title;
		this.uuid = uuid;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		long most = in.readLong();
		long least = in.readLong();
		uuid = new UUID(most, least);
		action = readVarInt(in);
		switch (action) {
		case 0: title = readString(in);
				health = in.readFloat();
				color = readVarInt(in);
				style = readVarInt(in);
				flags = in.readUnsignedByte();
				break;
		case 1: break;
		case 2: health = in.readFloat();
				break;
		case 3: title = readString(in);
				break;
		case 4: color = readVarInt(in);
				style = readVarInt(in);
				break;
		case 5: flags = in.readUnsignedByte();
				break;
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(action, out);
		switch (action) {
		case 0: writeString(title, out);
				out.writeFloat(health);
				writeVarInt(color, out);
				writeVarInt(style, out);
				out.writeByte(flags);
				break;
		case 1: break;
		case 2: out.writeFloat(health);
				break;
		case 3: writeString(title, out);
				break;
		case 4: writeVarInt(color, out);
				writeVarInt(style, out);
				break;
		case 5: out.writeByte(flags);
				break;
		}
	}

}
