package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface InstrumentComponent extends ItemComponent {
	
	public static final NBTCodec<InstrumentComponent>
	NBT_CODEC = NBTCodec
					.builder(InstrumentComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.registryValue(ComponentType.INSTRUMENT.getNamespacedKey(), InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.REGISTRY_KEY, Instrument.NBT_HANDLER)
					.build();
	
	public static final StreamCodec<InstrumentComponent>
	STREAM_CODEC = StreamCodec
					.builder(InstrumentComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.registryValueOrCodec(InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.REGISTRY_KEY, Instrument.STREAM_CODEC)
					.build();
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	InstrumentComponent clone();
	
	@Override
	default NBTCodec<? extends InstrumentComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends InstrumentComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
