package de.atlasmc.node.entity.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class MinecartCommandBlockMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<MinecartCommandBlockMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(MinecartCommandBlockMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Command", MinecartCommandBlockMetaComponent::getCommand, MinecartCommandBlockMetaComponent::setCommand, NBTCodecs.STRING)
					.codec("LastOutput", MinecartCommandBlockMetaComponent::getLastMessage, MinecartCommandBlockMetaComponent::setLastMessage, Chat.NBT_CODEC)
					.build();
	
	protected static final MetaDataField<String>
	META_COMMAND = new MetaDataField<>(13, null, EntityMetaTypes.STRING);
	protected static final MetaDataField<Chat>
	META_LAST_OUTPUT = new MetaDataField<>(14, ChatUtil.EMPTY, EntityMetaTypes.CHAT);
	
	public MinecartCommandBlockMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_COMMAND);
		container.set(META_LAST_OUTPUT);
	}

	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public String getCommand() {
		return getHolder().getMetaContainer().getData(META_COMMAND);
	}

	public Chat getLastMessage() {
		return getHolder().getMetaContainer().getData(META_LAST_OUTPUT);
	}

	public void setCommand(String command) {
		getHolder().getMetaContainer().setData(META_COMMAND, command);
	}

	/**
	 * While {@link #sendMessage(Chat)} does only set the last message if {@link #isTrackingOutput()} is true,
	 * this method will always set the last output
	 * @param message
	 */
	public void setLastMessage(Chat message) {
		getHolder().getMetaContainer().setData(META_LAST_OUTPUT, message != null ? message : ChatUtil.EMPTY);
	}
	
	@Override
	public NBTCodec<? extends MinecartCommandBlockMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
