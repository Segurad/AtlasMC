package de.atlasmc.node.inventory.component;

import de.atlasmc.network.player.PlayerProfile;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ProfileComponent extends ItemComponent {

	public static final NBTSerializationHandler<ProfileComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ProfileComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.PROFILE.getNamespacedKey(), ProfileComponent::getProfile, ProfileComponent::setProfile, PlayerProfile.NBT_HANDLER)
					.build();
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	ProfileComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
