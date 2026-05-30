package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public interface InstrumentComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<InstrumentComponent>
	NBT_CODEC = NBTCodec
					.builder(InstrumentComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.INSTRUMENT.getNamespacedKey(), InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Registries.registryValueNBTCodec(Instrument.REGISTRY_KEY))
					.codec(ComponentType.INSTRUMENT.getNamespacedKey(), InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.NBT_CODEC)
					.build();
	
	@NotNull
	public static final StreamCodec<InstrumentComponent>
	STREAM_CODEC = StreamCodec
					.builder(InstrumentComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varIntRegistryOrCodec(InstrumentComponent::getInstrument, InstrumentComponent::setInstrument, Instrument.REGISTRY_KEY, Instrument.STREAM_CODEC)
					.build();
	
	Instrument getInstrument();
	
	void setInstrument(Instrument instrument);
	
	@Override
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
