package de.atlascore.block.tile;

import de.atlasmc.atlasnetwork.player.PlayerProfile;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Skull;
import de.atlasmc.sound.EnumSound;

public class CoreSkull extends CoreTileEntity implements Skull {
	
	private String customName;
	private PlayerProfile profile;
	private EnumSound noteBlockSound;
	
	public CoreSkull(BlockType type) {
		super(type);
	}

	@Override
	public String getCustomName() {
		return customName;
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public PlayerProfile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(PlayerProfile profile) {
		this.profile = profile;
	}

	@Override
	public EnumSound getNoteBlockSound() {
		return noteBlockSound;
	}

	@Override
	public void setNoteBlockSound(EnumSound sound) {
		this.noteBlockSound = sound;
	}

}
