package de.atlascore.inventory.meta;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.block.tile.Banner.Pattern;
import de.atlasmc.inventory.meta.BannerMeta;

public class CoreBannerMeta extends CoreTileEntityMeta implements BannerMeta {
	
	public CoreBannerMeta(Material material) {
		super(material);
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		if (!Banner.class.isInstance(tile)) throw new IllegalArgumentException("Tile is not instance of Banner");
		super.setTileEntity(tile);
	}
	
	@Override
	public Banner getTileEntity() {
		return (Banner) super.getTileEntity();
	}

	@Override
	public void addPattern(Pattern pattern) {
		getTileEntity().addPattern(pattern);
	}

	@Override
	public DyeColor getBaseColor() {
		return getTileEntity().getBaseColor();
	}

	@Override
	public Pattern getPattern(int index) {
		return getTileEntity().getPattern(index);
	}

	@Override
	public List<Pattern> getPatterns() {
		return getTileEntity().getPatterns();
	}

	@Override
	public int numberOfPatterns() {
		return getTileEntity().numberOfPatterns();
	}

	@Override
	public Pattern removePattern(int index) {
		return getTileEntity().removePattern(index);
	}

	@Override
	public void setPattern(int index, Pattern pattern) {
		getTileEntity().setPattern(index, pattern);
	}

	@Override
	public void setPatterns(List<Pattern> pattern) {
		getTileEntity().setPatterns(pattern);
	}
	
	@Override
	public CoreBannerMeta clone() {
		return (CoreBannerMeta) super.clone();
	}

}
