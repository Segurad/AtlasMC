package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NBTCodec<InstrumentComponent>
	NBT_HANDLER = NBTCodec
					.builder(InstrumentComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.registryValue(ComponentType.INSTRUMENT.getNamespacedKey(), InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.REGISTRY_KEY, Instrument.NBT_HANDLER)
					.build();
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	InstrumentComponent clone();
	
	@Override
	default NBTCodec<? extends InstrumentComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
