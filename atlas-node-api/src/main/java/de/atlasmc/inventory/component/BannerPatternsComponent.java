package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.Banner.Pattern;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BannerPatternsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:banner_patterns");
	
	public static final NBTSerializationHandler<BannerPatternsComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BannerPatternsComponent.class)
					.typeList(COMPONENT_KEY, BannerPatternsComponent::hasPatterns, BannerPatternsComponent::getPatterns, Pattern.NBT_HANDLER)
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
	default NBTSerializationHandler<? extends BannerPatternsComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
