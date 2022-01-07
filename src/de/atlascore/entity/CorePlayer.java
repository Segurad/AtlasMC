package de.atlascore.entity;

import java.util.UUID;

import de.atlascore.inventory.CoreInventoryView;
import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.chat.ChatAdapter;
import de.atlasmc.chat.ChatChannel;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.inventory.InventoryCloseEvent;
import de.atlasmc.event.inventory.InventoryOpenEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState;
import de.atlasmc.io.protocol.play.PacketOutCloseWindow;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.io.protocol.play.PacketOutNamedSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutOpenWindow;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import de.atlasmc.io.protocol.play.PacketOutChangeGameState.ChangeReason;
import de.atlasmc.io.protocol.play.PacketOutSetExperiance;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutStopSound;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.scoreboard.ScoreboardView;

public class CorePlayer extends CoreHumanEntity implements Player {

	private PlayerConnection con;
	private Inventory open;
	private final CoreInventoryView view;
	private volatile ScoreboardView scoreView;
	
	private int level;
	private Gamemode gamemode;
	private ItemStack cursorItem;
	private boolean canBuild;
	private Object pluginData;
	private ChatAdapter chat;
	private PermissionHandler permHandler;
	
	public CorePlayer(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
		view = new CoreInventoryView(this, getInventory(), getInventory().getCraftingInventory(), 0);
	}
	
	@Override
	public void openInventory(Inventory inv) {
		if (inv == null) throw new IllegalArgumentException("Inventory can not be null!");
		if (inv == getInventory()) throw new IllegalArgumentException("Can not open own Inventory!");
		if (inv.getType().getID() == -1) throw new IllegalArgumentException("Can not open Inventory with this type (Please use the dedicated method!): " + inv.getType().name());
		if (open != null) closeInventory();
		InventoryOpenEvent event = new InventoryOpenEvent(view, inv);
		HandlerList.callEvent(event);
		if (event.isCancelled()) return;
		this.open = inv;
		view.setTopInventory(open);
		view.setViewID(con.getNextInventoryID());
		PacketOutOpenWindow packet = con.getProtocol().createPacket(PacketOutOpenWindow.class);
		packet.setWindowID(view.getViewID());
		packet.setWindowType(inv.getType());
		packet.setTitle(inv.getTitle().getJsonText());
		con.sendPacked(packet);
		inv.updateSlots(this);
	}

	@Override
	public boolean hasPermission(String permission) {
		return permHandler.hasPermission(permission);
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
		PacketOutSetExperiance setXP = con.getProtocol().createPacket(PacketOutSetExperiance.class);
		setXP.setLevel(level);
		con.sendPacked(setXP);
	}

	@Override
	public void playEffect(Location loc, Effect effect, Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		HandlerList.callEvent(new InventoryCloseEvent(view));
		open.getViewers().remove(this);
		open = null;
		view.setTopInventory(getInventory().getCraftingInventory());
		view.setViewID(0);
		PacketOutCloseWindow packet = con.getProtocol().createPacket(PacketOutCloseWindow.class);
		packet.setWindowID(con.getInventoryID());
		con.sendPacked(packet);
	}

	@Override
	public void setItemOnCursor(ItemStack item) {
		this.cursorItem = item;
	}

	@Override
	public ItemStack getItemOnCursor() {
		return cursorItem;
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
	public UUID getInternalUUID() {
		return con.getAtlasPlayer().getInteranlUUID();
	}

	@Override
	public void setInternalUUID(UUID uuid) {
		con.getAtlasPlayer().setInternalUUID(uuid);
	}

	@Override
	public String getLocal() {
		return con.getClientLocal();
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
		PacketOutChangeGameState packet = con.getProtocol().createPacket(PacketOutChangeGameState.class);
		packet.setReason(ChangeReason.CHANGE_GAMEMODE);
		packet.setValue(gamemode.ordinal());
		con.sendPacked(packet);
	}

	@Override
	public void updateItemOnCursor() {
		PacketOutSetSlot packet = con.getProtocol().createPacket(PacketOutSetSlot.class);
		packet.setWindowID(-1);
		packet.setSlot(-1);
		packet.setItem(cursorItem);
	}

	@Override
	public void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float particledata, int count, Object data) {
		if (!particle.isValid(data)) throw new IllegalArgumentException("Data is not valid!");
		PacketOutParticle packet = getConnection().getProtocol().createPacket(PacketOutParticle.class);
		packet.setParticle(particle);
		packet.setPoition(x, y, z);
		packet.setOffset(offX, offY, offZ);
		packet.setParticleData(particledata);
		packet.setParticleCount(count);
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
	public ChatAdapter getChat() {
		return chat;
	}

	@Override
	public void sendMessage(ChatChannel channel, ChatComponent chat) {
		this.chat.sendMessage(channel, chat);
	}

	@Override
	public void sendMessage(ChatComponent chat) {
		// TODO message in SystemChannel
	}

	@Override
	public PermissionHandler getPermissionHandler() {
		return permHandler;
	}

	@Override
	public void setPermissionHandler(PermissionHandler handler) {
		this.permHandler = handler;
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
		PacketOutSoundEffect packet = con.getProtocol().createPacket(PacketOutSoundEffect.class);
		packet.setPosition(x, y, z);
		packet.setSound(sound);
		packet.setCategory(category);
		packet.setVolume(volume);
		packet.setPitch(pitch);
		con.sendPacked(packet);
	}

	@Override
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
		PacketOutEntitySoundEffect packet = con.getProtocol().createPacket(PacketOutEntitySoundEffect.class);
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
		PacketOutNamedSoundEffect packet = con.getProtocol().createPacket(PacketOutNamedSoundEffect.class);
		packet.setPosition(x, y, z);
		packet.setIdentifier(sound);
		packet.setCategory(category);
		packet.setVolume(volume);
		packet.setPitch(pitch);
		con.sendPacked(packet);
	}

	@Override
	public void stopSound(SoundCategory category, String sound) {
		PacketOutStopSound packet = con.getProtocol().createPacket(PacketOutStopSound.class);
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

}
