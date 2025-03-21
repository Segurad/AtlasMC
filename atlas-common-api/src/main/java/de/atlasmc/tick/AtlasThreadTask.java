package de.atlasmc.tick;

@FunctionalInterface
public interface AtlasThreadTask<T> {
	
	/**
	 * Called by the thread this tasked is registered.
	 * The given tick indicates the current tick of the thread or -1 if not called within tick e.g. shutdown or startup
	 * 
	 * @param context
	 * @param tick
	 * @throws Exception
	 */
	void run(T context, int tick) throws Exception;

}
