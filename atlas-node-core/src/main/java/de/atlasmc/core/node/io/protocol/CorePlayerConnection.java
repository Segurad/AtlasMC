package de.atlasmc.core.node.io.protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3d;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.core.node.plugin.channel.CorePlayerPluginChannelHandler;
import de.atlasmc.core.node.recipe.CoreRecipeBook;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.log.Log;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.node.SimpleLocation;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.player.PlayerAnimationEvent;
import de.atlasmc.node.event.player.PlayerDiggingEvent;
import de.atlasmc.node.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.node.event.player.PlayerMoveEvent;
import de.atlasmc.node.event.player.PlayerToggleSneakEvent;
import de.atlasmc.node.event.player.PlayerToggleSprintEvent;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.PacketProtocol;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.PlayerSettings;
import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.node.io.protocol.ProtocolPlay;
import de.atlasmc.node.io.protocol.play.PacketOutDisconnect;
import de.atlasmc.node.io.protocol.play.PacketOutKeepAlive;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookSettings;
import de.atlasmc.node.io.protocol.play.PacketOutSelectAdvancementTab;
import de.atlasmc.node.io.protocol.play.PacketOutStartConfiguration;
import de.atlasmc.node.io.protocol.play.PacketOutSynchronizePlayerPosition;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.recipe.BookType;
import de.atlasmc.node.recipe.Recipe;
import de.atlasmc.node.recipe.RecipeBook;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class CorePlayerConnection implements PlayerConnection {
	
	private Player player;
	private final NodePlayer aplayer;
	private final ConnectionHandler connection;
	private final ProtocolAdapter protocol;
	private final ProtocolPlay protocolPlay;
	private LocalServer server;
	private final PluginChannelHandler pluginChannelHandler;
	
	private volatile boolean waitingForProtocolChange;
	private volatile boolean inConfiguration;
	private byte invID;
	private int teleportID;
	private boolean teleportConfirmed = true;
	private HashMap<Integer, ItemStack> dragItems;
	private NamespacedKey advancementTabID;
	
	// Client Settings
	private final PlayerSettings settings;
	
	// Client controlled values
	private boolean clientOnGround;
	private boolean clientPushWall;
	private SimpleLocation clientLocation;
	private boolean locationChanged;
	private float chunksPerTick;
	private int selectedTrade = -1;
	
	// Events
	private PlayerAnimationEvent eventAnimation;
	private PlayerMoveEvent eventMove;
	private PlayerDiggingEvent eventDigging;
	private PlayerHeldItemChangeEvent eventHeldItemChange;
	private PlayerToggleSneakEvent eventSneak;
	private PlayerToggleSprintEvent eventSprint;
	
	// Keep Alive
	private long lastKeepAlive; // the Time the last KeepAlive has been send or received
	private boolean keepAliveResponse;
	
	// Recipes
	private final List<RecipeBook> recipeBooks; // TODO update on changed (currently only when change in recipes)
	private List<Recipe> recipesUnlocked;
	private List<Recipe> recipesAvailable;
	
	
	public CorePlayerConnection(AtlasPlayer player, ConnectionHandler connection, ProtocolAdapter protocol) {
		this.aplayer = new CoreNodePlayer(this, player);
		this.connection = connection;
		this.protocol = protocol;
		this.inConfiguration = true;
		this.protocolPlay = protocol.getPlayProtocol();
		this.settings = new CorePlayerSettings();
		this.pluginChannelHandler = new CorePlayerPluginChannelHandler(this);
		CoreRecipeBook[] recipeBooks = new CoreRecipeBook[BookType.getValues().size()];
		int index = 0;
		for (BookType type : BookType.getValues())
			recipeBooks[index++] = new CoreRecipeBook(type);
		this.recipeBooks = List.of(recipeBooks);
	}

	@Override
	public ProtocolAdapter getProtocolAdapter() {
		return protocol;
	}
	
	@Override
	public int getInventoryID() {
		return invID;
	}
	
	@Override
	public int getNextInventoryID() {
		return ++invID;
	}

	/**
	 * 
	 * @return the current server or null if not at this note
	 */
	@ThreadSafe
	public synchronized LocalServer getLocalSever() {
		return server;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void setPlayer(Player player) {
		this.player = player;
		// Reinit all Events for the new PlayerEntity
		eventAnimation = new PlayerAnimationEvent(player, null);
		eventMove = new PlayerMoveEvent(player, player.getLocation(), player.getLocation());
		eventHeldItemChange = new PlayerHeldItemChangeEvent(player, 0);
		eventSneak = new PlayerToggleSneakEvent(player, false);
		eventSprint = new PlayerToggleSprintEvent(player, false);
	}

	@Override
	public NodePlayer getNodePlayer() {
		return aplayer;
	}

	@Override
	public boolean hasKeepAliveResponse() {
		return keepAliveResponse;
	}

	@Override
	public long getLastKeepAlive() {
		return lastKeepAlive;
	}

	@Override
	public void sendKeepAlive() {
		if (!hasKeepAliveResponse()) 
			return;
		lastKeepAlive = System.currentTimeMillis();
		keepAliveResponse = false;
		if (inConfiguration) {
			de.atlasmc.node.io.protocol.configuration.PacketOutKeepAlive packet = new de.atlasmc.node.io.protocol.configuration.PacketOutKeepAlive();
			packet.keepAliveID = lastKeepAlive;
			sendPacked(packet);
		} else {
			PacketOutKeepAlive packet = new PacketOutKeepAlive();
			packet.keepAliveID = lastKeepAlive;
			sendPacked(packet);
		}
	}
	
	@Override
	public void confirmKeepAlive(long lastResponseTime) {
		lastKeepAlive = lastResponseTime;
		keepAliveResponse = true;	
	}

	@Override
	public void sendPacked(PacketProtocol packet) {
		connection.sendPacket(packet);
	}
	
	@Override
	public void sendPacket(PacketProtocol packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		connection.sendPacket(packet, listener);	
	}

	@Override
	public ProtocolPlay getProtocol() {
		return protocolPlay;
	}

	@Override
	public boolean hasClientLocationChanged() {
		return locationChanged;
	}
	
	@Override
	public void setClientLocationChanged(boolean changed) {
		this.locationChanged = changed;
	}

	@Override
	public SimpleLocation acceptClientLocation() {
		locationChanged = false;
		return clientLocation;
	}
	
	@Override
	public SimpleLocation getClientLocation() {
		return clientLocation;
	}
	
	@Override
	public int teleport(double x, double y, double z, float pitch, float yaw, Vector3d velocity, int flags) {
		PacketOutSynchronizePlayerPosition packet = new PacketOutSynchronizePlayerPosition();
		packet.x = x;
		packet.y = y;
		packet.z = z;
		packet.yaw = yaw;
		packet.pitch = pitch;
		packet.velocityX = velocity.x;
		packet.velocityY = velocity.y;
		packet.velocityZ = velocity.z;
		packet.teleportID = teleportID;
		packet.flags = flags;
		teleportConfirmed = false;
		sendPacked(packet);
		return teleportID++;
	}
	
	@Override
	public int teleport(SimpleLocation loc, Vector3d velocity, int flags) {
		PacketOutSynchronizePlayerPosition packet = new PacketOutSynchronizePlayerPosition();
		packet.x = loc.x;
		packet.y = loc.y;
		packet.z = loc.z;
		packet.yaw = loc.yaw;
		packet.pitch = loc.pitch;
		packet.velocityX = velocity.x;
		packet.velocityY = velocity.y;
		packet.velocityZ = velocity.z;
		packet.teleportID = teleportID;
		packet.flags = flags;
		teleportConfirmed = false;
		sendPacked(packet);
		return teleportID++;
	}

	@Override
	public List<RecipeBook> getRecipeBooks() {
		return recipeBooks;
	}

	@Override
	public RecipeBook getRecipeBook(BookType type) {
		return recipeBooks.get(type.getID());
	}

	@Override
	public void unlockRecipes(boolean notify, Recipe... recipes) {
		// TODO implements
	}

	@Override
	public void lockRecipes(Recipe... recipes) {
		// TODO implement
	}
	
	private void setRecipeBookState(PacketOutRecipeBookSettings packet) {
		RecipeBook book = getRecipeBook(BookType.CRAFTING);
		packet.craftingOpen = book.isOpen();
		packet.craftingFilter = book.hasFilter();
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.FURNACE);
		packet.smeltingOpen = book.isOpen();
		packet.smeltingFilter = book.hasFilter();
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.BLAST_FURNACE);
		packet.blastFurnaceOpen = book.isOpen();
		packet.blastFurnaceFilter = book.hasFilter();
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.SMOKER);
		packet.blastFurnaceOpen = book.isOpen();
		packet.blastFurnaceFilter = book.hasFilter();
		((CoreRecipeBook) book).setChanged(false);
	}

	@Override
	public void removeRecipes(Recipe... recipes) {
		for (Recipe recipe : recipes) {
			recipesAvailable.remove(recipe);
		}
		lockRecipes(recipes);
	}

	@Override
	public void addRecipes(Recipe... recipes) {
		// TODO implement
	}

	@Override
	public Collection<Recipe> getUnlockedRecipes() {
		if (recipesUnlocked == null)
			return List.of();
		return recipesUnlocked;
	}

	@Override
	public Collection<Recipe> getAvailableRecipes() {
		if (recipesAvailable == null)
			return List.of();
		return recipesAvailable;
	}

	@Override
	public int getTeleportID() {
		return teleportID - 1;
	}

	@Override
	public void confirmTeleport() {
		this.teleportConfirmed = true;	
	}

	@Override
	public boolean isTeleportConfirmed() {
		return teleportConfirmed;
	}

	@Override
	public PluginChannelHandler getPluginChannelHandler() {
		return pluginChannelHandler;
	}

	@Override
	public void disconnect(Chat message) {
		if (connection.isClosed())
			return;
		PacketOutDisconnect packet = new PacketOutDisconnect();
		packet.reason = message;
		connection.sendPacket(packet, (future) -> {
			connection.close();
		});
	}

	@Override
	public NodeSocket getSocket() {
		return (NodeSocket) ((ServerSocketConnectionHandler) connection).getSocket();
	}

	@Override
	public PlayerAnimationEvent getEventAnimation() {
		return eventAnimation;
	}

	@Override
	public PlayerHeldItemChangeEvent getEventHeldItemChange() {
		return eventHeldItemChange;
	}

	@Override
	public NamespacedKey getOpenAdvancementTab() {
		return advancementTabID;
	}

	@Override
	public boolean hasOpenAdvancementTab() {
		return advancementTabID != null;
	}

	@Override
	public void setOpenAdvancementTab(NamespacedKey tab, boolean update) {
		advancementTabID = tab;
		if (!update)
			return;
		PacketOutSelectAdvancementTab packet = new PacketOutSelectAdvancementTab();
		packet.tabID = tab;
		sendPacked(packet);
	}

	@Override
	public PlayerToggleSneakEvent getEventSneak() {
		return eventSneak;
	}

	@Override
	public PlayerToggleSprintEvent getEventSprint() {
		return eventSprint;
	}

	@Override
	public PlayerMoveEvent getEventMove() {
		return eventMove;
	}

	@Override
	public boolean isClientOnGround() {
		return clientOnGround;
	}

	@Override
	public void setClientOnGround(boolean onGround) {
		this.clientOnGround = onGround;
	}
	
	@Override
	public boolean isClientPushWall() {
		return clientPushWall;
	}
	
	@Override
	public void setClientPushWall(boolean pushWall) {
		this.clientPushWall = pushWall;
	}

	@Override
	public PlayerSettings getSettings() {
		return settings;
	}

	@Override
	public void setChunksPerTick(float chunksPerTick) {
		this.chunksPerTick = chunksPerTick;
	}

	@Override
	public float getChunksPerTick() {
		return chunksPerTick;
	}

	@Override
	public void switchToConfiguration() {
		if (waitingForProtocolChange || inConfiguration)
			return;
		synchronized (this) {
			if (waitingForProtocolChange || inConfiguration)
				return;
			waitingForProtocolChange = true;
			// TODO remove from server
			connection.sendPacket(new PacketOutStartConfiguration());
		}
	}

	@Override
	public boolean isWaitingForProtocolChange() {
		return waitingForProtocolChange;
	}

	@Override
	public boolean isInConfiguration() {
		return inConfiguration;
	}

	@Override
	public void protocolChangeAcknowledged() {
		if (!waitingForProtocolChange)
			return;
		synchronized (this) {
			if (!waitingForProtocolChange)
				return;
			boolean inConfiguration = this.inConfiguration;
			if (inConfiguration) {
				connection.setProtocol(protocolPlay, protocolPlay.createDefaultPacketListenerIn(this));
			} else {
				Protocol prot = protocol.getConfigurationProtocol();
				connection.setProtocol(prot, prot.createDefaultPacketListenerIn(this));
			}
			this.inConfiguration = !inConfiguration;
			waitingForProtocolChange = false;
		}
	}

	@Override
	public void handleSyncPackets(Log logger) {
		connection.handleSyncPackets(logger);
	}

	@Override
	public void sendTranslation(String key, Object... values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Chat chat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedTrade(int trade) {
		this.selectedTrade = trade;
	}

	@Override
	public int getSelectedTrade() {
		return selectedTrade;
	}
	
	

}
