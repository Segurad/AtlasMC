package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartCommandBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreMinecartCommandBlock extends CoreAbstractMinecart implements MinecartCommandBlock {

	protected static final MetaDataField<String>
	META_COMMAND = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+1, null, MetaDataType.STRING);
	protected static final MetaDataField<Chat>
	META_LAST_OUTPUT = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+2, ChatUtil.EMPTY, MetaDataType.CHAT);
	
	protected static final int LAST_META_INDEX = CoreAbstractMinecart.LAST_META_INDEX+2;
	
	protected static final String
	NBT_COMMAND = "Command",
	NBT_LAST_OUTPUT = "LastOutput",
	NBT_SUCCESS_COUNT = "SuccessCount",
	NBT_TRACK_OUTPUT = "TrackOutput";
	
	static {
		NBT_FIELDS.setField(NBT_COMMAND, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setCommand(reader.readStringTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LAST_OUTPUT, (holder, reader) -> {
			if (holder instanceof MinecartCommandBlock) {
				((MinecartCommandBlock) holder).setLastOutput(ChatUtil.toChat(reader.readStringTag()));
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
	
	public CoreMinecartCommandBlock(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
	public Chat getLastOutput() {
		return metaContainer.getData(META_LAST_OUTPUT);
	}

	@Override
	public void setCommand(String command) {
		metaContainer.get(META_COMMAND).setData(command);
	}

	@Override
	public void setLastOutput(Chat out) {
		if (out == null)
			out = ChatUtil.EMPTY;
		metaContainer.get(META_LAST_OUTPUT).setData(out);
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
		writer.writeStringTag(NBT_LAST_OUTPUT, getLastOutput().getText());
		writer.writeIntTag(NBT_SUCCESS_COUNT, getRedstoneSignal());
		writer.writeByteTag(NBT_TRACK_OUTPUT, isTrackingOutput());
	}

}
