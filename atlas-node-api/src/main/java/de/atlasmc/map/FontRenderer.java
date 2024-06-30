package de.atlasmc.map;

import java.util.HashMap;

public class FontRenderer implements Renderer {
	
	private Font font;
	private byte color;
	private int pixLine;
	private int pixChar;
	private int scale;
	private String text = "";
	
	public FontRenderer(Font font) {
		this(font, MapColor.COLOR_BLACK);
	}
	
	public FontRenderer(Font font, MapColor color) {
		this(font, color, 1, 1, 1);
	}
	
	public FontRenderer(Font font, int lineSpacing, int charSpacing) {
		this(font, lineSpacing, charSpacing, 1);
	}
	
	public FontRenderer(Font font, int lineSpacing, int charSpacing, int scale) {
		this(font, MapColor.COLOR_BLACK, lineSpacing, charSpacing, scale);
	}
	
	public FontRenderer(Font font, MapColor color, int lineSpacing, int charSpacing, int scale) {
		if (font == null) 
			throw new IllegalArgumentException("Font can not be null!");
		if (color == null) 
			throw new IllegalArgumentException("MapColor can not be null!");
		if (scale < 1) 
			throw new IllegalArgumentException("Scale can not be < 1!");
		this.font = font;
		this.color = color.getID();
		this.pixChar = charSpacing;
		this.pixLine = lineSpacing;
		this.scale = scale;
	}
	
	public void setColor(MapColor color) {
		if (color == null) 
			throw new IllegalArgumentException("MapColor can not be null!");
		this.color = color.getID();
	}
	
	public MapColor getColor() {
		return MapColor.byID(color);
	}
	
	public void setFont(Font font) {
		if (font == null) 
			throw new IllegalArgumentException("Font can not be null!");
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setLineSpacing(int value) {
		pixLine = value;
	}
	
	public void setCharSpacing(int value) {
		pixChar = value;
	}
	
	public int getLineSpacing() {
		return pixLine;
	}
	
	public int getCharSpacing() {
		return pixChar;
	}
	
	public int getScale() {
		return scale;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setScale(int value) {
		if (value < 1) 
			throw new IllegalArgumentException("Scale can not be < 1!");
		this.scale = value;
	}
	
	@Override
	public boolean render(MapData data, int offsetX, int offsetY) {
		final int height = font.getHeight();
		int x = offsetX;
		int y = offsetY + height;
		if (scale == 1) {
			for (char charr : text.toCharArray()) {
				if (charr == '\n') {
					x = offsetX;
					y += pixLine + height;
					continue;
				}
				int[] render = font.getCharRender(charr);
				if (render == null) {
					throw new IllegalArgumentException("Char (" + charr + ") not found!");
				}
				int length = render[0];
				if (render.length > 1)
				for (int i = 1; i < render.length; i++) {
					int tx = render[i++];
					int ty = render[i];
					data.setPixel(x + tx, y-ty, color);
				};
				x += length + pixChar;
			}
		} else {
			for (char charr : text.toCharArray()) {
				if (charr == '\n') {
					x = offsetX;
					y += pixLine*scale + height*scale;
					continue;
				}
				int[] render = font.getCharRender(charr);
				if (render == null) {
					throw new IllegalArgumentException("Char (" + charr + ") not found!");
				}
				int length = render[0];
				if (render.length > 1)
				for (int i = 1; i < render.length; i++) {
					int tx = render[i++]*scale;
					int ty = y - render[i]*scale;
					for (int j = 0; j < scale; j++) {
						int px = x + tx + j;
						for (int k = 0; k < scale; k++) {
							data.setPixel(px, ty-k, color);
						}
					}
					x += length*scale + pixChar*scale;
				};
			}
		}
		return true;
	}
	
	public static class Font {
		private HashMap<Character, int[]> chars = new HashMap<>(); 
		private int height = 5;
		
		/**
		 * @param charr
		 * @param render
		 * array[charLength, PixelX, PixelY...]
		 */
		public void setChar(char charr, int[] render) {
			chars.put(charr, render);
		}
		
		public boolean hasChar(char charr) {
			return chars.containsKey(charr);
		}
		
		public int[] getCharRender(char charr) {
			return chars.get(charr);
		}
		
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
	}

}
