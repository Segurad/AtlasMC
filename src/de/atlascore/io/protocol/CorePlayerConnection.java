package de.atlascore.io.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import de.atlascore.io.ProxyConnectionHandler;
import de.atlascore.plugin.channel.CorePlayerPluginChannelHandler;
import de.atlascore.recipe.CoreRecipeBook;
import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerToggleSneakEvent;
import de.atlasmc.event.player.PlayerToggleSprintEvent;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PacketProtocol;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolPlay;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes.RecipesAction;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import de.atlasmc.io.protocol.play.PacketOutDeclareRecipes;
import de.atlasmc.io.protocol.play.PacketOutDisconnect;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import de.atlasmc.io.protocol.play.PacketOutPlayerPositionAndLook;
import de.atlasmc.io.protocol.play.PacketOutSelectAdvancementTab;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes;
import de.atlasmc.io.protocol.play.PacketOutWindowConfirmation;
import de.atlasmc.recipe.BookType;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeBook;

public class CorePlayerConnection implements PlayerConnection {
	
	private Player player;
	private final AtlasPlayer aplayer;
	private final ProxyConnectionHandler connection;
	private final ProtocolAdapter protocol;
	private final ProtocolPlay protocolPlay;
	private LocalServer server;
	private final PluginChannelHandler pluginChannelHandler;
	
	private byte invID;
	private int teleportID;
	private boolean teleportConfirmed = true;
	private HashMap<Integer, ItemStack> dragItems;
	private String advancementTabID;
	
	// Client Settings
	private final PlayerSettings settings;
	
	// Client controlled values
	private boolean clientOnGround;
	private SimpleLocation clientLocation;
	private boolean locationChanged;
	
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
	
	// Click Control 
	private boolean windowActionLocked;
	private short windowActionID;
	
	// Digging related data
	private long diggingStartTime;
	private long diggingPosition;
	private BlockFace diggingFace;
	private boolean digging;
	
	// Recipes
	private final List<RecipeBook> recipeBooks; // TODO update on changed (currently only when change in recipes)
	private List<Recipe> recipesUnlocked;
	private List<Recipe> recipesAvailable;
	
	
	public CorePlayerConnection(AtlasPlayer player, ProxyConnectionHandler connection, ProtocolAdapter protocol) {
		this.aplayer = player;
		this.connection = connection;
		this.protocol = protocol;
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
		windowActionID = 0;
		windowActionLocked = false;
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
	public int getWindowActionID() {
		return windowActionID;
	}
	
	@Override
	public int getNextWindowActionID() {
		return windowActionID;
	}
	
	@Override
	public void lockWindowActions() {
		windowActionLocked = true;
		PacketOutWindowConfirmation packet = new PacketOutWindowConfirmation();
		packet.setActionNumber(getWindowActionID());
		packet.setAccepted(false);
		packet.setWindowID(invID);
		sendPacked(packet);
	}
	
	@Override
	public void unlockWindowActions() {
		windowActionLocked = false;	
	}
	
	@Override
	public boolean isWindowActionLocked() {
		return windowActionLocked;
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
		eventDigging = new PlayerDiggingEvent(player, null, 0, player.getLocation(), diggingFace);
		eventHeldItemChange = new PlayerHeldItemChangeEvent(player, 0);
		eventSneak = new PlayerToggleSneakEvent(player, false);
		eventSprint = new PlayerToggleSprintEvent(player, false);
	}

	@Override
	public AtlasPlayer getAtlasPlayer() {
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
		PacketOutKeepAlive packet = new PacketOutKeepAlive();
		packet.setKeepAlive(lastKeepAlive);
		sendPacked(packet);
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
		PacketOutPlayerPositionAndLook packet = new PacketOutPlayerPositionAndLook();
		packet.setX(x);
		packet.setY(y);
		packet.setZ(z);
		packet.setYaw(yaw);
		packet.setPitch(pitch);
		packet.setTeleportID(teleportID);
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
			PacketOutDeclareRecipes packet = new PacketOutDeclareRecipes();
			packet.setRecipes(toAdd);
			sendPacked(packet);
		}
		if (toUnlock == null)
			return;
		PacketOutUnlockRecipes packet = new PacketOutUnlockRecipes();
		if (notify) {
			packet.setAction(RecipesAction.ADD);
			packet.setTagged(toUnlock);
		} else {
			packet.setAction(RecipesAction.INIT);
			packet.setUntagged(toUnlock);
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
		PacketOutUnlockRecipes packet = new PacketOutUnlockRecipes();
		packet.setAction(RecipesAction.REMOVE);
		packet.setTagged(toRemove);
		setRecipeBookState(packet);
		sendPacked(packet);
	}
	
	private void setRecipeBookState(PacketOutUnlockRecipes packet) {
		RecipeBook book = getRecipeBook(BookType.CRAFTING);
		packet.setCraftingBookOpen(book.isOpen());
		packet.setCraftingBookFiltered(book.hasFilter());
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.FURNACE);
		packet.setSmeltingBookOpen(book.isOpen());
		packet.setSmeltingBookFiltered(book.hasFilter());
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.BLAST_FURNACE);
		packet.setBlastingBookOpen(book.isOpen());
		packet.setBlastingBookFiltered(book.hasFilter());
		((CoreRecipeBook) book).setChanged(false);
		book = getRecipeBook(BookType.SMOKER);
		packet.setSmokingBookOpen(book.isOpen());
		packet.setSmokingBookFiltered(book.hasFilter());
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
		PacketOutDeclareRecipes packet = new PacketOutDeclareRecipes();
		packet.setRecipes(toAdd);
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
	public void disconnect(String message) {
		if (connection.isClosed())
			return;
		PacketOutDisconnect packet = new PacketOutDisconnect();
		packet.setReason(message);
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
	public String getOpenAdvancementTab() {
		return advancementTabID;
	}

	@Override
	public boolean hasOpenAdvancementTab() {
		return advancementTabID != null;
	}

	@Override
	public void setOpenAdvancementTab(String tab, boolean update) {
		advancementTabID = tab;
		if (!update)
			return;
		PacketOutSelectAdvancementTab packet = new PacketOutSelectAdvancementTab();
		packet.setTabID(tab);
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
	public PlayerDiggingEvent getEventDigging() {
		return eventDigging;
	}

	@Override
	public PlayerMoveEvent getEventMove() {
		return eventMove;
	}

	@Override
	public boolean isDigging() {
		return digging;
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

}
