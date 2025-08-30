package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface WritableBookContentComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<WritableBookContentComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(WritableBookContentComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.WRITABLE_BOOK_CONTENT.getNamespacedKey())
					.stringListField("pages", WritableBookContentComponent::hasPages, WritableBookContentComponent::getPages)
					.endComponent()
					.build();
	
	@NotNull
	List<String> getPages();
	
	boolean hasPages();
	
	default boolean addPage(@NotNull String page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		return getPages().add(page);
	}
	
	default boolean removePage(@NotNull String page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		if (hasPages())
			return getPages().remove(page);
		return false;
	}
	
	WritableBookContentComponent clone();

	@Override
	default NBTSerializationHandler<? extends WritableBookContentComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
