package de.atlasmc.world.particle;

import de.atlasmc.Location;
import de.atlasmc.entity.Player;
import de.atlasmc.util.EulerAngle;

public interface Animation {

	public void play(Player player, Location loc, EulerAngle angle);
	public void playAll(Location loc, EulerAngle angle);
	
}
