package de.atlasmc.core.node.entity;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.TextDisplay;
import de.atlasmc.node.entity.metadata.MetaData;
import de.atlasmc.node.entity.metadata.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreTextDisplay extends CoreDisplay implements TextDisplay {

	protected static final int
	FLAG_IS_SHADOWED = 0x01,
	FLAG_IS_SEE_THROUGH = 0x02,
	FLAG_DEFAULT_BACKGROUND = 0x04,
	FLAG_TEXT_ALIGN_LEFT = 0x08,
	FLAG_TEXT_ALIGN_RIGHT = 0x10;
	
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
		return (val & FLAG_TEXT_ALIGN_LEFT) == FLAG_TEXT_ALIGN_LEFT ? TextAlignment.LEFT : 
			(val & FLAG_TEXT_ALIGN_RIGHT) == FLAG_TEXT_ALIGN_RIGHT ? TextAlignment.RIGHT : TextAlignment.CENTER;
	}
	
	protected void setTextDisplayFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_TEXT_DISPLAY_FLAGS, value);
	}

	@Override
	public void setAlignment(TextAlignment alignment) {
		MetaData<Byte> data = metaContainer.get(META_TEXT_DISPLAY_FLAGS);
		int val = data.getData() & ~(FLAG_TEXT_ALIGN_LEFT | FLAG_TEXT_ALIGN_RIGHT);
		if (alignment != null) {
			switch (alignment) {
			case LEFT:
				val |= FLAG_TEXT_ALIGN_LEFT;
				break;
			case RIGHT:
				val |= FLAG_TEXT_ALIGN_RIGHT;
				break;
			default:
				break;
			}
		}
		metaContainer.setData(META_TEXT_DISPLAY_FLAGS, (byte) val);
	}

	@Override
	public Color getBackgroundColor() {
		return Color.fromARGB(metaContainer.getData(META_BACKGROUND_COLOR));
	}

	@Override
	public void setBackgroundColor(Color color) {
			metaContainer.setData(META_BACKGROUND_COLOR, color == null ? 
					META_BACKGROUND_COLOR.getDefaultData() : color.asARGB());
	}

	@Override
	public boolean hasDefaultBackground() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & FLAG_DEFAULT_BACKGROUND) == FLAG_DEFAULT_BACKGROUND;
	}

	@Override
	public void setDefaultBachground(boolean defaultBackground) {
		setTextDisplayFlag(FLAG_DEFAULT_BACKGROUND, defaultBackground);
	}

	@Override
	public int getLineWidth() {
		return metaContainer.getData(META_LINE_WIDTH);
	}

	@Override
	public void setLineWidth(int lineWidth) {
		metaContainer.setData(META_LINE_WIDTH, lineWidth);
	}

	@Override
	public boolean isSeeThrough() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & FLAG_IS_SEE_THROUGH) == FLAG_IS_SEE_THROUGH;
	}

	@Override
	public void setSeeThrough(boolean seeThrough) {
		setTextDisplayFlag(FLAG_IS_SEE_THROUGH, seeThrough);
	}

	@Override
	public boolean isShadowed() {
		return (metaContainer.getData(META_TEXT_DISPLAY_FLAGS) & FLAG_IS_SHADOWED) == FLAG_IS_SHADOWED;
	}

	@Override
	public void setShadowed(boolean shadowed) {
		setTextDisplayFlag(FLAG_IS_SHADOWED, shadowed);
	}

	@Override
	public Chat getText() {
		return metaContainer.getData(META_TEXT);
	}

	@Override
	public void setText(Chat text) {
		metaContainer.setData(META_TEXT, text == null ? ChatUtil.EMPTY : text);
	}

	@Override
	public int getTextOpacity() {
		return metaContainer.getData(META_TEXT_OPACITY) & 0xFF;
	}

	@Override
	public void setTextOpacity(int opacity) {
		metaContainer.setData(META_TEXT_OPACITY, (byte) opacity);
	}

}
