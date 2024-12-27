package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.TrialSpawner;

public class CoreTrialSpawner extends CoreBlockData implements TrialSpawner {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, 
				BlockDataProperty.OMINOUS,
				BlockDataProperty.TRIAL_SPAWNER_STATE);
	}
	
	protected boolean ominous;
	protected TrialSpawnerState state;
	
	public CoreTrialSpawner(Material material) {
		super(material);
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
		return material.getBlockStateID() + state.ordinal() + (ominous?0:6);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
