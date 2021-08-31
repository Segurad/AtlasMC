package de.atlascore.main;

import de.atlascore.atlasnetwork.CoreAtlasNetwork;
import de.atlascore.atlasnetwork.CoreProxyConfig;
import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.proxy.PlayerLoginAtemptEvent;

public class Main {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		System.out.print(
				"       ____        \r\n" + 
				"      / /  \\       # _________  # __          # ________      # ______      # ___ __ __     # ______      #\r\n" + 
				"     / / /\\ \\      #/________/\\ #/_/\\         #/_______/\\     #/_____/\\     #/__//_//_/\\    #/_____/\\     #\r\n" + 
				"    / / /\\ \\ \\     #\\__.::.__\\/ #\\:\\ \\        #\\::: _  \\ \\    #\\::::_\\/_    #\\::\\| \\| \\ \\   #\\:::__\\/     #\r\n" + 
				"   / / /_/ /\\ \\    #   \\::\\ \\   # \\:\\ \\       # \\::(_)  \\ \\   # \\:\\/___/\\   # \\:.      \\ \\  # \\:\\ \\  __   #\r\n" + 
				"  / / /___/ /\\ \\   #    \\::\\ \\  #  \\:\\ \\____  #  \\:: __  \\ \\  #  \\_::._\\:\\  #  \\:.\\-/\\  \\ \\ #  \\:\\ \\/_/\\  #\r\n" + 
				" / /_______/\\ \\ \\  #     \\::\\ \\ #   \\:\\/___/\\ #   \\:.\\ \\  \\ \\ #    /____\\:\\ #   \\. \\  \\  \\ \\#   \\:\\_\\ \\ \\ #\r\n" + 
				"/ / /_     __\\ \\_\\ #      \\__\\/ #    \\_____\\/ #    \\__\\/\\__\\/ #    \\_____\\/ #    \\__\\/ \\__\\/#    \\_____\\/ #\r\n" + 
				"\\_\\___\\   /____/_/\r\n");
		System.out.println("Start...");
		new Atlas(new LocalAtlasNode(), new CoreAtlasNetwork());
		Atlas.getProtocolAdapterHandler().setProtocol(new CoreProtocolAdapter());
		LocalProxy proxy = new LocalProxy(25565, new CoreProxyConfig());
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
