package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.GlowItemFrame;

public class CoreGlowItemFrame extends CoreItemFrame implements GlowItemFrame {

	public CoreGlowItemFrame(EntityType type, UUID uuid) {
		super(type, uuid);
	}

}
