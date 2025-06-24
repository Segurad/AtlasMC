package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:instrument");
	
	public static final NBTSerializationHandler<InstrumentComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(InstrumentComponent.class)
					.registryValue(COMPONENT_KEY, InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Registries.getRegistry(Instrument.class), Instrument.NBT_HANDLER)
					.build();
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	InstrumentComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends InstrumentComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
