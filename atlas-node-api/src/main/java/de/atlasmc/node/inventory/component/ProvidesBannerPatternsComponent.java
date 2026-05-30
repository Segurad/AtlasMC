package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Banner.PatternType;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.annotation.NotNull;

public interface ProvidesBannerPatternsComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ProvidesBannerPatternsComponent>
	NBT_CODEC = NBTCodec
					.builder(ProvidesBannerPatternsComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.PROVIDES_BANNER_PATTERNS.getNamespacedKey(), ProvidesBannerPatternsComponent::getPatterns, ProvidesBannerPatternsComponent::setPatterns, TagKey.NBT_CODEC)
					.build();

	TagKey<PatternType> getPatterns();
	
	void setPatterns(TagKey<PatternType> tag);
	
	@Override
	ProvidesBannerPatternsComponent clone();
	
	@Override
	default NBTCodec<? extends ProvidesBannerPatternsComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
