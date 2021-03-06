package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective;
import de.atlasmc.scoreboard.RenderType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutScoreboardObjective extends AbstractPacket implements PacketOutScoreboardObjective {

	private String name;
	private int mode;
	private String displayName;
	private int renderType;
	
	public CorePacketOutScoreboardObjective() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutScoreboardObjective(String name, Mode mode, Chat displayName, RenderType renderType) {
		this();
		this.name = name;
		this.mode = mode.ordinal();
		this.displayName = displayName.getText();
		this.renderType = renderType.ordinal();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		name = readString(in);
		mode = in.readByte();
		if (mode == 1) return;
		displayName = readString(in);
		renderType = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(name, out);
		out.writeByte(mode);
		if (mode == 1) return;
		writeString(displayName, out);
		writeVarInt(renderType, out);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Mode getMode() {
		return Mode.getByID(mode);
	}

	@Override
	public Chat getDisplayName() {
		return ChatUtil.toChat(displayName);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.getByID(renderType);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode.ordinal();
	}

	@Override
	public void setDisplayName(Chat display) {
		this.displayName = display.getJsonText();
	}

	@Override
	public void setRenderType(RenderType type) {
		this.renderType = type.ordinal();
	}

}
