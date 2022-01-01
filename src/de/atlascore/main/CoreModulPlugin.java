package de.atlascore.main;

import de.atlasmc.entity.EntityType;
import de.atlasmc.plugin.Plugin;

public interface CoreModulPlugin extends Plugin {

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

	public void initNode(CoreNodeBuilder nodebuilder);

}
