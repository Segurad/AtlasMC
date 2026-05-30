package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.mojang.PlayerProfile;

public interface ProfileComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<ProfileComponent>
	NBT_CODEC = NBTCodec
					.builder(ProfileComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.PROFILE.getNamespacedKey(), ProfileComponent::getProfile, ProfileComponent::setProfile, PlayerProfile.NBT_CODEC)
					.build();
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	@Override
	ProfileComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
