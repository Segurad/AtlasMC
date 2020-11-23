package de.atlasmc.world;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.event.Listener;

public class WorldManager {

	private List<WorldData> data = new ArrayList<WorldData>();

	public void addData(WorldData data) {
		if (!this.data.contains(data)) {
			this.data.add(data);
		}
	}

	public List<WorldData> getData() {
		return data;
	}

	public void removeData(WorldData data) {
		this.data.remove(data);
	}
	
	public Listener createBlockListener() {
		return new WorldManagerBlockEvents(this);
	}
	
	public Listener createEntityListener() {
		return new WorldManagerEntityEvents(this);
	}

	public WorldData getData(String name) {
		for (WorldData data : data) {
			if (data.name().equals(name)) return data;
		}
		return null;
	}
	
	public WorldData getData(World world) {
		return getData(world.getName());
	}

}
