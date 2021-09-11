package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutOpenWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenWindow extends AbstractPacket implements PacketOutOpenWindow {

	private int windowID, type;
	private String title;
	
	public CorePacketOutOpenWindow() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutOpenWindow(int windowID, InventoryType type, ChatComponent title) {
		this();
		this.windowID = windowID;
		this.type = type.ordinal();
		if (this.type == -1) throw new IllegalArgumentException("Invalid InventoryType:" + type.name());
		this.title = title.getJsonText();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		windowID = readVarInt(in);
		type = readVarInt(in);
		title = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(windowID, out);
		writeVarInt(type, out);
		writeString(title, out);
	}

	@Override
	public int getWindowID() {
		return windowID;
	}

	@Override
	public InventoryType getWindowType() {
		return InventoryType.getByID(type);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}

	@Override
	public void setWindowType(InventoryType type) {
		this.type = type.getID();
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

}
