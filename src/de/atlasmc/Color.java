package de.atlasmc;

import java.util.List;
import de.atlasmc.util.MathUtil;

public final class Color {
	
	public static final String RGB = "RGB";
	
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

	private final byte r,g,b;
	private final String name;
	
	public Color(int red, int green, int blue) {
		this("RGB", red, green, blue);
	}
	
	public  Color(String name, int red, int green, int blue) {
		if (red < 0 || red > 255) throw new IllegalArgumentException("Red is not between 0 and 255: " + red);
		if (green < 0 || green > 255) throw new IllegalArgumentException("Green is not between 0 and 255: " + green);
		if (blue < 0 || blue > 255) throw new IllegalArgumentException("Red is not between 0 and 255: " + blue);
		this.r = (byte) red;
		this.b = (byte) blue;
		this.g = (byte) green;
		this.name = name != null ? name : RGB;
	}
	
	public Color(Color color) {
		this(color.name, color.r, color.g, color.b);
	}
	
	public Color(int rgb) {
		this((rgb & 0xFF0000) >> 16, (rgb & 0xFF00) >> 8, rgb & 0xFF);
	}

	public Color(float red, float green, float blue) {
		red = MathUtil.getInRange(0, red, 1.0f);
		green = MathUtil.getInRange(0, green, 1.0f);
		blue = MathUtil.getInRange(0, blue, 1.0f);
		r = (byte) (red == 1.0 ? 255 : red *256);
		g = (byte) (green == 1.0 ? 255 : green *256);
		b = (byte) (blue == 1.0 ? 255 : blue *256);
		this.name = RGB;
	}

	public byte getBlue() {
		return b;
	}
	
	public byte getGreen() {
		return g;
	}
	
	public byte getRed() {
		return r;
	}
	
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

	public String toString() {
		if (this.name.equals("RGB")) {
			return toRGBString();
		}
		return this.name;
	}

	public int asRGB() {
		return (r << 8 + g) << 8 + b;
	}

	public static Color fromRGB(int color) {
		for (Color c : VALUES) {
			if (c.asRGB() == color)
				return c;
		}
		return new Color(color);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
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
		if (r != color.r || g != color.g || b != color.b)
			return false;
		return true;
	}
	
}
