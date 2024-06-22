package de.atlascore.command.executor;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.NamespaceStatus;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:datarepo/status")
public class CoreDataRepositoryStatusCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		CommandSender sender = context.getSender();
		String reponame = context.getArgument("repo", String.class);
		LocalRepository repo = Atlas.getDataHandler().getLocalRepo(reponame);
		if (repo == null) {
			sender.sendMessage("No repository found with name: " + reponame);
			return true;
		}
		String namespace = context.getArgument("namespace", String.class, false);
		if (namespace == null) {
			for (RepositoryNamespace ns : repo.getNamespaces()) {
				sendStatus(sender, ns);
			}
		} else {
			RepositoryNamespace ns = repo.getNamespace(namespace);
			if (ns == null) {
				sender.sendMessage("No namespace found with name: " + namespace);
			} else {
				sendStatus(sender, ns);
			}
		}
		return true;
	}
	
	private void sendStatus(CommandSender sender, RepositoryNamespace namespace) {
		NamespaceStatus status = null;
		try {
			status = namespace.getStatus();
		} catch (IOException e) {
			sender.sendMessage("Error while reading status for: " + namespace.getNamespace());
			Atlas.getLogger().error("Error while reading repository namespace status: " + namespace.getNamespace(), e);
			return;
		}
		if (!status.touched().isEmpty() || !status.untracked().isEmpty()) {
			sender.sendMessage("=== " + namespace.getNamespace() + " ===");
		} else {
			return;
		}
		Collection<RepositoryEntry> touched = status.touched();
		if (!touched.isEmpty()) {
			sender.sendMessage("Touched:");
			for (RepositoryEntry entry : touched)
				sender.sendMessage("- " + entry.getKey());
		}
		Collection<String> untracked = status.untracked();
		if (!untracked.isEmpty()) {
			sender.sendMessage("Untracked:");
			for (String entry : untracked)
				sender.sendMessage("- " + entry);
		}
	}

}
