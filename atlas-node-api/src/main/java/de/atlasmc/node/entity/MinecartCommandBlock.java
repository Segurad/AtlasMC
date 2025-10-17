package de.atlasmc.node.entity;

import de.atlasmc.chat.Chat;
import de.atlasmc.command.CommandSender;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MinecartCommandBlock extends AbstractMinecart, CommandSender {
	
	public static final NBTCodec<MinecartCommandBlock>
	NBT_HANDLER = NBTCodec
					.builder(MinecartCommandBlock.class)
					.include(AbstractMinecart.NBT_HANDLER)
					.string("Command", MinecartCommandBlock::getCommand, MinecartCommandBlock::setCommand)
					.chat("LastOutput", MinecartCommandBlock::getLastMessage, MinecartCommandBlock::setLastMessage)
					.intField("SucessCount", MinecartCommandBlock::getRedstoneSignal, MinecartCommandBlock::setRedstoneSignal)
					.boolField("TrackOutput", MinecartCommandBlock::isTrackingOutput, MinecartCommandBlock::setTrackOutput, false)
					.build();
	
	String getCommand();
	
	Chat getLastMessage();

	void setCommand(String command);
	
	/**
	 * While {@link #sendMessage(Chat)} does only set the last message if {@link #isTrackingOutput()} is true,
	 * this method will always set the last output
	 * @param message
	 */
	void setLastMessage(Chat message);

	void setRedstoneSignal(int signal);
	
	int getRedstoneSignal();

	void setTrackOutput(boolean track);
	
	boolean isTrackingOutput();
	
	@Override
	default NBTCodec<? extends MinecartCommandBlock> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
