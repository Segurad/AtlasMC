package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class SnifferMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<SnifferMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(SnifferMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("SnifferState", SnifferMetaComponent::getState, SnifferMetaComponent::setState, EnumUtil.enumStringNBTCodec(State.class), State.IDLING) // non standard
					.build();
	
	protected static final MetaDataField<State> 
	META_SNIFFER_STATE = new MetaDataField<>(18, State.IDLING, EntityMetaTypes.SNIFFER_STATE);
	protected static final MetaDataField<Integer> 
	META_DROP_SEED_TICK = new MetaDataField<Integer>(19, 0, EntityMetaTypes.VAR_INT);
	
	public SnifferMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SNIFFER_STATE);
		container.set(META_DROP_SEED_TICK);
	}

	public State getState() {
		return getHolder().getMetaContainer().getData(META_SNIFFER_STATE);
	}

	public void setState(State state) {
		getHolder().getMetaContainer().setData(META_SNIFFER_STATE, state);
	}

	public int getDropSeedAtTick() {
		return getHolder().getMetaContainer().getData(META_DROP_SEED_TICK);
	}

	public void setDropSeedAtTick(int tick) {
		getHolder().getMetaContainer().setData(META_DROP_SEED_TICK, tick);
	}
	
	@Override
	public NBTCodec<? extends SnifferMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum State implements IDHolder, EnumName {
		
		IDLING,
		FEELING_HAPPY,
		SCENTING,
		SNIFFING,
		SEARCHING,
		DIGGING,
		RISING;
		
		@Override
		public String getName() {
			return name();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
