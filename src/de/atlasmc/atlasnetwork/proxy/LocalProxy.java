package de.atlasmc.atlasnetwork.proxy;

import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.ProxyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Proxy which is running on this AltasNode
 */
public class LocalProxy extends Proxy {

	private ChannelInitializer<SocketChannel> handler;
	private EventLoopGroup bossGroup, workerGroup;
	private Channel channel;
	private volatile ProxyConfig config;
	
	public LocalProxy(int port) {
		this(port, ProxyConfig.DEFAULT_CONFIG.get());
	}
	
	public LocalProxy(int port, ProxyConfig config) {
		super(null, port);
		this.config = config;
	}
	
	public void setChannelInitHandler(ChannelInitializer<SocketChannel> handler) {
		this.handler = handler;
	}
	
	public boolean isRunnning() {
		return channel != null && !channel.isOpen();
	}
	
	public void run() {
		if (isRunnning()) throw new RuntimeException("Proxy already running!");
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(handler);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			channel = b.bind(getPort()).channel();
	}
	
	public void stop() {
		if (!isRunnning()) return;
		channel.close();
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		bossGroup = null;
		workerGroup = null;
	}

	public JsonElement createServerListResponse(int protocol) {
		JsonObject response = new JsonObject();
		// Version
		JsonObject version = new JsonObject();
		response.add("version", version);
		version.addProperty("name", config.getProtocolText());
		version.addProperty("protocol", config.isMaintenance() ? -1 : protocol);
		// Player
		JsonObject players = new JsonObject();
		response.add("players", players);
		int maxPlayers = 0, onlinePlayers = 0;
		switch (config.getSlotDisplayMode()) {
		case NORMAL: 
			onlinePlayers = Atlas.getNetwork().getOnlinePlayerCount();
			maxPlayers = config.getMaxPlayers();
			break;
		case DYNAMIC: 
			onlinePlayers = Atlas.getNetwork().getOnlinePlayerCount();
			maxPlayers = onlinePlayers++;
			break;
		case NONE:
			break;
		}
		players.addProperty("max", maxPlayers);
		players.addProperty("online", onlinePlayers);
		players.add("sample", createServerListPlayerInfo());
		// MOTD
		response.add("description", config.getJsonMOTD());
		// Favicon
		response.addProperty("favicon", "data:image/png;base64," + config.getServerIconBase64());
		return response;
	}

	private JsonElement createServerListPlayerInfo() {
		JsonArray playerInfo = new JsonArray();
		String[] playerInfoText = config.getPlayerInfo();
		for (String s : playerInfoText) {
			JsonObject player = new JsonObject();
			playerInfo.add(player);
			player.addProperty("name", s);
			player.addProperty("id", UUID.randomUUID().toString());
		}
		return playerInfo;
	}
	
}
