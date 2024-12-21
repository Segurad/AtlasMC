package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.SculkSensor;
import de.atlasmc.block.data.type.SculkSensor.Phase;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class SculkSensorPhaseProperty extends AbstractEnumProperty<Phase>{

	public SculkSensorPhaseProperty() {
		super("sculk_sensor_phase");
	}

	@Override
	public Phase fromNBT(NBTReader reader) throws IOException {
		return Phase.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Phase value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Phase value) {
		if (data instanceof SculkSensor sensor)
			sensor.setPhase(value);
	}

	@Override
	public Phase get(BlockData data) {
		if (data instanceof SculkSensor sensor)
			return sensor.getPhase();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SculkSensor;
	}

}
