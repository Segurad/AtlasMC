package de.atlasmc.node.block.tile;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.command.CommandSender;
import de.atlasmc.node.Nameable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface CommandBlock extends TileEntity, Nameable, CommandSender {
	
	public static final NBTCodec<CommandBlock>
	NBT_HANDLER = NBTCodec
					.builder(CommandBlock.class)
					.include(TileEntity.NBT_HANDLER)
					.boolField("auto", CommandBlock::isAlwaysActive, CommandBlock::setAlwaysActive)
					.string("Command", CommandBlock::getCommand, CommandBlock::setCommand)
					.boolField("conditionMet", CommandBlock::isConditionMet, CommandBlock::setConditionMet)
					.chat("CustomName", CommandBlock::getCustomName, CommandBlock::setCustomName)
					.longField("LastExecution", CommandBlock::getLastExecution, CommandBlock::setLastExecution)
					.chat("LastOutput", CommandBlock::getLastMessage, CommandBlock::setLastMessage)
					.boolField("powered", CommandBlock::isPowered, CommandBlock::setPowered)
					.intField("SuccessCount", CommandBlock::getSuccessCount, CommandBlock::setSuccessCount)
					.boolField("TrackOutput", CommandBlock::getTrackOutput, CommandBlock::setTrackOutput)
					.boolField("UpdateLastExecution", CommandBlock::getUpdateLastExecution, CommandBlock::setUpdateLastExecution)
					.build();
	
	Mode getMode();
	
	void setMode(Mode mode);
	
	boolean isConditionMet();
	
	void setConditionMet(boolean conditional);
	
	boolean isAlwaysActive();
	
	void setAlwaysActive(boolean alwaysActive);
	
	boolean getTrackOutput();
	
	void setTrackOutput(boolean trackOutput);
	
	void setCommand(String command);
	
	String getCommand();
	
	Chat getLastMessage();
	
	/**
	 * While {@link #sendMessage(Chat)} does only set the last message if {@link #getTrackOutput()} is true,
	 * this method will always set the last output
	 * @param message
	 */
	void setLastMessage(Chat message);
	
	void setPowered(boolean powered);
	
	boolean isPowered();
	
	void setSuccessCount(int count);
	
	int getSuccessCount();
	
	long getLastExecution();
	
	void setLastExecution(long lastExecution);
	
	boolean getUpdateLastExecution();
	
	void setUpdateLastExecution(boolean value);
	
	public static enum Mode {
		SEQUENCE,
		AUTO,
		REDSTONE;

		private static List<Mode> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Mode getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Mode> getValues() {
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
	
	@Override
	default NBTCodec<? extends CommandBlock> getNBTCodec() {
		return NBT_HANDLER;
	}

}
