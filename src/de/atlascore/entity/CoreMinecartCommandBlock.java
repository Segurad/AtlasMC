package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartCommandBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreMinecartCommandBlock extends CoreAbstractMinecart implements MinecartCommandBlock {

	protected static final MetaDataField<String>
	META_COMMAND = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+1, null, MetaDataType.STRING);
	protected static final MetaDataField<ChatComponent>
	META_LAST_OUTPUT = new MetaDataField<>(CoreAbstractMinecart.LAST_META_INDEX+2, ChatUtil.EMPTY, MetaDataType.CHAT);
	
	protected static final int LAST_META_INDEX = CoreAbstractMinecart.LAST_META_INDEX+2;
	
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
	public ChatComponent getLastOutput() {
		return metaContainer.getData(META_LAST_OUTPUT);
	}

	@Override
	public void setCommand(String command) {
		metaContainer.get(META_COMMAND).setData(command);
	}

	@Override
	public void setLastOutput(ChatComponent out) {
		if (out == null)
			out = ChatUtil.EMPTY;
		metaContainer.get(META_LAST_OUTPUT).setData(out);
	}

}
