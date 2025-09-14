package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.TrialSpawner;
import de.atlasmc.node.block.data.type.TrialSpawner.TrialSpawnerState;

class TrialSpawnerStateProperty extends AbstractEnumProperty<TrialSpawnerState> {

	public TrialSpawnerStateProperty() {
		super("trial_spawner_state", TrialSpawnerState.class);
	}

	@Override
	public void set(BlockData data, TrialSpawnerState value) {
		if (data instanceof TrialSpawner spawner)
			spawner.setState(value);
	}

	@Override
	public TrialSpawnerState get(BlockData data) {
		if (data instanceof TrialSpawner spawner)
			return spawner.getState();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof TrialSpawner;
	}

}
