package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class IronGolemMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<IronGolemMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(IronGolemMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.boolField("PlayerCreated", IronGolemMetaComponent::isPlayerCreated, IronGolemMetaComponent::setPlayerCreated, false)
					.build();
	
	public static final int
	FLAG_IS_PLAYER_CREATED = 0x01;
	
	public static final MetaDataField<Byte>
	META_IRON_GOLEM_FLAGS = new MetaDataField<>(16, (byte) 0, EntityMetaTypes.BYTE);
	
	public IronGolemMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IRON_GOLEM_FLAGS);
	}
	
	public boolean isPlayerCreated() {
		return (getHolder().getMetaContainer().getData(META_IRON_GOLEM_FLAGS) & 0x01) == 0x01;
	}
	
	protected void setIronGolemFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_IRON_GOLEM_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_IRON_GOLEM_FLAGS, value);
	}

	public void setPlayerCreated(boolean playercreated) {
		setIronGolemFlag(FLAG_IS_PLAYER_CREATED, playercreated);
	}
	
	@Override
	public NBTCodec<? extends IronGolemMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
