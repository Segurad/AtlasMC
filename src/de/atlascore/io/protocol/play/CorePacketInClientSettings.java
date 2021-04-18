package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClientSettings;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientSettings extends AbstractPacket implements PacketInClientSettings {

	public CorePacketInClientSettings() {
		super(0x05, CoreProtocolAdapter.VERSION);
	}

	private String locale;
	private int viewDistance, chatMode, mainHand;
	private boolean chatColor;
	private byte skinParts;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		locale = readString(in);
		viewDistance = in.readByte();
		chatMode = readVarInt(in);
		chatColor = in.readBoolean();
		skinParts = in.readByte();
		mainHand = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(locale, out);
		out.writeByte(viewDistance);
		writeVarInt(chatMode, out);
		out.writeBoolean(chatColor);
		out.writeByte(skinParts);
		writeVarInt(mainHand, out);
	}

	@Override
	public String getLocale() {
		return locale;
	}

	@Override
	public int getViewDistance() {
		return viewDistance;
	}

	@Override
	public int getChatMode() {
		return chatMode;
	}

	@Override
	public boolean getChatColor() {
		return chatColor;
	}

	@Override
	public byte getDisplaySkinParts() {
		return skinParts;
	}

	@Override
	public int getMainHand() {
		return mainHand;
	}

}
