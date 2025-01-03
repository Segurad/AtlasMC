package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ExperienceOrb;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSpawnExperienceOrb;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreExperienceOrb extends CoreEntity implements ExperienceOrb {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnExperienceOrb packet = new PacketOutSpawnExperienceOrb();
			packet.setEntity((ExperienceOrb) holder);
			con.sendPacked(packet);
			((CoreExperienceOrb) holder).sendMetadata(viewer);
		};
	
	protected static final NBTFieldSet<CoreExperienceOrb> NBT_FIELDS;
	
	protected static final CharKey
	NBT_AGE = CharKey.literal("Age"),
	NBT_VALUE = CharKey.literal("Value");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			holder.setLifeTime(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_VALUE, (holder, reader) -> {
			holder.setExperience(reader.readShortTag());
		});
	}
		
	private short lifeTime;
	private short xp;
	
	public CoreExperienceOrb(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreExperienceOrb> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
	}
	
	@Override
	public int getExperience() {
		return xp;
	}

	@Override
	public void setExperience(int xp) {
		this.xp = (short) xp;
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifeTime = (short) ticks;
	}

	@Override
	public int getLifeTime() {
		return lifeTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_AGE, getLifeTime());
		writer.writeIntTag(NBT_VALUE, getExperience());
	}

}
