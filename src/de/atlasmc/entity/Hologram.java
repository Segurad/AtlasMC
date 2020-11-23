package de.atlasmc.entity;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Location;

public class Hologram {

	private Location loc;
	private List<String> lines = new ArrayList<String>();
	private List<ArmorStand> stands = new ArrayList<ArmorStand>();

	public Hologram(String text, Location loc) {
		lines.add(text);
		this.loc = loc;
	}

	public Hologram(List<String> text, Location loc) {
		lines = text;
		this.loc = loc;
	}

	public void setLocation(Location loc) {
		this.loc = loc.clone();
	}

	public Location getLocation() {
		return loc.clone();
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void removeLine(int index) {
		lines.remove(index);
	}

	public void addLine(String text) {
		lines.add(text);
	}

	public void addLine(int index, String text) {
		lines.add(index, text);
	}

	public void editLine(int index, String text) {
		lines.set(index, text);
	}

	public void hide() {
		for (ArmorStand stand : stands) {
			stand.remove();
		}
	}

	public void update() {
		hide();
		show();
	}

	public void show() {
		for (String line : lines) {
			Location loc = this.loc;
			loc.setY(loc.getY() - 0.3 * (lines.indexOf(line) + 1));
			ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
			stand.setCustomNameVisible(true);
			stand.setCustomName(line);
			stands.add(stand);
		}
	}
}
