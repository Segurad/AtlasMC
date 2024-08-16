package de.atlascore.atlasnetwork.master;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CorePermissionManager implements PermissionManager {

	private de.atlasmc.master.PermissionManager manager;
	
	@Override
	public Future<List<PermissionGroup>> getGroups(String... names) {
		return CompleteFuture.of(manager.getGroups(names));
	}

	@Override
	public Future<PermissionGroup> getGroup(String name) {
		return CompleteFuture.of(manager.getGroup(name));
	}

	@Override
	public Future<PermissionHandler> getHandler(AtlasPlayer player) {
		return CompleteFuture.of(manager.getHandler(player));
	}

	@Override
	public PermissionGroup getCachedGroup(String name) {
		return manager.getGroup(name);
	}

	@Override
	public List<PermissionGroup> getCachedGroups(String... names) {
		return manager.getGroups(names);
	}

	@Override
	public PermissionHandler getCachedHandler(AtlasPlayer player) {
		return manager.getHandler(player);
	}

}
