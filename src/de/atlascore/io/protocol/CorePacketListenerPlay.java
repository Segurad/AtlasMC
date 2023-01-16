package de.atlascore.io.protocol;

import java.io.IOException;
import java.util.Queue;

import de.atlascore.block.CoreBlock;
import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.block.BlockPlaceEvent;
import de.atlasmc.event.block.SignChangeEvent;
import de.atlasmc.event.inventory.ClickType;
import de.atlasmc.event.inventory.InventoryAction;
import de.atlasmc.event.inventory.InventoryButtonType;
import de.atlasmc.event.inventory.InventoryClickButtonEvent;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.event.inventory.InventoryCreativeClickEvent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.SelectTradeEvent;
import de.atlasmc.event.inventory.SmithingNameInputEvent;
import de.atlasmc.event.player.AdvancementsCloseEvent;
import de.atlasmc.event.player.AdvancementsOpenEvent;
import de.atlasmc.event.player.AsyncPlayerChatEvent;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerDropItemEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerInteractEvent;
import de.atlasmc.event.player.PlayerLeaveBedEvent;
import de.atlasmc.event.player.PlayerLocaleChangeEvent;
import de.atlasmc.event.player.PlayerMainHandChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerPickItemEvent;
import de.atlasmc.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.event.player.PlayerQueryBlockNBTEvent;
import de.atlasmc.event.player.PlayerQueryEntityNBTEvent;
import de.atlasmc.event.player.PlayerQuickcraftRequestEvent;
import de.atlasmc.event.player.PlayerResourcePackStatusEvent;
import de.atlasmc.event.player.PlayerRespawnEvent;
import de.atlasmc.event.player.PlayerSetDisplayRecipeEvent;
import de.atlasmc.event.player.PlayerSetRecipeBookStateEvent;
import de.atlasmc.event.player.PlayerSpectateEvent;
import de.atlasmc.event.player.PlayerSwapHandItemsEvent;
import de.atlasmc.event.player.PlayerToggleFlightEvent;
import de.atlasmc.event.player.PlayerToggleSneakEvent;
import de.atlasmc.event.player.PlayerToggleSprintEvent;
import de.atlasmc.event.player.PlayerUpdateCommandBlockEvent;
import de.atlasmc.event.player.PlayerUpdateCommandBlockMinecartEvent;
import de.atlasmc.event.player.PlayerViewDistanceChangeEvent;
import de.atlasmc.event.player.PlayerAnimationEvent.PlayerAnimationType;
import de.atlasmc.event.player.PlayerChangeDisplayedSkinPartsEvent;
import de.atlasmc.event.player.PlayerChatSettingsEvent;
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerDiggingEvent.DiggingStatus;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.play.*;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab.Action;
import de.atlasmc.io.protocol.play.PacketInClientStatus.StatusAction;
import de.atlasmc.plugin.channel.PluginChannel;
import de.atlasmc.recipe.BookType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.raytracing.BlockRayCollisionRule;
import de.atlasmc.util.raytracing.BlockRayTracer;
import de.atlasmc.world.Chunk;

public class CorePacketListenerPlay implements PacketListener {

	private static final PacketHandler<?>[] handlers;
	private static final boolean[] handleAsync;
	
	static {
		handleAsync = new boolean[PacketPlay.PACKET_COUNT_IN];
		handlers = new PacketHandler[PacketPlay.PACKET_COUNT_IN];
		initHandler(PacketInTeleportConfirm.class, (con, packet) -> { // 0x00
			if (packet.getTeleportID() == con.getTeleportID())
				con.confirmTeleport();
		});
		initHandler(PacketInQueryBlockNBT.class, (con, packet) -> { // 0x01
				Player player = con.getPlayer();
				Location loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
				HandlerList.callEvent(new PlayerQueryBlockNBTEvent(player, packet.getTransactionID(), loc));
		});
		initHandler(PacketInSetDifficulty.class, (con, packet) -> { // 0x02
			// TODO Button not available in multiplayer clarification needed
		});
		initHandler(PacketInChatMessage.class, (con, packet) -> { // 0x03
			HandlerList.callEvent(new AsyncPlayerChatEvent(true, con.getPlayer(), packet.getMessage()));
		}, true);
		initHandler(PacketInClientStatus.class, (con, packet) -> { // 0x04
			if (packet.getAction() == StatusAction.RESPAWN) {
				HandlerList.callEvent(new PlayerRespawnEvent(con.getPlayer()));
			}
		});
		initHandler(PacketInClientSettings.class, (con, packet) -> { // 0x05
			PlayerSettings settings = con.getSettings();
			// Chat settings
			boolean chatColors = packet.getChatColor();
			ChatType chatmode = packet.getChatMode();
			if (chatColors != settings.getChatColor() || chatmode != settings.getChatMode()) {
				settings.setChatColor(chatColors);
				settings.setChatMode(chatmode);
				HandlerList.callEvent(new PlayerChatSettingsEvent(con.getPlayer(), chatColors, chatmode));
			}
			// Skin Parts
			int skinparts = packet.getDisplaySkinParts();
			if (skinparts != settings.getSkinParts()) {
				int oldSkinParts = settings.getSkinParts();
				settings.setSkinParts(skinparts);
				HandlerList.callEvent(new PlayerChangeDisplayedSkinPartsEvent(con.getPlayer(), skinparts, oldSkinParts));
			}
			// Main Hand
			int mainHand = packet.getMainHand();
			if (mainHand != settings.getMainHand()) {
				settings.setMainHand(mainHand);
				HandlerList.callEvent(new PlayerMainHandChangeEvent(con.getPlayer(), mainHand));
			}
			// View Distance
			int viewDistance = packet.getViewDistance();
			if (viewDistance != settings.getViewDistance()) {
				int oldDistance = settings.getViewDistance();
				settings.setViewDistance(viewDistance);
				HandlerList.callEvent(new PlayerViewDistanceChangeEvent(con.getPlayer(), viewDistance, oldDistance));
			}
			// Local
			String clientLocale = packet.getLocale();
			if (!clientLocale.equals(settings.getLocal())) {
				settings.setLocal(clientLocale);
				HandlerList.callEvent(new PlayerLocaleChangeEvent(con.getPlayer(), clientLocale));
			}
		});
		initHandler(PacketInTabComplete.class, (player, packet) -> { // 0x06
			// TODO handle packet
		});
		initHandler(PacketInWindowConfirmation.class, (con, packet) -> { // 0x07
			if (con.getWindowActionID() == packet.getActionNumber())
				con.unlockWindowActions();
		});
		initHandler(PacketInClickWindowButton.class, (con, packet) -> { // 0x08
			LocalServer s = con.getLocalSever();
			if (s == null) return;
			InventoryView view = con.getPlayer().getOpenInventory();
			InventoryButtonType type = null;
			int id = packet.getButtonID();
			if (view.getType() == InventoryType.ENCHANTING) {
				switch (id) {
				case 0: type = InventoryButtonType.ENCHANTING_TOP_ENCHANTMENT; break;
				case 1: type = InventoryButtonType.ENCHANTING_MIDDLE_ENCHANTMENT; break;
				case 2: type = InventoryButtonType.ENCHANTING_BOTTOM_EMCHANTMENT; break;
				}
				id = -1;
			} else if (view.getType() == InventoryType.LECTERN) {
				switch (id) {
				case 1: type = InventoryButtonType.LECTERN_PREVIOUS_PAGE; break;
				case 2: type = InventoryButtonType.LECTERN_NEXT_PAGE; break;
				case 3: type = InventoryButtonType.LECTERN_TAKE_BOOK; break;
				default: type = InventoryButtonType.LECTERN_OPEN_PAGE_NUMBER; break;
				}
				if (id < 100) {
					id = -1;
				} else id -= 100;
			} else if (view.getType() == InventoryType.STONECUTTER) {
				type = InventoryButtonType.STONECUTTER_RECIPE_NUMBER;
			} else if (view.getType() == InventoryType.LOOM) {
				type = InventoryButtonType.LOOM_RECIPE_NUMBER;
			}
			HandlerList.callEvent(new InventoryClickButtonEvent(view, type, id));
		});
		initHandler(PacketInClickWindow.class, (con, packet) -> { // 0x09
			if (con.isWindowActionLocked()) {
				return; // Block further clicks until PacketInWindowConfirmation after invalid click;
			}
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			ClickType click = null;
			int key = -1;
			int slot = packet.getSlot();
			int button = packet.getButton();
			con.getNextWindowActionID();
			InventoryView view = con.getPlayer().getOpenInventory();
			if (packet.getMode() != 5) {
				switch (packet.getMode()) {
				case 0: 
					if (button == 0) {
						click = ClickType.LEFT;
					} else {
						click = ClickType.RIGHT;
					}
					break;
				case 1:
					if (button == 0) {
						click = ClickType.SHIFT_LEFT;
					} else {
						click = ClickType.SHIFT_RIGHT;
					}
					break;
				case 2:
					key = packet.getButton();
					click = ClickType.NUMBER_KEY;
					break;
				case 3:
					click = ClickType.MIDDLE;
					break;
				case 4:
					if (slot != -999) {
						if (button == 0) {
							click = ClickType.DROP;
						} else {
							click = ClickType.CONTROL_DROP;
						}
					} else if (button == 0) {
						click = ClickType.WINDOW_BORDER_LEFT;
					} else {
						click = ClickType.WINDOW_BORDER_RIGHT;
					}
					break;
				case 6:
					click = ClickType.DOUBLE_CLICK;
					break;
				}
				InventoryAction action = null; // TODO make not null lol -> approach GUI API RealclickButton
				HandlerList.callEvent(new InventoryClickEvent(view, slot, click, action, key));
			} else {
				switch (button) {
				// TODO inventory paintmode
				case 0:
					// Start drag left
				case 4:
					// Start drag right
				case 8:
					// Start middle mouse drag
				case 1:
					// add slot left drag
				case 5:
					// add slot right drag
				case 9:
					// add slot middle drag
				case 2:
					// ending left drag
				case 6:
					// ending right drag
				case 10:
					// ending middle drag
					break;
				}
				
				//HandlerList.callEvent(new InventoryDragEvent(player.getOpenInventory(), newCursor, oldCursor, dragItems));
			}
		});
		initHandler(PacketInCloseWindow.class, (con, packet) -> { // 0x0A
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			con.getPlayer().closeInventory();
		});
		initHandler(PacketInPluginMessage.class, (con, packet) -> { // 0x0B
			PluginChannel channel = con.getPluginChannelHandler().getChannel(packet.getChannel());
			if (channel == null) {
				Player player = con.getPlayer();
				String channelID = packet.getChannel();
				byte[] data = packet.getData();
				HandlerList.callEvent(new PlayerPluginChannelUnknownEvent(true, player, channelID, data));
				return;
			}
			channel.handleMessage(packet.getData());
		});
		initHandler(PacketInEditBook.class, (con, packet) -> { // 0x0C
			// TODO handle packet
		});
		initHandler(PacketInInteractEntity.class, (con, packet) -> { // 0x0E
			// TODO handle packet
		});
		initHandler(PacketInQueryEntityNBT.class, (con, packet) -> { // 0x0D
			int transaction = packet.getTransactionID();
			int entityID = packet.getEntityID();
			HandlerList.callEvent(new PlayerQueryEntityNBTEvent(con.getPlayer(), transaction, entityID));
		});
		initHandler(PacketInGenerateStructure.class, (player, packet) -> { // 0x0F
			// TODO handle packet
		});
		initHandler(PacketInKeepAlive.class, (con, packet) -> { // 0x10
			if (packet.getKeepAliveID() != con.getLastKeepAlive()) 
				return;
			con.confirmKeepAlive(packet.getTimestamp());
		});
		initHandler(PacketInLockDifficulty.class, (con, packet) -> { // 0x11
			// TODO Button not available in multiplayer clarification needed
		});
		initHandler(PacketInPlayerPosition.class, (con, packet) -> { // 0x12
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			SimpleLocation clientLocation = con.getClientLocation();
			packet.getLocation(clientLocation);
			clientLocation.copyTo(eventMove.getTo());
			con.setClientOnGround(packet.isOnGround());
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInPlayerPositionAndRotation.class, (con, packet) -> { // 0x13
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			SimpleLocation clientLocation = con.getClientLocation();
			packet.getLocation(clientLocation);
			clientLocation.copyTo(eventMove.getTo());
			con.setClientOnGround(packet.isOnGround());
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInPlayerRotation.class, (con, packet) -> { // 0x14
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			SimpleLocation clientLocation = con.getClientLocation();
			packet.getLocation(clientLocation);
			clientLocation.copyTo(eventMove.getTo());
			con.setClientOnGround(packet.isOnGround());
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInPlayerMovement.class, (con, packet) -> { // 0x15
			if (!con.isTeleportConfirmed())
				return;
			con.setClientOnGround(packet.isOnGround());
		});
		initHandler(PacketInVehicleMove.class, (con, packet) -> { // 0x16
			// TODO handle packet
		});
		initHandler(PacketInSteerBoat.class, (con, packet) -> { // 0x17
			// TODO handle packet
		});
		initHandler(PacketInPickItem.class, (con, packet) -> { // 0x18
			HandlerList.callEvent(new PlayerPickItemEvent(con.getPlayer(), packet.getSlotToUse()));
		});
		initHandler(PacketInCraftRecipeRequest.class, (con, packet) -> { // 0x19
			int windowID = packet.getWindowID();
			if (con.getInventoryID() != windowID) 
				return;
			String recipe = packet.getRecipe();
			boolean craftAll = packet.getMakeAll();
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerQuickcraftRequestEvent(player, recipe, craftAll));
		});
		initHandler(PacketInPlayerAbilities.class, (con, packet) -> { // 0x1A
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerToggleFlightEvent(player, packet.isFlying()));
		});
		initHandler(PacketInPlayerDigging.class, (con, packet) -> { // 0x1B
			final int status = packet.getStatus();
			Player player = con.getPlayer();
			switch (status) {
			case 0: { // Start Digging
				PlayerDiggingEvent eventDigging = con.getEventDigging();
				eventDigging.setCancelled(false);
				if (con.isDigging()) {
					eventDigging.setStatus(DiggingStatus.CANCELLED_DIGGING);
					eventDigging.setFace(con.getDiggingFace());
					HandlerList.callEvent(eventDigging);
				}
				diggingStartTime = packet.getTimestamp();
				diggingFace = packet.getFace();
				diggingPosition = packet.getPosition();
				MathUtil.getLocation(player.getWorld(), eventDigging.getLocation(), diggingPosition);
				eventDigging.setFace(diggingFace);
				eventDigging.setStatus(DiggingStatus.START_DIGGING);
				eventDigging.setTime(0);
				HandlerList.callEvent(eventDigging);
				break;
			}
			case 1: // Cancelled Digging
			case 2: { // Finished Digging
				PlayerDiggingEvent eventDigging = con.getEventDigging();
				eventDigging.setCancelled(false);
				eventDigging.setStatus(status == 1 ? DiggingStatus.CANCELLED_DIGGING : DiggingStatus.FINISHED_DIGGING);
				eventDigging.setTime((int) (packet.getTimestamp()-diggingStartTime));
				HandlerList.callEvent(eventDigging);
				break;
			}
			case 3: // Drop ItemStack all
				ItemStack mainHand = player.getInventory().getItemInMainHand();
				HandlerList.callEvent(new PlayerDropItemEvent(player, mainHand, mainHand.getAmount(), mainHand.getAmount(), true));
				break;
			case 4: // Drop ItemStack one
				mainHand = player.getInventory().getItemInMainHand();
				HandlerList.callEvent(new PlayerDropItemEvent(player, mainHand, 1, 1, false));
				break;
			case 5: // Shoot arrow / finish eating
				// TODO eating shooting etc
				break;
			case 6: // Swap Item in Hand
				PlayerInventory inv = player.getInventory();
				mainHand = inv.getItemInMainHand();
				ItemStack offHand = inv.getItemInOffHand();
				HandlerList.callEvent(new PlayerSwapHandItemsEvent(player, mainHand, offHand));
				break;
			}
		});
		initHandler(PacketInEntityAction.class, (con, packet) -> { // 0x1C
			// TODO missing implementations
			switch (packet.getActionID()) {
			case 0: { // Start sneaking
				PlayerToggleSneakEvent eventSneak = con.getEventSneak(); 
				eventSneak.setSneaking(true);
				HandlerList.callEvent(eventSneak);
				break;
			}
			case 1: { // Stop sneaking
				PlayerToggleSneakEvent eventSneak = con.getEventSneak(); 
				eventSneak.setSneaking(false);
				HandlerList.callEvent(eventSneak);
				break;
			}
			case 2: // Leave Bed (GUI Button)
				HandlerList.callEvent(new PlayerLeaveBedEvent(con.getPlayer()));
				break;
			case 3: { // Start sprinting
				PlayerToggleSprintEvent eventSprint = con.getEventSprint();
				eventSprint.setSprinting(true);
				HandlerList.callEvent(eventSprint);
				break;
			}
			case 4: { // Stop sprinting
				PlayerToggleSprintEvent eventSprint = con.getEventSprint();
				eventSprint.setSprinting(false);
				HandlerList.callEvent(eventSprint);
				break;
			}
			case 5: // Start horse jump
			case 6: // Stop horse jump
			case 7: // Open horse inventory (mounted)
			case 8: // start flying with elytra
				break;
			}
		});
		initHandler(PacketInSteerVehicle.class, (con, packet) -> { // 0x1D
			// TODO handle packet
		});
		initHandler(PacketInSetRecipeBookState.class, (con, packet) -> { // 0x1E
			Player player = con.getPlayer();
			BookType type = packet.getBookType();
			boolean open = packet.isBookOpen();
			boolean filtered = packet.isFilterActive();
			HandlerList.callEvent(new PlayerSetRecipeBookStateEvent(player, type, open, filtered));
		});
		initHandler(PacketInSetDisplayedRecipe.class, (con, packet) -> { // 0x1F
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerSetDisplayRecipeEvent(player, packet.getRecipeID()));
		});
		initHandler(PacketInNameItem.class, (con, packet) -> { // 0x20
			Player player = con.getPlayer();
			HandlerList.callEvent(new SmithingNameInputEvent(player.getOpenInventory(), packet.getItemName()));
		});
		initHandler(PacketInResourcePackStatus.class, (con, packet) -> { // 0x21
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerResourcePackStatusEvent(player, packet.getStatus()));
		});
		initHandler(PacketInAdvancementTab.class, (con, packet) -> { // 0x22
			Player player = con.getPlayer();
			if (packet.getAction() == Action.OPEN) {
				HandlerList.callEvent(new AdvancementsOpenEvent(player, packet.getTabID()));
			} else {
				HandlerList.callEvent(new AdvancementsCloseEvent(player));
			}
		});
		initHandler(PacketInSelectTrade.class, (con, packet) -> { // 0x23
			Player player = con.getPlayer();
			HandlerList.callEvent(new SelectTradeEvent(player.getOpenInventory(), packet.getSelectedSlot()));
		});
		initHandler(PacketInUseItem.class, (listener, packet) -> { // 0x24
			// int primaryID = packet.getPrimaryEffect();
			// int secondaryID = packet.getSecondaryEffect();
			// PotionEffect primary = PotionEffectType.createByPotionID(primaryID); TODO research for ids
			// PotionEffect secondary = PotionEffectType.createByPotionID(secondaryID);
			// HandlerList.callEvent(new BeaconEffectChangeEvent(player.getOpenInventory(), primary, secondary, primaryID, secondaryID));
		});
		initHandler(PacketInHeldItemChange.class, (con, packet) -> { // 0x25
			PlayerHeldItemChangeEvent event = con.getEventHeldItemChange();
			event.setCancelled(false);
			event.setNewSlot(packet.getSlot());
			HandlerList.callEvent(event);
		});
		initHandler(PacketInUpdateCommandBlock.class, (con, packet) -> { // 0x26
			Player player = con.getPlayer();
			Location loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
			boolean trackoutput = (packet.getFlags() & 0x01) == 0x01;
			boolean conditional = (packet.getFlags() & 0x02) == 0x02;
			boolean alwaysactive = (packet.getFlags() & 0x04) == 0x04;
			String command = packet.getCommand();
			HandlerList.callEvent(new PlayerUpdateCommandBlockEvent(player, loc, command, packet.getMode(), trackoutput, conditional, alwaysactive));
		});
		initHandler(PacketInUpdateCommandBlockMinecart.class, (con, packet) -> { // 0x27
			Player player = con.getPlayer();
			int entityID = packet.getEntityID();
			String command = packet.getCommand();
			boolean trackOutput = packet.getTrackOutput();
			HandlerList.callEvent(new PlayerUpdateCommandBlockMinecartEvent(player, entityID, command, trackOutput));
		});
		initHandler(PacketInCreativeInventoryAction.class, (con, packet) -> { // 0x28
			Player player = con.getPlayer();
			InventoryView view = player.getOpenInventory();
			int slot = packet.getSlot() < 0 ? -999 : packet.getSlot();
			ItemStack item = packet.getClickedItem();
			HandlerList.callEvent(new InventoryCreativeClickEvent(view, slot, item));
		});
		initHandler(PacketInUpdateJigsawBlock.class, (con, packet) -> { // 0x29
			// TODO handle packet
		});
		initHandler(PacketInUpdateStructureBlock.class, (con, packet) -> { // 0x2A
			// TODO handle packet
		});
		initHandler(PacketInUpdateSign.class, (con, packet) -> { // 0x2B
			long pos = packet.getPosition();
			Player player = con.getPlayer();
			int x = MathUtil.getPositionX(pos);
			int y = MathUtil.getPositionY(pos);
			int z = MathUtil.getPositionZ(pos);
			Block b = player.getWorld().getBlock(x, y, z);
			String[] lines = new String[] {
					packet.getLine1(),
					packet.getLine2(),
					packet.getLine3(),
					packet.getLine4()
			};
			HandlerList.callEvent(new SignChangeEvent(b, player, lines));
		});
		initHandler(PacketInAnimation.class, (con, packet) -> { // 0x2C
			PlayerAnimationEvent event = con.getEventAnimation();
			event.setAnimation(packet.getHand() == 0 ? 
					PlayerAnimationType.SWING_MAIN_HAND : PlayerAnimationType.SWING_OFF_HAND);
			event.setCancelled(false);
			HandlerList.callEvent(event);
		});
		initHandler(PacketInSpectate.class, (con, packet) -> { // 0x2D
			HandlerList.callEvent(new PlayerSpectateEvent(con.getPlayer(), packet.getUUID()));
		});
		initHandler(PacketInPlayerBlockPlacement.class, (con, packet) -> { // 0x2E
			float cX = packet.getCursurPositionX();
			float cY = packet.getCursurPositionY();
			float cZ = packet.getCursurPositionZ();
			EquipmentSlot hand = packet.getHand();
			BlockFace face = packet.getFace();
			long pos = packet.getPosition();
			Player player = con.getPlayer();
			Location loc = MathUtil.getLocation(player.getWorld(), pos);
			Block block = new CoreBlockAccess(loc);
			Block against = new CoreBlock(loc, player.getInventory().getItemInMainHand().getType());
			HandlerList.callEvent(new BlockPlaceEvent(block, against, player, hand, face, cX, cY, cZ));
		});
		initHandler(PacketInUseItem.class, (con, packet) -> { // 0x2F
			Player player = con.getPlayer();
			Location loc = player.getLocation();
			double length = player.getGamemode() == Gamemode.CREATIVE ? 5.0 : 4.5;
			BlockRayTracer ray = new BlockRayTracer(loc, loc.getDirection(), BlockRayCollisionRule.IGNORE_FUID_AND_AIR);
			Chunk chunk = ray.getFirstBlockHit(length);
			Block block = new CoreBlockAccess(loc, chunk);
			
			EquipmentSlot hand = packet.getHand();
			PlayerInteractEvent.Action action;
			if (block.getType().isAir()) {
				if (hand == EquipmentSlot.HAND) {
					action = PlayerInteractEvent.Action.LEFT_CLICK_AIR;
				} else action = PlayerInteractEvent.Action.RIGHT_CLICK_AIR;
			} else if (hand == EquipmentSlot.HAND) {
				action = PlayerInteractEvent.Action.LEFT_CLICK_BLOCK;
			} else action = PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;
			ItemStack item = player.getInventory().getItemInMainHand();
			HandlerList.callEvent(new PlayerInteractEvent(player, action, item, block, ray.getLastHitFace(), hand));
		});
	}

	private static <T extends PacketPlayIn> PacketHandler<T> initHandler(Class<T> clazz, PacketHandler<T> handler) {
		initHandler(clazz, handler, false);
	}
		
	private static <T extends PacketPlayIn> PacketHandler<T> initHandler(Class<T> clazz, PacketHandler<T> handler, boolean async) {
		DefaultPacketID annotation = clazz.getAnnotation(DefaultPacketID.class);
	    if (annotation == null) 
	    	throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation!");
	    int id = annotation.value();
	    handlers[id] = handler;
	    handleAsync[id] = async;
	}
	
	private PlayerConnection con;
	private Queue<Packet> syncQueue;
	
	public CorePacketListenerPlay(PlayerConnection con) {
		this.con = con;
	}

	@Override
	public void handlePacket(Packet packet) {
		int id = packet.getID();
		if (id < 0 && id >= PacketPlayIn.PACKET_COUNT_IN) {
			unhandledPacket(con, packet);
			return;
		}
		if (handleAsync[id]) {
			internalHandlePacket(packet);
		} else
			syncQueue.add(packet);
	}

	@Override
	public void handleUnregister() {}
	
	protected static void unhandledPacket(PlayerConnection player, Packet packet) {
		throw new IllegalStateException("Not implemented unhandledPacket"); // TODO
	}
	
	@FunctionalInterface
	public interface PacketHandler<T extends Packet> {
		public void handle(PlayerConnection con, T packet);
	}

	@Override
	public void handleSyncPackets() throws IOException {
		Packet packet = null;
		while ((packet = syncQueue.poll()) != null) {
			internalHandlePacket(packet);
		}
	}
	
	private void internalHandlePacket(Packet packet) {
		int id = packet.getID();
		@SuppressWarnings("unchecked")
		PacketHandler<Packet> handler = (PacketHandler<Packet>) handlers[id];
		handler.handle(con, packet);
	}

}
