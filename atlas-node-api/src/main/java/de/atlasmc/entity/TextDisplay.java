package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;

public interface TextDisplay {
	
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
	
	public static enum TextAlignment {

		CENTER,
		LEFT,
		RIGHT;
		
		private static List<TextAlignment> VALUES;
		
		private String nameID;
		
		private TextAlignment() {
			nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static TextAlignment getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (TextAlignment value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + nameID);
		}
		
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
