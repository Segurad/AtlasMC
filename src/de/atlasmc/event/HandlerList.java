package de.atlasmc.event;

import java.util.ArrayList;
import java.util.List;

public class HandlerList {
	
	private static final List<HandlerList> handlers = new ArrayList<>();
	private final List<EventExecutor> executors;
	
	public HandlerList() {
		handlers.add(this);
		this.executors = new ArrayList<>();
	}

}
