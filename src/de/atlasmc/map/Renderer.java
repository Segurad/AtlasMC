package de.atlasmc.map;

public interface Renderer {

	/**
	 * 
	 * @param data
	 * @return true if changes are made
	 */
	public default boolean render(MapData data) {
		return render(data, 0, 0);
	}
	
	/**
	 * 
	 * @param data
	 * @param offsetX
	 * @param offsetY
	 * @return true if changes are made
	 */
	public boolean render(MapData data, int offsetX, int offsetY);
}
