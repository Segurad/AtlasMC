package de.atlasmc.world.particle;

import org.joml.Vector3f;

import de.atlasmc.Location;
import de.atlasmc.entity.Player;

public interface Animation {

	void play(Player player, Location loc, Vector3f angle);
	
	void playAll(Location loc, Vector3f angle);
	
}
