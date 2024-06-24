package de.atlascore.command.executor;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.command.CommandSender;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:datarepo/create")
public class CoreDataRepositoryCreateCommand implements CommandExecutor {

	@Override
	public boolean execute(CommandContext context) {
		CommandSender sender = context.getSender();
		String repoName = context.getArgument("repo", String.class);
		LocalRepository repo = Atlas.getDataHandler().getLocalRepo(repoName);
		if (repo == null) {
			sender.sendMessage("No repository found with name: " + repoName);
			return true;
		}
		String namespacename = context.getArgument("namespace", String.class);
		String path = context.getArgument("path", String.class);
		String toCreate = context.getArgument("toCreate", String.class);
		switch(toCreate) {
		case "entry":
			RepositoryNamespace namespace = repo.getNamespace(namespacename);
			if (namespace == null) {
				sender.sendMessage("No namespace found with name: " + namespacename);
				return true;
			}
			String entryname = context.getArgument("entryname", String.class);
			namespace.track(entryname, path).setListener((future) -> {
				if (future.isSuccess())
					sender.sendMessage("Entry created");
			});
			break;
		case "namespace":
			repo.registerNamespace(namespacename, path);
			sender.sendMessage("Namespace created");
			break;
		}
		return true;
	}

}
