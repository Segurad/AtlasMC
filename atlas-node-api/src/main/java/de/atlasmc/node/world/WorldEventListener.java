package de.atlasmc.node.world;

import org.joml.Vector3i;

public interface WorldEventListener {
	
	default void playEffect(Vector3i loc, WorldEvent effect) {
		playEffect(loc, effect, null, true);
	}
	
	/**
	 * 
	 * @param loc
	 * @param effect
	 * @param data
	 * @implNote {@link #playEffect(int, int, int, WorldEvent, Object, boolean)}
	 */
	default void playEffect(Vector3i loc, WorldEvent effect, Object data) {
		playEffect(loc, effect, data, true);
	}
	
	/**
	 * Plays a sound or particle effect
	 * @param loc
	 * @param effect
	 * @param data
	 * @param relativeSound
	 */
	void playEffect(Vector3i loc, WorldEvent effect, Object data, boolean relativeSound);

}
