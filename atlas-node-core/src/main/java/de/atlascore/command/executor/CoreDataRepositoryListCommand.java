package de.atlascore.command.executor;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:datarepo/list")
public class CoreDataRepositoryListCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		CommandSender sender = context.getSender();
		String reponame = context.getArgument("repo", String.class, false);
		if (reponame == null) {
			LocalRepository repo = Atlas.getDataHandler().getLocalRepo(reponame);
			if (repo != null) {
				for (RepositoryNamespace ns : repo.getNamespaces()) {
					sender.sendMessage(" - " + ns.getNamespace());
				}
			} else {
				sender.sendMessage("No repository found with name: " + reponame);
			}
			return true;
		}
		sender.sendMessage("--- Repositories ---");
		for (LocalRepository repo : Atlas.getDataHandler().getLocalRepos()) {
			sender.sendMessage(" - " + repo.getName());
		}
		return true;
	}

}
