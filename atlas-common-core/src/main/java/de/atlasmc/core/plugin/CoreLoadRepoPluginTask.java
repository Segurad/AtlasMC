package de.atlasmc.core.plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.util.concurrent.future.CumulativeFuture;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLoadRepoPluginTask implements Runnable {

	private final CorePluginManager manager;
	private final Log log;
	private final Map<NamespacedKey, CompletableFuture<Plugin>> entryFutures;
	private final Object pluginLock;
	
	public CoreLoadRepoPluginTask(CorePluginManager manager, Map<NamespacedKey, CompletableFuture<Plugin>> entryFutures, Object pluginLock) {
		this.manager = manager;
		this.entryFutures = entryFutures;
		this.pluginLock = pluginLock;
		log = manager.getLogger();
	}

	@Override
	public void run() {
		Map<NamespacedKey, Future<RepositoryEntry>> entries = fetchEntries();
		if (entries.isEmpty())
			return;
		entries = makeAvailable(entries);
		if (entries.isEmpty())
			return;
		File tempDir = new File(Atlas.getTempDir(), "plugins/");
		tempDir.mkdirs();
		Map<File, CompletableFuture<Plugin>> files = new HashMap<>(entries.size());
		for (Entry<NamespacedKey, Future<RepositoryEntry>> entry : entries.entrySet()) {
			Future<RepositoryEntry> future = entry.getValue();
			RepositoryEntry repoEntry = future.getNow();
			try {
				File file;
				if (repoEntry.isDirectory()) {
					file = new File(tempDir, repoEntry.getNamespacedKey().key());
					repoEntry.copyTo(file);
				} else {
					repoEntry.copyTo(tempDir);
					file = new File(tempDir, repoEntry.getFiles().get(0).file());
				}
				files.put(file, entryFutures.get(entry.getKey()));
			} catch (IOException e) {
				NamespacedKey key = entry.getKey();
				String msg = "Failed to copy repository entry to temp dir for plugin: " + key;
				log.error(msg, e);
				entryFutures.get(key).complete(null, new PluginException(msg));
			}
		}
		if (files.isEmpty())
			return;
		entries = null;
		CoreLoadPluginTask task = new CoreLoadPluginTask(manager, files, pluginLock);
		task.run();
	}
	
	private Map<NamespacedKey, Future<RepositoryEntry>> makeAvailable(Map<NamespacedKey, Future<RepositoryEntry>> entries) {
		for (Entry<NamespacedKey, Future<RepositoryEntry>> entry : entries.entrySet()) {
			Future<RepositoryEntry> future = entry.getValue();
			RepositoryEntry repoEntry = future.getNow();
			if (repoEntry == null)
				continue;
			entry.setValue(repoEntry.makeAvailable());
		}
		Collection<Future<RepositoryEntry>> futures = entries.values(); 
		CumulativeFuture<RepositoryEntry> cumFuture = new CumulativeFuture<>(futures);
		try {
			cumFuture.get();
		} catch (InterruptedException e) {
			log.error("Interruped while waiting for plugin repository entry data!", e);
		} catch (ExecutionException e) {
			log.error("Exception while waiting for plugin repository entry data!", e);
		}
		if (!cumFuture.isSuccess()) {
			Iterator<Entry<NamespacedKey, Future<RepositoryEntry>>> iter = entries.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<NamespacedKey, Future<RepositoryEntry>> entry = iter.next();
				NamespacedKey key = entry.getKey();
				Future<RepositoryEntry> value = entry.getValue();
				if (!value.isSuccess()) {
					String msg = "Failed to load data for repositry entry: " + key;
					log.error(msg, value.cause());
					entryFutures.get(key).complete(null, new PluginException(msg, value.cause()));
					iter.remove();
				}
			}
			return Map.of();
		}
		return entries;
	}
	
	@NotNull
	private Map<NamespacedKey, Future<RepositoryEntry>> fetchEntries() {
		DataRepositoryHandler handler = Atlas.getDataHandler();
		Map<NamespacedKey, Future<RepositoryEntry>> repoEntryFutures = new HashMap<>(entryFutures.size());
		for (NamespacedKey key : entryFutures.keySet()) {
			Future<RepositoryEntry> future = handler.getEntry(key);
			repoEntryFutures.put(key, future);
		}
		Collection<Future<RepositoryEntry>> futures = repoEntryFutures.values(); 
		CumulativeFuture<RepositoryEntry> cumFuture = new CumulativeFuture<>(futures);
		try {
			cumFuture.get();
		} catch (InterruptedException e) {
			log.error("Interruped while fetching plugin repository entries!", e);
		} catch (ExecutionException e) {
			log.error("Exception while fetching plugin repository entries!", e);
		}
		if (!cumFuture.isSuccess()) {
			Iterator<Entry<NamespacedKey, Future<RepositoryEntry>>> iter = repoEntryFutures.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<NamespacedKey, Future<RepositoryEntry>> entry = iter.next();
				NamespacedKey key = entry.getKey();
				Future<RepositoryEntry> value = entry.getValue();
				if (!value.isSuccess()) {
					String msg = "Failed to request repositry entry: " + key;
					log.error(msg, value.cause());
					entryFutures.get(key).complete(null, new PluginException(msg, value.cause()));
					iter.remove();
				}
			}
			return Map.of();
		}
		return repoEntryFutures;
	}

}
