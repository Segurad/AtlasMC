package de.atlascore.io.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.atlascore.plugin.channel.CorePlayerPluginChannelHandler;
import de.atlascore.recipe.CoreRecipeBook;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NodePlayer;
import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerToggleSneakEvent;
import de.atlasmc.event.player.PlayerToggleSprintEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.io.protocol.PacketProtocol;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolPlay;
import de.atlasmc.io.protocol.play.PacketOutDisconnect;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import de.atlasmc.io.protocol.play.PacketOutSelectAdvancementTab;
import de.atlasmc.io.protocol.play.PacketOutStartConfiguration;
import de.atlasmc.io.protocol.play.PacketOutSynchronizePlayerPosition;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook.RecipesAction;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipes;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.recipe.BookType;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeBook;
import de.atlasmc.server.LocalServer;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class CorePlayerConnection implements PlayerConnection {
	
	private Player player;
	private final NodePlayer aplayer;
	private final ProxyConnectionHandler connection;
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
	private SimpleLocation clientLocation;
	private boolean locationChanged;
	private float chunksPerTick;
	
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
	
	
	public CorePlayerConnection(AtlasPlayer player, ProxyConnectionHandler connection, ProtocolAdapter protocol) {
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
			de.atlasmc.io.protocol.configuration.PacketOutKeepAlive packet = new de.atlasmc.io.protocol.configuration.PacketOutKeepAlive();
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
	public int teleport(double x, double y, double z, float yaw, float pitch) {
		PacketOutSynchronizePlayerPosition packet = new PacketOutSynchronizePlayerPosition();
		packet.x = x;
		packet.y = y;
		packet.z = z;
		packet.yaw = yaw;
		packet.pitch = pitch;
		packet.teleportID = teleportID;
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
		List<Recipe> toAdd = null;
		List<NamespacedKey> toUnlock = null;
		for (Recipe recipe : recipes) {
			if (recipesUnlocked.contains(recipe))
				continue; // assuming the recipe is already available
			if (toUnlock == null)
				toUnlock = new ArrayList<>();
			toUnlock.add(recipe.getNamespacedKey());
			if (!recipesAvailable.contains(recipe)) {
				if (toAdd == null)
					toAdd = new ArrayList<>();
				toAdd.add(recipe);
				recipesAvailable.add(recipe);
			}
		}
		if (toAdd != null) {
			PacketOutUpdateRecipes packet = new PacketOutUpdateRecipes();
			packet.recipes = toAdd;
			sendPacked(packet);
		}
		if (toUnlock == null)
			return;
		PacketOutUpdateRecipeBook packet = new PacketOutUpdateRecipeBook();
		if (notify) {
			packet.action = RecipesAction.ADD;
			packet.tagged = toUnlock;
		} else {
			packet.action = RecipesAction.INIT;
			packet.tagged = toUnlock;
		}
		setRecipeBookState(packet);
		sendPacked(packet);
	}

	@Override
	public void lockRecipes(Recipe... recipes) {
		List<NamespacedKey> toRemove = null;
		for (Recipe recipe : recipes) {
			if (!recipesUnlocked.remove(recipe))
				continue;
			if (toRemove == null)
				toRemove = new ArrayList<>();
			toRemove.add(recipe.getNamespacedKey());
		}
		if (toRemove == null)
			return;
		PacketOutUpdateRecipeBook packet = new PacketOutUpdateRecipeBook();
		packet.action = RecipesAction.REMOVE;
		packet.tagged = toRemove;
		setRecipeBookState(packet);
		sendPacked(packet);
	}
	
	private void setRecipeBookState(PacketOutUpdateRecipeBook packet) {
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
		List<Recipe> toAdd = null;
		for (Recipe recipe : recipes)
			if (!recipesAvailable.contains(recipe)) {
				if (toAdd == null)
					toAdd = new ArrayList<>();
				toAdd.add(recipe);
				recipesAvailable.add(recipe);
			}
		PacketOutUpdateRecipes packet = new PacketOutUpdateRecipes();
		packet.recipes = toAdd;
		sendPacked(packet);
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
	public LocalProxy getProxy() {
		return connection.getProxy();
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
				connection.setProtocol(protocolPlay, protocolPlay.createDefaultPacketListener(this));
			} else {
				Protocol prot = protocol.getConfigurationProtocol();
				connection.setProtocol(prot, prot.createDefaultPacketListener(this));
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
	
	

}
