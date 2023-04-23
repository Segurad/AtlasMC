package de.atlasmc.plugin;

import de.atlasmc.entity.EntityType;

public interface CoremodulPlugin extends Plugin {

	/**
	 * Init <u>ALL</u> static fields of this class e.g. {@link Material}
	 * @param clazz
	 * @return true if initialized
	 */
	public boolean initBaseFields(Class<?> clazz);

	/**
	 * Add additional types to classes like {@link Material} or {@link EntityType}
	 */
	public void addTypes();

	public void initNode(NodeBuilder nodebuilder);

}
