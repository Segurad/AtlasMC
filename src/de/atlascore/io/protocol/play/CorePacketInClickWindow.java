package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInClickWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickWindow extends AbstractPacket implements PacketInClickWindow {

	public CorePacketInClickWindow() {
		super(0x09, CoreProtocolAdapter.VERSION);
	}
	
	private byte windowID, button;
	private short slot, action;
	private int mode;
	private ItemStack clickedItem;

	@Override
	public byte getWindowID() {
		return windowID;
	}

	@Override
	public short getSlot() {
		return slot;
	}

	@Override
	public byte getButton() {
		return button;
	}

	@Override
	public short getActionNumber() {
		return action;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = in.readByte();
		slot = in.readShort();
		button = in.readByte();
		action = in.readShort();
		mode = readVarInt(in);
		clickedItem = readSlot(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeByte(windowID);
		out.writeShort(slot);
		out.writeByte(button);
		out.writeShort(action);
		writeVarInt(mode, out);
		writeSlot(clickedItem, out);
	}

	@Override
	public ItemStack getClickedItem() {
		return clickedItem;
	}

}
