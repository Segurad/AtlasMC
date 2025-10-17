package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface WrittenBookContentComponent extends ItemComponent {
	
	public static final NBTCodec<WrittenBookContentComponent>
	NBT_HANDLER = NBTCodec
					.builder(WrittenBookContentComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.WRITTEN_BOOK_CONTENT.getNamespacedKey())
					.chatList("pages", WrittenBookContentComponent::hasPages, WrittenBookContentComponent::getPages, true)
					.beginComponent("title")
					.string("raw", WrittenBookContentComponent::getTitle, WrittenBookContentComponent::setTitle)
					.endComponent()
					.string("author", WrittenBookContentComponent::getAuthor, WrittenBookContentComponent::setAuthor)
					.enumIntField("generation", WrittenBookContentComponent::getGeneration, WrittenBookContentComponent::setGeneration, Generation.class, Generation.ORGINAL)
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
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum Generation implements IDHolder {
		
		ORGINAL,
		COPY_OF_ORGINAL,
		COPY_OF_COPY,
		TATTERED;

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
