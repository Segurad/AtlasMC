package de.atlasmc.block.tile;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.util.Validate;

public interface Banner extends TileEntity {
	
	public void addPattern(Pattern pattern);
	
	public DyeColor getBaseColor();
	
	public void setBaseColor(DyeColor color);
	
	public Pattern getPattern(int index);
	
	public List<Pattern> getPatterns();
	
	public int numberOfPatterns();
	
	public Pattern removePattern(int index);
	
	public void setPattern(int index, Pattern pattern);
	
	public void setPatterns(List<Pattern> pattern);
	
	public static enum Pattern {
		BOTTOM_STRIPE("bs"),
		TOP_STRIPE("ts"),
		LEFT_STRIPE("ls"),
		RIGHT_STRIPE("rs"),
		CENTER_STRIPE("cs"),
		MIDDLE_STRIPE("ms"),
		DOWN_RIGHT_STRIPE("drs"),
		DOWN_LEFT_STRIPE("dls"),
		SMALL_STRIPES("ss"),
		CROSS("cr"),
		SQUARE_CROSS("sc"),
		LEFT_OF_DIAGONAL("ld"),
		RIGHT_OF_UPSIDEDOWN_DIAGONAL("rud"),
		LEFT_OF_UPSIDEDOWN_DIAGONAL("lud"),
		RIGHT_OF_DIAGONAL("rd"),
		VERTICAL_HALF("vh"),
		VERTICAL_HALF_RIGHT("vhr"),
		HORIZONTAL_HALF("hh"),
		HORIZONTAL_HALF_BOTTOM("hhb"),
		BOTTOM_LEFT("bl"),
		BOTTOM_RIGHT("br"),
		TOP_LEFT("tl"),
		TOP_RIGHT("tr"),
		BOTTOM_TRIANGEL("bt"),
		TOP_TRIANGLE("tt"),
		BOTTOM_TRIANGLE_SAWTOOTH("bts"),
		TOP_TRIANGLE_SAWTOOTH("tts"),
		MIDDLE_CIRCLE("mc"),
		MIDDLE_RHOMBUS("mr"),
		BORDER("bo"),
		CURLY_BORDER("cbo"),
		BRICK("bri"),
		GRADIENT("gra"),
		GRADIENT_UPSIDE_DOWN("gru"),
		CREEPER("cre"),
		SKULL("sku"),
		FLOWER("flo"),
		MOJANG("moj");
		
		private final String identifier;
		
		private Pattern(String identifier) {
			this.identifier = identifier;
		}
		
		public String getIdentifier() {
			return identifier;
		}
		
		public static Pattern getByIdentifier(String identifier) {
			Validate.notNull(identifier, "Identifier can not be null!");
			for (Pattern pattern : values()) {
				if (pattern.getIdentifier().equals(identifier)) return pattern;
			}
			return null;
		}
	}

}
