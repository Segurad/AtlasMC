package de.atlascore.block.tile;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.chat.Chat;

public class CoreBanner extends CoreTileEntity implements Banner {
	
	private List<Pattern> patterns;
	private Chat name;
	
	public CoreBanner(BlockType type) {
		super(type);
	}

	@Override
	public void addPattern(Pattern pattern) {
		if (patterns == null) patterns = new ArrayList<>();
		patterns.add(pattern);
	}

	@Override
	public DyeColor getBaseColor() {
		return DyeColor.getByBanner(getType());
	}

	@Override
	public void setBaseColor(DyeColor color, boolean wall) {
		if (wall) 
			setType(color.getWallBanner());
		else 
			setType(color.getBanner());
	}

	@Override
	public Pattern getPattern(int index) {
		if (patterns == null) 
			return null;
		return patterns.get(index);
	}

	@Override
	public List<Pattern> getPatterns() {
		return patterns;
	}

	@Override
	public int numberOfPatterns() {
		if (patterns == null) 
			return 0;
		return patterns.size();
	}

	@Override
	public Pattern removePattern(int index) {
		if (patterns == null) 
			return null;
		return patterns.remove(index);
	}

	@Override
	public void setPattern(int index, Pattern pattern) {
		if (patterns == null) 
			patterns = new ArrayList<>();
		if (patterns.size() > index) {
			patterns.set(index, pattern);
		} else 
			patterns.add(index, pattern);
	}

	@Override
	public void setPatterns(List<Pattern> pattern) {
		if (patterns == null) 
			patterns = new ArrayList<>();
		else 
			patterns.clear();
		patterns.addAll(pattern);
	}
	
	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setCustomName(Chat chat) {
		this.name = chat;
	}
	
	@Override
	public boolean hasCustomName() {
		return name != null;
	}

	@Override
	public boolean hasPatterns() {
		return patterns != null && !patterns.isEmpty();
	}

}
