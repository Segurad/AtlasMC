package de.atlasmc.node.inventory.component;

import java.util.Collection;
import java.util.List;
import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface LoreComponent extends ItemComponent {
	
	public static final NBTCodec<LoreComponent>
	NBT_HANDLER = NBTCodec
					.builder(LoreComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecCollection(ComponentType.LORE.getNamespacedKey(), LoreComponent::hasLore, LoreComponent::getLore, Chat.CHAT_LIST_NBT_CODEC)
					.build();
	
	@NotNull
	List<Chat> getLore();
	
	boolean hasLore();
	
	default boolean addLore(@NotNull Chat chat) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		return getLore().add(chat);
	}
	
	default boolean removeLore(@NotNull Chat chat) {
		if (chat == null)
			throw new IllegalArgumentException("Chat can not be null!");
		if (hasLore())
			return getLore().remove(chat);
		return false;
	}
	
	LoreComponent clone();

	default void setLore(@NotNull Collection<Chat> lore) {
		if (lore == null)
			throw new IllegalArgumentException("Lore can not be null!");
		List<Chat> list = getLore();
		list.clear();
		list.addAll(lore);
	}
	
	@Override
	default NBTCodec<? extends LoreComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
