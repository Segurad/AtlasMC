package de.atlasmc.node.entity;

import de.atlasmc.node.Location;
import de.atlasmc.node.SimpleLocation;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Turtle extends Animal {
	
	public static final NBTCodec<Turtle>
	NBT_HANDLER = NBTCodec
					.builder(Turtle.class)
					.include(Animal.NBT_HANDLER)
					.boolField("has_egg", Turtle::hasEgg, Turtle::setEgg, false)
					.build();
	
	boolean hasEgg();
	
	void setEgg(boolean egg);
	
	boolean isLayingEgg();
	
	void setLayingEgg(boolean laying);
	
	default Location getTravelPos() {
		return getTravelPos(new Location(getWorld()));
	}
	
	Location getTravelPos(Location loc);
	
	default void setTravelPos(SimpleLocation loc) {
		setTravelPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	void setTravelPos(int x, int y, int z);
	
	boolean isGoingHome();
	
	void setGoingHome(boolean home);
	
	boolean isTraveling();

	void setTraveling(boolean traveling);
	
	@Override
	default NBTCodec<? extends Turtle> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
