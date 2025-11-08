package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Ominous;
import de.atlasmc.util.enums.EnumName;

public interface Vault extends Directional, Ominous {
	
	VaultState getState();
	
	void setState(VaultState state);
	
	public static enum VaultState implements EnumName {
		
		INACTIVE,
		ACTIVE,
		UNLOCKING,
		EJECTING;

		private final String name;
		
		private VaultState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}

}
