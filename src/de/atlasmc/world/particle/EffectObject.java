package de.atlasmc.world.particle;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.entity.Player;
import de.atlasmc.util.EulerAngle;

public class EffectObject implements Animation {

	private final Effect effect;
	private final Object data;
	private final int radius;
	
	public EffectObject(Effect effect, Object data) {
		this(effect, data, 64);
	}
	
	public EffectObject(Effect effect, Object data, int radius) {
		this.effect = effect;
		this.data = data;
		this.radius = radius;
	}
	
	
	@Override
	public void play(Player player, Location loc, EulerAngle angle) {
		player.playEffect(loc, effect, data);
	}

	@Override
	public void playAll(Location loc, EulerAngle angle) {
		loc.getWorld().playEffect(loc, effect, data, radius);
	}

}
