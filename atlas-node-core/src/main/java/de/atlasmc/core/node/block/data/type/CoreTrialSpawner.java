package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.TrialSpawner;

public class CoreTrialSpawner extends CoreBlockData implements TrialSpawner {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, 
				PropertyType.OMINOUS,
				PropertyType.TRIAL_SPAWNER_STATE);
	}
	
	protected boolean ominous;
	protected TrialSpawnerState state;
	
	public CoreTrialSpawner(BlockType type) {
		super(type);
		state = TrialSpawnerState.INACTIVE;
	}

	@Override
	public boolean isOminous() {
		return ominous;
	}

	@Override
	public void setOminous(boolean ominous) {
		this.ominous = ominous;
	}

	@Override
	public TrialSpawnerState getState() {
		return state;
	}

	@Override
	public void setState(TrialSpawnerState state) {
		if (state == null)
			throw new IllegalArgumentException("State can not be null!");
		this.state = state;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID() + state.ordinal() + (ominous?0:6);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
