package de.atlasmc.world.entitytracker;

import java.util.Collection;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.annotation.NotNull;

public interface TrackingTarget<T extends Entity> {
	
	@NotNull
	Class<T> getTarget();
	
	@NotNull
	Collection<T> getEntities();
	
	@NotNull
	Collection<T> getEntities(int x, int z);
	
	@NotNull
	<C extends Collection<T>> C getEntities(int x, int z, C entities);
	
}
