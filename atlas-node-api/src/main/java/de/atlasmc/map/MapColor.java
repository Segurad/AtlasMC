package de.atlasmc.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.block.Block;
import de.atlasmc.world.Region;
import de.atlasmc.world.World;

public final class MapColor {

	//https://minecraft.gamepedia.com/Map_item_format
	
	public static final int LEVEL_NORMAL = 2,
			LEVEL_LOW = 0,
			LEVEL_LOWER = 1,
			LEVEL_LOWEST = 3;
	private static final byte[] RGB_TO_COLOR;
	private static final MapColor[] COLORS;
	public static final MapColor 
	NONE,
	GRASS,
	SAND,
	WOOL,
	FIRE,
	ICE,
	METAL,
	PLANT,
	SNOW,
	CLAY,
	
	DIRT,
	STONE,
	WATER,
	WOOD,
	QUARTZ,
	COLOR_ORANGE,
	COLOR_MAGENTA,
	COLOR_LIGHT_BLUE,
	COLOR_YELLOW,
	COLOR_LIGHT_GREEN,
	
	COLOR_PINK,
	COLOR_GRAY,
	COLOR_LIGHT_GRAY,
	COLOR_CYAN,
	COLOR_PURPLE,
	COLOR_BLUE,
	COLOR_BROWN,
	COLOR_GREEN,
	COLOR_RED,
	COLOR_BLACK,
	
	GOLD,
	DIAMOND,
	LAPIS,
	EMERALD,
	PODZOL,
	NETHER,
	TERRACOTTA_WHITE,
	TERRACOTTA_ORANGE,
	TERRACOTTA_MAGENTA,
	TERRACOTTA_LIGHT_BLUE,
	
	TERRACOTTA_YELLOW,
	TERRACOTTA_LIGHT_GREEN,
	TERRACOTTA_PINK,
	TERRACOTTA_GRAY,
	TERRACOTTA_LIGHT_GRAY,
	TERRACOTTA_CYAN,
	TERRACOTTA_PURPLE,
	TERRACOTTA_BLUE,
	TERRACOTTA_BROWN,
	TERRACOTTA_GREEN,
	
	TERRACOTTA_RED,
	TERRACOTTA_BLACK,
	CRIMSON_NYLIUM,
	CRIMSON_STEM,
	CRIMSON_HYPHAE,
	WARPED_NYLIUM,
	WARPED_STEM,
	WARPED_HYPHAE,
	WARPED_WART_BLOCK;
	
	
	static {
		COLORS = buildMapColors();
		NONE = COLORS[0];
		GRASS = COLORS[6];
		SAND = COLORS[10];
		WOOL = COLORS[14];
		FIRE = COLORS[18];
		ICE = COLORS[22];
		METAL = COLORS[26];
		PLANT = COLORS[30];
		SNOW = COLORS[34];
		CLAY = COLORS[38];
		
		DIRT = COLORS[42];
		STONE = COLORS[46];
		WATER = COLORS[50];
		WOOD = COLORS[54];
		QUARTZ = COLORS[58];
		COLOR_ORANGE = COLORS[62];
		COLOR_MAGENTA = COLORS[66];
		COLOR_LIGHT_BLUE = COLORS[70];
		COLOR_YELLOW = COLORS[74];
		COLOR_LIGHT_GREEN = COLORS[78];
		
		COLOR_PINK = COLORS[82];
		COLOR_GRAY = COLORS[86];
		COLOR_LIGHT_GRAY = COLORS[90];
		COLOR_CYAN = COLORS[94];
		COLOR_PURPLE = COLORS[98];
		COLOR_BLUE = COLORS[102];
		COLOR_BROWN = COLORS[106];
		COLOR_GREEN = COLORS[110];
		COLOR_RED = COLORS[114];
		COLOR_BLACK = COLORS[118];
		
		GOLD = COLORS[122];
		DIAMOND = COLORS[126];
		LAPIS = COLORS[130];
		EMERALD = COLORS[134];
		PODZOL = COLORS[138];
		NETHER = COLORS[142];
		TERRACOTTA_WHITE = COLORS[146];
		TERRACOTTA_ORANGE = COLORS[150];
		TERRACOTTA_MAGENTA = COLORS[154];
		TERRACOTTA_LIGHT_BLUE = COLORS[158];
		
		TERRACOTTA_YELLOW = COLORS[162];
		TERRACOTTA_LIGHT_GREEN = COLORS[166];
		TERRACOTTA_PINK = COLORS[170];
		TERRACOTTA_GRAY = COLORS[174];
		TERRACOTTA_LIGHT_GRAY = COLORS[178];
		TERRACOTTA_CYAN = COLORS[182];
		TERRACOTTA_PURPLE = COLORS[186];
		TERRACOTTA_BLUE = COLORS[190];
		TERRACOTTA_BROWN = COLORS[194];
		TERRACOTTA_GREEN = COLORS[198];
		
		TERRACOTTA_RED = COLORS[202];
		TERRACOTTA_BLACK = COLORS[206];
		CRIMSON_NYLIUM = COLORS[210];
		CRIMSON_STEM = COLORS[214];
		CRIMSON_HYPHAE = COLORS[218];
		WARPED_NYLIUM = COLORS[222];
		WARPED_STEM = COLORS[226];
		WARPED_HYPHAE = COLORS[230];
		WARPED_WART_BLOCK = COLORS[234];
		boolean useCache = Boolean.parseBoolean(System.getProperty("de.atlasmc.map.useIntToMapColorCache", "true"));
		if (!useCache) {
			RGB_TO_COLOR = null;
		} else {
			RGB_TO_COLOR = buildRGBToColorCache();
		}
	}
	
	private final byte level;
	private final byte R;
	private final byte G;
	private final byte B;
	private final byte baseID;
	private final byte ID;
	
	private MapColor(int r, int g, int b, int level, int id, int bid) {
		this.R = (byte) r;
		this.B = (byte) b;
		this.G = (byte) g;
		this.level = (byte) level;
		this.ID = (byte) id;
		this.baseID = (byte) bid;
	}
	
	/**
	 * 
	 * @return the Red value
	 */
	public int getB() {
		return B & 0xFF;
	}
	
	/**
	 * 
	 * @return the id of the color group
	 */
	public int getBaseID() {
		return baseID & 0xFF;
	}
	
	/**
	 * 
	 * @return the Green value
	 */
	public int getG() {
		return G & 0xFF;
	}
	
	/**
	 * 
	 * @return the color's id
	 */
	public byte getID() {
		return ID;
	}
	
	/**
	 * 
	 * @return the red value
	 */
	public int getR() {
		return R & 0xFF;
	}
	
	/**
	 * 
	 * @return true if the MapColor has a darker color
	 */
	public boolean hasDarkerColor() {
		return level != LEVEL_LOWEST;
	}
	
	/**
	 * 
	 * @return true if the MapColor has i lighter color
	 */
	public boolean hasLighterColor() {
		return level != LEVEL_NORMAL;
	}
	
	/**
	 * 
	 * @return return the Color of this MapColor
	 */
	public Color getColor() {
		return new Color(R, G, B);
	}
	
	public static MapData imageToMap(String name, BufferedImage buffimg) {
		if (buffimg == null) 
			return null;
		final int width = buffimg.getWidth();
		final int height = buffimg.getHeight();
		final int[] pixels = new int[width*height];
		buffimg.getRGB(0, 0, buffimg.getWidth(), buffimg.getHeight(), pixels, 0, buffimg.getWidth());
		final byte[] map = new byte[width*height];
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[x+y*width] = matchColor(pixels[x+y*width]);
			}
		};
		return new MapData(map, width, height, MapData.SOURCE_IMAGE, name);
	}
	
	public static BufferedImage resizeImage(BufferedImage image, int newwith, int newheight) {
		BufferedImage newimage = new BufferedImage(newwith, newheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = newimage.createGraphics();
		graphics.drawImage(image, null, 0, 0);
		graphics.dispose();
		return newimage;
	}

	/**
	 * Returns the byte ID of the nearest MapColor to the RGBA input.<br>
	 * If the alpha value is lower than 128 this method will always return the ID of {@link MapColor#NONE}
	 * @param color
	 * @return MapColor ID
	 */
	public static byte matchColor(int color) {
		if ((color & 0xFF000000) > 0x10000000) // alpha > 0x10 = transparent color
			return NONE.ID;
		if (RGB_TO_COLOR != null)
			return RGB_TO_COLOR[color & 0xFFFFFF];
		int index = 2;
		double distance = -1.0;
		final int length = COLORS.length;
		final int r = color >> 16 & 0xFF;
		final int g = color >> 8 & 0xFF;
		final int b = color & 0xFF;
		for (int i = 4; i < length; i++) {
			final double d = getDistance(r, g, b, COLORS[i]);
			if (d < distance || distance == -1.0) {
				distance = d;
				index = i;
			}
		}
		return COLORS[index].ID;
	}
	
	public static BufferedImage mapToImage(MapData data) {
		if (data == null) return null;
		final int width = data.getWidth();
		final int height = data.getHeight();
		final byte[] pixels = data.getPixels();
		final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				img.setRGB(x, y, COLORS[pixels[x+y*width]].getColor().asRGB());
			}
		};
		return img;
	}
	
	public static MapData regionToMap(final Region region, final World world) {
		if (region == null) return null;
		final int width = (int) region.getWidhtX();
		final int height = (int) region.getWidhtZ();
		final byte[] map = new byte[width*height];
		final int wx = (int) region.getMinX();
		final int wz = (int) region.getMinZ();
		for (int z = 0; z < height; z++) {
			for (int x = 0; x < width; x++) {
				final Block b = world.getHighestBlockAt(wx+x, wz+z);
				MapColor c = byID(b.getType().getColorID());
				int y = world.getHighestBlockYAt(wx+x, wz+z-1);
				if (y > b.getY()) {
					int ty = world.getHighestBlockYAt(wx+x, wz+z-2);
					if (ty > y) {
						y = ty;
						ty = world.getHighestBlockYAt(wx+x, wz+z-3);
						if (ty > y) {
							c = COLORS[c.baseID*4+LEVEL_LOWEST];
						} else c = COLORS[c.baseID*4+LEVEL_LOWER];
					} else c = COLORS[c.baseID*4+LEVEL_LOW];
				}
				map[x+z*width] = c.ID;
			}
		}
		return new MapData(map, width, height, MapData.SOURCE_WORLD, world.getName());
	}
	
	/**
	 * 
	 * @param id
	 * @return the MapColor with the id
	 */
	public static MapColor byID(byte id) {
		return COLORS[id & 0xFF];
	}
	
	/**
	 * 
	 * @param id
	 * @return the MapColor with the baseID
	 */
	public static MapColor byBaseID(int id) {
		return COLORS[id*4+LEVEL_NORMAL];
	}
	
	/**
	 * Returns the Distance between RGB and {@link MapColor}
	 * @param r
	 * @param g
	 * @param b
	 * @param color
	 * @return distance
	 */
	private static double getDistance(int r, int g, int b, MapColor color) {
		final double deltaR = color.R-r;
		final double deltaG = color.G-g;
		final double deltaB = color.B-b;
		return (deltaR*deltaR)+(deltaG*deltaG)+(deltaB*deltaB);
	}

	public static void writeIntToMapColorCache(File file) throws IOException {
		file.mkdirs();
		if (file.exists())
			file.delete();
		FileOutputStream out = new FileOutputStream(file);
		out.write(RGB_TO_COLOR);
		out.close();
	}
	
	private static byte[] buildRGBToColorCache() {
		byte[] cache = new byte[0x1000000];
		for (int i = 0; i < 0x1000000; i++) {
			cache[i] = matchColor(i);
		}
		return cache;
	}
	
	private static MapColor[] buildMapColors() {
		final int[] c = new int[] {
			0,0,0,
			127, 178, 56,
			247, 233, 163,
			199, 199, 199,
			255, 0, 0,
			160, 160, 255,
			167, 167, 167,
			0, 124, 0,
			255, 255, 255,
			164, 168, 184,
			151, 109, 77,
			112, 112, 112,
			64, 64, 255,
			143, 119, 72,
			255, 252, 245,
			216, 127, 51,
			178, 76, 216,
			102, 153, 216,
			229, 229, 51,
			127, 204, 25,
			242, 127, 165,
			76, 76, 76,
			153, 153, 153,
			76, 127, 153,
			127, 63, 178,
			51, 76, 178,
			102, 76, 51,
			102, 127, 51,
			153, 51, 51,
			25, 25, 25,
			250, 238, 77,
			92, 219, 213,
			74, 128, 255,
			0, 217, 58,
			129, 86, 49,
			112, 2, 0,
			209, 177, 161,
			159, 82, 36,
			149, 87, 108,
			112, 108, 138,
			186, 133, 36,
			103, 117, 53,
			160, 77, 78,
			57, 41, 35,
			135, 107, 98,
			87, 92, 92,
			122, 73, 88,
			76, 62, 92,
			76, 50, 35,
			76, 82, 42,
			142, 60, 46,
			37, 22, 16,
			189, 48, 49,
			148, 63, 97,
			92, 25, 29,
			22, 126, 134,
			58, 142, 140,
			86, 44, 62,
			20, 180, 133,
		};
		final int size = c.length/3;
		final MapColor[] colors = new MapColor[size*4];
		for (int i = 0; i < size; i++) {
			final int j = i;
			colors[j*4+LEVEL_LOW] = 
					new MapColor(
							c[j]*180/255, 
							c[j+1]*180/255, 
							c[j+2]*180/255, 
							LEVEL_LOW, 
							j*4+LEVEL_LOW, 
							j);
			colors[j*4+LEVEL_LOWER] = new MapColor(c[j]*220/255, c[j+1]*220/255, c[j+2]*220/255, LEVEL_LOWER, j*4+LEVEL_LOWER, j);
			colors[j*4+LEVEL_NORMAL] = new MapColor(c[j], c[j+1], c[j+2], LEVEL_NORMAL, j*4+LEVEL_NORMAL, j);
			colors[j*4+LEVEL_LOWEST] = new MapColor(c[j]*135/255, c[j+1]*135/255, c[j+2]*135/255, LEVEL_LOWEST, j*4+LEVEL_LOWEST, j);
		}
		return colors;
	}
		
}
