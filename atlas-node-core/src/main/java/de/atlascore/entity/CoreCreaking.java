package de.atlascore.entity;

import java.util.UUID;

import org.joml.Vector3i;

import de.atlasmc.entity.Creaking;
import de.atlasmc.entity.EntityType;

public class CoreCreaking extends CoreMob implements Creaking {

	protected Vector3i home;
	
	public CoreCreaking(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public Vector3i getHome() {
		return home;
	}

	@Override
	public void setHome(Vector3i position) {
		if (position == null) {
			this.home = null;
		} else if (this.home == null) {
			this.home = new Vector3i(position);
		} else {
			this.home.set(position);
		}
	}

}
