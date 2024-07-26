package de.atlasmc.tick;

public interface AtlasThreadTask {
	
	String name();
	
	/**
	 * Called by the thread this tasked is registered.
	 * The given tick indicates the current tick of the thread or -1 if not called within tick e.g. shutdown or startup
	 * @param tick
	 * @throws Exception
	 */
	void tick(int tick) throws Exception;

}
