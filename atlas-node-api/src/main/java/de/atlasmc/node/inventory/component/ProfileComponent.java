package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.network.player.PlayerProfile;

public interface ProfileComponent extends ItemComponent {

	public static final NBTCodec<ProfileComponent>
	NBT_HANDLER = NBTCodec
					.builder(ProfileComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.PROFILE.getNamespacedKey(), ProfileComponent::getProfile, ProfileComponent::setProfile, PlayerProfile.NBT_CODEC)
					.build();
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	ProfileComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
