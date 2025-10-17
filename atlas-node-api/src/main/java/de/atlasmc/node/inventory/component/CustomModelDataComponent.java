package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.util.nbt.codec.NBTCodec;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntList;

public interface CustomModelDataComponent extends ItemComponent {
	
	public static final NBTCodec<CustomModelDataComponent>
	NBT_HANDLER = NBTCodec
					.builder(CustomModelDataComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.CUSTOM_MODEL_DATA.getNamespacedKey())
					.floatListField("floats", CustomModelDataComponent::hasFloats, CustomModelDataComponent::getFloats)
					.booleanListField("flags", CustomModelDataComponent::hasFlags, CustomModelDataComponent::getFlags)
					.stringListField("strings", CustomModelDataComponent::hasStrings, CustomModelDataComponent::getStrings)
					.intListField("colors", CustomModelDataComponent::hasColors, CustomModelDataComponent::getColors)
					.endComponent()
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

}
