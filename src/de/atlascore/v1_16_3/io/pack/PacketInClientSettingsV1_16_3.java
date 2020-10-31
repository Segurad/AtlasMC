package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInClientSettings;

public class PacketInClientSettingsV1_16_3 extends AbstractPacket implements PacketInClientSettings {

	public PacketInClientSettingsV1_16_3() {
		super(0x05, V1_16_3.version);
	}

	private String locale;
	private int viewDistance, chatMode, mainHand;
	private boolean chatColor;
	private byte skinParts;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		locale = readString(input);
		viewDistance = input.readByte();
		chatMode = readVarInt(input);
		chatColor = input.readBoolean();
		skinParts = input.readByte();
		mainHand = readVarInt(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

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
