package de.atlascore.plugin;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class CorePluginLock extends WeakReference<Object> {

	public final CoreRegisteredPlugin plugin;
	
	public CorePluginLock(CoreRegisteredPlugin plugin, Object referent, ReferenceQueue<? super Object> q) {
		super(referent, q);
		this.plugin = plugin;
	}

}
