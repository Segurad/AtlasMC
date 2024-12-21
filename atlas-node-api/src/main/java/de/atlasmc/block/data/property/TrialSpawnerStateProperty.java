package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.TrialSpawner;
import de.atlasmc.block.data.type.TrialSpawner.TrialSpawnerState;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class TrialSpawnerStateProperty extends AbstractEnumProperty<TrialSpawnerState> {

	public TrialSpawnerStateProperty() {
		super("trial_spawner_state");
	}

	@Override
	public TrialSpawnerState fromNBT(NBTReader reader) throws IOException {
		return TrialSpawnerState.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(TrialSpawnerState value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
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
