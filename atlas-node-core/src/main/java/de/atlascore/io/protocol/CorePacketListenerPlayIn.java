package de.atlascore.io.protocol;

import de.atlascore.block.CoreBlock;
import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.chat.ChatMode;
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
import de.atlasmc.event.inventory.SelectTradeEvent;
import de.atlasmc.event.inventory.SmithingNameInputEvent;
import de.atlasmc.event.player.AdvancementsCloseEvent;
import de.atlasmc.event.player.AdvancementsOpenEvent;
import de.atlasmc.event.player.AsyncPlayerChatEvent;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerAnimationEvent.PlayerAnimationType;
import de.atlasmc.event.player.PlayerChangeDisplayedSkinPartsEvent;
import de.atlasmc.event.player.PlayerChatSettingsEvent;
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
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.PlayerSettings;
import de.atlasmc.io.protocol.play.PacketInChangeDifficulty;
import de.atlasmc.io.protocol.play.PacketInChangeRecipeBookSettings;
import de.atlasmc.io.protocol.play.PacketInChatCommand;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import de.atlasmc.io.protocol.play.PacketInChunkBatchReceived;
import de.atlasmc.io.protocol.play.PacketInClickContainer;
import de.atlasmc.io.protocol.play.PacketInClickContainerButton;
import de.atlasmc.io.protocol.play.PacketInClientCommand;
import de.atlasmc.io.protocol.play.PacketInClientCommand.StatusAction;
import de.atlasmc.io.protocol.play.PacketInClientInformation;
import de.atlasmc.io.protocol.play.PacketInCloseContainer;
import de.atlasmc.io.protocol.play.PacketInCommandSuggestionsRequest;
import de.atlasmc.io.protocol.play.PacketInConfigurationAcknowledged;
import de.atlasmc.io.protocol.play.PacketInConfirmTeleport;
import de.atlasmc.io.protocol.play.PacketInEditBook;
import de.atlasmc.io.protocol.play.PacketInInteract;
import de.atlasmc.io.protocol.play.PacketInJigsawGenerate;
import de.atlasmc.io.protocol.play.PacketInKeepAlive;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import de.atlasmc.io.protocol.play.PacketInMessageAcknowledgment;
import de.atlasmc.io.protocol.play.PacketInMoveVehicle;
import de.atlasmc.io.protocol.play.PacketInPaddleBoat;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import de.atlasmc.io.protocol.play.PacketInPingRequest;
import de.atlasmc.io.protocol.play.PacketInPlaceRecipe;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import de.atlasmc.io.protocol.play.PacketInPlayerAction;
import de.atlasmc.io.protocol.play.PacketInPlayerCommand;
import de.atlasmc.io.protocol.play.PacketInPlayerInput;
import de.atlasmc.io.protocol.play.PacketInPlayerSession;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import de.atlasmc.io.protocol.play.PacketInPong;
import de.atlasmc.io.protocol.play.PacketInProgramCommandBlock;
import de.atlasmc.io.protocol.play.PacketInProgramCommandBlockMinecart;
import de.atlasmc.io.protocol.play.PacketInProgramJigsawBlock;
import de.atlasmc.io.protocol.play.PacketInProgramStructureBlock;
import de.atlasmc.io.protocol.play.PacketInQueryBlockEntityTag;
import de.atlasmc.io.protocol.play.PacketInQueryEntityTag;
import de.atlasmc.io.protocol.play.PacketInRenameItem;
import de.atlasmc.io.protocol.play.PacketInResourcePack;
import de.atlasmc.io.protocol.play.PacketInSeenAdvancements;
import de.atlasmc.io.protocol.play.PacketInSeenAdvancements.Action;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;
import de.atlasmc.io.protocol.play.PacketInSetCreativeModeSlot;
import de.atlasmc.io.protocol.play.PacketInSetHeldItem;
import de.atlasmc.io.protocol.play.PacketInSetPlayerOnGround;
import de.atlasmc.io.protocol.play.PacketInSetPlayerPosition;
import de.atlasmc.io.protocol.play.PacketInSetPlayerPositionAndRotation;
import de.atlasmc.io.protocol.play.PacketInSetPlayerRotation;
import de.atlasmc.io.protocol.play.PacketInSetSeenRecipe;
import de.atlasmc.io.protocol.play.PacketInSwingArm;
import de.atlasmc.io.protocol.play.PacketInTeleportToEntity;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import de.atlasmc.io.protocol.play.PacketInUseItemOn;
import de.atlasmc.io.protocol.play.PacketPlay;
import de.atlasmc.io.protocol.play.PacketPlayIn;
import de.atlasmc.plugin.channel.PluginChannel;
import de.atlasmc.recipe.BookType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.raytracing.BlockRayCollisionRule;
import de.atlasmc.util.raytracing.BlockRayTracer;
import de.atlasmc.world.Chunk;
import io.netty.buffer.ByteBuf;

public class CorePacketListenerPlayIn extends CoreAbstractPacketListener<PlayerConnection, PacketPlayIn> {

	private static final PacketHandler<?,?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLE_ASYNC = new boolean[PacketPlay.PACKET_COUNT_IN];
		HANDLERS = new PacketHandler[PacketPlay.PACKET_COUNT_IN];
		initHandler(PacketInConfirmTeleport.class, (con, packet) -> { // 0x00
			if (packet.getTeleportID() == con.getTeleportID())
				con.confirmTeleport();
		});
		initHandler(PacketInQueryBlockEntityTag.class, (con, packet) -> { // 0x01
				Player player = con.getPlayer();
				Location loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
				HandlerList.callEvent(new PlayerQueryBlockNBTEvent(player, packet.getTransactionID(), loc));
		});
		initHandler(PacketInChangeDifficulty.class, (con, packet) -> { // 0x02
			// TODO Button not available in multiplayer clarification needed
		});
		initHandler(PacketInChatMessage.class, (con, packet) -> { // 0x03
			HandlerList.callEvent(new AsyncPlayerChatEvent(true, con.getPlayer(), packet.getMessage()));
		}, true);
		initHandler(PacketInClientCommand.class, (con, packet) -> { // 0x04
			if (packet.getAction() == StatusAction.RESPAWN) {
				HandlerList.callEvent(new PlayerRespawnEvent(con.getPlayer()));
			}
		});
		initHandler(PacketInClientInformation.class, (con, packet) -> { // 0x05
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
			int id = packet.getButtonID();
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
		initHandler(PacketInClickContainer.class, (con, packet) -> { // 0x09
			LocalServer s = con.getLocalSever();
			if (s == null) 
				return;
			int key = -1;
			final int slot = packet.getSlot();
			final int button = packet.getButton();
			final InventoryView view = con.getPlayer().getOpenInventory();
			int stateID = packet.getStateID();
			Inventory clickedInv = view.getInventory(slot);
			if (clickedInv != null && stateID != clickedInv.getStateID()) {
				clickedInv.updateSlots(con.getPlayer());
				return; // inv out of sync resend to resync
			}
			if (packet.getMode() != 5) {
				InventoryAction action = InventoryAction.UNKNOWN;
				ClickType click = ClickType.UNKNOWN;
				final ItemStack slotItem = slot != -999 ? view.getItemUnsafe(slot) : null;
				final ItemStack cursorItem = con.getPlayer().getItemOnCursorUnsafe();
				if (slotItem == null && cursorItem == null)
					action = InventoryAction.NOTHING;
				switch (packet.getMode()) {
				case 0: 
					if (button == 0) {
						click = ClickType.LEFT;
						if (slotItem != null) {
							if (cursorItem == null) {
								action = InventoryAction.PICKUP_ALL;
							} else if (slotItem.isSimilar(cursorItem)) {
								if (slotItem.getAmount() < slotItem.getMaxStackSize()) {
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
								if (slotItem.getAmount() < slotItem.getMaxStackSize()) {
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
					key = packet.getButton();
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
			int transaction = packet.getTransactionID();
			int entityID = packet.getEntityID();
			HandlerList.callEvent(new PlayerQueryEntityNBTEvent(con.getPlayer(), transaction, entityID));
		});
		initHandler(PacketInJigsawGenerate.class, (player, packet) -> { // 0x0F
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
		initHandler(PacketInSetPlayerPosition.class, (con, packet) -> { // 0x12
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
		initHandler(PacketInSetPlayerPositionAndRotation.class, (con, packet) -> { // 0x13
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
		initHandler(PacketInSetPlayerRotation.class, (con, packet) -> { // 0x14
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
		initHandler(PacketInSetPlayerOnGround.class, (con, packet) -> { // 0x15
			if (!con.isTeleportConfirmed())
				return;
			con.setClientOnGround(packet.isOnGround());
		});
		initHandler(PacketInMoveVehicle.class, (con, packet) -> { // 0x16
			// TODO handle packet
		});
		initHandler(PacketInPaddleBoat.class, (con, packet) -> { // 0x17
			// TODO handle packet
		});
		initHandler(PacketInPickItem.class, (con, packet) -> { // 0x18
			HandlerList.callEvent(new PlayerPickItemEvent(con.getPlayer(), packet.getSlotToUse()));
		});
		initHandler(PacketInPlaceRecipe.class, (con, packet) -> { // 0x19
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
		initHandler(PacketInPlayerAction.class, (con, packet) -> { // 0x1B
			final int status = packet.getStatus();
			Player player = con.getPlayer();
			switch (status) {
			case 0: { // Start Digging
				DiggingHandler handler = player.getDigging();
				long pos = packet.getPosition();
				int x = MathUtil.getPositionX(pos);
				int y = MathUtil.getPositionY(pos);
				int z = MathUtil.getPositionZ(pos);
				handler.startDigging(packet.getFace(), player.getWorld(), x, y, z);
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
			}
		});
		initHandler(PacketInPlayerCommand.class, (con, packet) -> { // 0x1C
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
		initHandler(PacketInPlayerInput.class, (con, packet) -> { // 0x1D
			// TODO handle packet
		});
		initHandler(PacketInChangeRecipeBookSettings.class, (con, packet) -> { // 0x1E
			Player player = con.getPlayer();
			BookType type = packet.getBookType();
			boolean open = packet.isBookOpen();
			boolean filtered = packet.isFilterActive();
			HandlerList.callEvent(new PlayerSetRecipeBookStateEvent(player, type, open, filtered));
		});
		initHandler(PacketInSetSeenRecipe.class, (con, packet) -> { // 0x1F
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerSetDisplayRecipeEvent(player, packet.getRecipeID()));
		});
		initHandler(PacketInRenameItem.class, (con, packet) -> { // 0x20
			Player player = con.getPlayer();
			HandlerList.callEvent(new SmithingNameInputEvent(player.getOpenInventory(), packet.getItemName()));
		});
		initHandler(PacketInResourcePack.class, (con, packet) -> { // 0x21
			Player player = con.getPlayer();
			HandlerList.callEvent(new PlayerResourcePackStatusEvent(player, packet.getStatus()));
		});
		initHandler(PacketInSeenAdvancements.class, (con, packet) -> { // 0x22
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
			event.setNewSlot(packet.getSlot());
			HandlerList.callEvent(event);
		});
		initHandler(PacketInProgramCommandBlock.class, (con, packet) -> { // 0x26
			Player player = con.getPlayer();
			Location loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
			boolean trackoutput = (packet.getFlags() & 0x01) == 0x01;
			boolean conditional = (packet.getFlags() & 0x02) == 0x02;
			boolean alwaysactive = (packet.getFlags() & 0x04) == 0x04;
			String command = packet.getCommand();
			HandlerList.callEvent(new PlayerUpdateCommandBlockEvent(player, loc, command, packet.getMode(), trackoutput, conditional, alwaysactive));
		});
		initHandler(PacketInProgramCommandBlockMinecart.class, (con, packet) -> { // 0x27
			Player player = con.getPlayer();
			int entityID = packet.getEntityID();
			String command = packet.getCommand();
			boolean trackOutput = packet.getTrackOutput();
			HandlerList.callEvent(new PlayerUpdateCommandBlockMinecartEvent(player, entityID, command, trackOutput));
		});
		initHandler(PacketInSetCreativeModeSlot.class, (con, packet) -> { // 0x28
			Player player = con.getPlayer();
			InventoryView view = player.getOpenInventory();
			int slot = packet.getSlot() < 0 ? -999 : packet.getSlot();
			ItemStack item = packet.getClickedItem();
			HandlerList.callEvent(new InventoryCreativeClickEvent(view, slot, item));
		});
		initHandler(PacketInProgramJigsawBlock.class, (con, packet) -> { // 0x29
			// TODO handle packet
		});
		initHandler(PacketInProgramStructureBlock.class, (con, packet) -> { // 0x2A
			// TODO handle packet
		});
		initHandler(PacketInUpdateSign.class, (con, packet) -> { // 0x2B
			long pos = packet.getPosition();
			Player player = con.getPlayer();
			int x = MathUtil.getPositionX(pos);
			int y = MathUtil.getPositionY(pos);
			int z = MathUtil.getPositionZ(pos);
			boolean front = packet.isFront();
			Block b = player.getWorld().getBlock(x, y, z);
			String[] lines = new String[] {
					packet.getLine1(),
					packet.getLine2(),
					packet.getLine3(),
					packet.getLine4()
			};
			HandlerList.callEvent(new SignChangeEvent(b, player, lines, front));
		});
		initHandler(PacketInSwingArm.class, (con, packet) -> { // 0x2C
			PlayerAnimationEvent event = con.getEventAnimation();
			event.setAnimation(packet.getHand() == 0 ? 
					PlayerAnimationType.SWING_MAIN_HAND : PlayerAnimationType.SWING_OFF_HAND);
			event.setCancelled(false);
			HandlerList.callEvent(event);
		});
		initHandler(PacketInTeleportToEntity.class, (con, packet) -> { // 0x2D
			HandlerList.callEvent(new PlayerSpectateEvent(con.getPlayer(), packet.getUUID()));
		});
		initHandler(PacketInUseItemOn.class, (con, packet) -> { // 0x2E
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
		initHandler(PacketInMessageAcknowledgment.class, (con, packet) -> {
			// TODO handle message acknowledgment
		});
		initHandler(PacketInPong.class, (con, packet) -> {
			// TODO handle pong
		});
		initHandler(PacketInChatCommand.class, (con, packet) -> {
			// TODO handle chat command
		});
		initHandler(PacketInPlayerSession.class, (con, packet) -> {
			// TODO handle session
		});
		initHandler(PacketInChunkBatchReceived.class, (con, packet) -> {
			con.setChunksPerTick(packet.chunksPerTick);
			// TODO handle chunks batch received
		});
		initHandler(PacketInConfigurationAcknowledged.class, (con, packet) -> {
			con.protocolChangeAcknowledged();
		});
		initHandler(PacketInPingRequest.class, (con, packet) -> {
			// TODO handle ping request
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
		if (holder.isWaitingForProtocolChange() && !(packet instanceof PacketInConfigurationAcknowledged))
			return;
		@SuppressWarnings("unchecked")
		PacketHandler<PlayerConnection, Packet> handler = (PacketHandler<PlayerConnection, Packet>) HANDLERS[packet.getID()];
		handler.handle(holder, packet);
	}

}
