package de.atlasmc.node.world.particle;

import org.joml.Vector3f;

import de.atlasmc.node.Location;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.world.WorldEvent;

public class EffectObject implements Animation {

	private final WorldEvent effect;
	private final Object data;
	private final int radius;
	
	public EffectObject(WorldEvent effect, Object data) {
		this(effect, data, 64);
	}
	
	public EffectObject(WorldEvent effect, Object data, int radius) {
		this.effect = effect;
		this.data = data;
		this.radius = radius;
	}
	
	
	@Override
	public void play(Player player, Location loc, Vector3f angle) {
		player.playEffect(loc, effect, data);
	}

	@Override
	public void playAll(Location loc, Vector3f angle) {
		loc.getWorld().playEffect(loc, effect, data, radius);
	}

}
