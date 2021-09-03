package de.atlascore.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import de.atlascore.atlasnetwork.CoreAtlasNetwork;
import de.atlascore.atlasnetwork.CoreProxyConfig;
import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.ProxyConfig;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.proxy.PlayerLoginAtemptEvent;

public class Main {
	
	@SuppressWarnings("resource")
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
		System.out.println("Start...");
		new Atlas(new LocalAtlasNode(), new CoreAtlasNetwork());
		Atlas.getProtocolAdapterHandler().setProtocol(new CoreProtocolAdapter());
		File f = new File(Main.class.getResource("/assets/server_icon.png").getFile());
		byte[] data = new byte[(int) f.length()];
		new FileInputStream(f).read(data);
		String s = Base64.getEncoder().encodeToString(data);
		ProxyConfig cfg = new CoreProxyConfig();
		cfg.setServerIconBase64(s);
		cfg.setMaintenance(true);
		LocalProxy proxy = new LocalProxy(25565, cfg);
		proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
		proxy.run();
		
		HandlerList.registerListener(new Listener() {
			@EventHandler
			public void proxyAtempt(PlayerLoginAtemptEvent e) {
				System.out.println("event success");
			}
		}, null);
		System.out.println("Started");
		while (true) {
			
		}
	}

}
