package de.atlasmc.inventory.meta;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.TropicalFish.Pattern;

public interface TropicalFishBucketMeta extends ItemMeta {
	
	public TropicalFishBucketMeta clone();
	public DyeColor getBodyColor();
	public Pattern getPattern();
	public DyeColor getPatternColor();
	public void setBodyColor(DyeColor color);
	public void setPattern(Pattern pattern);
	public void setPatternColor(DyeColor color);

}
