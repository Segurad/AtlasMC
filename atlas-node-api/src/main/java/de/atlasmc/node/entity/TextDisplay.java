package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TextDisplay extends Display {
	
	public static final NBTSerializationHandler<TextDisplay>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TextDisplay.class)
					.include(Display.NBT_HANDLER)
					.enumStringField("alignment", TextDisplay::getAlignment, TextDisplay::setAlignment, TextAlignment::getByName, TextAlignment.CENTER)
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
	default NBTSerializationHandler<? extends TextDisplay> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum TextAlignment implements AtlasEnum {

		CENTER,
		LEFT,
		RIGHT;
		
		private static List<TextAlignment> VALUES;
		
		private String name;
		
		private TextAlignment() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static TextAlignment getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			final List<TextAlignment> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				TextAlignment value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + name);
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static TextAlignment getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<TextAlignment> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

	}

}
