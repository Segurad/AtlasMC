package de.atlasmc.node.inventory.component;

import de.atlasmc.chat.Filterable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface WritableBookContentComponent extends BookContentComponent<String> {
	
	public static final NBTCodec<WritableBookContentComponent>
	NBT_HANDLER = NBTCodec
					.builder(WritableBookContentComponent.class)
					.include(BookContentComponent.NBT_CODEC)
					.beginComponent(ComponentType.WRITABLE_BOOK_CONTENT.getNamespacedKey())
					.codecList("pages", WritableBookContentComponent::hasPages, WritableBookContentComponent::getPages, Filterable.filterableCodec(NBTCodecs.STRING))
					.endComponent()
					.build();

	@Override
	default NBTCodec<? extends WritableBookContentComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
