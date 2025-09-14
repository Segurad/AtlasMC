package de.atlasmc.core.node.entity;

import java.util.function.BiConsumer;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ExperienceOrb;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.play.PacketOutSpawnExperienceOrb;
import de.atlasmc.util.ViewerSet;

public class CoreExperienceOrb extends CoreEntity implements ExperienceOrb {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnExperienceOrb packet = new PacketOutSpawnExperienceOrb();
			packet.setEntity((ExperienceOrb) holder);
			con.sendPacked(packet);
			((CoreExperienceOrb) holder).sendMetadata(viewer);
		};
		
	private short lifeTime = 6000;
	private short xp;
	private short health = 5;
	private int count = 1;
	
	public CoreExperienceOrb(EntityType type) {
		super(type);
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
	public int getHealth() {
		return health;
	}
	@Override
	public void setHealth(int health) {
		this.health = (short) health;
	}
	@Override
	public int getCount() {
		return count;
	}
	@Override
	public void setCount(int count) {
		this.count = count;
	}

}
