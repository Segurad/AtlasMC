package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Sign;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreSign extends CoreTileEntity implements Sign {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	TEXT_1 = "Text1",
	TEXT_2 = "Text2",
	TEXT_3 = "Text3",
	TEXT_4 = "Text4";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(TEXT_1, (holder, reader) -> {
			if (holder instanceof Sign)
			((Sign) holder).setLine(0, ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(TEXT_2, (holder, reader) -> {
			if (holder instanceof Sign)
			((Sign) holder).setLine(1, ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(TEXT_3, (holder, reader) -> {
			if (holder instanceof Sign)
			((Sign) holder).setLine(2, ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(TEXT_4, (holder, reader) -> {
			if (holder instanceof Sign)
			((Sign) holder).setLine(3, ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private ChatComponent[] lines;
	
	public CoreSign(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	public ChatComponent[] getLines() {
		if (lines == null) lines = new ChatComponent[4];
		return lines;
	}

	@Override
	public void setLines(ChatComponent[] lines) {
		if (lines == null) return;
		if (this.lines == null) this.lines = new ChatComponent[4];
		for (int i = 0; i < 4 && i < lines.length; i++) {
			this.lines[i] = lines[i];
		}
	}

	@Override
	public void setLine(int index, ChatComponent line) {
		getLines()[index] = line;
	}

	@Override
	public ChatComponent getLine(int index) {
		if (lines == null) return null;
		return lines[index];
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (lines == null) return;
		if (lines[0] != null)
			writer.writeStringTag(TEXT_1, lines[0].getJsonText());
		if (lines[1] != null)
			writer.writeStringTag(TEXT_2, lines[1].getJsonText());
		if (lines[2] != null)
			writer.writeStringTag(TEXT_3, lines[2].getJsonText());
		if (lines[3] != null)
			writer.writeStringTag(TEXT_4, lines[3].getJsonText());
	}
}
