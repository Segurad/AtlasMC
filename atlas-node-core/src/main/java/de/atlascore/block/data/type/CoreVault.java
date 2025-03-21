package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Vault;

public class CoreVault extends CoreDirectional4Faces implements Vault {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.OMINOUS,
				BlockDataProperty.VAULT_STATE);
	}
	
	protected boolean ominous;
	protected VaultState state;
	
	public CoreVault(BlockType type) {
		super(type);
		state = VaultState.INACTIVE;
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
	public VaultState getState() {
		return state;
	}

	@Override
	public void setState(VaultState state) {
		if (state == null)
			throw new IllegalArgumentException("State can not be null!");
		this.state = state;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID() + state.ordinal() + (ominous?0:4) + getFaceValue()*8;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
