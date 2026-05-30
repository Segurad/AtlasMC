package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Filterable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface WritableBookContentComponent extends BookContentComponent<String> {
	
	@NotNull
	public static final NBTCodec<WritableBookContentComponent>
	NBT_CODEC = NBTCodec
					.builder(WritableBookContentComponent.class)
					.include(BookContentComponent.NBT_CODEC)
					.beginComponent(ComponentType.WRITABLE_BOOK_CONTENT.getNamespacedKey())
					.codecList("pages", WritableBookContentComponent::hasPages, WritableBookContentComponent::getPages, Filterable.filterableCodec(NBTCodecs.STRING))
					.endComponent()
					.build();

	@Override
	default NBTCodec<? extends WritableBookContentComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
