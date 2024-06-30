package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Sign;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSign extends CoreTileEntity implements Sign {

	protected static final ChildNBTFieldContainer<CoreSign> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TEXT_1 = CharKey.literal("Text1"),
	NBT_TEXT_2 = CharKey.literal("Text2"),
	NBT_TEXT_3 = CharKey.literal("Text3"),
	NBT_TEXT_4 = CharKey.literal("Text4");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_TEXT_1, (holder, reader) -> {
			holder.setLine(0, ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_TEXT_2, (holder, reader) -> {
			holder.setLine(1, ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_TEXT_3, (holder, reader) -> {
			holder.setLine(2, ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_TEXT_4, (holder, reader) -> {
			holder.setLine(3, ChatUtil.toChat(reader.readStringTag()));
		});
	}
	
	private Chat[] lines;
	
	public CoreSign(Material type) {
		super(type);
	}

	@Override
	public Chat[] getLines() {
		if (lines == null) 
			lines = new Chat[4];
		return lines;
	}

	@Override
	public void setLines(Chat[] lines) {
		if (lines == null) 
			return;
		if (this.lines == null) 
			this.lines = new Chat[4];
		for (int i = 0; i < 4 && i < lines.length; i++) {
			this.lines[i] = lines[i];
		}
	}

	@Override
	public void setLine(int index, Chat line) {
		getLines()[index] = line;
	}

	@Override
	public Chat getLine(int index) {
		if (lines == null) 
			return null;
		return lines[index];
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreSign> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (lines == null) 
			return;
		if (lines[0] != null)
			writer.writeStringTag(NBT_TEXT_1, lines[0].toText());
		if (lines[1] != null)
			writer.writeStringTag(NBT_TEXT_2, lines[1].toText());
		if (lines[2] != null)
			writer.writeStringTag(NBT_TEXT_3, lines[2].toText());
		if (lines[3] != null)
			writer.writeStringTag(NBT_TEXT_4, lines[3].toText());
	}
}
