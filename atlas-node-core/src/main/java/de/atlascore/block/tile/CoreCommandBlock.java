package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.CommandBlock;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.permission.Permission;

public class CoreCommandBlock extends CoreTileEntity implements CommandBlock {
	
	private Mode mode;
	private boolean conditionMet;
	private boolean alwaysActive;
	private boolean trackOutput;
	private boolean powered;
	private Chat name;
	private Chat lastoutput;
	private String command;
	private int successCount;
	private boolean updateLastExecution;
	private long lastExecution;
	
	public CoreCommandBlock(BlockType type) {
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
	public boolean isConditionMet() {
		return conditionMet;
	}

	@Override
	public void setConditionMet(boolean conditional) {
		this.conditionMet = conditional;
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
	public boolean hasCustomName() {
		return name != null;
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

	@Override
	public long getLastExecution() {
		return lastExecution;
	}

	@Override
	public void setLastExecution(long lastExecution) {
		this.lastExecution = lastExecution;
	}

	@Override
	public boolean getUpdateLastExecution() {
		return updateLastExecution;
	}

	@Override
	public void setUpdateLastExecution(boolean value) {
		this.updateLastExecution = value;
	}
}
