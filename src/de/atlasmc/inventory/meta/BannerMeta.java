package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.block.tile.Banner.Pattern;

public interface BannerMeta extends TileEntityMeta {
	
	public BannerMeta clone();
	public Banner getTileEntity();
	public void addPattern(Pattern pattern);
	public DyeColor getBaseColor();
	public Pattern getPattern(int index);
	public List<Pattern> getPatterns();
	public int numberOfPatterns();
	public Pattern removePattern(int index);
	public void setPattern(int index, Pattern pattern);
	public void setPatterns(List<Pattern> pattern);

}
