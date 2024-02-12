package de.atlasmc.plugin;

public interface CoremodulPlugin extends Plugin {

	/**
	 * Called during the initialization of the node
	 * @param nodebuilder
	 */
	public void initNode(NodeBuilder nodebuilder);

}
