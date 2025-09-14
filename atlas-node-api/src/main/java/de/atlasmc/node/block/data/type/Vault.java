package de.atlasmc.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Ominous;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Vault extends Directional, Ominous {
	
	VaultState getState();
	
	void setState(VaultState state);
	
	public static enum VaultState implements EnumName, EnumValueCache {
		
		INACTIVE,
		ACTIVE,
		UNLOCKING,
		EJECTING;
		
		private static List<VaultState> VALUES;
		
		private final String name;
		
		private VaultState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static VaultState getByNameID(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<VaultState> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				VaultState value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<VaultState> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

	}

}
