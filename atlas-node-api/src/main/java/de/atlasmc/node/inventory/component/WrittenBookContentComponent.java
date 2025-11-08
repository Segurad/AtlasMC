package de.atlasmc.node.inventory.component;

import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.Filterable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.enums.EnumUtil;

public interface WrittenBookContentComponent extends BookContentComponent<Chat> {
	
	public static final NBTCodec<WrittenBookContentComponent>
	NBT_HANDLER = NBTCodec
					.builder(WrittenBookContentComponent.class)
					.include(BookContentComponent.NBT_CODEC)
					.beginComponent(ComponentType.WRITTEN_BOOK_CONTENT.getNamespacedKey())
					.codecList("pages", WrittenBookContentComponent::hasPages, WrittenBookContentComponent::getPages, Filterable.filterableCodec(Chat.NBT_CODEC))
					.beginComponent("title")
					.codec("raw", WrittenBookContentComponent::getTitle, WrittenBookContentComponent::setTitle, NBTCodecs.STRING)
					.endComponent()
					.codec("author", WrittenBookContentComponent::getAuthor, WrittenBookContentComponent::setAuthor, NBTCodecs.STRING)
					.codec("generation", WrittenBookContentComponent::getGeneration, WrittenBookContentComponent::setGeneration, EnumUtil.enumIntNBTCodec(Generation.class), Generation.ORGINAL)
					.boolField("resolved", WrittenBookContentComponent::isResolved, WrittenBookContentComponent::setResolved, false)
					.endComponent()
					.build();
	
	@Nullable
	String getTitle();
	
	void setTitle(String title);
	
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
