package de.atlasmc.node.world.particle;

import org.joml.Vector3f;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;

public interface Animation {

	void play(Player player, WorldLocation loc, Vector3f angle);
	
	void playAll(WorldLocation loc, Vector3f angle);
	
}
