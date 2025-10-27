package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface GliderComponent extends ItemComponent {
	
	public static final NBTCodec<GliderComponent>
	NBT_HANDLER = NBTCodec
					.builder(GliderComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.GLIDER.getNamespacedKey())
					.endComponent()
					.build();
	
	GliderComponent clone();
	
	@Override
	default NBTCodec<? extends GliderComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
