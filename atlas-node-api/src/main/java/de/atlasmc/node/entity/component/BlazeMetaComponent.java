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

public class BlazeMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final NBTCodec<BlazeMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BlazeMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					// non standard by atlas
					.boolField("IsOnFire", BlazeMetaComponent::isOnFire, BlazeMetaComponent::setOnFire, false)
					.build();
	
	public static final MetaDataField<Byte>
	META_BLAZE_ON_FIRE = new MetaDataField<>(16, (byte) 0, EntityMetaTypes.BYTE);
	
	public BlazeMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BLAZE_ON_FIRE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public boolean isOnFire() {
		return (getHolder().getMetaContainer().getData(META_BLAZE_ON_FIRE) & 0x01) == 0x01;
	}

	public void setOnFire(boolean fire) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_BLAZE_ON_FIRE);
		container.setData(META_BLAZE_ON_FIRE, (byte) (fire ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

}
