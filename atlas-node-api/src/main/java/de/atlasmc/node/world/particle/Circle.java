package de.atlasmc.node.world.particle;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import de.atlasmc.node.Location;
import de.atlasmc.node.entity.Player;

public class Circle implements Animation {

	protected Animation anm;
	protected double radius;
	protected int points;
	
	public Circle(Animation anm, double radius, int points) {
		this.anm = anm; this.radius = radius; this.points = points;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	@Override
	public void play(Player player, Location loc, Vector3f angle) {
		for(Location l : getLocations(loc, radius)) {
			anm.play(player, l, angle);
		}
	}

	@Override
	public void playAll(Location loc, Vector3f angle) {
		for(Location l : getLocations(loc, radius)) {
			anm.playAll(l, angle);
		}
	}
	
	protected List<Location> getLocations(Location center, double radius) {
		List<Location> locs = new ArrayList<>();
		double degree = 360 / points;
		for (int d = 0; d <= points; d++) {
			//loc(x|z) = [ loc.x + radius * cos(d) | loc.z + radius * sin(d)]
			double x = center.x + radius * Math.cos(Math.toRadians(degree) * d);
			double z = center.z + radius * Math.sin(Math.toRadians(degree) * d);
			locs.add(new Location(center.getWorld(), x, center.y, z));
		}
		return locs;
	}

}
