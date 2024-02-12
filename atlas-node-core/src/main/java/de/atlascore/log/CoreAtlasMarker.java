package de.atlascore.log;

import org.apache.logging.log4j.Marker;

import de.atlasmc.log.Log;

public class CoreAtlasMarker implements Marker {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Log logger;
	
	public CoreAtlasMarker(Log logger) {
		this.logger = logger;
	}
	
	@Override
	public Marker addParents(Marker... markers) {return this;}

	@Override
	public String getName() {
		return logger.getName();
	}
	
	public Log getLogger() {
		return logger;
	}

	@Override
	public Marker[] getParents() {
		return null;
	}

	@Override
	public boolean hasParents() {
		return false;
	}

	@Override
	public boolean isInstanceOf(Marker m) {
		return m == this;
	}

	@Override
	public boolean isInstanceOf(String name) {
		return this.logger.getName().equals(name);
	}

	@Override
	public boolean remove(Marker marker) {
		return false;
	}

	@Override
	public Marker setParents(Marker... markers) {
		return this;
	}
	
}
