package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.Banner.Pattern;

public interface BannerPatternsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:banner_pattern");
	
	BannerPatternsComponent clone();
	
	void addPattern(Pattern pattern);
	
	DyeColor getBaseColor();
	
	Pattern getPattern(int index);
	
	List<Pattern> getPatterns();
	
	int numberOfPatterns();
	
	Pattern removePattern(int index);
	
	void setPattern(int index, Pattern pattern);
	
	void setPatterns(List<Pattern> pattern);

}
