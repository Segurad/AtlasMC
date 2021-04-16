package de.atlasmc.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.block.Block;
import de.atlasmc.world.Region;
import de.atlasmc.world.World;

public final class MapColor {

	//https://minecraft.gamepedia.com/Map_item_format
	
	public static final int LEVEL_NORMAL = 2,
			LEVEL_LOW = 0,
			LEVEL_LOWER = 1,
			LEVEL_LOWEST = 3;
	private final int level, R, G, B, baseID;
	private final byte ID;
	private static final MapColor[] colors;
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
		final int size = c.length*4;
		colors = new MapColor[size];
		for (int i = 0; i < c.length; i++) {
			final int j = i;
			colors[j*4+LEVEL_LOW] = c(c[j]*180/255, c[j+1]*180/255, c[j+2]*180/255, LEVEL_LOW, j*4+LEVEL_LOW,j);
			colors[j*4+LEVEL_LOWER] = c(c[j]*220/255, c[j+1]*220/255, c[j+2]*220/255, LEVEL_LOWER, j*4+LEVEL_LOWER,j);
			colors[j*4+LEVEL_NORMAL] = c(c[j], c[j+1], c[j+2], LEVEL_NORMAL, j*4+LEVEL_NORMAL,j);
			colors[j*4+LEVEL_LOWEST] = c(c[j]*135/255, c[j+1]*135/255, c[j+2]*135/255, LEVEL_LOWEST, j*4+LEVEL_LOWEST,j);
		}
		NONE = colors[0];
		GRASS = colors[6];
		SAND = colors[10];
		WOOL = colors[14];
		FIRE = colors[18];
		ICE = colors[22];
		METAL = colors[26];
		PLANT = colors[30];
		SNOW = colors[34];
		CLAY = colors[38];
		
		DIRT = colors[42];
		STONE = colors[46];
		WATER = colors[50];
		WOOD = colors[54];
		QUARTZ = colors[58];
		COLOR_ORANGE = colors[62];
		COLOR_MAGENTA = colors[66];
		COLOR_LIGHT_BLUE = colors[70];
		COLOR_YELLOW = colors[74];
		COLOR_LIGHT_GREEN = colors[78];
		
		COLOR_PINK = colors[82];
		COLOR_GRAY = colors[86];
		COLOR_LIGHT_GRAY = colors[90];
		COLOR_CYAN = colors[94];
		COLOR_PURPLE = colors[98];
		COLOR_BLUE = colors[102];
		COLOR_BROWN = colors[106];
		COLOR_GREEN = colors[110];
		COLOR_RED = colors[114];
		COLOR_BLACK = colors[118];
		
		GOLD = colors[122];
		DIAMOND = colors[126];
		LAPIS = colors[130];
		EMERALD = colors[134];
		PODZOL = colors[138];
		NETHER = colors[142];
		TERRACOTTA_WHITE = colors[146];
		TERRACOTTA_ORANGE = colors[150];
		TERRACOTTA_MAGENTA = colors[154];
		TERRACOTTA_LIGHT_BLUE = colors[158];
		
		TERRACOTTA_YELLOW = colors[162];
		TERRACOTTA_LIGHT_GREEN = colors[166];
		TERRACOTTA_PINK = colors[170];
		TERRACOTTA_GRAY = colors[174];
		TERRACOTTA_LIGHT_GRAY = colors[178];
		TERRACOTTA_CYAN = colors[182];
		TERRACOTTA_PURPLE = colors[186];
		TERRACOTTA_BLUE = colors[190];
		TERRACOTTA_BROWN = colors[194];
		TERRACOTTA_GREEN = colors[198];
		
		TERRACOTTA_RED = colors[202];
		TERRACOTTA_BLACK = colors[206];
		CRIMSON_NYLIUM = colors[210];
		CRIMSON_STEM = colors[214];
		CRIMSON_HYPHAE = colors[218];
		WARPED_NYLIUM = colors[222];
		WARPED_STEM = colors[226];
		WARPED_HYPHAE = colors[230];
		WARPED_WART_BLOCK = colors[234];
	}
	
	private static MapColor c(int r, int g, int b, int l, int id, int bid) {
		return new MapColor(r,g,b,l,id, bid);
	}
	
	MapColor(int r, int g, int b, int level, int id, int bid) {
		this.R = r;
		this.B = b;
		this.G = g;
		this.level = level;
		this.ID = (byte) id;
		this.baseID = bid;
	}
	
	/**
	 * 
	 * @return the Red value
	 */
	public int getB() {
		return B;
	}
	
	/**
	 * 
	 * @return the id of the color group
	 */
	public int getBaseID() {
		return baseID;
	}
	
	/**
	 * 
	 * @return the Green value
	 */
	public int getG() {
		return G;
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
		return R;
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
		if (buffimg == null) return null;
		final int width = buffimg.getWidth();
		final int height = buffimg.getHeight();
		final int[] pixels = new int[width*height];
		buffimg.getRGB(0, 0, buffimg.getWidth(), buffimg.getHeight(), pixels, 0, buffimg.getWidth());
		final byte[] map = new byte[width*height];
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[x+y*width] = matchColor(new java.awt.Color(pixels[x+y*width]));
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

	public static byte matchColor(java.awt.Color color) {
		if (color.getAlpha() < 128) return NONE.ID;
		
		int index = 2;
		double distance = -1.0;
		final int length = colors.length;
		for (int i = 4; i < length; i++) {
			final double d = getDistance(color, colors[i]);
			if (d < distance || distance == -1.0) {
				distance = d;
				index = i;
			}
		}
		return colors[index].ID;
	}
	
	public static BufferedImage mapToImage(MapData data) {
		if (data == null) return null;
		final int width = data.getWidth();
		final int height = data.getHeight();
		final byte[] pixels = data.getPixels();
		final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				img.setRGB(x, y, colors[pixels[x+y*width]].getColor().asRGB());
			}
		};
		return img;
	}
	
	public static MapData regionToMap(final Region region, final World world) {
		if (region == null) return null;
		final int width = (int) region.getWidhtX(), height = (int) region.getWidhtZ();
		final byte[] map = new byte[width*height];
		final int wx = (int) region.getMinX(),wz = (int) region.getMinZ();
		for (int z = 0; z < height; z++) {
			for (int x = 0; x < width; x++) {
				final Block b = world.getHighestBlockAt(wx+x, wz+z);
				MapColor c = fromMaterial(b.getType());
				int y = world.getHighestBlockYAt(wx+x, wz+z-1);
				if (y > b.getY()) {
					int ty = world.getHighestBlockYAt(wx+x, wz+z-2);
					if (ty > y) {
						y = ty;
						ty = world.getHighestBlockYAt(wx+x, wz+z-3);
						if (ty > y) {
							c = colors[c.baseID*4+LEVEL_LOWEST];
						} else c = colors[c.baseID*4+LEVEL_LOWER];
					} else c = colors[c.baseID*4+LEVEL_LOW];
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
		return colors[id & 0xFF];
	}
	
	/**
	 * 
	 * @param id
	 * @return the MapColor with the baseID
	 */
	public static MapColor byBaseID(int id) {
		return colors[id*4+LEVEL_NORMAL];
	}
	
	private static double getDistance(java.awt.Color color1, MapColor color2) {
		final double dr = color2.R-color1.getRed();
		final double dg = color2.G-color1.getGreen();
		final double db = color2.B-color1.getBlue();
		return (dr*dr)+(dg*dg)+(db*db);
	}
	
	public static MapColor fromMaterial(Material material) {
		return COLOR_BLACK;
	}
}
