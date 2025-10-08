package de.atlasmc.core.datarepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
	private final Map<String, Repository> remotes;
	private final Map<String, Repository> repoByNamespace;
	private final Map<UUID, Repository> repoByUUID;
	
	public CoreDataRepositoryHandler(CacheRepository cache) {
		if (cache == null)
			throw new IllegalArgumentException("Cache repository can not be null!");
		this.cache = cache;
		localRepos = new ConcurrentHashMap<>();
		remotes = new ConcurrentHashMap<>();
		repoByNamespace = new ConcurrentHashMap<>();
		repoByUUID = new ConcurrentHashMap<>();
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
		return remotes.values();
	}
	
	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load) {
		if (key.hasChildKey()) {
			NamespacedKey repoKey = key.getChildKey();
			Repository repo = getRepo(key.getNamespace());
			return repo.getEntry(repoKey);
		}
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
			entry = local.getLocalEntry(key);
			if (entry != null)
				break;
		}
		if (entry == null)
			return cache.getLocalEntry(key);
		return entry;
	}
	
	@Override
	public void addRepo(Repository repo) {
		if (repo == null)
			throw new IllegalArgumentException("Repo can not be null!");
		if (repo instanceof LocalRepository local) {
			if (localRepos.putIfAbsent(repo.getName(), local) != null)
				return;
		} else if (remotes.putIfAbsent(repo.getName(), repo) != null) {
			return;
		}	
		Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
		for (RepositoryNamespace namespace : namespaces)
			repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
		repoByUUID.put(repo.getUUID(), repo);
	}

	@Override
	public void removeRepo(Repository repo) {
		if (repo == null)
			throw new IllegalArgumentException("Repo can not be null!");
		if (repo instanceof LocalRepository local) {
			if (!localRepos.remove(repo.getName(), local))
				return;
		} else if (!remotes.remove(repo.getName(), repo)) {
			return;
		}
		Collection<? extends RepositoryNamespace> namespaces = repo.getNamespaces();
		for (RepositoryNamespace namespace : namespaces)
			repoByNamespace.putIfAbsent(namespace.getNamespace(), repo);
		repoByUUID.remove(repo.getUUID(), repo);
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
						futureAll.complete(entries);
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
							finalFuture.complete(entries);
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

	@Override
	public Repository getRepo(String name) {
		Repository repo = localRepos.get(name);
		if (repo != null)
			return repo;
		return remotes.get(name);
	}
	
	@Override
	public Repository getRepo(UUID uuid) {
		return repoByUUID.get(uuid);
	}

	@Override
	public void addRepos(Collection<Repository> repositories) {
		for (Repository repo : repositories)
			addRepo(repo);
	}

}
