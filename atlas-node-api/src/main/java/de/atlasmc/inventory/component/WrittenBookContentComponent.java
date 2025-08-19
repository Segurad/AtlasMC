package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface WrittenBookContentComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<WrittenBookContentComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(WrittenBookContentComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.WRITTEN_BOOK_CONTENT)
					.chatList("pages", WrittenBookContentComponent::hasPages, WrittenBookContentComponent::getPages, true)
					.beginComponent("title")
					.string("raw", WrittenBookContentComponent::getTitle, WrittenBookContentComponent::setTitle)
					.endComponent()
					.string("author", WrittenBookContentComponent::getAuthor, WrittenBookContentComponent::setAuthor)
					.enumIntField("generation", WrittenBookContentComponent::getGeneration, WrittenBookContentComponent::setGeneration, Generation::getByID, Generation.ORGINAL)
					.boolField("resolved", WrittenBookContentComponent::isResolved, WrittenBookContentComponent::setResolved, false)
					.endComponent()
					.build();
	
	@Nullable
	String getTitle();
	
	void setTitle(String title);
	
	@NotNull
	List<Chat> getPages();
	
	boolean hasPages();
	
	default boolean addPage(@NotNull Chat page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		return getPages().add(page);
	}
	
	default boolean removePage(@NotNull Chat page) {
		if (page == null)
			throw new IllegalArgumentException("Page can not be null!");
		if (hasPages())
			return getPages().remove(page);
		return false;
	}
	
	@Nullable
	String getAuthor();
	
	void setAuthor(String author);
	
	@NotNull
	Generation getGeneration();
	
	void setGeneration(@NotNull Generation generation);
	
	boolean isResolved();
	
	void setResolved(boolean resolved);
	
	WrittenBookContentComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Generation implements EnumID, EnumValueCache {
		
		ORGINAL,
		COPY_OF_ORGINAL,
		COPY_OF_COPY,
		TATTERED;

		private static List<Generation> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Generation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Generation> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
