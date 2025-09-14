package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.SculkSensor;
import de.atlasmc.node.block.data.type.SculkSensor.Phase;

class SculkSensorPhaseProperty extends AbstractEnumProperty<Phase>{

	public SculkSensorPhaseProperty() {
		super("sculk_sensor_phase", Phase.class);
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
