package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Banner.Pattern;
import de.atlasmc.util.annotation.NotNull;

public interface BannerPatternsComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BannerPatternsComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(BannerPatternsComponent.class, Pattern.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.hasData(BannerPatternsComponent::hasPatterns) 
				.getter(BannerPatternsComponent::getPatterns)
				.build();
	
	@NotNull
	public static final StreamCodec<BannerPatternsComponent>
	STREAM_CODEC = StreamCodec
					.builder(BannerPatternsComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codecList(BannerPatternsComponent::hasPatterns, BannerPatternsComponent::getPatterns, Pattern.STREAM_CODEC)
					.build();
	
	@Override
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
