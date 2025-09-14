package de.atlasmc.core.node.entity;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.TextDisplay;
import de.atlasmc.node.entity.data.MetaData;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreTextDisplay extends CoreDisplay implements TextDisplay {

	protected static final MetaDataField<Chat> META_TEXT = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, ChatUtil.EMPTY, MetaDataType.CHAT);
	protected static final MetaDataField<Integer> META_LINE_WIDTH = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, 200, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer> META_BACKGROUND_COLOR = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+3, 0x40000000, MetaDataType.VAR_INT);
	protected static final MetaDataField<Byte> META_TEXT_OPACITY = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+4, (byte) -1, MetaDataType.BYTE);
	protected static final MetaDataField<Byte> META_TEXT_DISPLAY_FLAGS = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+5, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+5;
	
	public CoreTextDisplay(EntityType type) {
		super(type);
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

}
