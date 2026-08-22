package de.atlasmc.node.entity.component;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class TextDisplayMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<TextDisplayMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(TextDisplayMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("alignment", TextDisplayMetaComponent::getAlignment, TextDisplayMetaComponent::setAlignment, EnumUtil.enumStringNBTCodec(TextAlignment.class), TextAlignment.CENTER)
					.codec("background", TextDisplayMetaComponent::getBackgroundColor, TextDisplayMetaComponent::setBackgroundColor, Color.NBT_CODEC, Color.fromARGB(0x40000000))
					.boolField("default_background", TextDisplayMetaComponent::hasDefaultBackground, TextDisplayMetaComponent::setDefaultBachground, false)
					.intField("line_width", TextDisplayMetaComponent::getLineWidth, TextDisplayMetaComponent::setLineWidth, 200)
					.boolField("see_through", TextDisplayMetaComponent::isSeeThrough, TextDisplayMetaComponent::setSeeThrough, false)
					.boolField("shadow", TextDisplayMetaComponent::isShadowed, TextDisplayMetaComponent::setShadowed, false)
					.codec("text", TextDisplayMetaComponent::getText, TextDisplayMetaComponent::setText, Chat.NBT_CODEC)
					.byteField("text_opacity", TextDisplayMetaComponent::getTextOpacity, TextDisplayMetaComponent::setTextOpacity, (byte) -1)
					.build();
	
	public static final int
	FLAG_IS_SHADOWED = 0x01,
	FLAG_IS_SEE_THROUGH = 0x02,
	FLAG_DEFAULT_BACKGROUND = 0x04,
	FLAG_TEXT_ALIGN_LEFT = 0x08,
	FLAG_TEXT_ALIGN_RIGHT = 0x10;
	
	public static final MetaDataField<Chat> 
	META_TEXT = new MetaDataField<>(23, ChatUtil.EMPTY, EntityMetaTypes.CHAT);
	public static final MetaDataField<Integer> 
	META_LINE_WIDTH = new MetaDataField<>(24, 200, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer> 
	META_BACKGROUND_COLOR = new MetaDataField<>(25, 0x40000000, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Byte> 
	META_TEXT_OPACITY = new MetaDataField<>(26, (byte) -1, EntityMetaTypes.BYTE);
	public static final MetaDataField<Byte> 
	META_TEXT_DISPLAY_FLAGS = new MetaDataField<>(27, (byte) 0, EntityMetaTypes.BYTE);
	
	public TextDisplayMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 5;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_TEXT);
		container.set(META_LINE_WIDTH);
		container.set(META_BACKGROUND_COLOR);
		container.set(META_TEXT_OPACITY);
		container.set(META_TEXT_DISPLAY_FLAGS);
	}

	public TextAlignment getAlignment() {
		int val = getHolder().getMetaContainer().getData(META_TEXT_DISPLAY_FLAGS);
		return (val & FLAG_TEXT_ALIGN_LEFT) == FLAG_TEXT_ALIGN_LEFT ? TextAlignment.LEFT : 
			(val & FLAG_TEXT_ALIGN_RIGHT) == FLAG_TEXT_ALIGN_RIGHT ? TextAlignment.RIGHT : TextAlignment.CENTER;
	}
	
	protected void setTextDisplayFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_TEXT_DISPLAY_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_TEXT_DISPLAY_FLAGS, value);
	}

	public void setAlignment(TextAlignment alignment) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_TEXT_DISPLAY_FLAGS);
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
		container.setData(META_TEXT_DISPLAY_FLAGS, (byte) val);
	}

	public Color getBackgroundColor() {
		return Color.fromARGB(getHolder().getMetaContainer().getData(META_BACKGROUND_COLOR));
	}

	public void setBackgroundColor(Color color) {
		getHolder().getMetaContainer().setData(META_BACKGROUND_COLOR, color == null ? 
				META_BACKGROUND_COLOR.getDefaultData() : color.asARGB());
	}

	public boolean hasDefaultBackground() {
		return (getHolder().getMetaContainer().getData(META_TEXT_DISPLAY_FLAGS) & FLAG_DEFAULT_BACKGROUND) == FLAG_DEFAULT_BACKGROUND;
	}

	public void setDefaultBachground(boolean defaultBackground) {
		setTextDisplayFlag(FLAG_DEFAULT_BACKGROUND, defaultBackground);
	}

	public int getLineWidth() {
		return getHolder().getMetaContainer().getData(META_LINE_WIDTH);
	}

	public void setLineWidth(int lineWidth) {
		getHolder().getMetaContainer().setData(META_LINE_WIDTH, lineWidth);
	}

	public boolean isSeeThrough() {
		return (getHolder().getMetaContainer().getData(META_TEXT_DISPLAY_FLAGS) & FLAG_IS_SEE_THROUGH) == FLAG_IS_SEE_THROUGH;
	}

	public void setSeeThrough(boolean seeThrough) {
		setTextDisplayFlag(FLAG_IS_SEE_THROUGH, seeThrough);
	}

	public boolean isShadowed() {
		return (getHolder().getMetaContainer().getData(META_TEXT_DISPLAY_FLAGS) & FLAG_IS_SHADOWED) == FLAG_IS_SHADOWED;
	}

	public void setShadowed(boolean shadowed) {
		setTextDisplayFlag(FLAG_IS_SHADOWED, shadowed);
	}

	public Chat getText() {
		return getHolder().getMetaContainer().getData(META_TEXT);
	}

	public void setText(Chat text) {
		getHolder().getMetaContainer().setData(META_TEXT, text == null ? ChatUtil.EMPTY : text);
	}

	public int getTextOpacity() {
		return getHolder().getMetaContainer().getData(META_TEXT_OPACITY) & 0xFF;
	}

	public void setTextOpacity(int opacity) {
		getHolder().getMetaContainer().setData(META_TEXT_OPACITY, (byte) opacity);
	}
	
	@Override
	public NBTCodec<? extends TextDisplayMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum TextAlignment implements IDHolder, EnumName {

		CENTER,
		LEFT,
		RIGHT;
		
		private final String name;
		
		private TextAlignment() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}

	}

}
