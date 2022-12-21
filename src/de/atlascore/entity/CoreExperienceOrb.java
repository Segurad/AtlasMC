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
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreExperienceOrb extends CoreEntity implements ExperienceOrb {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnExperienceOrb packet = new PacketOutSpawnExperienceOrb();
			packet.setEntityID(holder.getID());
			packet.setLocation(holder.getX(), holder.getY(), holder.getZ());
			packet.setExperience(((ExperienceOrb) holder).getExperience());
			con.sendPacked(packet);
			((CoreExperienceOrb) holder).sendMetadata(viewer);
		};
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_AGE = CharKey.of("Age"),
	NBT_VALUE = CharKey.of("Value");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			if (holder instanceof ExperienceOrb) {
				((ExperienceOrb) holder).setLifeTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_VALUE, (holder, reader) -> {
			if (holder instanceof ExperienceOrb) {
				((ExperienceOrb) holder).setExperience(reader.readShortTag());
			} else reader.skipTag();
		});
	}
		
	private short lifeTime;
	private short xp;
	
	public CoreExperienceOrb(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
