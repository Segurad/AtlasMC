package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface GliderComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<GliderComponent>
	NBT_CODEC = NBTCodec
					.builder(GliderComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.GLIDER.getNamespacedKey())
					.endComponent()
					.build();
	
	@Override
	GliderComponent clone();
	
	@Override
	default NBTCodec<? extends GliderComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
