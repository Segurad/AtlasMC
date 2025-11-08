package de.atlasmc;

import java.io.IOException;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.codec.CodecContext;

/**
 * Simple rgba color value
 */
@ThreadSafe
public final class Color implements ColorValue {
	
	public static final NBTCodec<Color> NBT_CODEC = new NBTCodec<Color>() {
		
		@Override
		public Class<?> getType() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Color deserialize(Color value, NBTReader input, CodecContext context) throws IOException {
			switch (input.getType()) {
			case TagType.INT: {
				return Color.fromARGB(input.readIntTag());
			}
			case TagType.LIST: {
				if (input.getListType() != TagType.FLOAT)
					throw new NBTException("Expected float tag but was: " + input.getListType());
				input.readNextEntry();
				float r = input.readFloatTag();
				float g = input.readFloatTag();
				float b = input.readFloatTag();
				float a = input.getRestPayload() > 0 ? input.readFloatTag() : 0;
				Color c = Color.fromRGB(Color.asARGB(r, g, b, a));
				input.readNextEntry();
				return c;
			}
			default:
				throw new NBTException("Unexpected tag type: " + input.getType());
			}
		}
		
		@Override
		public boolean serialize(CharSequence key, Color value, NBTWriter output, CodecContext context) throws IOException {
			output.writeIntTag(key, value.asARGB());
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.INT_LIST;
		}
	};
	
	public static final Color 
	BLACK = new Color("BLACK", 0x00, 0x00, 0x00), 
	AQUA = new Color("AQUA", 0x00, 0xFF, 0xFF), 
	BLUE = new Color("BLUE", 0x00, 0x00, 0xFF),
	FUCHSIA = new Color("FUCHSIA", 0xFF, 0x00, 0xFF), 
	GRAY =  new Color("GRAY", 0x80, 0x80, 0x80), 
	GREEN = new Color("GREEN", 0x00, 0x80, 0x00),
	LIME = new Color("LIME", 0x00, 0xFF, 0x00),
	MAROON = new Color("MAROON", 0x80, 0x00, 0x00),
	NAVY = new Color("NAVY", 0x00, 0x00, 0x80),
	OLIVE = new Color("OLIVE", 0x80, 0x80, 0x00),
	ORANGE = new Color("ORANGE", 0xFF, 0xA5, 0x00),
	PURPLE = new Color("PURPLE", 0x80, 0x00, 0x80),
	RED = new Color("RED", 0xFF, 0x00, 0x00), 
	SILVER = new Color("SILVER", 0xC0, 0xC0, 0xC0), 
	TEAL = new Color("TEAL", 0x00, 0x80, 0x80),
	WHITE = new Color("WHITE", 0xFF, 0xFF, 0xFF),
	YELLOW = new Color("YELLOW", 0xFF, 0xFF, 0x00);
	
	private static final List<Color> VALUES;

	static {
		VALUES = List.of(
				AQUA,
				BLACK,
				BLUE,
				FUCHSIA,
				GRAY,
				GREEN,
				LIME,
				MAROON,
				NAVY,
				OLIVE,
				ORANGE,
				PURPLE,
				RED,
				SILVER,
				TEAL,
				WHITE,
				YELLOW);
	}

	public final byte r; 
	public final byte g; 
	public final byte b; 
	public final byte a;
	
	private final String name;
	
	public Color(int red, int green, int blue) {
		this("RGB", red, green, blue);
	}
	
	public  Color(String name, int red, int green, int blue) {
		if (red < 0 || red > 255) 
			throw new IllegalArgumentException("Red is not between 0 and 255: " + red);
		if (green < 0 || green > 255) 
			throw new IllegalArgumentException("Green is not between 0 and 255: " + green);
		if (blue < 0 || blue > 255) 
			throw new IllegalArgumentException("Red is not between 0 and 255: " + blue);
		this.r = (byte) red;
		this.g = (byte) blue;
		this.b = (byte) green;
		this.a = 0;
		this.name = name != null ? name : "RGB";
	}
	
	public  Color(String name, int red, int green, int blue, int alpha) {
		if (red < 0 || red > 255) 
			throw new IllegalArgumentException("Red is not between 0 and 255: " + red);
		if (green < 0 || green > 255) 
			throw new IllegalArgumentException("Green is not between 0 and 255: " + green);
		if (blue < 0 || blue > 255) 
			throw new IllegalArgumentException("Red is not between 0 and 255: " + blue);
		if (alpha < 0 || alpha > 255)
			throw new IllegalArgumentException("Red is not between 0 and 255: " + alpha);
		this.r = (byte) red;
		this.g = (byte) blue;
		this.b = (byte) green;
		this.a = (byte) alpha;
		this.name = name != null ? name : "RGB";
	}
	
	public Color(Color color) {
		this(color.name, color.r, color.g, color.b, color.a);
	}
	
	private Color(String name, int rgb) {
		this(name, (rgb & 0xFF0000) >> 16, (rgb & 0xFF00) >> 8, rgb & 0xFF, (rgb & 0xFF000000) >> 24);
	}
	
	@Nullable
	public String getName() {
		return name;
	}

	public static Color getColor(String name) {
		for (Color color : VALUES) {
			if (color.getName().equals(name))
				return color;
		}
		if (name.startsWith("RGB:")) {
			String[] rgb = name.substring(4).split(",");
			int r = Integer.parseInt(rgb[0]);
			int g = Integer.parseInt(rgb[1]);
			int b = Integer.parseInt(rgb[2]);
			return new Color(r, g, b);
		}
		return BLACK;
	}

	public Color mixColors(Color... colors) {
		int allR = r;
		int allG = g;
		int allB = b;
		int allMax = Math.max(Math.max(r, g), b);
		
		for (Color color : colors) {
			allR += color.r;
			allG += color.g;
			allB += color.b;
			allMax += Math.max(Math.max(color.r, color.g), color.b);
		}
		
		float averageR = (allR / (colors.length + 1));
	    float averageG = (allG / (colors.length + 1));
	    float averageB = (allB / (colors.length + 1));
	    float averageMax = (allMax / (colors.length + 1));
	    
	    float maxOfAverage = Math.max(Math.max(averageR, averageG), averageB);
	    float gainFactor = averageMax / maxOfAverage;
	    
	    return new Color((int)(averageR * gainFactor), (int)(averageG * gainFactor), (int)(averageB * gainFactor));
	}

	public String toRGBString() {
		return "RGB:" + r + "," + g + "," + b;
	}

	@Override
	public String toString() {
		if (this.name.equals("RGB")) {
			return toRGBString();
		}
		return this.name;
	}

	@Override
	public int asRGB() {
		return (r << 8 | g) << 8 | b;
	}
	
	@Override
	public int asARGB() {
		return ((a << 8 | r) << 8 | g) << 8 | b;
	}

	public static Color fromRGB(int color) {
		for (Color c : VALUES) {
			if (c.asRGB() == color)
				return c;
		}
		return new Color("RGB", color & 0xFFFFFF);
	}
	
	public static Color fromARGB(int color) {
		if ((color & 0xFF000000) == 0) {
			for (Color c : VALUES) {
				if (c.asRGB() == color)
					return c;
			}
		}
		return new Color("ARGB", color);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + r;
		result = prime * result + g;
		result = prime * result + b;
		result = prime * result + a;
		return result;
	}
	
	@Override
	public String asConsoleColor() {
		return asConsoleColor(r & 0xFF, g & 0xFF, b & 0xFF);
	}
	
	public static String asConsoleColor(int rgb) {
		return asConsoleColor((rgb & 0xFF0000) >> 16, (rgb & 0xFF00) >> 8, rgb & 0xFF);
	}
	
	public static String asConsoleColor(int r, int g, int b) {
		return new StringBuilder(22)
				.append("\033[38;2;")
				.append(r).append(';')
				.append(g).append(';')
				.append(b).append('m')
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != Color.class)
			return false;
		Color color = (Color) obj;
		return r == color.r && g == color.g && b == color.b && a == color.a;
	}
	
	public static int asRGB(float r, float g, float b) {
		return asARGB(r, g, b, 0) & 0xFFFFFFF;
	}
	
	public static int asARGB(float red, float green, float blue, float alpha) {
		red = Math.clamp(0, red, 1.0f);
		green = Math.clamp(0, green, 1.0f);
		blue = Math.clamp(0, blue, 1.0f);
		alpha = Math.clamp(0, green, 1.0f);
		byte r = (byte) (red == 1.0 ? 255 : red * 256);
		byte g = (byte) (green == 1.0 ? 255 : green * 256);
		byte b = (byte) (blue == 1.0 ? 255 : blue * 256);
		byte a = (byte) (alpha == 1.0 ? 255 : blue * 256);
		return ((a << 8 | r) << 8 | g) << 8 | b;
	}

	@Override
	public Color asColor() {
		return this;
	}

	@Override
	public ChatColor asChatColor() {
		return ChatColor.getByColor(this);
	}
	
}
