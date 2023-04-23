package de.atlascore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlascore.block.CorePlayerDiggingHandler;
import de.atlascore.inventory.CoreInventoryView;
import de.atlascore.inventory.CorePlayerItemCooldownHandler;
import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Material;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.inventory.InventoryCloseEvent;
import de.atlasmc.event.inventory.InventoryOpenEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.MerchantInventory;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState;
import de.atlasmc.io.protocol.play.PacketOutCloseWindow;
import de.atlasmc.io.protocol.play.PacketOutEffect;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.io.protocol.play.PacketOutNamedSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutOpenWindow;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState.ChangeReason;
import de.atlasmc.io.protocol.play.PacketOutSetExperiance;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutSpawnPlayer;
import de.atlasmc.io.protocol.play.PacketOutStopSound;
import de.atlasmc.io.protocol.play.PacketOutTradeList;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.util.CooldownHandler;
import de.atlasmc.util.MathUtil;

public class CorePlayer extends CoreHumanEntity implements Player {
	
	protected static final BiConsumer<Entity, Player>
	VIEWER_ADD_FUNCTION = (holder, viewer) -> {
		CorePlayer player = (CorePlayer) holder;
		PlayerConnection con = viewer.getConnection();
		PacketOutSpawnPlayer packet = new PacketOutSpawnPlayer();
		packet.setEntity(player);
		con.sendPacked(packet);
		player.sendMetadata(viewer);
		player.sendEntityEffects(viewer);
		player.sendAttributes(viewer);
		DiggingHandler digging = player.getDigging();
		digging.sendAnimation(viewer);
	};
	
	private PlayerConnection con;
	private Inventory open;
	private final CoreInventoryView view;
	private ScoreboardView scoreView;
	//private final TablistView tabView;
	
	private int level;
	private Gamemode gamemode;
	private ItemStack cursorItem;
	private boolean canBuild;
	private Object pluginData;
	private DiggingHandler digging;
	
	public CorePlayer(EntityType type, UUID uuid, PlayerConnection con) {
		super(type, uuid);
		view = new CoreInventoryView(this, getInventory(), getCraftingInventory(), 0);
		this.con = con;
		this.digging = new CorePlayerDiggingHandler(this);
	}
	
	@Override
	protected CooldownHandler<Material> createItemCooldownHander() {
		return new CorePlayerItemCooldownHandler(this);
	}
	
	@Override
	public void openInventory(Inventory inv) {
		if (inv == null) 
			throw new IllegalArgumentException("Inventory can not be null!");
		if (inv == getInventory()) 
			throw new IllegalArgumentException("Can not open own Inventory!");
		if (inv.getType().getID() == -1) 
			throw new IllegalArgumentException("Can not open Inventory with this type (Please use the dedicated method!): " + inv.getType().name());
		if (open != null)
			closeInventory();
		InventoryOpenEvent event = new InventoryOpenEvent(view, inv);
		HandlerList.callEvent(event);
		if (event.isCancelled()) 
			return;
		this.open = inv;
		view.setTopInventory(open);
		view.setViewID(con.getNextInventoryID());
		
		PacketOutOpenWindow packet = new PacketOutOpenWindow();
		packet.setWindowID(view.getViewID());
		packet.setType(inv.getType());
		packet.setTitle(inv.getTitle().getText());
		con.sendPacked(packet);
		
		inv.updateSlots(this);
		
		if (inv instanceof MerchantInventory) {
			openMerchantInventory((MerchantInventory) inv);
		}
	}
	
	/**
	 * Sends the {@link MerchantRecipe}s and some inventory information
	 * @param inv
	 */
	protected void openMerchantInventory(MerchantInventory inv) {
		List<MerchantRecipe> recipes = inv.getRecipes();
		List<MerchantRecipe> copy = new ArrayList<>(recipes.size());
		for (MerchantRecipe recipe : recipes)
			copy.add(recipe.clone());
		
		PacketOutTradeList packet = new PacketOutTradeList();
		packet.setTrades(copy);
		packet.setLevel(inv.getLevel());
		packet.setExperience(inv.getExperience());
		packet.setRegular(!inv.getHideLevelProgress());
		packet.setCanRestock(inv.canRestock());
		
		con.sendPacked(packet);
	}
	
	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		return getPermissionHandler().getPermission(permission);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
		PacketOutSetExperiance setXP = new PacketOutSetExperiance();
		setXP.setLevel(level);
		con.sendPacked(setXP);
	}

	@Override
	public void playEffect(SimpleLocation loc, Effect effect, Object data, boolean relativeSound) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playEffect(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), effect, data, relativeSound);
	}
	
	@Override
	public void playEffect(int x, int y, int z, Effect effect, Object data, boolean relativeSound) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		PacketOutEffect packet = new PacketOutEffect();
		packet.setEffect(effect);
		packet.setPosition(MathUtil.toPosition(x, y, z));
		packet.setData(effect.getDataValueByObject(data));
		packet.setDisableRelativeVolume(!relativeSound);
		con.sendPacked(packet);
	}

	@Override
	public void closeInventory() {
		HandlerList.callEvent(new InventoryCloseEvent(view));
		open.getViewers().remove(this);
		open = null;
		view.setTopInventory(getInventory().getCraftingInventory());
		view.setViewID(0);
		PacketOutCloseWindow packet = new PacketOutCloseWindow();
		packet.setWindowID(con.getInventoryID());
		con.sendPacked(packet);
	}

	@Override
	public void setItemOnCursor(ItemStack item) {
		if (this.cursorItem == item)
			return;
		if (item != null)
			item = item.clone();
		this.cursorItem = item;
		updateItemOnCursor();
	}

	@Override
	public ItemStack getItemOnCursor() {
		return cursorItem != null ? cursorItem.clone() : null;
	}
	
	@Override
	public void setItemOnCursorUnsafe(ItemStack item) {
		if (cursorItem == item)
			return;
		this.cursorItem = item;
		updateItemOnCursor();
	}
	
	@Override
	public ItemStack getItemOnCursorUnsafe() {
		return this.cursorItem;
	}

	@Override
	public PlayerConnection getConnection() {
		return con;
	}

	@Override
	public AtlasPlayer getAtlasPlayer() {
		return con.getAtlasPlayer();
	}

	@Override
	public String getName() {
		return getAtlasPlayer().getInternalName();
	}

	@Override
	public String getLocal() {
		return con.getSettings().getLocal();
	}

	@Override
	public InventoryView getOpenInventory() {
		return view;
	}

	@Override
	public Gamemode getGamemode() {
		return gamemode;
	}

	@Override
	public boolean getCanBuild() {
		return canBuild;
	}

	@Override
	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

	@Override
	public void setGamemode(Gamemode gamemode) {
		this.gamemode = gamemode;
		PacketOutChangeGameState packet = new PacketOutChangeGameState();
		packet.setReason(ChangeReason.CHANGE_GAMEMODE);
		packet.setValue(gamemode.ordinal());
		con.sendPacked(packet);
	}

	@Override
	public void updateItemOnCursor() {
		PacketOutSetSlot packet = new PacketOutSetSlot();
		packet.setWindowID(-1);
		packet.setSlot(-1);
		packet.setItem(cursorItem);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float particledata, int count, Object data) {
		if (!particle.isValid(data)) throw new IllegalArgumentException("Data is not valid!");
		PacketOutParticle packet = new PacketOutParticle();
		packet.setParticle(particle);
		packet.setLocation(x, y, z);
		packet.setOffset(offX, offY, offZ);
		packet.setParticleData(particledata);
		packet.setCount(count);
		packet.setData(data);
		con.sendPacked(packet);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata) {
		spawnParticle(particle, x, y, z, particledata, 1);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count) {
		spawnParticle(particle, x, y, z, particledata, count, null);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count, Object data) {
		spawnParticle(particle, x, y, z, 0, 0, 0, particledata, count, data);
	}

	@Override
	public void sendMessage(Chat chat, ChatType type) {
		con.getAtlasPlayer().sendMessage(chat, type);
	}
	
	@Override
	public void sendMessage(String message) {
		con.getAtlasPlayer().sendMessage(message);
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return getAtlasPlayer().getPermissionHandler();
	}

	@Override
	public void setPermissionHandler(PermissionHandler handler) {
		getAtlasPlayer().setPermissionHandler(handler);
	}

	@Override
	public Object getPluginData() {
		return pluginData;
	}

	@Override
	public void setPluginData(Object data) {
		this.pluginData = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getPluginData(Class<T> clazz) {
		return clazz.isInstance(pluginData) ? (T) pluginData : null;
	}

	@Override
	public boolean hasPluginData() {
		return pluginData != null;
	}

	@Override
	public void playSound(double x, double y, double z, Sound sound, SoundCategory category, float volume, float pitch) {
		PacketOutSoundEffect packet = new PacketOutSoundEffect();
		packet.setLocation(x, y, z);
		packet.setSound(sound);
		packet.setCategory(category);
		packet.setVolume(volume);
		packet.setPitch(pitch);
		con.sendPacked(packet);
	}

	@Override
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
		PacketOutEntitySoundEffect packet = new PacketOutEntitySoundEffect();
		packet.setEntityID(entity.getID());
		packet.setSound(sound);
		packet.setCategory(category);
		packet.setVolume(volume);
		packet.setPitch(pitch);
		con.sendPacked(packet);
	}

	@Override
	public void playSound(Entity entity, String sound, SoundCategory category, float volume, float pitch) {
		playSound(entity.getX(), entity.getY(), entity.getZ(), sound, category, volume, pitch);
	}

	@Override
	public void playSound(double x, double y, double z, String sound, SoundCategory category, float volume, float pitch) {
		PacketOutNamedSoundEffect packet = new PacketOutNamedSoundEffect();
		packet.setLocation(x, y, z);
		packet.setIdentifier(sound);
		packet.setCategory(category);
		packet.setVolume(volume);
		packet.setPitch(pitch);
		con.sendPacked(packet);
	}

	@Override
	public void stopSound(SoundCategory category, String sound) {
		PacketOutStopSound packet = new PacketOutStopSound();
		packet.setCategory(category);
		packet.setSound(sound);
		con.sendPacked(packet);
	}

	@Override
	public synchronized void setScoreboard(ScoreboardView view) {
		if (view != null && view.getViewer() != this)
			throw new IllegalArgumentException("ScoreboardView does not belong to this Player!");
		if (this.scoreView != null)
			this.scoreView.remove();
		this.scoreView = view;
		if (view != null)
			view.add();
	}

	@Override
	public ScoreboardView getScoreboard() {
		return scoreView;
	}
	
	@Override
	protected void doTick() {
		super.doTick();
		if (con.hasClientLocationChanged())
			con.acceptClientLocation().copyTo(loc);
	}
	
	@Override
	public void teleport(double x, double y, double z, float yaw, float pitch) {
		super.teleport(x, y, z, yaw, pitch);
		con.teleport(x, y, z, yaw, pitch);
	}

	@Override
	public void disconnect(String message) {
		con.disconnect(message);
	}

	@Override
	public DiggingHandler getDigging() {
		return digging;
	}

	@Override
	public void setDiggingHandller(DiggingHandler handler) {
		this.digging = handler;
	}

}
