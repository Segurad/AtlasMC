package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class DolphinMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<DolphinMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(DolphinMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Moistness", DolphinMetaComponent::getMoistureLevel, DolphinMetaComponent::setMoistureLevel, 2400)
					.boolField("GotFish", DolphinMetaComponent::hasFish, DolphinMetaComponent::setFish, false)
					.build();
	
	protected static final MetaDataField<Boolean>
	META_HAS_FISH = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_MOISTURE_LEVEL = new MetaDataField<>(19, 2400, EntityMetaTypes.VAR_INT);

	public DolphinMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HAS_FISH);
		container.set(META_MOISTURE_LEVEL);
	}

	public boolean hasFish() {
		return getHolder().getMetaContainer().getData(META_HAS_FISH);
	}

	public void setFish(boolean fish) {
		getHolder().getMetaContainer().setData(META_HAS_FISH, fish);
	}

	public int getMoistureLevel() {
		return getHolder().getMetaContainer().getData(META_MOISTURE_LEVEL);
	}

	public void setMoistureLevel(int level) {
		if (level > 2400 || level < 0)
			throw new IllegalArgumentException("Level is not between 0 and 2400: " + level);
		getHolder().getMetaContainer().setData(META_MOISTURE_LEVEL, level);
	}

	public int getMaxMoistureLevel() {
		return 2400;
	}
	
	@Override
	public NBTCodec<? extends DolphinMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
