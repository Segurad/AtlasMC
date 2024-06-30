package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.TextDisplay;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTextDisplay extends CoreDisplay implements TextDisplay {

	protected static final MetaDataField<Chat> META_TEXT = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, ChatUtil.EMPTY, MetaDataType.CHAT);
	protected static final MetaDataField<Integer> META_LINE_WIDTH = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, 200, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer> META_BACKGROUND_COLOR = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+3, 0x40000000, MetaDataType.VAR_INT);
	protected static final MetaDataField<Byte> META_TEXT_OPACITY = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+4, (byte) -1, MetaDataType.BYTE);
	protected static final MetaDataField<Byte> META_TEXT_DISPLAY_FLAGS = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+5, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+5;
	
	private static final CharKey
	NBT_ALIGNMENT = CharKey.literal("alignment"),
	NBT_BACKGROUND = CharKey.literal("background"),
	NBT_DEFAULT_BACKGROUND = CharKey.literal("default_background"),
	NBT_LINE_WIDTH = CharKey.literal("line_width"),
	NBT_SEE_THROUGH = CharKey.literal("shadow"),
	NBT_TEXT = CharKey.literal("text"),
	NBT_TEXT_OPACITY = CharKey.literal("text_opacity");
	
	static {
		NBT_FIELDS.setField(NBT_ALIGNMENT, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setAlignment(TextAlignment.getByNameID(reader.readStringTag()));
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_BACKGROUND, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setBackgroundColor(Color.fromRGB(reader.readIntTag()));
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_DEFAULT_BACKGROUND, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setDefaultBachground(reader.readByteTag() == 1);
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_LINE_WIDTH, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setLineWidth(reader.readIntTag());
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_SEE_THROUGH, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setSeeThrough(reader.readByteTag() == 1);
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_TEXT, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setText(ChatUtil.toChat(reader.readStringTag()));
			} else {
				reader.skipTag();
			}
		});
		NBT_FIELDS.setField(NBT_TEXT_OPACITY, (holder, reader) -> {
			if (holder instanceof TextDisplay ent) {
				ent.setTextOpacity(reader.readByteTag() & 0xFF);
			} else {
				reader.skipTag();
			}
		});
	}
	
	public CoreTextDisplay(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TEXT);
		metaContainer.set(META_LINE_WIDTH);
		metaContainer.set(META_BACKGROUND_COLOR);
		metaContainer.set(META_TEXT_OPACITY);
		metaContainer.set(META_TEXT_DISPLAY_FLAGS);
	}

	@Override
	public TextAlignment getAlignment() {
		int val = metaContainer.getData(META_TEXT_DISPLAY_FLAGS);
		return (val & 0x08) != 0 ? TextAlignment.LEFT : (val & 0x10) != 0 ? TextAlignment.RIGHT : TextAlignment.CENTER;
	}

	@Override
	public void setAlignment(TextAlignment alignment) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		int val = data.getData() & 0x07;
		if (alignment != null) {
			switch (alignment) {
			case LEFT:
				val |= 0x08;
				break;
			case RIGHT:
				val |= 0x10;
				break;
			default:
				break;
			}
		}
		data.setData((byte) val);
	}

	@Override
	public Color getBackgroundColor() {
		return Color.fromARGB(metaContainer.getData(META_BACKGROUND_COLOR));
	}

	@Override
	public void setBackgroundColor(Color color) {
		if (color == null) {
			metaContainer.get(META_BACKGROUND_COLOR).setData(META_BACKGROUND_COLOR.getDefaultData());
		} else {
			metaContainer.get(META_BACKGROUND_COLOR).setData(color.asARGB());
		}
	}

	@Override
	public boolean hasDefaultBackground() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & 0x04) != 0;
	}

	@Override
	public void setDefaultBachground(boolean defaultBackground) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		if (defaultBackground) {
			data.setData((byte) (data.getData() | 0x04));
		} else {
			data.setData((byte) (data.getData() & 0xFB));
		}
	}

	@Override
	public int getLineWidth() {
		return metaContainer.getData(META_LINE_WIDTH);
	}

	@Override
	public void setLineWidth(int lineWidth) {
		metaContainer.get(META_LINE_WIDTH).setData(lineWidth);
	}

	@Override
	public boolean isSeeThrough() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & 0x02) != 0;
	}

	@Override
	public void setSeeThrough(boolean seeThrough) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		if (seeThrough) {
			data.setData((byte) (data.getData() | 0x02));
		} else {
			data.setData((byte) (data.getData() & 0xFD));
		}
	}

	@Override
	public boolean isShadowed() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & 0x01) != 0;
	}

	@Override
	public void setShadowed(boolean shadowed) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		if (shadowed) {
			data.setData((byte) (data.getData() | 0x01));
		} else {
			data.setData((byte) (data.getData() & 0xFE));
		}
	}

	@Override
	public Chat getText() {
		return metaContainer.getData(META_TEXT);
	}

	@Override
	public void setText(Chat text) {
		if (text == null)
			text = ChatUtil.EMPTY;
		metaContainer.get(META_TEXT).setData(text);
	}

	@Override
	public int getTextOpacity() {
		return metaContainer.getData(META_TEXT_OPACITY) & 0xFF;
	}

	@Override
	public void setTextOpacity(int opacity) {
		metaContainer.get(META_TEXT_OPACITY).setData((byte) opacity);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		TextAlignment alignment = getAlignment();
		if (alignment != TextAlignment.CENTER) {
			writer.writeStringTag(NBT_ALIGNMENT, alignment.getNameID());
		}
		MetaData<Integer> background = metaContainer.get(META_BACKGROUND_COLOR);
		if (!background.isDefault()) {
			writer.writeIntTag(NBT_BACKGROUND, background.getData());
		}
		if (hasDefaultBackground())
			writer.writeByteTag(NBT_DEFAULT_BACKGROUND, true);
		MetaData<Integer> lineWidth = metaContainer.get(META_LINE_WIDTH);
		if (!lineWidth.isDefault())
			writer.writeIntTag(NBT_LINE_WIDTH, lineWidth.getData());
		if (isSeeThrough())
			writer.writeByteTag(NBT_SEE_THROUGH, true);
		int opacity = getTextOpacity();
		if (opacity != 0xFF)
			writer.writeByteTag(NBT_TEXT_OPACITY, opacity);
		writer.writeStringTag(NBT_TEXT, getText().toText());
	}

}
