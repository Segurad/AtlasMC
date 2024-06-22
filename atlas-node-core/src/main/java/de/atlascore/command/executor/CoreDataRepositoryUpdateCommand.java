package de.atlascore.command.executor;

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
import de.atlasmc.util.concurrent.future.FutureListener;

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
		final FutureListener<Collection<RepositoryEntryUpdate>> listener = (future) -> {
			if (future.isSuccess()) {
				Collection<RepositoryEntryUpdate> updated = future.getNow();
				if (updated.isEmpty()) {
					sender.sendMessage("Nothing update...");
					return;
				}
				for (RepositoryEntryUpdate update : updated) {
					sender.sendMessage("=== Updated: " + update.getKey() + " ===");
					for (Pair<String, Change> change : update.getFilesChanged())
						sender.sendMessage("- " + change.getValue1() + " [" + change.getValue2().symbol() + "]");
				}
			} else {
				sender.sendMessage("Failed to perform update!");
			}
		};
		// Update full repo
		String rawNamespace = context.getArgument("namespace", String.class, false);
		if (rawNamespace == null) {
			repo.update().setListener(listener);
			return true;
		}
		// Update full namespace
		String rawEntry = context.getArgument("entry", String.class, false);
		if (rawEntry == null) {
			RepositoryNamespace namespace = repo.getNamespace(rawNamespace);
			namespace.update().setListener(listener);
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

}
