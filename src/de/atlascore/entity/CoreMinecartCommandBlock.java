package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartCommandBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMinecartCommandBlock extends CoreAbstractMinecart implements MinecartCommandBlock {

	protected static final MetaDataField<String>
	META_COMMAND = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+1, null, MetaDataType.STRING);
	protected static final MetaDataField<Chat>
	META_LAST_OUTPUT = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+2, ChatUtil.EMPTY, MetaDataType.CHAT);
	
	protected static final int LAST_META_INDEX = CoreAbstractMinecart.LAST_META_INDEX+2;
	
	protected static final CharKey
	NBT_COMMAND = CharKey.of("Command"),
	NBT_LAST_OUTPUT = CharKey.of("LastOutput"),
	NBT_SUCCESS_COUNT = CharKey.of("SuccessCount"),
	NBT_TRACK_OUTPUT = CharKey.of("TrackOutput");
	
	static {
		NBT_FIELDS.setField(NBT_COMMAND, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setCommand(reader.readStringTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LAST_OUTPUT, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setLastMessage(ChatUtil.toChat(reader.readStringTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_SUCCESS_COUNT, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setRedstoneSignal(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_TRACK_OUTPUT, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setTrackOutput(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	protected byte redstoneSignal;
	protected boolean trackOutput;
	
	public CoreMinecartCommandBlock(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_COMMAND);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public String getCommand() {
		return metaContainer.getData(META_COMMAND);
	}

	@Override
	public Chat getLastMessage() {
		return metaContainer.getData(META_LAST_OUTPUT);
	}

	@Override
	public void setCommand(String command) {
		metaContainer.get(META_COMMAND).setData(command);
	}

	@Override
	public void setLastMessage(Chat message) {
		if (message == null)
			message = ChatUtil.EMPTY;
		metaContainer.get(META_LAST_OUTPUT).setData(message);
	}

	@Override
	public void setRedstoneSignal(int signal) {
		this.redstoneSignal = (byte) signal;
	}

	@Override
	public int getRedstoneSignal() {
		return redstoneSignal;
	}

	@Override
	public void setTrackOutput(boolean track) {
		this.trackOutput = track;
	}

	@Override
	public boolean isTrackingOutput() {
		return trackOutput;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_COMMAND, getCommand());
		writer.writeStringTag(NBT_LAST_OUTPUT, getLastMessage().getText());
		writer.writeIntTag(NBT_SUCCESS_COUNT, getRedstoneSignal());
		writer.writeByteTag(NBT_TRACK_OUTPUT, isTrackingOutput());
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		// TODO command block minecart Permission
		return null;
	}

	@Override
	public void sendMessage(String message) {
		sendMessage(ChatUtil.toChat(message), ChatType.SYSTEM);
	}

	@Override
	public void sendMessage(Chat chat, ChatType type) {
		if (chat == null)
			chat = ChatUtil.EMPTY;
		if (isTrackingOutput())
			metaContainer.set(META_LAST_OUTPUT, chat);
	}

}
