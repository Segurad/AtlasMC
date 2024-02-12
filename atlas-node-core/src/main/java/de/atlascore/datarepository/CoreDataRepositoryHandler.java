package de.atlascore.datarepository;

import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.PluginConfigEntry;
import de.atlasmc.datarepository.PluginEntry;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.SchematicEntry;
import de.atlasmc.datarepository.WorldEntry;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.concurrent.future.FutureListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.CacheRepository;
import de.atlasmc.datarepository.DataRepositoryHandler;

public class CoreDataRepositoryHandler implements DataRepositoryHandler {
	
	private final CacheRepository cache;
	private final LocalRepository local;
	private final Set<Repository> remotes;
	private final Map<String, Repository> repoByNamespace;
	
	public CoreDataRepositoryHandler(CacheRepository cache, LocalRepository local) {
		if (cache == null)
			throw new IllegalArgumentException("Cache repository can not be null!");
		if (local == null)
			throw new IllegalArgumentException("Local repository can not be null!");
		this.cache = cache;
		this.local = local;
		remotes = ConcurrentHashMap.newKeySet();
		repoByNamespace = new ConcurrentHashMap<>();
	}

	@Override
	public CacheRepository getCache() {
		return cache;
	}

	@Override
	public LocalRepository getLocal() {
		return local;
	}

	@Override
	public Collection<Repository> getRemoteRepos() {
		return remotes;
	}

	@Override
	public Future<PluginEntry> getPlugin(NamespacedKey key, boolean load) {
		return getEntry(key, PluginEntry.class, load);
	}

	@Override
	public Future<WorldEntry> getWorld(NamespacedKey key, boolean load) {
		return getEntry(key, WorldEntry.class, load);
	}

	@Override
	public Future<SchematicEntry> getSchematic(NamespacedKey key, boolean load) {
		return getEntry(key, SchematicEntry.class, load);
	}

	@Override
	public Future<PluginConfigEntry> getPluginConfiguration(NamespacedKey key, boolean load) {
		return getEntry(key, PluginConfigEntry.class, load);
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load) {
		return getEntry(key, RepositoryEntry.class, load);
	}
	
	private <E extends RepositoryEntry> Future<E> getEntry(NamespacedKey key, Class<E> clazz, boolean load) {
		E entry = getLocal(key, clazz);
		if (entry != null)
			return new CompleteFuture<>(entry);
		if (!load)
			return CompleteFuture.getNullFuture();
		return getRemote(key, clazz);
	}
	
	private <E extends RepositoryEntry> Future<E> getRemote(NamespacedKey key, Class<E> clazz) {
		Repository repo = repoByNamespace.get(key.getNamespace());
		if (repo == null)
			return CompleteFuture.getNullFuture();
		RemoteFuture<E> future = new RemoteFuture<>(clazz);
		repo.getRepoEntry(key).setListener(future);
		return future;
	}
	
	@SuppressWarnings("unchecked")
	private <E extends RepositoryEntry> E getLocal(NamespacedKey key, Class<E> clazz) {
		RepositoryEntry entry = null;
		if (local.getNamespaces().contains(key.getNamespace()))
			entry = local.getEntry(key);
		if (entry == null)
			cache.getEntry(key);
		return clazz.isInstance(entry) ? (E) entry : null;
	}
	
	@Override
	public void addRepo(Repository repo) {
		if (repo == null)
			throw new IllegalArgumentException("Repo can not be null!");
		if (remotes.add(repo)) {
		Collection<String> namespaces = repo.getNamespaces();
			for (String namespace : namespaces)
				repoByNamespace.putIfAbsent(namespace, repo);
		}	
	}

	@Override
	public void removeRepo(Repository repo) {
		if (remotes.remove(repo)) {
			Collection<String> namespaces = repo.getNamespaces();
			for (String namespace : namespaces)
				repoByNamespace.remove(namespace, repo);
		}
	}
	
	@Override
	public Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys) {
		if (keys == null)
			throw new IllegalArgumentException("Keys can not be null!");
		final List<RepositoryEntry> entries = new ArrayList<>(keys.size());
		FutureListener<RepositoryEntry> listener = null;
		final AtomicInteger waiting = new AtomicInteger(keys.size());
		CompletableFuture<Collection<RepositoryEntry>> futureAll = null;
		for (NamespacedKey key : keys) {
			RepositoryEntry entry = getLocal(key, RepositoryEntry.class);
			if (entry != null) {
				synchronized (entries) {
					entries.add(entry);
					if (waiting.decrementAndGet() <= 0 && futureAll != null)
						futureAll.finish(entries);
				}
				continue;
			}
			if (listener == null) {
				futureAll = new CompletableFuture<>();
				final CompletableFuture<Collection<RepositoryEntry>> finalFuture = futureAll;
				listener = (future) -> {
					RepositoryEntry futureEntry = future.getNow();
					if (futureEntry == null)
						return;
					synchronized (entries) {
						entries.add(futureEntry);
						if (waiting.decrementAndGet() <= 0)
							finalFuture.finish(entries);
					}
				};
			}
			getRemote(key, RepositoryEntry.class).setListener(listener);
		}
		if (futureAll == null)
			return new CompleteFuture<>(entries);
		return futureAll;
	}
	
	private static class RemoteFuture<V extends RepositoryEntry> extends CompletableFuture<V> implements FutureListener<RepositoryEntry> {
		
		private final Class<V> entryClass;
		
		public RemoteFuture(Class<V> entryClass) {
			this.entryClass = entryClass;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void complete(Future<RepositoryEntry> future) {
			RepositoryEntry entry = future.getNow();
			if (entryClass.isInstance(entry)) {
				finish((V) entry);
			} else {
				finish(null);
			}
		}
		
	}

}
