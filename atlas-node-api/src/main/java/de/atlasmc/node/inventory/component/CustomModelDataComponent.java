package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamCodecs;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntList;

public interface CustomModelDataComponent extends ItemComponent {
	
	public static final NBTCodec<CustomModelDataComponent>
	NBT_CODEC = NBTCodec
					.builder(CustomModelDataComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.CUSTOM_MODEL_DATA.getNamespacedKey())
					.codecCollection("floats", CustomModelDataComponent::hasFloats, CustomModelDataComponent::getFloats, NBTCodecs.FLOAT_LIST)
					.codecCollection("flags", CustomModelDataComponent::hasFlags, CustomModelDataComponent::getFlags, NBTCodecs.BOOLEAN_LIST)
					.codecList("strings", CustomModelDataComponent::hasStrings, CustomModelDataComponent::getStrings, NBTCodecs.STRING)
					.codecCollection("colors", CustomModelDataComponent::hasColors, CustomModelDataComponent::getColors, NBTCodecs.INT_COLLECTION)
					.endComponent()
					.build();
	
	public static final StreamCodec<CustomModelDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(CustomModelDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codecCollection(CustomModelDataComponent::hasFloats, CustomModelDataComponent::getFloats, StreamCodecs.FLOAT_LIST)
					.codecCollection(CustomModelDataComponent::hasFlags, CustomModelDataComponent::getFlags, StreamCodecs.BOOLEAN_LIST)
					.stringList(CustomModelDataComponent::hasStrings, CustomModelDataComponent::getStrings)
					.codecCollection(CustomModelDataComponent::hasColors, CustomModelDataComponent::getColors, StreamCodecs.INT_COLLECTION)
					.build();
	
	FloatList getFloats();
	
	void setFloats(FloatList floats);
	
	boolean hasFloats();
	
	BooleanList getFlags();
	
	void setFlags(BooleanList flags);
	
	boolean hasFlags();
	
	List<String> getStrings();
	
	void setStrings(List<String> strings);
	
	boolean hasStrings();
	
	IntList getColors();
	
	void setColors(IntList colors);
	
	boolean hasColors();
	
	CustomModelDataComponent clone();
	
	@Override
	default NBTCodec<? extends CustomModelDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends CustomModelDataComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
