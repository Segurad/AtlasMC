package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Vault;
import de.atlasmc.node.block.data.type.Vault.VaultState;

class VaultStateProperty extends AbstractEnumProperty<VaultState> {

	public VaultStateProperty() {
		super("vault_state", VaultState.class);
	}

	@Override
	public void set(BlockData data, VaultState value) {
		if (data instanceof Vault vault)
			vault.setState(value);
	}

	@Override
	public VaultState get(BlockData data) {
		if (data instanceof Vault vault)
			return vault.getState();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Vault;
	}

}
