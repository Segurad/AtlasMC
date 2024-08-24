package de.atlascore.permission;

public class CorePermissionHandlerContextProvider extends CoreContextProvider {
	
	private final CorePermissionHandler handler;
	
	public CorePermissionHandlerContextProvider(CorePermissionHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public String set(String key, String context) {
		String old = super.set(key, context);
		handler.onContextChange(key, old, context);
		return old;
	}
	
	@Override
	public String removeContext(String key) {
		String old = super.removeContext(key);
		handler.onContextChange(key, old, null);
		return old;
	}
	
	@Override
	public boolean removeContext(String key, String context) {
		if (super.removeContext(key, context)) {
			handler.onContextChange(key, context, null);
			return true;
		}
		return false;
	}

}
