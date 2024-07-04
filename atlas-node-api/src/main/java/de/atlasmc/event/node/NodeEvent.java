package de.atlasmc.event.node;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.SyncThreadHolder;

/**
 * Events of this Node or the Atlas Network. 
 * If is synchronous this event will be fired only from the node thread
 */
public abstract class NodeEvent extends GenericEvent<AtlasNode, HandlerList> {

	public NodeEvent(AtlasNode eventSource) {
		this(false, eventSource);
	}
	
	public NodeEvent(boolean async, AtlasNode eventSource) {
		super(async, eventSource);
	}
	
	@Override
	public SyncThreadHolder getSyncThreadHolder() {
		return Atlas.getThreadHolder();
	}

}
