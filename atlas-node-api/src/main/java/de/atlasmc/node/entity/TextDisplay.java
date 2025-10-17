package de.atlasmc.node.entity;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface TextDisplay extends Display {
	
	public static final NBTCodec<TextDisplay>
	NBT_HANDLER = NBTCodec
					.builder(TextDisplay.class)
					.include(Display.NBT_HANDLER)
					.enumStringField("alignment", TextDisplay::getAlignment, TextDisplay::setAlignment, TextAlignment.class, TextAlignment.CENTER)
					.color("background", TextDisplay::getBackgroundColor, TextDisplay::setBackgroundColor, 0x40000000)
					.boolField("default_background", TextDisplay::hasDefaultBackground, TextDisplay::setDefaultBachground, false)
					.intField("line_width", TextDisplay::getLineWidth, TextDisplay::setLineWidth, 200)
					.boolField("see_through", TextDisplay::isSeeThrough, TextDisplay::setSeeThrough, false)
					.boolField("shadow", TextDisplay::isShadowed, TextDisplay::setShadowed, false)
					.chat("text", TextDisplay::getText, TextDisplay::setText)
					.byteField("text_opacity", TextDisplay::getTextOpacity, TextDisplay::setTextOpacity, (byte) -1)
					.build();
	
	TextAlignment getAlignment();
	
	void setAlignment(TextAlignment alignment);
	
	Color getBackgroundColor();
	
	void setBackgroundColor(Color color);
	
	boolean hasDefaultBackground();
	
	void setDefaultBachground(boolean defaultBackground);
	
	int getLineWidth();
	
	void setLineWidth(int lineWidth);
	
	boolean isSeeThrough();
	
	void setSeeThrough(boolean seeThrough);
	
	boolean isShadowed();
	
	void setShadowed(boolean shadowed);
	
	Chat getText();
	
	void setText(Chat text);
	
	int getTextOpacity();
	
	void setTextOpacity(int opacity);
	
	@Override
	default NBTCodec<? extends TextDisplay> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum TextAlignment implements IDHolder, EnumName {

		CENTER,
		LEFT,
		RIGHT;
		
		private String name;
		
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
