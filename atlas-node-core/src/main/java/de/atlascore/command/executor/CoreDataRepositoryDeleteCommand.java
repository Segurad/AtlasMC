package de.atlascore.command.executor;

import java.io.IOException;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.command.Commands;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:datarepo/delete")
public class CoreDataRepositoryDeleteCommand implements CommandExecutor {
	
	@Override
	public boolean execute(CommandContext context) {
		String rawRepo = context.getArgument("repo", String.class);
		CommandSender sender = context.getSender();
		boolean skipConfirm = context.getLastArg().getName().equals("confirm");
		LocalRepository repo = Atlas.getDataHandler().getLocalRepo(rawRepo);
		if (repo == null) {
			sender.sendMessage("No repository found with name: " + rawRepo);
			return true;
		}
		// Delete repo
		String rawNamespace = context.getArgument("namespace", String.class, false);
		if (rawNamespace == null) {
			if (skipConfirm) {
				repo.delete().setListener((future) -> {
					if (future.isSuccess() && future.getNow()) {
						sender.sendMessage("Repository deleted");
					} else {
						sender.sendMessage("Failed to delete repository!");
					}
				});
			} else {
				Commands.awaitConfirm(sender, 1200).setListener((confirm) -> {
					CommandContext ctx = confirm.getNow();
					String confirmValue = ctx.getArgument("confirmation", String.class, false);
					if (!rawRepo.equalsIgnoreCase(confirmValue)) {
						sender.sendMessage("Invalid repository name: " + confirmValue);
						return;
					}
					repo.delete().setListener((future) -> {
						if (future.isSuccess() && future.getNow()) {
							sender.sendMessage("Repository deleted");
						} else {
							sender.sendMessage("Failed to delete repository!");
						}
					});
				});
				sender.sendMessage("Confirm this action with: /confirm " + rawRepo);
			}
			return true;
		}
		// Update full namespace
		String rawEntry = context.getArgument("entry", String.class, false);
		if (rawEntry == null) {
			RepositoryNamespace namespace = repo.getNamespace(rawNamespace);
			if (skipConfirm) {
				try {
					if (namespace.delete()) {
						sender.sendMessage("Namespace deleted");
					} else {
						sender.sendMessage("Failed to delete namespace!");
					}
				} catch (IOException e) {
					sender.sendMessage("Error while deleting namespace!");
					Atlas.getLogger().error("Erro while deleting namespace: " + rawNamespace, e);
				}
			} else {
				Commands.awaitConfirm(sender, 1200).setListener((confirm) -> {
					CommandContext ctx = confirm.getNow();
					String confirmValue = ctx.getArgument("confirmation", String.class, false);
					if (!rawNamespace.equalsIgnoreCase(confirmValue)) {
						sender.sendMessage("Invalid namespace name: " + confirmValue);
						return;
					}
					repo.delete().setListener((future) -> {
						if (future.isSuccess() && future.getNow()) {
							sender.sendMessage("Namespace deleted");
						} else {
							sender.sendMessage("Failed to delete namespace!");
						}
					});
				});
				sender.sendMessage("Confirm this action with: /confirm " + rawRepo);
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
			if (skipConfirm) {
				entry.delete().setListener((delFuture) -> {
					if (delFuture.isSuccess() && delFuture.getNow()) {
						sender.sendMessage("Entry deleted");
					} else {
						sender.sendMessage("Failed to delete entry!");
					}
				});
				sender.sendMessage("Entry deleted");
			} else {
				Commands.awaitConfirm(sender, 1200).setListener((confirm) -> {
					CommandContext ctx = confirm.getNow();
					String confirmValue = ctx.getArgument("confirmation", String.class, false);
					if (!rawEntry.equalsIgnoreCase(confirmValue)) {
						sender.sendMessage("Invalid entry name: " + confirmValue);
						return;
					}
					repo.delete().setListener((defFuture) -> {
						if (defFuture.isSuccess() && defFuture.getNow()) {
							sender.sendMessage("Repository deleted");
						} else {
							sender.sendMessage("Failed to delete repository!");
						}
					});
				});
				sender.sendMessage("Confirm this action with: /confirm " + rawRepo);
			}
		});
		return true;
	}

}
