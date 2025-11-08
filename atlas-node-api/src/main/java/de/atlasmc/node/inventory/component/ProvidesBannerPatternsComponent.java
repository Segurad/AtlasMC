package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Banner.PatternType;
import de.atlasmc.tag.TagKey;

public interface ProvidesBannerPatternsComponent extends ItemComponent {
	
	public static final NBTCodec<ProvidesBannerPatternsComponent>
	NBT_HANDLER = NBTCodec
					.builder(ProvidesBannerPatternsComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.PROVIDES_BANNER_PATTERNS.getNamespacedKey(), ProvidesBannerPatternsComponent::getPatterns, ProvidesBannerPatternsComponent::setPatterns, TagKey.NBT_CODEC)
					.build();

	TagKey<PatternType> getPatterns();
	
	void setPatterns(TagKey<PatternType> tag);
	
	ProvidesBannerPatternsComponent clone();
	
	@Override
	default NBTCodec<? extends ProvidesBannerPatternsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
