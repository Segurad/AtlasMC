package de.atlasmc.entity;

public interface Painting extends Hanging {

	public Motive getMotive();
	
	public static enum Motive {
		KEBAB,
		AZTEC,
		ALBAN,
		AZTEC2,
		BOMB,
		PLANT,
		WASTELAND,
		POOL,
		COURBET,
		SEA,
		SUNSET,
		CREEBET,
		WANDERER,
		GRAHAM,
		MATCH,
		BUST,
		STAGE,
		VOID,
		SKULL_AND_ROSE,
		WITHER,
		FIGHTERS,
		POINTER,
		PIGSCENE,
		BURNING_SKULL,
		SKELETON,
		DONEKY_KONG;

		public static Motive getByID(int id) {
			Motive[] values = values();
			return values[id];
		}
	}

}
