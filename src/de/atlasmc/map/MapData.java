package de.atlasmc.map;

public class MapData {

	public static final int SOURCE_UNKNOWN = 0,
			SOURCE_WORLD = 1,
			SOURCE_IMAGE = 2,
			SOURCE_CUSTOM = 3;
	
	private final byte[] pixels;
	private final int width, height, source;
	private final String sourcename;
	
	public MapData(byte[] pixels, int width, int height, int source, String sourcename) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		this.source = source;
		this.sourcename = sourcename;
	}

	public String getSourcename() {
		return sourcename;
	}

	public byte[] getPixels() {
		return pixels;
	}
	
	public byte[] getPixels128(final int offsetX,final int offsetY) {
		final byte[] map = new byte[128*128];
		for (int i = 0; i+offsetY < height && i < 128; i++) {
			for (int j = 0; j+offsetX < width && j < 128; j++) {
				map[j+i*128] = pixels[offsetX+j+(i+offsetY)*width];
			}
		}
		return map;
	}
	
	public void setPixel(int x, int y, MapColor color) {
		setPixel(x, y, color.getID());
	}
	
	public void setPixel(int x, int y, byte color) {
		pixels[x+y*width] = color;
	}

	public int getWidth() {
		return width;
	}

	public int getSource() {
		return source;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void clear() {
		final int length = pixels.length;
		for (int i = 0; i < length; i++) {
			pixels[i] = 0x00;
		}
	}
}