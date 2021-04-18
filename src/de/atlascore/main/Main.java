package de.atlascore.main;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.event.EventHandler;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.proxy.PlayerLoginAtemptEvent;
import de.atlasmc.io.channel.ChannelInitHandler;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Start...");
		new Atlas(new LocalAtlasNode());
		Atlas.getProtocolAdapterHandler().setProtocol(new CoreProtocolAdapter());
		LocalProxy proxy = new LocalProxy(25565);
		proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
		proxy.run();
		HandlerList.registerListener(new Listener() {
			@EventHandler
			public void proxyAtempt(PlayerLoginAtemptEvent e) {
				System.out.println("event");
			}
		}, null);
		System.out.println("Started");
		while (true) {
			
		}
	}

}
