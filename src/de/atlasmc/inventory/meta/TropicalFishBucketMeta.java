package de.atlasmc.inventory.meta;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.TropicalFish;

public interface TropicalFishBucketMeta extends ItemMeta {
	
	public TropicalFishBucketMeta clone();
	public DyeColor getBodyColor();
	public TropicalFish.Pattern getPattern();
	public DyeColor getPatternColor();
	public boolean hasVariant();
	public void setBodyColor(DyeColor color);
	public void setPattern(TropicalFish.Pattern pattern);
	public void setPatternColor(DyeColor color);

}
