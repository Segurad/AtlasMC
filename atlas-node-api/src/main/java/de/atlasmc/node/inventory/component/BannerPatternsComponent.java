package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.block.tile.Banner.Pattern;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BannerPatternsComponent extends ItemComponent {
	
	public static final NBTCodec<BannerPatternsComponent>
	NBT_HANDLER = NBTCodec
					.builder(BannerPatternsComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeList(ComponentType.BANNER_PATTERNS.getNamespacedKey(), BannerPatternsComponent::hasPatterns, BannerPatternsComponent::getPatterns, Pattern.NBT_HANDLER)
					.build();
	
	BannerPatternsComponent clone();
	
	void addPattern(Pattern pattern);
	
	Pattern getPattern(int index);
	
	List<Pattern> getPatterns();
	
	boolean hasPatterns();
	
	int numberOfPatterns();
	
	Pattern removePattern(int index);
	
	void setPattern(int index, Pattern pattern);
	
	void setPatterns(List<Pattern> pattern);
	
	@Override
	default NBTCodec<? extends BannerPatternsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
