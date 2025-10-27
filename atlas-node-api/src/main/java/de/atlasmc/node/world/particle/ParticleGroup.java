package de.atlasmc.node.world.particle;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;

public class ParticleGroup implements Animation {

	private final List<Animation> animations;
	private Animation next;
	
	public ParticleGroup() {
		animations = new ArrayList<>();
		next = null;
	}
	
	public ParticleGroup(List<Animation> animations, Animation next) {
		this.animations = animations;
		this.next = next;
	}
	
	public void addAnimation(Animation animation) {
		if (animation == null) return;
		animations.add(animation);
	}
	
	public void removeAnimation(Animation animation) {
		animations.remove(animation);
	}
	
	public List<Animation> getAnimations() {
		return animations;
	}

	@Override
	public void play(Player player, WorldLocation loc, Vector3f angle) {
		animations.forEach(a -> a.play(player, loc, angle));
		if (next != null) 
			next.play(player, loc, angle);
	}

	@Override
	public void playAll(WorldLocation loc, Vector3f angle) {
		animations.forEach(a -> a.playAll(loc, angle));
		if (next != null) 
			next.playAll(loc, angle);
	}
}
