package de.atlasmc.core.node.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.log.Log;
import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.node.server.ServerDeploymentException;
import de.atlasmc.scheduler.AtlasTask;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.concurrent.future.CumulativeFuture;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.file.YamlConfiguration;

/**
 * Task that ensures all required templates, data, plugins are available.
 * Copies templates and data to server workdir.
 */
public class CoreLocalServerPreparingTask extends AtlasTask {

	private final CoreLocalServer server;
	private final CompletableFuture<Boolean> future;
	private volatile int stage = 0;
	private final Map<NamespacedKey, RepositoryEntry> data;
	private volatile boolean failed;
	
	public CoreLocalServerPreparingTask(CoreLocalServer server) {
		this.server = server;
		this.future = new CompletableFuture<>();
		this.data = new ConcurrentHashMap<>();
	}
	
	public Future<Boolean> getFuture() {
		return future;
	}
	
	private void copyEntries() {
		Log log = server.getLogger();
		log.info("Writing server data...");
		File workDir = server.getWorkdir();
		ServerConfig cfg = server.getConfig();
		{ // write config to server dir
			YamlConfiguration serverConfig = new YamlConfiguration(cfg.getConfig());
			try {
				serverConfig.save(new File(workDir, "server-config.yml"));
			} catch (IOException e) {
				log.error("Error while writing server config!", e);
				failed = true;
				future.complete(false);
				return;
			}
		}
		// load template if exists
		NamespacedKey key = cfg.getTemplate();
		if (key != null) {
			RepositoryEntry entry = data.get(key);
			try {
				entry.copyTo(workDir, true);
			} catch (IOException e) {
				log.error("Error while copying template!", e);
			}
		}
		// create worlds directory
		FileUtils.ensureDir(server.getWorlddir());
		// load data
		if (cfg.hasData()) {
			Map<NamespacedKey, String> data = cfg.getData();
			for (Entry<NamespacedKey, String> dataEntry : data.entrySet()) {
				RepositoryEntry entry = this.data.get(dataEntry.getKey());
				try {
					entry.copyTo(new File(workDir, dataEntry.getValue()), true);
				} catch (IOException e) {
					log.error("Error while copying data", e);
				}
			}
		}
	}

	private void makeEntriesAvailble() {
		Log log = server.getLogger();
		log.info("Pulling server data...");
		ArrayList<Future<RepositoryEntry>> futures = new ArrayList<>(data.size());
		for (RepositoryEntry entry : data.values()) {
			futures.add(entry.makeAvailable());
		}
		data.clear();
		CumulativeFuture<RepositoryEntry> cumFuture = new CumulativeFuture<>(futures);
		cumFuture.setListener(future -> {
			for (Future<RepositoryEntry> entryFuture : future.getNow()) {
				if (entryFuture.isSuccess()) {
					RepositoryEntry entry = entryFuture.getNow();
					data.put(entry.getNamespacedKey(), entry);
				} else {
					if (entryFuture.cause() != null)
						log.error("Failed to pull entry data!", entryFuture.cause());
				}
			}
			Atlas.getScheduler().runAsyncTaskLater(Atlas.getSystem(), this, 1L);
		});
	}
	
	private void checkData(String msg) {
		Log log = server.getLogger();
		ServerConfig cfg = server.getConfig();
		NamespacedKey template = cfg.getTemplate();
		boolean failed = this.failed;
		if (template != null && !data.containsKey(template)) {
			log.info(msg, template);
			failed = true;
		}
		if (cfg.hasData()) {
			for (NamespacedKey key : cfg.getData().keySet()) {
				if (!data.containsKey(key)) {
					log.info(msg, key);
					failed = true;
				}
			}
		}
		if (cfg.hasPlugins()) {
			for (Entry<NamespacedKey, NamespacedKey> plugin : cfg.getPlugins().entrySet()) {
				if (!data.containsKey(plugin.getKey())) {
					log.info(msg, plugin.getKey());
					failed = true;
				}
				if (!data.containsKey(plugin.getValue())) {
					log.info(msg, plugin.getValue());
					failed = true;
				}
			}
		}
		this.failed = failed;
		Atlas.getScheduler().runAsyncTaskLater(Atlas.getSystem(), this, 1L);
	}

	private void fetchRepoEntries() {
		Log log = server.getLogger();
		log.info("Aquireing repository entries...");
		ServerConfig cfg = server.getConfig();
		Collection<Future<Collection<RepositoryEntry>>> futures = new ArrayList<>();
		DataRepositoryHandler repoHandler = Atlas.getDataHandler();
		if (cfg.hasTemplate()) {
			NamespacedKey key = cfg.getTemplate();
			Future<Collection<RepositoryEntry>> templateFuture = repoHandler.getEntries(List.of(key));
			templateFuture.setListener((future) -> {
				if (!future.isSuccess()) {
					log.info("Failed to prepare template: {}", key);
					failed = true;
				} else {
					Collection<RepositoryEntry> entries = future.getNow();
					for (RepositoryEntry entry : entries)
						this.data.put(entry.getNamespacedKey(), entry);
				}
			});
			futures.add(templateFuture);
		}
		if (cfg.hasData()) {
			Collection<NamespacedKey> key = cfg.getData().keySet();
			Future<Collection<RepositoryEntry>> dataFuture = repoHandler.getEntries(key);
			dataFuture.setListener((future) -> {
				if (!future.isSuccess()) {
					log.info("Failed to prepare data!");
					failed = true;
				} else {
					Collection<RepositoryEntry> entries = future.getNow();
					for (RepositoryEntry entry : entries) {
						this.data.put(entry.getNamespacedKey(), entry);
					}
				}
			});
			futures.add(dataFuture);
		}
		if (cfg.hasPlugins()) {
			Map<NamespacedKey, NamespacedKey> plugins = cfg.getPlugins();
			Future<Collection<RepositoryEntry>> pluginFuture = repoHandler.getEntries(plugins.keySet());
			pluginFuture.setListener((future) -> {
				if (!future.isSuccess()) {
					log.info("Failed to prepare plugins!");
					failed = true;
				} else {
					Collection<RepositoryEntry> entries = future.getNow();
					for (RepositoryEntry entry : entries) {
						this.data.put(entry.getNamespacedKey(), entry);
					}
				}
			});
			futures.add(pluginFuture);
			Future<Collection<RepositoryEntry>> pluginCfgFuture = repoHandler.getEntries(plugins.values());
			pluginCfgFuture.setListener((future) -> {
				if (!future.isSuccess()) {
					log.info("Failed to prepare plugin configurations!");
					failed = true;
				} else {
					Collection<RepositoryEntry> entries = future.getNow();
					for (RepositoryEntry entry : entries) {
						this.data.put(entry.getNamespacedKey(), entry);
					}
				}
			});
			futures.add(pluginCfgFuture);
		}
		CumulativeFuture<Collection<RepositoryEntry>> cumFuture = new CumulativeFuture<>(futures);
		cumFuture.setListener((_) -> {
			Atlas.getScheduler().runAsyncTaskLater(Atlas.getSystem(), this, 1L);
		});
	}

	@Override
	public void run() {
		if (failed) {
			future.complete(false);
			return;
		}
		switch(stage) {
		case 0: // fetching required entries
			stage++;
			fetchRepoEntries();
			break;
		case 1:
			stage++;
			checkData("Failed to fetch entry: {}");
			break;
		case 2:
			stage++;
			makeEntriesAvailble();
			break;
		case 3:
			stage++;
			checkData("Failed to pull entry data: {}");
			break;
		case 4:
			stage++;
			copyEntries();
			future.complete(true);
			break;
		default:
			future.complete(false, new ServerDeploymentException("Invalid stage: " + stage));
		}
	}

}
