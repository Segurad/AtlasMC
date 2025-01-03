package de.atlasmc.world;

import org.joml.Vector3d;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;

/**
 * Represents a region between two locations
 */
public class Region implements Cloneable {

	protected SimpleLocation loc1;
	protected SimpleLocation loc2;

	protected double maxx;
	protected double maxy;
	protected double maxz;
	protected double minx;
	protected double miny;
	protected double minz;

	/**
	 * Constructs a region between two locations
	 * 
	 * @param loc1 The region's location 1
	 * @param loc2 The region's location 2
	 */
	public Region(SimpleLocation loc1, SimpleLocation loc2) {
		this.loc1 = loc1.clone();
		this.loc2 = loc2.clone();
		update();
	}

	public Region(SimpleLocation loc) {
		loc1 = loc.clone();
		loc2 = loc.clone();
		maxx = loc.x;
		minx = loc.x;
		maxy = loc.y;
		miny = loc.y;
		maxz = loc.z;
		minz = loc.z;
	}

	/**
	 * Gets the region's location 1
	 * 
	 * @return location 1
	 */
	public SimpleLocation getLoc1() {
		return loc1.clone();
	}
	
	public SimpleLocation getLoc1(SimpleLocation loc) {
		return loc.set(loc);
	}

	/**
	 * Sets the region's location 1 to location
	 * @param loc The location you want to set as location 1
	 */
	public void setLoc1(Location loc) {
		loc1.set(loc);
		update();
	}

	/**
	 * Gets the region's location 2
	 * 
	 * @return location 2
	 */
	public SimpleLocation getLoc2() {
		return loc2.clone();
	}
	
	public SimpleLocation getLoc2(SimpleLocation loc) {
		return loc.set(loc);
	}

	/**
	 * Sets the region's location 2 to location
	 * @param loc The location you want to set as location 2
	 */
	public void setLoc2(Location loc) {
		loc2.set(loc);
		update();
	}

	/**
	 * Returns true if the location is in this region
	 * 
	 * @param loc The location you want to check
	 * @return true if the location is in this region
	 */
	public boolean contains(Vector3d loc) {
		return contains(loc.x, loc.y, loc.z);
	}
	
	public boolean contains(double x, double y, double z) {
		if (isIntersecting(getMinX(), getWidhtX(), x, 0)) {
			if (isIntersecting(getMinY(), getHeight(), y, 0)) {
				if (isIntersecting(getMinZ(), getWidhtZ(), z, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isIntersecting(Region region) {
		if (isIntersecting(getMinX(), getWidhtX(), region.getMinX(), region.getWidhtX())) {
			if (isIntersecting(getMinY(), getHeight(), region.getMinY(), region.getHeight())) {
				if (isIntersecting(getMinZ(), getWidhtZ(), region.getMinZ(), region.getWidhtZ())) {
					return true;
				}
			}
		}
		return false;
	}

	protected final boolean isIntersecting(double l1, double w1, double l2, double w2) {
		// l muss der kleiner Punkt der Strecke auf der Achse sein!!!
		// l == Location auf der Achse
		// w == widthe der Strecke auf der Achse
		return ((l1 <= l2 + w2) && (l2 <= l1 + w1));
	}

	public double getWidhtX() {
		return maxx - minx;
	}

	public double getHeight() {
		return maxy - miny;
	}

	public double getWidhtZ() {
		return maxz - minz;
	}

	public double getMaxX() {
		return maxx;
	}

	public double getMaxY() {
		return maxy;
	}

	public double getMaxZ() {
		return maxz;
	}

	public double getMinX() {
		return minx;
	}

	public double getMinY() {
		return miny;
	}

	public double getMinZ() {
		return minz;
	}

	private void update() {
		maxx = Math.max(loc1.x, loc2.x);
		maxy = Math.max(loc1.y, loc2.y);
		maxz = Math.max(loc1.z, loc2.z);

		minx = Math.min(loc1.x, loc2.x);
		miny = Math.min(loc1.y, loc2.y);
		minz = Math.min(loc1.z, loc2.z);
	}

	public Region expand(Vector3d loc) {
		final double x = loc.x;
		final double y = loc.y;
		final double z = loc.z;
		loc1.set(Math.max(maxx, x), Math.max(maxy, y), Math.max(maxz, z));
		loc2.set(Math.min(minx, x), Math.max(miny, y), Math.max(minz, z));
		update();
		return this;
	}

	public Region expand(Region region) {
		loc1.set(Math.max(maxx, region.maxx), Math.max(maxy, region.maxy), Math.max(maxz, region.maxz));
		loc2.set(Math.min(minx, region.minx), Math.max(miny, region.miny), Math.max(minz, region.minz));
		update();
		return this;
	}

	public double getVolume() {
		return getWidhtX() * getHeight() * getWidhtZ();
	}

	public SimpleLocation getCenter() {
		return new SimpleLocation(getMinX() + getWidhtX() / 2, getMinY() + getHeight() / 2, getMinZ() + getWidhtZ() / 2);
	}

	public Region clone() {
		return new Region(loc1.clone(), loc2.clone());
	}
}
