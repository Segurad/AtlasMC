package de.atlasmc.core.node.inventory.component;

import de.atlasmc.network.player.PlayerProfile;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ProfileComponent;

public class CoreProfileComponent extends AbstractItemComponent implements ProfileComponent {

	private PlayerProfile profile;
	
	public CoreProfileComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreProfileComponent clone() {
		CoreProfileComponent clone = (CoreProfileComponent) super.clone();
		if (profile != null)
			clone.profile = profile.clone();
		return clone;
	}

	@Override
	public PlayerProfile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(PlayerProfile profile) {
		this.profile = profile;
	}

}
