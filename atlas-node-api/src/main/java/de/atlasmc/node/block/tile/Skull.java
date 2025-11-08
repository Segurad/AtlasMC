package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.network.player.PlayerProfile;
import de.atlasmc.node.sound.EnumSound;

public interface Skull extends TileEntity {
	
	public static final NBTCodec<Skull>
	NBT_HANDLER = NBTCodec
					.builder(Skull.class)
					.include(TileEntity.NBT_HANDLER)
					.codec("custom_name", Skull::getCustomName, Skull::setCustomName, NBTCodecs.STRING)
					.codec("note_block_sound", Skull::getNoteBlockSound, Skull::setNoteBlockSound, EnumSound.NBT_CODEC)
					.codec("profile", Skull::getProfile, Skull::setProfile, PlayerProfile.NBT_CODEC)
					.codec("profile", Skull::getProfile, Skull::setProfile, PlayerProfile.NAME_NBT_CODEC)
					.build();
	
	String getCustomName();
	
	void setCustomName(String name);
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	EnumSound getNoteBlockSound();
	
	void setNoteBlockSound(EnumSound sound);
	
	@Override
	default NBTCodec<? extends Skull> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
