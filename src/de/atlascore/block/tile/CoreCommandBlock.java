package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.CommandBlock;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreCommandBlock extends CoreTileEntity implements CommandBlock {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_CUSTOM_NAME = "CustomName",
	NBT_AUTO = "auto",
	NBT_COMMAND = "conditionMet",
	NBT_CONDITION_MET = "LastExecution",
	NBT_LAST_EXECUTION = "LastExecution",
	NBT_LAST_OUTPUT = "LastOutput",
	NBT_POWERED = "powered",
	NBT_SUCCESSCOUNT = "SuccessCount",
	NBT_TRACKOUTPUT = "TrackOutput",
	NBT_UPDATE_LAST_EXECUTION = "UpdateLastExecution";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_AUTO, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setAlwaysActive(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_COMMAND, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setCommand(reader.readStringTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CONDITION_MET, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setConditional(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LAST_EXECUTION, NBTField.SKIP); // TODO Wait for CommandBlock logic
		NBT_FIELDS.setField(NBT_LAST_OUTPUT, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setLastOutput(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_POWERED, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setPowered(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_SUCCESSCOUNT, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setSuccessCount(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_TRACKOUTPUT, (holder, reader) -> {
			if (holder instanceof CommandBlock)
			((CommandBlock) holder).setTrackOutput(reader.readByteTag() == 1);
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_UPDATE_LAST_EXECUTION, NBTField.SKIP); // TODO see NBT_LAST_EXECUTION ^
	}
	
	private Mode mode;
	private boolean conditional, alwaysActive, trackOutput, powered;
	private ChatComponent name, lastoutput;
	private String command;
	private int successCount;
	
	public CoreCommandBlock(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
	public void setCustomName(ChatComponent name) {
		this.name = name;
	}

	@Override
	public ChatComponent getCustomName() {
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
	public void setLastOutput(ChatComponent lastoutput) {
		this.lastoutput = lastoutput;
	}

	@Override
	public ChatComponent getLastOutput() {
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
			writer.writeStringTag(NBT_CUSTOM_NAME, getCustomName().getJsonText());
			writer.writeByteTag(NBT_AUTO, isAlwaysActive());
			writer.writeStringTag(NBT_COMMAND, getCommand());
			writer.writeLongTag(NBT_LAST_EXECUTION, 0);
			writer.writeStringTag(NBT_LAST_OUTPUT, getLastOutput().getJsonText());
			writer.writeByteTag(NBT_POWERED, isPowered());
			writer.writeIntTag(NBT_SUCCESSCOUNT, getSuccessCount());
			writer.writeByteTag(NBT_TRACKOUTPUT, getTrackOutput());
			writer.writeByteTag(NBT_UPDATE_LAST_EXECUTION, true);
		} else {
			writer.writeStringTag(NBT_COMMAND, getCommand());
			writer.writeStringTag(NBT_LAST_OUTPUT, getLastOutput().getJsonText());
		}
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
}
