package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Sign;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSign extends CoreTileEntity implements Sign {

	protected static final NBTFieldSet<CoreSign> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TEXT_1 = CharKey.literal("Text1"),
	NBT_TEXT_2 = CharKey.literal("Text2"),
	NBT_TEXT_3 = CharKey.literal("Text3"),
	NBT_TEXT_4 = CharKey.literal("Text4");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_TEXT_1, (holder, reader) -> {
			holder.setLine(0, ChatUtil.fromNBT(reader));
		});
		NBT_FIELDS.setField(NBT_TEXT_2, (holder, reader) -> {
			holder.setLine(1, ChatUtil.fromNBT(reader));
		});
		NBT_FIELDS.setField(NBT_TEXT_3, (holder, reader) -> {
			holder.setLine(2, ChatUtil.fromNBT(reader));
		});
		NBT_FIELDS.setField(NBT_TEXT_4, (holder, reader) -> {
			holder.setLine(3, ChatUtil.fromNBT(reader));
		});
	}
	
	private Chat[] lines;
	
	public CoreSign(BlockType type) {
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
	protected NBTFieldSet<? extends CoreSign> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (lines == null) 
			return;
		if (lines[0] != null)
			ChatUtil.toNBT(NBT_TEXT_1, lines[0], writer);
		if (lines[1] != null)
			ChatUtil.toNBT(NBT_TEXT_1, lines[1], writer);
		if (lines[2] != null)
			ChatUtil.toNBT(NBT_TEXT_1, lines[2], writer);
		if (lines[3] != null)
			ChatUtil.toNBT(NBT_TEXT_1, lines[3], writer);
	}
}
