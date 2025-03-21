package de.atlascore.command.executor;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.datarepository.RepositoryEntryUpdate.Change;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.Pair;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:datarepo/update")
public class CoreDataRepositoryUpdateCommand implements CommandExecutor {
	
	@Override
	public boolean execute(CommandContext context) {
		String rawRepo = context.getArgument("repo", String.class);
		CommandSender sender = context.getSender();
		LocalRepository repo = Atlas.getDataHandler().getLocalRepo(rawRepo);
		if (repo == null) {
			sender.sendMessage("No repository found with name: " + rawRepo);
			return true;
		}
		// Update full repo
		String rawNamespace = context.getArgument("namespace", String.class, false);
		if (rawNamespace == null) {
			notifyUpdate(sender, repo.update());
			return true;
		}
		// Update full namespace
		String rawEntry = context.getArgument("entry", String.class, false);
		if (rawEntry == null) {
			RepositoryNamespace namespace = repo.getNamespace(rawNamespace);
			try {
				notifyUpdate(sender, namespace.update());
			} catch (IOException e) {
				sender.sendMessage("Error while updating namespace!");
				Atlas.getLogger().error("Error while updating namespace: " + rawNamespace, e);
			}
			return true;
		}
		// Upadete Entry
		repo.getEntry(NamespacedKey.of(rawNamespace, rawEntry)).setListener((future) -> {
			if (!future.isSuccess()) {
				sender.sendMessage("No entry found with name: " + rawEntry);
				return;
			}
			RepositoryEntry entry = future.getNow();
			entry.update().setListener((entryFuture) -> {
				if (entryFuture.isSuccess()) {
					RepositoryEntryUpdate update = entryFuture.getNow();
					if (update.getEntryChange() == null && !update.hasFilesChanged()) {
						sender.sendMessage("Nothing update...");
						return;
					}
					sender.sendMessage("=== Updated: " + update.getKey() + " ===");
					for (Pair<String, Change> change : update.getFilesChanged())
						sender.sendMessage("- " + change.getValue1() + " [" + change.getValue2().symbol() + "]");
				} else {
					sender.sendMessage("Failed to perform update!");
				}
			});
		});
		return true;
	}
	
	private void notifyUpdate(CommandSender sender, Collection<RepositoryEntryUpdate> updated) {
		if (updated.isEmpty()) {
			sender.sendMessage("Nothing update...");
			return;
		}
		for (RepositoryEntryUpdate update : updated) {
			sender.sendMessage("=== Updated: " + update.getKey() + " ===");
			for (Pair<String, Change> change : update.getFilesChanged())
				sender.sendMessage("- " + change.getValue1() + " [" + change.getValue2().symbol() + "]");
		}
	}

}
