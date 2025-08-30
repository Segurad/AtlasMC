package de.atlasmc.inventory.component;

import de.atlasmc.block.tile.Banner.PatternType;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ProvidesBannerPatternsComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<ProvidesBannerPatternsComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ProvidesBannerPatternsComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.tagField(ComponentType.PROVIDES_BANNER_PATTERNS.getNamespacedKey(), ProvidesBannerPatternsComponent::getPatterns, ProvidesBannerPatternsComponent::setPatterns)
					.build();

	TagKey<PatternType> getPatterns();
	
	void setPatterns(TagKey<PatternType> tag);
	
	ProvidesBannerPatternsComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ProvidesBannerPatternsComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
