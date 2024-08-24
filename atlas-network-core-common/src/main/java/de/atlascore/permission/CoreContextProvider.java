package de.atlascore.permission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.permission.ContextProvider;

public class CoreContextProvider implements ContextProvider {

	private final Map<String, String> context;
	private boolean contextChanged;
	
	public CoreContextProvider() {
		context = new ConcurrentHashMap<>();
	}
	
	@Override
	public String set(String key, String context) {
		String old = this.context.put(key, context);
		if (context == old)
			return old;
		if (context != null && context.equals(old))
			return old;
		contextChanged = true;
		return old;
	}

	@Override
	public boolean hasChangedContext() {
		return contextChanged;
	}

	@Override
	public void changedContext() {
		contextChanged = false;
	}
	
	@Override
	public Map<String, String> getContext() {
		return context;
	}
	
	@Override
	public boolean removeContext(String key, String context) {
		if (this.context.remove(key, context)) {
			contextChanged = true;
			return true;
		}
		return false;
	}
	
	@Override
	public String removeContext(String key) {
		String old = this.context.remove(key);
		if (old != null)
			contextChanged = true;
		return old;
	}

	@Override
	public String getContext(String key) {
		return context.get(key);
	}

}
