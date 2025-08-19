package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<InstrumentComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(InstrumentComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.registryValue(ComponentType.INSTRUMENT, InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.REGISTRY_KEY, Instrument.NBT_HANDLER)
					.build();
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	InstrumentComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends InstrumentComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
