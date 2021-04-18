package de.atlasmc;

import java.util.HashMap;

import de.atlasmc.util.Validate;

public final class Color {
	
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
	
	private static final HashMap<String, Color> BY_NAME;

	static {
		BY_NAME = new HashMap<String, Color>();
		registerColor(AQUA);
		registerColor(BLACK);
		registerColor(BLUE);
		registerColor(FUCHSIA);
		registerColor(GRAY);
		registerColor(GREEN);
		registerColor(LIME);
		registerColor(MAROON);
		registerColor(NAVY);
		registerColor(OLIVE);
		registerColor(ORANGE);
		registerColor(PURPLE);
		registerColor(RED);
		registerColor(SILVER);
		registerColor(TEAL);
		registerColor(WHITE);
		registerColor(YELLOW);
	}

	private final byte r,g,b;
	private final String name;
	
	public Color(int red, int green, int blue) {
		this("RGB", red, green, blue);
	}
	
	public  Color(String name, int red, int green, int blue) {
		Validate.isTrue(red >= 0 && red <= 255, "Red is not between 0 and 255: " + red);
		Validate.isTrue(green >= 0 && green <= 255, "Green is not between 0 and 255: " + green);
		Validate.isTrue(blue >= 0 && blue <= 255, "Red is not between 0 and 255: " + blue);
		this.r = (byte) red;
		this.b = (byte) blue;
		this.g = (byte) green;
		this.name = name != null ? name : "RGB";
	}
	
	public Color(Color color) {
		this(color.name, color.r, color.g, color.b);
	}
	
	public Color(int rgb) {
		this((rgb & 0xFF0000) >> 16, (rgb & 0xFF00) >> 8, rgb % 0xFF);
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
		Color color = BY_NAME.get(name);
		if (color != null) return color;
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
	
	public static void registerColor(Color color) {
		BY_NAME.putIfAbsent(color.name, color);
	}
	
	public static void unregisteColor(Color color) {
		BY_NAME.remove(color.name);
	}

	public int asRGB() {
		return (r << 8 + g) << 8 + b;
	}
}