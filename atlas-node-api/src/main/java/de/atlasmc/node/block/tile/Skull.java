package de.atlasmc.node.block.tile;

import de.atlasmc.network.player.PlayerProfile;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Skull extends TileEntity {
	
	public static final NBTSerializationHandler<Skull>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Skull.class)
					.include(TileEntity.NBT_HANDLER)
					.string("custom_name", Skull::getCustomName, Skull::setCustomName)
					.enumStringField("note_block_sound", Skull::getNoteBlockSound, Skull::setNoteBlockSound, EnumSound::getByName, null)
					.typeCompoundField("profile", Skull::getProfile, Skull::setProfile, PlayerProfile.NBT_HANDLER)
					.stringToObject("profile", Skull::getProfile, Skull::setProfile, PlayerProfile::new, PlayerProfile::getName)
					.build();
	
	String getCustomName();
	
	void setCustomName(String name);
	
	PlayerProfile getProfile();
	
	void setProfile(PlayerProfile profile);
	
	EnumSound getNoteBlockSound();
	
	void setNoteBlockSound(EnumSound sound);
	
	@Override
	default NBTSerializationHandler<? extends Skull> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
