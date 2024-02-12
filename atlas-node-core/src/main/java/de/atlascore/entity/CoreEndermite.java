package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Endermite;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEndermite extends CoreMob implements Endermite {

	protected static final CharKey
	NBT_LIFETIME = CharKey.literal("Lifetime"),
	NBT_PLAYER_SPAWNED = CharKey.literal("PlayerSpawned");
	
	static {
		NBT_FIELDS.setField(NBT_LIFETIME, (holder, reader) -> {
			if (holder instanceof Endermite) {
				((Endermite) holder).setLifetime(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_PLAYER_SPAWNED, (holder, reader) -> {
			if (holder instanceof Endermite) {
				((Endermite) holder).setPlayerSpawned(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private int lifetime;
	private boolean playerSpawned;
	
	public CoreEndermite(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;		
	}

	@Override
	public int getLifetime() {
		return lifetime;
	}

	@Override
	public void setPlayerSpawned(boolean playerSpawned) {
		this.playerSpawned = playerSpawned;
	}

	@Override
	public boolean isPlayerSpawned() {
		return playerSpawned;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_LIFETIME, getLifetime());
		if (isPlayerSpawned())
			writer.writeByteTag(NBT_PLAYER_SPAWNED, true);
	}

}
