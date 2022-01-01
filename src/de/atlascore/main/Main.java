package de.atlascore.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlascore.plugin.CoreJavaPluginLoader;
import de.atlascore.plugin.CorePluginManager;
import de.atlascore.proxy.CoreLocalProxy;
import de.atlascore.proxy.CoreProxyConfig;
import de.atlasmc.Material;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.ProxyConfig;
import de.atlasmc.entity.Entity;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.proxy.PlayerLoginAtemptEvent;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.print("       ____\n" + 
				"      / /  \\     _________   __        ________    ______\n" + 
				"     / / /\\ \\   /________/\\ /_/\\      /_______/\\  /_____/\\\n" + 
				"    / / /\\ \\ \\  \\__.::.__\\/ \\:\\ \\     \\::: _  \\ \\ \\::::_\\/_\n" + 
				"   / / /_/ /\\ \\    \\::\\ \\    \\:\\ \\     \\::(_)  \\ \\ \\:\\/___/\\\n" + 
				"  / / /___/ /\\ \\    \\::\\ \\    \\:\\ \\____ \\:: __  \\ \\ \\_::._\\:\\\n" + 
				" / /_______/\\ \\ \\    \\::\\ \\    \\:\\/___/\\ \\:.\\ \\  \\ \\  /____\\:\\\n" + 
				"/ / /_     __\\ \\_\\    \\__\\/     \\_____\\/  \\__\\/\\__\\/  \\_____\\/\n" + 
				"\\_\\___\\   /____/_/\n");
		final CorePluginManager pmanager = new CorePluginManager();
		pmanager.addLoader(new CoreJavaPluginLoader());
		pmanager.loadCoreModuls();
		
		final CoreNodeBuilder builder = new CoreNodeBuilder();
		LocalAtlasNode node = builder.build();
		ProxyConfig cfg = new CoreProxyConfig();
		cfg.setServerIconBase64(builder.getServerIcon());
		cfg.setMaintenance(true);
		CoreLocalProxy proxy = new CoreLocalProxy(node, builder.getPort(), cfg);
		proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
		proxy.run();
		
		HandlerList.registerListener(new Listener() {
			@EventHandler
			public void proxyAtempt(PlayerLoginAtemptEvent e) {
				System.out.println("event success");
			}
		});
		System.out.println("Started");
	}

}
