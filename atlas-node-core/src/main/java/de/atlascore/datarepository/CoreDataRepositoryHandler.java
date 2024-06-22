package de.atlascore.datarepository;

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
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.concurrent.future.FutureListener;

public class CoreDataRepositoryHandler implements DataRepositoryHandler {
	
	private final CacheRepository cache;
	private final Map<String, LocalRepository> localRepos;
	private final Set<Repository> remotes;
	private final Map<String, Repository> repoByNamespace;
	
	public CoreDataRepositoryHandler(CacheRepository cache) {
		if (cache == null)
			throw new IllegalArgumentException("Cache repository can not be null!");
		this.cache = cache;
		localRepos = new ConcurrentHashMap<>();
		remotes = ConcurrentHashMap.newKeySet();
		repoByNamespace = new ConcurrentHashMap<>();
	}

	@Override
	public CacheRepository getCache() {
		return cache;
	}

	@Override
	public Collection<LocalRepository> getLocalRepos() {
		return localRepos.values();
	}

	@Override
	public Collection<Repository> getRemoteRepos() {
		return remotes;
	}
	
	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load) {
		RepositoryEntry entry = getLocal(key);
		if (entry != null)
			return new CompleteFuture<>(entry);
		if (!load)
			return CompleteFuture.getNullFuture();
		return getRemote(key);
	}
	
	private Future<RepositoryEntry> getRemote(NamespacedKey key) {
		Repository repo = repoByNamespace.get(key.getNamespace());
		if (repo == null)
			return CompleteFuture.getNullFuture();
		return repo.getEntry(key);
	}
	
	private RepositoryEntry getLocal(NamespacedKey key) {
		RepositoryEntry entry = null;
		for (LocalRepository local : localRepos.values()) {
			if (local.getNamespace(key.getKey()) == null)
				continue;
			entry = local.getEntry(key).getNow();
			if (entry != null)
				break;
		}
		if (entry == null)
			cache.getEntry(key);
		return entry;
	}
	
	@Override
	public void addRepo(Repository repo) {
		if (repo == null)
			throw new IllegalArgumentException("Repo can not be null!");
		if (repo instanceof LocalRepository local) {
			if (localRepos.putIfAbsent(repo.getName(), local) != null)
				return;
			Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
			for (RepositoryNamespace namespace : namespaces)
				repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
		} else if (remotes.add(repo)) {
			Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
			for (RepositoryNamespace namespace : namespaces)
				repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
		}	
	}

	@Override
	public void removeRepo(Repository repo) {
		if (repo == null)
			throw new IllegalArgumentException("Repo can not be null!");
		if (repo instanceof LocalRepository local) {
			if (!localRepos.remove(repo.getName(), local))
				return;
			Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
			for (RepositoryNamespace namespace : namespaces)
				repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
		} else if (remotes.remove(repo)) {
			Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
			for (RepositoryNamespace namespace : namespaces)
				repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
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
			RepositoryEntry entry = getLocal(key);
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
			getRemote(key).setListener(listener);
		}
		if (futureAll == null)
			return new CompleteFuture<>(entries);
		return futureAll;
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key) {
		return getEntry(key, true);
	}

	@Override
	public LocalRepository getLocalRepo(String name) {
		return localRepos.get(name);
	}

}
