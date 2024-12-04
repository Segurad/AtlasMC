package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.CommandBlock;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCommandBlock extends CoreTileEntity implements CommandBlock {

	protected static final NBTFieldContainer<CoreCommandBlock> NBT_FIELDS;
	
	protected static final CharKey
	NBT_AUTO = CharKey.literal("auto"),
	NBT_COMMAND = CharKey.literal("conditionMet"),
	NBT_CONDITION_MET = CharKey.literal("LastExecution"),
	NBT_LAST_EXECUTION = CharKey.literal("LastExecution"),
	NBT_LAST_OUTPUT = CharKey.literal("LastOutput"),
	NBT_POWERED = CharKey.literal("powered"),
	NBT_SUCCESSCOUNT = CharKey.literal("SuccessCount"),
	NBT_TRACKOUTPUT = CharKey.literal("TrackOutput"),
	NBT_UPDATE_LAST_EXECUTION = CharKey.literal("UpdateLastExecution");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_AUTO, (holder, reader) -> {
			holder.setAlwaysActive(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_COMMAND, (holder, reader) -> {
			holder.setCommand(reader.readStringTag());
		});
		NBT_FIELDS.setField(NBT_CONDITION_MET, (holder, reader) -> {
			holder.setConditional(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_LAST_EXECUTION, NBTField.skip()); // TODO Wait for CommandBlock logic
		NBT_FIELDS.setField(NBT_LAST_OUTPUT, (holder, reader) -> {
			holder.setLastMessage(ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_POWERED, (holder, reader) -> {
			holder.setPowered(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SUCCESSCOUNT, (holder, reader) -> {
			holder.setSuccessCount(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_TRACKOUTPUT, (holder, reader) -> {
			holder.setTrackOutput(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_UPDATE_LAST_EXECUTION, NBTField.skip()); // TODO see NBT_LAST_EXECUTION ^
	}
	
	private Mode mode;
	private boolean conditional;
	private boolean alwaysActive;
	private boolean trackOutput;
	private boolean powered;
	private Chat name;
	private Chat lastoutput;
	private String command;
	private int successCount;
	
	public CoreCommandBlock(Material type) {
		super(type);
		mode = Mode.AUTO;
		name = ChatUtil.toChat("@");
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public boolean isConditional() {
		return conditional;
	}

	@Override
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}

	@Override
	public boolean isAlwaysActive() {
		return alwaysActive;
	}

	@Override
	public void setAlwaysActive(boolean alwaysActive) {
		this.alwaysActive = alwaysActive;
	}

	@Override
	public boolean getTrackOutput() {
		return trackOutput;
	}

	@Override
	public void setTrackOutput(boolean trackOutput) {
		this.trackOutput = trackOutput;
	}

	@Override
	public void setCustomName(Chat name) {
		this.name = name;
	}

	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public Chat getLastMessage() {
		return lastoutput;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setSuccessCount(int count) {
		this.successCount = count;
	}

	@Override
	public int getSuccessCount() {
		return successCount;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (systemData) {
			writer.writeStringTag(NBT_CUSTOM_NAME, getCustomName().toJsonText());
			writer.writeByteTag(NBT_AUTO, isAlwaysActive());
			writer.writeStringTag(NBT_COMMAND, getCommand());
			writer.writeLongTag(NBT_LAST_EXECUTION, 0);
			writer.writeStringTag(NBT_LAST_OUTPUT, getLastMessage().toJsonText());
			writer.writeByteTag(NBT_POWERED, isPowered());
			writer.writeIntTag(NBT_SUCCESSCOUNT, getSuccessCount());
			writer.writeByteTag(NBT_TRACKOUTPUT, getTrackOutput());
			writer.writeByteTag(NBT_UPDATE_LAST_EXECUTION, true);
		} else {
			writer.writeStringTag(NBT_COMMAND, getCommand());
			writer.writeStringTag(NBT_LAST_OUTPUT, getLastMessage().toJsonText());
		}
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreCommandBlock> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Permission getPermission(CharSequence permission) {
		// TODO has Permission command block
		return null;
	}

	@Override
	public void sendMessage(String message) {
		sendMessage(ChatUtil.toChat(message));
	}
	
	@Override
	public void sendTranslation(String key, Object... values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Chat chat) {
		if (trackOutput)
			lastoutput = chat;
	}

	@Override
	public void setLastMessage(Chat message) {
		lastoutput = message;
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		sendMessage(message);
	}
}
