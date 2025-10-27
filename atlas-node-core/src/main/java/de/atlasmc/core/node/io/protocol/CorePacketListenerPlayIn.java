package de.atlasmc.core.node.io.protocol;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.ChatMode;
import de.atlasmc.core.node.block.CoreBlock;
import de.atlasmc.core.node.block.CoreBlockAccess;
import de.atlasmc.event.HandlerList;
import de.atlasmc.io.Packet;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.node.Gamemode;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.Location;
import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.DiggingHandler;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.block.BlockPlaceEvent;
import de.atlasmc.node.event.block.SignChangeEvent;
import de.atlasmc.node.event.inventory.ClickType;
import de.atlasmc.node.event.inventory.InventoryAction;
import de.atlasmc.node.event.inventory.InventoryButtonType;
import de.atlasmc.node.event.inventory.InventoryClickButtonEvent;
import de.atlasmc.node.event.inventory.InventoryClickEvent;
import de.atlasmc.node.event.inventory.InventoryCreativeClickEvent;
import de.atlasmc.node.event.inventory.SelectTradeEvent;
import de.atlasmc.node.event.inventory.SmithingNameInputEvent;
import de.atlasmc.node.event.player.AdvancementsCloseEvent;
import de.atlasmc.node.event.player.AdvancementsOpenEvent;
import de.atlasmc.node.event.player.AsyncPlayerChatEvent;
import de.atlasmc.node.event.player.PlayerAnimationEvent;
import de.atlasmc.node.event.player.PlayerChangeDisplayedSkinPartsEvent;
import de.atlasmc.node.event.player.PlayerChatSettingsEvent;
import de.atlasmc.node.event.player.PlayerDropItemEvent;
import de.atlasmc.node.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.node.event.player.PlayerInteractEvent;
import de.atlasmc.node.event.player.PlayerLeaveBedEvent;
import de.atlasmc.node.event.player.PlayerLocaleChangeEvent;
import de.atlasmc.node.event.player.PlayerMainHandChangeEvent;
import de.atlasmc.node.event.player.PlayerMoveEvent;
import de.atlasmc.node.event.player.PlayerPickItemEvent;
import de.atlasmc.node.event.player.PlayerPluginChannelUnknownEvent;
import de.atlasmc.node.event.player.PlayerQueryBlockNBTEvent;
import de.atlasmc.node.event.player.PlayerQueryEntityNBTEvent;
import de.atlasmc.node.event.player.PlayerQuickcraftRequestEvent;
import de.atlasmc.node.event.player.PlayerResourcePackStatusEvent;
import de.atlasmc.node.event.player.PlayerRespawnEvent;
import de.atlasmc.node.event.player.PlayerSetDisplayRecipeEvent;
import de.atlasmc.node.event.player.PlayerSetRecipeBookStateEvent;
import de.atlasmc.node.event.player.PlayerSpectateEvent;
import de.atlasmc.node.event.player.PlayerSwapHandItemsEvent;
import de.atlasmc.node.event.player.PlayerToggleFlightEvent;
import de.atlasmc.node.event.player.PlayerToggleSneakEvent;
import de.atlasmc.node.event.player.PlayerToggleSprintEvent;
import de.atlasmc.node.event.player.PlayerUpdateCommandBlockEvent;
import de.atlasmc.node.event.player.PlayerUpdateCommandBlockMinecartEvent;
import de.atlasmc.node.event.player.PlayerViewDistanceChangeEvent;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.InventoryView;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.PlayerInventory;
import de.atlasmc.node.inventory.InventoryType.SlotType;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.PlayerSettings;
import de.atlasmc.node.io.protocol.play.PacketInAcknowledgeConfiguration;
import de.atlasmc.node.io.protocol.play.PacketInAcknowledgeMessage;
import de.atlasmc.node.io.protocol.play.PacketInBundleItemSelected;
import de.atlasmc.node.io.protocol.play.PacketInChangeContaierSlotState;
import de.atlasmc.node.io.protocol.play.PacketInChangeDifficulty;
import de.atlasmc.node.io.protocol.play.PacketInChangeRecipeBookSettings;
import de.atlasmc.node.io.protocol.play.PacketInChatCommand;
import de.atlasmc.node.io.protocol.play.PacketInChatMessage;
import de.atlasmc.node.io.protocol.play.PacketInChunkBatchReceived;
import de.atlasmc.node.io.protocol.play.PacketInClickContainer;
import de.atlasmc.node.io.protocol.play.PacketInClickContainerButton;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand;
import de.atlasmc.node.io.protocol.play.ServerboundClientInformation;
import de.atlasmc.node.io.protocol.play.PacketInClientTickEnd;
import de.atlasmc.node.io.protocol.play.PacketInCloseContainer;
import de.atlasmc.node.io.protocol.play.PacketInCommandSuggestionsRequest;
import de.atlasmc.node.io.protocol.play.PacketInConfirmTeleport;
import de.atlasmc.node.io.protocol.play.PacketInCookieResponse;
import de.atlasmc.node.io.protocol.play.PacketInDebugSampleSubscription;
import de.atlasmc.node.io.protocol.play.PacketInEditBook;
import de.atlasmc.node.io.protocol.play.PacketInInteract;
import de.atlasmc.node.io.protocol.play.PacketInJigsawGenerate;
import de.atlasmc.node.io.protocol.play.PacketInKeepAlive;
import de.atlasmc.node.io.protocol.play.PacketInLockDifficulty;
import de.atlasmc.node.io.protocol.play.PacketInMoveVehicle;
import de.atlasmc.node.io.protocol.play.PacketInPaddleBoat;
import de.atlasmc.node.io.protocol.play.PacketInPickItemFromBlock;
import de.atlasmc.node.io.protocol.play.PacketInPickItemFromEntity;
import de.atlasmc.node.io.protocol.play.PacketInPingRequest;
import de.atlasmc.node.io.protocol.play.PacketInPlaceRecipe;
import de.atlasmc.node.io.protocol.play.PacketInPlayerAbilities;
import de.atlasmc.node.io.protocol.play.PacketInPlayerAction;
import de.atlasmc.node.io.protocol.play.PacketInPlayerCommand;
import de.atlasmc.node.io.protocol.play.PacketInPlayerInput;
import de.atlasmc.node.io.protocol.play.PacketInPlayerLoaded;
import de.atlasmc.node.io.protocol.play.PacketInPlayerSession;
import de.atlasmc.node.io.protocol.play.PacketInPluginMessage;
import de.atlasmc.node.io.protocol.play.PacketInPong;
import de.atlasmc.node.io.protocol.play.PacketInProgramCommandBlock;
import de.atlasmc.node.io.protocol.play.PacketInProgramCommandBlockMinecart;
import de.atlasmc.node.io.protocol.play.PacketInProgramJigsawBlock;
import de.atlasmc.node.io.protocol.play.PacketInProgramStructureBlock;
import de.atlasmc.node.io.protocol.play.PacketInQueryBlockEntityTag;
import de.atlasmc.node.io.protocol.play.PacketInQueryEntityTag;
import de.atlasmc.node.io.protocol.play.PacketInRenameItem;
import de.atlasmc.node.io.protocol.play.PacketInResourcePack;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements;
import de.atlasmc.node.io.protocol.play.PacketInSelectTrade;
import de.atlasmc.node.io.protocol.play.PacketInSetBeaconEffect;
import de.atlasmc.node.io.protocol.play.PacketInSetCreativeModeSlot;
import de.atlasmc.node.io.protocol.play.PacketInSetHeldItem;
import de.atlasmc.node.io.protocol.play.PacketInSetPlayerMoveFlags;
import de.atlasmc.node.io.protocol.play.PacketInSetPlayerPosition;
import de.atlasmc.node.io.protocol.play.PacketInSetPlayerPositionAndRotation;
import de.atlasmc.node.io.protocol.play.PacketInSetPlayerRotation;
import de.atlasmc.node.io.protocol.play.PacketInSetSeenRecipe;
import de.atlasmc.node.io.protocol.play.PacketInSignedChatCommand;
import de.atlasmc.node.io.protocol.play.PacketInSwingArm;
import de.atlasmc.node.io.protocol.play.PacketInTeleportToEntity;
import de.atlasmc.node.io.protocol.play.PacketInUpdateSign;
import de.atlasmc.node.io.protocol.play.PacketInUseItem;
import de.atlasmc.node.io.protocol.play.PacketInUseItemOn;
import de.atlasmc.node.io.protocol.play.PacketPlay;
import de.atlasmc.node.io.protocol.play.PacketPlayIn;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand.StatusAction;
import de.atlasmc.node.io.protocol.play.PacketInSeenAdvancements.Action;
import de.atlasmc.node.recipe.BookType;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.raytracing.BlockRayCollisionRule;
import de.atlasmc.node.util.raytracing.BlockRayTracer;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.plugin.channel.PluginChannel;
import io.netty.buffer.ByteBuf;

public class CorePacketListenerPlayIn extends CoreAbstractPacketListener<PlayerConnection, PacketPlayIn> {

	private static final PacketHandler<?,?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLE_ASYNC = new boolean[PacketPlay.PACKET_COUNT_IN];
		HANDLERS = new PacketHandler[PacketPlay.PACKET_COUNT_IN];
		initHandler(PacketInConfirmTeleport.class, (con, packet) -> { // 0x00
			if (packet.teleportID == con.getTeleportID())
				con.confirmTeleport();
		});
		initHandler(PacketInQueryBlockEntityTag.class, (con, packet) -> { // 0x01
				Player player = con.getPlayer();
				WorldLocation loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
				HandlerList.callEvent(new PlayerQueryBlockNBTEvent(player, packet.getTransactionID(), loc));
		});
		initHandler(PacketInChangeDifficulty.class, (con, packet) -> { // 0x02
			// TODO Button not available in multiplayer clarification needed
		});
		initHandler(PacketInChatMessage.class, (con, packet) -> { // 0x03
			HandlerList.callEvent(new AsyncPlayerChatEvent(true, con.getPlayer(), packet.message));
		}, true);
		initHandler(PacketInClientCommand.class, (con, packet) -> { // 0x04
			if (packet.action == StatusAction.RESPAWN) {
				HandlerList.callEvent(new PlayerRespawnEvent(con.getPlayer()));
			}
		});
		initHandler(ServerboundClientInformation.class, (con, packet) -> { // 0x05
			PlayerSettings settings = con.getSettings();
			// Chat settings
			boolean chatColors = packet.chatColors;
			ChatMode chatmode = packet.chatMode;
			boolean allowServerListing = packet.allowServerListing;
			boolean enableTextFiltering = packet.enableTextFiltering;
			if (chatColors != settings.getChatColor() || 
					chatmode != settings.getChatMode() || 
					allowServerListing != settings.getAllowServerListing() ||
					enableTextFiltering != settings.hasTextFiltering()) {
				HandlerList.callEvent(new PlayerChatSettingsEvent(con.getPlayer(), chatColors, chatmode, enableTextFiltering, allowServerListing));
			}
			// Skin Parts
			int skinparts = packet.skinParts;
			if (skinparts != settings.getSkinParts()) {
				int oldSkinParts = settings.getSkinParts();
				settings.setSkinParts(skinparts);
				HandlerList.callEvent(new PlayerChangeDisplayedSkinPartsEvent(con.getPlayer(), skinparts, oldSkinParts));
			}
			// Main Hand
			int mainHand = packet.mainHand;
			if (mainHand != settings.getMainHand()) {
				settings.setMainHand(mainHand);
				HandlerList.callEvent(new PlayerMainHandChangeEvent(con.getPlayer(), mainHand));
			}
			// View Distance
			int viewDistance = packet.viewDistance;
			if (viewDistance != settings.getViewDistance()) {
				int oldDistance = settings.getViewDistance();
				settings.setViewDistance(viewDistance);
				HandlerList.callEvent(new PlayerViewDistanceChangeEvent(con.getPlayer(), viewDistance, oldDistance));
			}
			// Local
			String clientLocale = packet.local;
			if (!clientLocale.equals(settings.getLocal())) {
				settings.setLocal(clientLocale);
				HandlerList.callEvent(new PlayerLocaleChangeEvent(con.getPlayer(), clientLocale));
			}
		});
		initHandler(PacketInCommandSuggestionsRequest.class, (player, packet) -> { // 0x06
			// TODO handle packet
		});
		initHandler(PacketInClickContainerButton.class, (con, packet) -> { // 0x08
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			InventoryView view = con.getPlayer().getOpenInventory();
			InventoryButtonType type = null;
			int id = packet.buttonID;
			if (view.getType() == InventoryType.ENCHANTING) {
				switch (id) {
				case 0: 
					type = InventoryButtonType.ENCHANTING_TOP_ENCHANTMENT; 
					break;
				case 1: 
					type = InventoryButtonType.ENCHANTING_MIDDLE_ENCHANTMENT; 
					break;
				case 2: 
					type = InventoryButtonType.ENCHANTING_BOTTOM_EMCHANTMENT; 
					break;
				default:
					throw new ProtocolException("Unknown button id for Enchanting Inventory: " + id);
				}
				id = -1;
			} else if (view.getType() == InventoryType.LECTERN) {
				switch (id) {
				case 1: 
					type = InventoryButtonType.LECTERN_PREVIOUS_PAGE; 
					break;
				case 2: 
					type = InventoryButtonType.LECTERN_NEXT_PAGE; 
					break;
				case 3: 
					type = InventoryButtonType.LECTERN_TAKE_BOOK; 
					break;
				default: 
					type = InventoryButtonType.LECTERN_OPEN_PAGE_NUMBER; 
					break;
				}
				id = id < 100 ? -1 : id - 100;
			} else if (view.getType() == InventoryType.STONECUTTER) {
				type = InventoryButtonType.STONECUTTER_RECIPE_NUMBER;
			} else if (view.getType() == InventoryType.LOOM) {
				type = InventoryButtonType.LOOM_RECIPE_NUMBER;
			}
			HandlerList.callEvent(new InventoryClickButtonEvent(view, type, id));
		});
		initHandler(PacketInClickContainer.class, (con, packet) -> { // 0x09
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			int key = -1;
			final int slot = packet.slot;
			final int button = packet.button;
			final InventoryView view = con.getPlayer().getOpenInventory();
			int stateID = packet.stateID;
			Inventory clickedInv = view.getInventory(slot);
			if (clickedInv != null && stateID != clickedInv.getStateID()) {
				clickedInv.updateSlots(con.getPlayer());
				return; // inv out of sync resend to resync
			}
			if (packet.mode != 5) {
				InventoryAction action = InventoryAction.UNKNOWN;
				ClickType click = ClickType.UNKNOWN;
				final ItemStack slotItem = slot != -999 ? view.getItemUnsafe(slot) : null;
				final ItemStack cursorItem = con.getPlayer().getItemOnCursorUnsafe();
				if (slotItem == null && cursorItem == null)
					action = InventoryAction.NOTHING;
				switch (packet.mode) {
				case 0: 
					if (button == 0) {
						click = ClickType.LEFT;
						if (slotItem != null) {
							if (cursorItem == null) {
								action = InventoryAction.PICKUP_ALL;
							} else if (slotItem.isSimilar(cursorItem)) {
								if (slotItem.getAmount() < slotItem.getMaxAmount()) {
									action = InventoryAction.PLACE_SOME;
								} else {
									action = InventoryAction.NOTHING;
								}
							} else {
								action = InventoryAction.SWAP_WITH_CURSOR;
							}
						} else if (cursorItem != null) {
							action = InventoryAction.PLACE_ALL;
						}
					} else {
						click = ClickType.RIGHT;
						if (slotItem != null) {
							if (cursorItem == null) {
								action = InventoryAction.PICKUP_HALF;
							} else if (slotItem.isSimilar(cursorItem)) {
								if (slotItem.getAmount() < slotItem.getMaxAmount()) {
									action = InventoryAction.PLACE_ONE;
								} else {
									action = InventoryAction.NOTHING;
								}
							} else {
								action = InventoryAction.SWAP_WITH_CURSOR;
							}
						} else if (cursorItem != null) {
							action = InventoryAction.PLACE_ONE;
						}
					}
					break;
				case 1:
					if (button == 0) {
						click = ClickType.SHIFT_LEFT;
					} else {
						click = ClickType.SHIFT_RIGHT;
					}
					if (slotItem != null) {
						action = InventoryAction.MOVE_TO_OTHER_INVENTORY; // TODO check for available space else NOTHING
					}
					break;
				case 2:
					key = packet.button;
					click = ClickType.NUMBER_KEY;
					if (slot == 40)
						click = ClickType.SWAP_OFFHAND;
					if (cursorItem != null) {
						action = InventoryAction.NOTHING;
					} else if (view.getSlotType(slot) == SlotType.QUICKBAR) {
						action = InventoryAction.HOTBAR_SWAP;
					} else if (view.getSlotType(slot) != SlotType.OUTSIDE) {
						action = InventoryAction.HOTBAR_MOVE_AND_READD;
					}
					break;
				case 3:
					click = ClickType.MIDDLE;
					if (con.getPlayer().getGamemode() == Gamemode.CREATIVE) {
						action = InventoryAction.CLONE_STACK;
					}
					break;
				case 4:
					if (slot != -999) {
						if (button == 0) {
							click = ClickType.DROP;
							if (cursorItem == null && slotItem != null) {
								action = InventoryAction.DROP_ONE_SLOT;
							} else {
								action = InventoryAction.NOTHING;
							}
						} else {
							click = ClickType.CONTROL_DROP;
							if (cursorItem == null && slotItem != null) {
								action = InventoryAction.DROP_ALL_SLOT;
							} else {
								action = InventoryAction.NOTHING;
							}
						}
					} else if (button == 0) {
						click = ClickType.WINDOW_BORDER_LEFT;
						if (cursorItem != null) {
							action = InventoryAction.DROP_ALL_CURSOR;
						} else {
							action = InventoryAction.NOTHING;
						}
					} else {
						click = ClickType.WINDOW_BORDER_RIGHT;
						if (cursorItem != null) {
							action = InventoryAction.DROP_ONE_CURSOR;
						} else {
							action = InventoryAction.NOTHING;
						}
					}
					break;
				case 6:
					click = ClickType.DOUBLE_CLICK;
					if (slotItem != null) {
						action = InventoryAction.COLLECT_TO_CURSOR; // TODO check if any items of type are present else NOTHING
					}
					break;
				default:
					throw new IllegalArgumentException("Unknown mode: " + packet.mode);
				}
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
				default:
					throw new IllegalArgumentException("Unknown button: " + button);
				}
				
				//HandlerList.callEvent(new InventoryDragEvent(player.getOpenInventory(), newCursor, oldCursor, dragItems));
			}
		});
		initHandler(PacketInCloseContainer.class, (con, packet) -> { // 0x0A
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			con.getPlayer().closeInventory();
		});
		initHandler(PacketInPluginMessage.class, (con, packet) -> { // 0x0B
			PluginChannel channel = con.getPluginChannelHandler().getChannel(packet.channel);
			if (channel == null) {
				Player player = con.getPlayer();
				NamespacedKey channelID = packet.channel;
				ByteBuf data = packet.data;
				HandlerList.callEvent(new PlayerPluginChannelUnknownEvent(true, player, channelID, data));
				return;
			}
			channel.handleMessage(packet.data);
		});
		initHandler(PacketInEditBook.class, (con, packet) -> { // 0x0C
			// TODO handle packet
		});
		initHandler(PacketInInteract.class, (con, packet) -> { // 0x0E
			// TODO handle packet
		});
		initHandler(PacketInQueryEntityTag.class, (con, packet) -> { // 0x0D
			int transaction = packet.transactionID;
			int entityID = packet.entityID;
			HandlerList.callEvent(new PlayerQueryEntityNBTEvent(con.getPlayer(), transaction, entityID));
		});
		initHandler(PacketInJigsawGenerate.class, (player, packet) -> { // 0x0F
			// TODO handle packet
		});
		initHandler(PacketInKeepAlive.class, (con, packet) -> { // 0x10
			if (packet.keepAliveID != con.getLastKeepAlive()) 
				return;
			con.confirmKeepAlive(packet.getTimestamp());
		});
		initHandler(PacketInLockDifficulty.class, (con, packet) -> { // 0x11
			// TODO Button not available in multiplayer clarification needed
		});
		initHandler(PacketInSetPlayerPosition.class, (con, packet) -> { // 0x12
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			Location loc = con.getClientLocation();
			loc.x = packet.x;
			loc.y = packet.feetY;
			loc.z = packet.z;
			loc.copyTo(eventMove.getTo());
			boolean onGround = (packet.flags & 0x01) == 0x01;
			boolean pushWall = (packet.flags & 0x02) == 0x02;
			con.setClientOnGround(onGround);
			con.setClientPushWall(pushWall);
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInSetPlayerPositionAndRotation.class, (con, packet) -> { // 0x13
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			Location loc = con.getClientLocation();
			loc.x = packet.x;
			loc.y = packet.feetY;
			loc.z = packet.z;
			loc.pitch = packet.pitch;
			loc.yaw = packet.yaw;
			loc.copyTo(eventMove.getTo());
			boolean onGround = (packet.flags & 0x01) == 0x01;
			boolean pushWall = (packet.flags & 0x02) == 0x02;
			con.setClientOnGround(onGround);
			con.setClientPushWall(pushWall);
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInSetPlayerRotation.class, (con, packet) -> { // 0x14
			if (!con.isTeleportConfirmed()) 
				return;
			PlayerMoveEvent eventMove = con.getEventMove();
			eventMove.setCancelled(false);
			Location loc = con.getClientLocation();
			loc.yaw = packet.yaw;
			loc.pitch = packet.pitch;
			loc.copyTo(eventMove.getTo());
			boolean onGround = (packet.flags & 0x01) == 0x01;
			boolean pushWall = (packet.flags & 0x02) == 0x02;
			con.setClientOnGround(onGround);
			con.setClientPushWall(pushWall);
			con.getPlayer().getLocation(eventMove.getFrom());
			HandlerList.callEvent(eventMove);
		});
		initHandler(PacketInSetPlayerMoveFlags.class, (con, packet) -> { // 0x15
			if (!con.isTeleportConfirmed())
				return;
			boolean onGround = (packet.flags & 0x01) == 0x01;
			boolean pushWall = (packet.flags & 0x02) == 0x02;
			con.setClientOnGround(onGround);
			con.setClientPushWall(pushWall);
		});
		initHandler(PacketInMoveVehicle.class, (con, packet) -> { // 0x16
			// TODO handle packet
		});
		initHandler(PacketInPaddleBoat.class, (con, packet) -> { // 0x17
			// TODO handle packet
		});
		initHandler(PacketInPickItemFromBlock.class, (con, packet) -> { // 0x18
			HandlerList.callEvent(new PlayerPickItemEvent(con.getPlayer(), packet.slotToUse));
		});
		initHandler(PacketInPlaceRecipe.class, (con, packet) -> { // 0x19
			int windowID = packet.windowID;
			if (con.getInventoryID() != windowID) 
				return;
			NamespacedKey recipe = packet.recipe;
			boolean craftAll = packet.makeAll;
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerQuickcraftRequestEvent(player, recipe, craftAll));
		});
		initHandler(PacketInPlayerAbilities.class, (con, packet) -> { // 0x1A
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerToggleFlightEvent(player, packet.isFlying()));
		});
		initHandler(PacketInPlayerAction.class, (con, packet) -> { // 0x1B
			final int status = packet.status;
			Player player = con.getPlayer();
			switch (status) {
			case 0: { // Start Digging
				DiggingHandler handler = player.getDigging();
				long pos = packet.position;
				int x = MathUtil.getPositionX(pos);
				int y = MathUtil.getPositionY(pos);
				int z = MathUtil.getPositionZ(pos);
				handler.startDigging(packet.face, player.getWorld(), x, y, z);
				break;
			}
			case 1: { // Cancelled Digging
				DiggingHandler handler = player.getDigging();
				handler.cancelDigging();
				break;
			}
			case 2: { // Finished Digging
				DiggingHandler handler = player.getDigging();
				handler.finishDigging();
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
			default:
				throw new ProtocolException("Unknown status: " + status);
			}
		});
		initHandler(PacketInPlayerCommand.class, (con, packet) -> { // 0x1C
			switch (packet.action) {
			case START_SNEAKING: {
				PlayerToggleSneakEvent eventSneak = con.getEventSneak(); 
				eventSneak.setSneaking(true);
				HandlerList.callEvent(eventSneak);
				break;
			}
			case STOP_SNEAKING: {
				PlayerToggleSneakEvent eventSneak = con.getEventSneak(); 
				eventSneak.setSneaking(false);
				HandlerList.callEvent(eventSneak);
				break;
			}
			case LEAVE_BED:
				HandlerList.callEvent(new PlayerLeaveBedEvent(con.getPlayer()));
				break;
			case START_SPRINTING: {
				PlayerToggleSprintEvent eventSprint = con.getEventSprint();
				eventSprint.setSprinting(true);
				HandlerList.callEvent(eventSprint);
				break;
			}
			case STOP_SPRINTING: {
				PlayerToggleSprintEvent eventSprint = con.getEventSprint();
				eventSprint.setSprinting(false);
				HandlerList.callEvent(eventSprint);
				break;
			}
			case START_HORSE_JUMP:
				// TODO start horse jump
			case STOP_HORSE_JUMP:
				// TODO stop horse jump
			case OPEN_VEHICLE_INVENTORY:
				// TODO open vehicle inventory
			case START_FLYING_ELYTRA:
				// TODO elytra fly
				break;
			default:
				throw new ProtocolException("Unknown action: " + packet.action);
			}
		});
		initHandler(PacketInPlayerInput.class, (con, packet) -> { // 0x1D
			// TODO handle packet
		});
		initHandler(PacketInChangeRecipeBookSettings.class, (con, packet) -> { // 0x1E
			Player player = con.getPlayer();
			BookType type = packet.bookType;
			boolean open = packet.bookOpen;
			boolean filtered = packet.filterActive;
			HandlerList.callEvent(new PlayerSetRecipeBookStateEvent(player, type, open, filtered));
		});
		initHandler(PacketInSetSeenRecipe.class, (con, packet) -> { // 0x1F
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerSetDisplayRecipeEvent(player, packet.recipeID));
		});
		initHandler(PacketInRenameItem.class, (con, packet) -> { // 0x20
			Player player = con.getPlayer();
			HandlerList.callEvent(new SmithingNameInputEvent(player.getOpenInventory(), packet.itemName));
		});
		initHandler(PacketInResourcePack.class, (con, packet) -> { // 0x21
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerResourcePackStatusEvent(player, packet.uuid ,packet.status));
		});
		initHandler(PacketInSeenAdvancements.class, (con, packet) -> { // 0x22
			Player player = con.getPlayer();
			if (packet.action == Action.OPEN) {
				HandlerList.callEvent(new AdvancementsOpenEvent(player, packet.tabID));
			} else {
				HandlerList.callEvent(new AdvancementsCloseEvent(player));
			}
		});
		initHandler(PacketInSelectTrade.class, (con, packet) -> { // 0x23
			Player player = con.getPlayer();
			int oldID = con.getSelectedTrade();
			con.setSelectedTrade(packet.selectedSlot);
			HandlerList.callEvent(new SelectTradeEvent(player.getOpenInventory(), packet.selectedSlot, oldID));
		});
		initHandler(PacketInSetBeaconEffect.class, (listener, packet) -> { // 0x24
			// int primaryID = packet.getPrimaryEffect();
			// int secondaryID = packet.getSecondaryEffect();
			// PotionEffect primary = PotionEffectType.createByPotionID(primaryID); TODO research for ids
			// PotionEffect secondary = PotionEffectType.createByPotionID(secondaryID);
			// HandlerList.callEvent(new BeaconEffectChangeEvent(player.getOpenInventory(), primary, secondary, primaryID, secondaryID));
		});
		initHandler(PacketInSetHeldItem.class, (con, packet) -> { // 0x25
			PlayerHeldItemChangeEvent event = con.getEventHeldItemChange();
			event.setCancelled(false);
			event.setNewSlot(packet.slot);
			HandlerList.callEvent(event);
		});
		initHandler(PacketInProgramCommandBlock.class, (con, packet) -> { // 0x26
			Player player = con.getPlayer();
			WorldLocation loc = MathUtil.getLocation(player.getWorld(), packet.position);
			boolean trackoutput = (packet.flags & 0x01) == 0x01;
			boolean conditional = (packet.flags & 0x02) == 0x02;
			boolean alwaysactive = (packet.flags & 0x04) == 0x04;
			String command = packet.command;
			HandlerList.callEvent(new PlayerUpdateCommandBlockEvent(player, loc, command, packet.mode, trackoutput, conditional, alwaysactive));
		});
		initHandler(PacketInProgramCommandBlockMinecart.class, (con, packet) -> { // 0x27
			Player player = con.getPlayer();
			int entityID = packet.entityID;
			String command = packet.command;
			boolean trackOutput = packet.trackOutput;
			HandlerList.callEvent(new PlayerUpdateCommandBlockMinecartEvent(player, entityID, command, trackOutput));
		});
		initHandler(PacketInSetCreativeModeSlot.class, (con, packet) -> { // 0x28
			Player player = con.getPlayer();
			InventoryView view = player.getOpenInventory();
			int slot = packet.slot < 0 ? -999 : packet.slot;
			ItemStack item = packet.clickedItem;
			HandlerList.callEvent(new InventoryCreativeClickEvent(view, slot, item));
		});
		initHandler(PacketInProgramJigsawBlock.class, (con, packet) -> { // 0x29
			// TODO handle packet
		});
		initHandler(PacketInProgramStructureBlock.class, (con, packet) -> { // 0x2A
			// TODO handle packet
		});
		initHandler(PacketInUpdateSign.class, (con, packet) -> { // 0x2B
			long pos = packet.position;
			Player player = con.getPlayer();
			int x = MathUtil.getPositionX(pos);
			int y = MathUtil.getPositionY(pos);
			int z = MathUtil.getPositionZ(pos);
			boolean front = packet.isFront;
			Block b = player.getWorld().getBlock(x, y, z);
			String[] lines = new String[] {
					packet.line1,
					packet.line2,
					packet.line3,
					packet.line4
			};
			HandlerList.callEvent(new SignChangeEvent(b, player, lines, front));
		});
		initHandler(PacketInSwingArm.class, (con, packet) -> { // 0x2C
			PlayerAnimationEvent event = con.getEventAnimation();
			event.setAnimation(packet.hand);
			event.setCancelled(false);
			HandlerList.callEvent(event);
		});
		initHandler(PacketInTeleportToEntity.class, (con, packet) -> { // 0x2D
			HandlerList.callEvent(new PlayerSpectateEvent(con.getPlayer(), packet.uuid));
		});
		initHandler(PacketInUseItemOn.class, (con, packet) -> { // 0x2E
			float cX = packet.cursorPosX;
			float cY = packet.cursorPosY;
			float cZ = packet.cursorPosZ;
			EquipmentSlot hand = packet.hand;
			BlockFace face = packet.face;
			long pos = packet.position;
			Player player = con.getPlayer();
			WorldLocation loc = MathUtil.getLocation(player.getWorld(), pos);
			Block block = new CoreBlockAccess(loc);
			ItemType itemType;
			switch (hand) {
			case MAIN_HAND: 
				itemType = player.getInventory().getItemInMainHandUnsafe().getType();
				break;
			case OFF_HAND:
				itemType = player.getInventory().getItemInOffHandUnsafe().getType();
				break;
			default:
				throw new ProtocolException("Invalid equipment slot: " + hand);
			}
			BlockType blockType = itemType.getBlockType();
			Block against = new CoreBlock(loc, blockType);
			HandlerList.callEvent(new BlockPlaceEvent(block, against, player, hand, face, cX, cY, cZ));
		});
		initHandler(PacketInUseItem.class, (con, packet) -> { // 0x2F
			Player player = con.getPlayer();
			WorldLocation loc = player.getLocation();
			double length = player.getGamemode() == Gamemode.CREATIVE ? 5.0 : 4.5;
			BlockRayTracer ray = new BlockRayTracer(loc, loc.getDirection(), BlockRayCollisionRule.IGNORE_FUID_AND_AIR);
			Chunk chunk = ray.getFirstBlockHit(length);
			Block block = new CoreBlockAccess(loc, chunk);
			
			EquipmentSlot hand = packet.hand;
			PlayerInteractEvent.Action action;
			final BlockType air = BlockType.AIR.get();
			if (air == block.getType()) {
				if (hand == EquipmentSlot.MAIN_HAND) {
					action = PlayerInteractEvent.Action.LEFT_CLICK_AIR;
				} else action = PlayerInteractEvent.Action.RIGHT_CLICK_AIR;
			} else if (hand == EquipmentSlot.MAIN_HAND) {
				action = PlayerInteractEvent.Action.LEFT_CLICK_BLOCK;
			} else action = PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK;
			ItemStack item = player.getInventory().getItemInMainHand();
			HandlerList.callEvent(new PlayerInteractEvent(player, action, item, block, ray.getLastHitFace(), hand));
		});
		initHandler(PacketInAcknowledgeMessage.class, (con, packet) -> {
			// TODO handle message acknowledgment
		});
		initHandler(PacketInPong.class, (con, packet) -> {
			// TODO handle pong
		});
		initHandler(PacketInSignedChatCommand.class, (con, packet) -> {
			// TODO handle chat command
		});
		initHandler(PacketInPlayerSession.class, (con, packet) -> {
			// TODO handle session
		});
		initHandler(PacketInChunkBatchReceived.class, (con, packet) -> {
			con.setChunksPerTick(packet.chunksPerTick);
			// TODO handle chunks batch received
		});
		initHandler(PacketInAcknowledgeConfiguration.class, (con, packet) -> {
			con.protocolChangeAcknowledged();
		});
		initHandler(PacketInPingRequest.class, (con, packet) -> {
			// TODO handle ping request
		});
		initHandler(PacketInChangeContaierSlotState.class, (con, packet) -> {
			// TODO handle in change container slot state
		});
		initHandler(PacketInChatCommand.class, (con, packet) -> {
			// TODO handle in command
		});
		initHandler(PacketInCookieResponse.class, (con, packet) -> {
			// TODO handle cookie response
		});
		initHandler(PacketInDebugSampleSubscription.class, (con, packet) -> {
			// TODO handle debug sample subscription
		});
		initHandler(PacketInBundleItemSelected.class, (con, packet) -> {
			// TODO handle bundle item selected
		});
		initHandler(PacketInClientTickEnd.class, (con, packet) -> {
			// TODO handle client tick end
		});
		initHandler(PacketInPickItemFromEntity.class, (con, packet) -> {
			// TODO handle pick item from entity
		});
		initHandler(PacketInPlayerLoaded.class, (con, packet) -> {
			// TODO handle player loaded
		});
	}

	private static <T extends PacketPlayIn> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler) {
		initHandler(clazz, handler, false);
	}
		
	private static <T extends PacketPlayIn> void initHandler(Class<T> clazz, PacketHandler<PlayerConnection, T> handler, boolean async) {
		int id = Packet.getDefaultPacketID(clazz);
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}
	
	public CorePacketListenerPlayIn(PlayerConnection con) {
		super(con, PacketPlay.PACKET_COUNT_IN);
	}

	@Override
	protected boolean handleAsync(int packetID) {
		return HANDLE_ASYNC[packetID];
	}

	@Override
	protected void handle(Packet packet) {
		if (holder.isWaitingForProtocolChange() && !(packet instanceof PacketInAcknowledgeConfiguration))
			return;
		@SuppressWarnings("unchecked")
		PacketHandler<PlayerConnection, Packet> handler = (PacketHandler<PlayerConnection, Packet>) HANDLERS[packet.getID()];
		handler.handle(holder, packet);
	}

}
