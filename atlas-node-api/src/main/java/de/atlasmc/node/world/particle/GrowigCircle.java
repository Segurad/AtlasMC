package de.atlasmc.node.world.particle;

import org.joml.Vector3f;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;

public class GrowigCircle extends Circle {

	protected int repeats;
	protected int delay;
	protected double growth;
	protected Animation next;
	
	public GrowigCircle(Animation anm, double radius, int points, int repeats, double growth) {
		this(anm, radius, points, repeats, growth, 0, null);
	}
	
	public GrowigCircle(Animation anm, double radius, int points, int repeats, double growth, int delay) {
		this(anm, radius, points, repeats, growth, delay, null);
	}
	
	public GrowigCircle(Animation anm, double radius, int points, int repeats, double growth, int delay, Animation next) {
		super(anm, radius, points);
		this.delay = delay;
		this.repeats = repeats;
		this.growth = growth;
		this.next = next;
	}
	
	@Override
	public void play(Player player, WorldLocation loc, Vector3f angle) {
		if (delay > 0) {
			Runnable rable = new Runnable() {
				int repeats = GrowigCircle.this.repeats;
				double rad = radius;
				@Override
				public void run() {
					if (repeats > 0) {
						for(WorldLocation l : getLocations(loc, rad)) {
							anm.play(player, l, angle);
						}
						repeats--;
						rad += growth;
						// TODO schedule Atlas.getScheduler().runSyncTaskLater(rable, delay);
					} else if (next != null) next.play(player, loc, angle);
				}
			};
			rable.run();
		} else {
			Runnable rable = new Runnable() {
				int repeats = GrowigCircle.this.repeats;
				double rad = radius;
				@Override
				public void run() {
					if (repeats > 0) {
						for(WorldLocation l : getLocations(loc, rad)) {
							anm.play(player, l, angle);
						}
						repeats--;
						rad += growth;
						this.run();
					} else if (next != null) next.play(player, loc, angle);
				}
			};
			rable.run();
		}
	}
	
	@Override
	public void playAll(WorldLocation loc, Vector3f angle) {
		if (delay > 0) {
			Runnable rable = new Runnable() {
				int repeats = GrowigCircle.this.repeats;
				double rad = radius;
				@Override
				public void run() {
					if (repeats > 0) {
						for(WorldLocation l : getLocations(loc, rad)) {
							anm.playAll(l, angle);
						}
						repeats--;
						rad += growth;
						// TODO schedule Bukkit.getScheduler().runTaskLater(pl, new DummyTask(this), delay);
					} else if (next != null) next.playAll(loc, angle);
				}
			};
			rable.run();
		} else {
			Runnable rable = new Runnable() {
				int repeats = GrowigCircle.this.repeats;
				double rad = radius;
				@Override
				public void run() {
					if (repeats > 0) {
						for(WorldLocation l : getLocations(loc, rad)) {
							anm.playAll(l, angle);
						}
						repeats--;
						rad += growth;
						this.run();
					} else if (next != null) next.playAll(loc, angle);
				}
			};
			rable.run();
		}
	}

}
