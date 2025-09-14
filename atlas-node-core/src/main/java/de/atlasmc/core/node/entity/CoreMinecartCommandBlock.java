package de.atlasmc.core.node.entity;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.MinecartCommandBlock;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.permission.Permission;

public class CoreMinecartCommandBlock extends CoreAbstractMinecart implements MinecartCommandBlock {

	protected static final MetaDataField<String>
	META_COMMAND = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+1, null, MetaDataType.STRING);
	protected static final MetaDataField<Chat>
	META_LAST_OUTPUT = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+2, ChatUtil.EMPTY, MetaDataType.CHAT);
	
	protected static final int LAST_META_INDEX = CoreAbstractMinecart.LAST_META_INDEX+2;
	
	protected int redstoneSignal;
	protected boolean trackOutput;
	
	public CoreMinecartCommandBlock(EntityType type) {
		super(type);
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
		metaContainer.get(META_LAST_OUTPUT).setData(message != null ? message : ChatUtil.EMPTY);
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
	public Permission getPermission(CharSequence permission) {
		// TODO command block minecart Permission
		return null;
	}

	@Override
	public void sendMessage(String message) {
		sendMessage(ChatUtil.toChat(message));
	}

	@Override
	public void sendMessage(Chat chat) {
		if (isTrackingOutput())
			metaContainer.set(META_LAST_OUTPUT, chat != null ? chat : ChatUtil.EMPTY);
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		sendMessage(message);
	}

	@Override
	public void sendTranslation(String key, Object... values) {
		// TODO Auto-generated method stub
		
	}

}
