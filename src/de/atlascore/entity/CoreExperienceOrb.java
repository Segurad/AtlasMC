package de.atlascore.entity;

import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ExperiemceOrb;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutSpawnExperienceOrb;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.world.World;

public class CoreExperienceOrb extends CoreEntity implements ExperiemceOrb {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnExperienceOrb packet = con.createPacket(PacketOutSpawnExperienceOrb.class);
			packet.setEntityID(holder.getID());
			packet.setLocation(holder.getX(), holder.getY(), holder.getZ());
			packet.setExperience(((ExperiemceOrb) holder).getExperience());
			con.sendPacked(packet);
			holder.sendMetadata(viewer);
		};
	
	private int xp;
	
	public CoreExperienceOrb(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		this.xp = xp;
	}

}
