package de.atlasmc.world;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.SimpleLocation;

public class WorldData extends WorldCreator {

	private World world;
	private SimpleLocation spawn;
	private List<WorldFlag> flags = new ArrayList<WorldFlag>();
	private List<String> whitelist = new ArrayList<String>(); // Mixed List with UUIDs and Player names
	private boolean blacklist = false;

	public WorldData(String name) {
		this(name, null);
	}

	public WorldData(String name, SimpleLocation spawn) {
		super(name);
		this.spawn = spawn;
		world = Bukkit.getWorld(name);
	}

	public boolean hasFlag(WorldFlag flag) {
		return flags.contains(flag);
	}

	public void addFlag(WorldFlag flag) {
		if (!flags.contains(flag))
			flags.add(flag);
	}

	public void removeFlag(WorldFlag flag) {
		flags.remove(flag);
	}

	public void setSpawn(SimpleLocation loc) {
		spawn = loc.clone();
		if (world != null && spawn != null)
			world.setSpawnLocation(loc.getLocation(world));
	}

	public SimpleLocation getSpawn() {
		return (spawn != null ? spawn.clone() : null);
	}

	public World createWorld() {
		World world = super.createWorld();
		if (world == null) throw new Error("Error while creating world: " + name());
		if (world != null && spawn != null) world.setSpawnLocation(spawn.getLocation(world));
		this.world = world;
		if (spawn == null && world.getSpawnLocation() != null) spawn = new SimpleLocation(world.getSpawnLocation());
		return world;
	}

	/**
	 * returns a mixed list with player names and uuids
	 * 
	 * @return the whitelist
	 */
	public List<String> getWhitelisted() {
		return whitelist;
	}

	public boolean isBlacklist() {
		return blacklist;
	}
	
	public void setBlacklist(boolean value) {
		blacklist = value;
	}

	/**
	 * add a Player to the world's whitelist
	 * 
	 * @param player the player's name/uuid
	 */
	public void addToWhitelist(String player) {
		if (!whitelist.contains(player)) whitelist.add(player);
	}
	
	public void addToWhitelist(List<String> players) {
		if (players != null) whitelist.addAll(players);
	}

	/**
	 * remove a Player from the world's whitelist
	 * 
	 * @param player the player's name/uuid
	 */
	public void removeWhitelisted(String player) {
		whitelist.remove(player);
	}

	public boolean isLoaded() {
		world = Bukkit.getWorld(name());
		return (world != null ? true : false);
	}

	public World getWorld() {
		return world;
	}

	public void unload() {
		if (world == null)
			return;
		Bukkit.unloadWorld(world, true);
	}
}
