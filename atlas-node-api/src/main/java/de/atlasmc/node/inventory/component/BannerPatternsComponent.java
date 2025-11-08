package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Banner.Pattern;

public interface BannerPatternsComponent extends ItemComponent {
	
	public static final NBTCodec<BannerPatternsComponent>
	NBT_CODEC = NBTCodec
					.builder(BannerPatternsComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecList(ComponentType.BANNER_PATTERNS.getNamespacedKey(), BannerPatternsComponent::hasPatterns, BannerPatternsComponent::getPatterns, Pattern.NBT_CODEC)
					.build();
	
	public static final StreamCodec<BannerPatternsComponent>
	STREAM_CODEC = StreamCodec
					.builder(BannerPatternsComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.listCodec(BannerPatternsComponent::hasPatterns, BannerPatternsComponent::getPatterns, Pattern.STREAM_CODEC)
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
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BannerPatternsComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
