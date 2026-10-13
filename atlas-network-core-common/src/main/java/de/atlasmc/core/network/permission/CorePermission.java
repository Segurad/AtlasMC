package de.atlasmc.core.network.permission;

import java.util.Objects;

import de.atlasmc.permission.Permission;

public class CorePermission implements Permission {

	private final String permission;
	private final int value;
	
	public CorePermission(CharSequence permission, int value) {
		this.permission = Objects.requireNonNull(permission, "permission").toString();
		this.value = value;
	}
	
	@Override
	public String permission() {
		return permission;
	}

	@Override
	public int value() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorePermission other = (CorePermission) obj;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		return value == other.value;
	}

}
