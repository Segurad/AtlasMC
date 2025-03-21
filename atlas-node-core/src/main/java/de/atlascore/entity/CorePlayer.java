package de.atlascore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.joml.Vector3d;

import de.atlascore.block.CorePlayerDiggingHandler;
import de.atlascore.inventory.CoreInventoryView;
import de.atlascore.inventory.CorePlayerItemCooldownHandler;
import de.atlasmc.Gamemode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NodePlayer;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.inventory.InventoryCloseEvent;
import de.atlasmc.event.inventory.InventoryOpenEvent;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.MerchantInventory;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutCloseContainer;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.io.protocol.play.PacketOutGameEvent;
import de.atlasmc.io.protocol.play.PacketOutGameEvent.GameEventType;
import de.atlasmc.io.protocol.play.PacketOutMerchantOffers;
import de.atlasmc.io.protocol.play.PacketOutOpenBook;
import de.atlasmc.io.protocol.play.PacketOutOpenScreen;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import de.atlasmc.io.protocol.play.PacketOutSetContainerSlot;
import de.atlasmc.io.protocol.play.PacketOutSetExperiance;
import de.atlasmc.io.protocol.play.PacketOutSetHealth;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.io.protocol.play.PacketOutStopSound;
import de.atlasmc.io.protocol.play.PacketOutWorldEvent;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.CooldownHandler;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.WorldEvent;

public class CorePlayer extends CoreHumanEntity implements Player {
	
	protected static final BiConsumer<Entity, Player>
	VIEWER_ADD_FUNCTION = (holder, viewer) -> {
		CorePlayer player = (CorePlayer) holder;
		PlayerConnection con = viewer.getConnection();
		PacketOutSpawnEntity packet = new PacketOutSpawnEntity();
		packet.setEntity(player);
		con.sendPacked(packet);
		player.sendMetadata(viewer);
		player.sendEntityEffects(viewer);
		player.sendAttributes(viewer);
		DiggingHandler digging = player.getDigging();
		digging.sendAnimation(viewer);
	};
	
	private PlayerConnection con;
	private AtlasPlayer atlasPlayer;
	private NodePlayer nodePlayer;
	private Inventory open;
	private final CoreInventoryView view;
	private ScoreboardView scoreView;
	//private final TablistView tabView;
	
	private boolean updateFoodLevel;
	private int level;
	private int xp;
	private float xpBar;
	private Gamemode gamemode;
	private ItemStack cursorItem;
	private boolean canBuild;
	private Object pluginData;
	private DiggingHandler digging;
	
	public CorePlayer(EntityType type, UUID uuid, PlayerConnection con) {
		super(type, uuid);
		view = new CoreInventoryView(this, getInventory(), getCraftingInventory(), 0);
		this.con = con;
		this.nodePlayer = con.getNodePlayer();
		this.atlasPlayer = con.getNodePlayer().getAtlasPlayer();
		this.digging = new CorePlayerDiggingHandler(this);
	}
	
	@Override
	protected CooldownHandler<ItemType> createItemCooldownHander() {
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
		
		PacketOutOpenScreen packet = new PacketOutOpenScreen();
		packet.windowID = view.getViewID();
		packet.type = inv.getType();
		packet.title = inv.getTitle();
		con.sendPacked(packet);
		
		inv.updateSlots(this);
		
		if (inv instanceof MerchantInventory merchant) {
			openMerchantInventory(merchant);
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
		
		PacketOutMerchantOffers packet = new PacketOutMerchantOffers();
		packet.trades = copy;
		packet.level = inv.getLevel();
		packet.experience = inv.getExperience();
		packet.regular = !inv.getHideLevelProgress();
		packet.canRestock = inv.canRestock();
		
		con.sendPacked(packet);
	}
	
	@Override
	public void openBook(EquipmentSlot hand) {
		if (hand == null)
			throw new IllegalArgumentException("Hand can not be null!");
		if (hand != EquipmentSlot.OFF_HAND && hand != EquipmentSlot.MAIN_HAND)
			throw new IllegalArgumentException("Given slot does not match MAIN_HAND or OFF_HAND: " + hand.name());
		PacketOutOpenBook packet = new PacketOutOpenBook();
		packet.hand = hand;
		con.sendPacked(packet);
	}
	
	@Override
	public void openBook(ItemStack book) {
		if (book == null)
			throw new IllegalArgumentException("Book can not be null!");
		openBookUsafe(book.clone());
	}
	
	@Override
	public void openBookUsafe(ItemStack book) {
		if (book == null)
			throw new IllegalArgumentException("Book can not be null!");
		PlayerInventory inv = getInventory();
		ItemStack currentHand = inv.getItemInMainHandUnsafe();
		inv.setItemInMainHandUnsafe(book);
		PacketOutOpenBook packet = new PacketOutOpenBook();
		packet.hand = EquipmentSlot.MAIN_HAND;
		con.sendPacked(packet);
		inv.setItemInMainHandUnsafe(currentHand);
	}
	
	@Override
	public Permission getPermission(CharSequence permission) {
		return getPermissionHandler().getPermission(permission);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		setExp(level, xp, xpBar);
	}
	
	@Override
	public int getExp() {
		return xp;
	}
	
	@Override
	public void setExp(int xp) {
		setExp(level, xp, xpBar);
	}
	
	@Override
	public float getExpBar() {
		return xpBar;
	}
	
	@Override
	public void setExpBar(float progress) {
		setExp(level, xp, progress);
	}
	
	@Override
	public void setExp(int level, int exp, float progress) {
		this.level = level;
		this.xp = exp;
		this.xpBar = progress;
		PacketOutSetExperiance packet = new PacketOutSetExperiance();
		packet.level = level;
		packet.experienceBar = progress;
		packet.totalExperience = exp;
		con.sendPacked(packet);
	}
	
	@Override
	public void setLevel(int level, float progress) {
		setExp(level, xp, progress);
	}

	@Override
	public void playEffect(SimpleLocation loc, WorldEvent effect, Object data, boolean relativeSound) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playEffect(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), effect, data, relativeSound);
	}
	
	@Override
	public void playEffect(int x, int y, int z, WorldEvent effect, Object data, boolean relativeSound) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		PacketOutWorldEvent packet = new PacketOutWorldEvent();
		packet.event = effect;
		packet.position = MathUtil.toPosition(x, y, z);
		packet.data = effect.getDataValueByObject(data);
		packet.disableRelativeVolume = !relativeSound;
		con.sendPacked(packet);
	}

	@Override
	public void closeInventory() {
		HandlerList.callEvent(new InventoryCloseEvent(view));
		open.getViewers().remove(this);
		open = null;
		view.setTopInventory(getInventory().getCraftingInventory());
		view.setViewID(0);
		PacketOutCloseContainer packet = new PacketOutCloseContainer();
		packet.windowID = con.getInventoryID();
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
		return atlasPlayer;
	}
	
	@Override
	public NodePlayer getNodePlayer() {
		return nodePlayer;
	}

	@Override
	public String getName() {
		return atlasPlayer.getInternalName();
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
		PacketOutGameEvent packet = new PacketOutGameEvent();
		packet.event = GameEventType.CHANGE_GAMEMODE;
		packet.value = gamemode.ordinal();
		con.sendPacked(packet);
	}

	@Override
	public void updateItemOnCursor() {
		PacketOutSetContainerSlot packet = new PacketOutSetContainerSlot();
		packet.windowID = -1;
		packet.slot = -1;
		packet.item = cursorItem;
		con.sendPacked(packet);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float maxSpeed, int count, Object data) {
		if (!particle.isValid(data)) throw new IllegalArgumentException("Data is not valid!");
		PacketOutParticle packet = new PacketOutParticle();
		packet.particle = particle;
		packet.x = x;
		packet.y = y;
		packet.z = z;
		packet.offX = offX;
		packet.offY = offY;
		packet.offZ = offZ;
		packet.maxSpeed = maxSpeed;
		packet.count = count;
		packet.data = data;
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
	public void sendMessage(Chat chat) {
		con.sendMessage(chat);
	}
	
	@Override
	public void sendMessage(String message) {
		con.sendMessage(message);
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return atlasPlayer.getPermissionHandler();
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
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		if (entity == null)
			throw new IllegalArgumentException("Entity can not be null!");
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutEntitySoundEffect packet = new PacketOutEntitySoundEffect();
		packet.entityID = entity.getID();
		packet.sound = sound;
		packet.category = category;
		packet.volume = volume;
		packet.pitch = pitch;
		packet.seed = seed;
		con.sendPacked(packet);
	}
	
	@Override
	public void playSound(double x, double y, double z, Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutSoundEffect packet = new PacketOutSoundEffect();
		packet.x = x;
		packet.y = y;
		packet.z = z;
		packet.sound = sound;
		packet.category = category;
		packet.volume = volume;
		packet.pitch = pitch;
		packet.seed = seed;
		con.sendPacked(packet);
	}

	@Override
	public void stopSound(SoundCategory category, NamespacedKey sound) {
		PacketOutStopSound packet = new PacketOutStopSound();
		packet.category = category;
		packet.sound = sound;
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
	public void setFoodLevel(int level) {
		super.setFoodLevel(level);
		updateFoodLevel = true;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
	}
	
	@Override
	protected void update() {
		boolean healthUpdate = metaContainer.get(META_HEALTH).hasChanged();
		super.update();
		if (healthUpdate || updateFoodLevel) {
			PacketOutSetHealth packet = new PacketOutSetHealth();
			packet.health = metaContainer.getData(META_HEALTH);
			packet.food = getFoodLevel();
			packet.saturation = getFoodSaturationLevel();
			con.sendPacked(packet);
			updateFoodLevel = false;
		}
	}
	
	@Override
	public void teleport(double x, double y, double z, int flags) {
		super.teleport(x, y, z, flags);
		con.teleport(x, y, z, loc.pitch, loc.yaw, motion, flags);
	}
	
	@Override
	public void teleport(double x, double y, double z, float pitch, float yaw, int flags) {
		super.teleport(x, y, z, pitch, yaw, flags);
		con.teleport(x, y, z, yaw, pitch, motion, flags);
	}
	
	@Override
	public void teleport(SimpleLocation loc, Vector3d velocity, int flags) {
		super.teleport(loc, velocity, flags);
		if (velocity == null)
			velocity = super.motion;
		con.teleport(loc, velocity, flags);
	}

	@Override
	public void disconnect(Chat message) {
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

	@Override
	public boolean performCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		con.sendMessage(message, type, source, target);
	}

	@Override
	public void sendTranslation(String key, Object... values) {
		con.sendTranslation(key, values);
	}

}
