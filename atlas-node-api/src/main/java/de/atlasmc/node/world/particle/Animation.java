package de.atlasmc.node.world.particle;

import org.joml.Vector3f;

import de.atlasmc.node.Location;
import de.atlasmc.node.entity.Player;

public interface Animation {

	void play(Player player, Location loc, Vector3f angle);
	
	void playAll(Location loc, Vector3f angle);
	
}
