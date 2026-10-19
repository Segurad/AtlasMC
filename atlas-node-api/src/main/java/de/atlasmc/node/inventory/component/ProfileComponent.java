package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.mojang.PlayerProfile;

public interface ProfileComponent extends ItemComponent {

	@NotNull
	public static final NBTCodec<ProfileComponent>
	NBT_CODEC = CodecFieldCodec
					.builder(ProfileComponent.class, PlayerProfile.NBT_CODEC)
					.include(ItemComponent.NBT_CODEC)
					.getter(ProfileComponent::getProfile)
					.setter(ProfileComponent::setProfile)
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
