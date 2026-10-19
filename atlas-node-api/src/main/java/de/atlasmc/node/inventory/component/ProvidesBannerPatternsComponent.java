package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Banner.PatternType;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.annotation.NotNull;

public interface ProvidesBannerPatternsComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ProvidesBannerPatternsComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(ProvidesBannerPatternsComponent.class, TagKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(ProvidesBannerPatternsComponent::getPatterns)
				.setter(ProvidesBannerPatternsComponent::setPatterns)
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
