package de.atlascore.io.protocol;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlascore.block.CoreBlock;
import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Atlas;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.chat.ChatChannel.ChannelType;
import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.block.BlockPlaceEvent;
import de.atlasmc.event.block.SignChangeEvent;
import de.atlasmc.event.inventory.BeaconEffectChangeEvent;
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
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerDiggingEvent.DiggingStatus;
import de.atlasmc.event.player.PlayerDropItemEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerInteractEvent;
import de.atlasmc.event.player.PlayerLeaveBedEvent;
import de.atlasmc.event.player.PlayerAnimationEvent.PlayerAnimationType;
import de.atlasmc.event.player.PlayerLocaleChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerPickItemEvent;
import de.atlasmc.event.player.PlayerQueryBlockNBTEvent;
import de.atlasmc.event.player.PlayerQueryEntityNBTEvent;
import de.atlasmc.event.player.PlayerQuickcraftRequestEvent;
import de.atlasmc.event.player.PlayerResourcePackStatusEvent;
import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
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
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.PacketProtocol;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.ProtocolPlay;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab;
import de.atlasmc.io.protocol.play.PacketInAnimation;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import de.atlasmc.io.protocol.play.PacketInClickWindow;
import de.atlasmc.io.protocol.play.PacketInClickWindowButton;
import de.atlasmc.io.protocol.play.PacketInClientSettings;
import de.atlasmc.io.protocol.play.PacketInClientStatus;
import de.atlasmc.io.protocol.play.PacketInClientStatus.StatusAction;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.raytracing.BlockRayCollisionRule;
import de.atlasmc.util.raytracing.BlockRayTracer;
import de.atlasmc.world.Chunk;
import de.atlasmc.io.protocol.play.PacketInCloseWindow;
import de.atlasmc.io.protocol.play.PacketInCraftRecipeRequest;
import de.atlasmc.io.protocol.play.PacketInCreativeInventoryAction;
import de.atlasmc.io.protocol.play.PacketInEditBook;
import de.atlasmc.io.protocol.play.PacketInEntityAction;
import de.atlasmc.io.protocol.play.PacketInGenerateStructure;
import de.atlasmc.io.protocol.play.PacketInHeldItemChange;
import de.atlasmc.io.protocol.play.PacketInInteractEntity;
import de.atlasmc.io.protocol.play.PacketInKeepAlive;
import de.atlasmc.io.protocol.play.PacketInLockDifficulty;
import de.atlasmc.io.protocol.play.PacketInNameItem;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import de.atlasmc.io.protocol.play.PacketInPlayerAbilities;
import de.atlasmc.io.protocol.play.PacketInPlayerBlockPlacement;
import de.atlasmc.io.protocol.play.PacketInPlayerDigging;
import de.atlasmc.io.protocol.play.PacketInPlayerMovement;
import de.atlasmc.io.protocol.play.PacketInPlayerPosition;
import de.atlasmc.io.protocol.play.PacketInPlayerPositionAndRotation;
import de.atlasmc.io.protocol.play.PacketInPlayerRotation;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import de.atlasmc.io.protocol.play.PacketInQueryBlockNBT;
import de.atlasmc.io.protocol.play.PacketInQueryEntityNBT;
import de.atlasmc.io.protocol.play.PacketInResourcePackStatus;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import de.atlasmc.io.protocol.play.PacketInSetBeaconEffect;
import de.atlasmc.io.protocol.play.PacketInSetDifficulty;
import de.atlasmc.io.protocol.play.PacketInSetDisplayedRecipe;
import de.atlasmc.io.protocol.play.PacketInSetRecipeBookState;
import de.atlasmc.io.protocol.play.PacketInSpectate;
import de.atlasmc.io.protocol.play.PacketInSteerBoat;
import de.atlasmc.io.protocol.play.PacketInSteerVehicle;
import de.atlasmc.io.protocol.play.PacketInTabComplete;
import de.atlasmc.io.protocol.play.PacketInTeleportConfirm;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlock;
import de.atlasmc.io.protocol.play.PacketInUpdateCommandBlockMinecart;
import de.atlasmc.io.protocol.play.PacketInUpdateJigsawBlock;
import de.atlasmc.io.protocol.play.PacketInUpdateSign;
import de.atlasmc.io.protocol.play.PacketInUpdateStructureBlock;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import de.atlasmc.io.protocol.play.PacketInVehicleMove;
import de.atlasmc.io.protocol.play.PacketInWindowConfirmation;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;
import de.atlasmc.io.protocol.play.PacketOutWindowConfirmation;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.recipe.RecipeBook;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab.Action;

public class CorePlayerConnection implements PlayerConnection {
	
	private Player player;
	private final AtlasPlayer aplayer;
	private final ConnectionHandler connection;
	private final ProtocolAdapter protocol;
	private final ProtocolPlay protocolPlay;
	private LocalServer server;
	private final ConcurrentLinkedQueue<Packet> inboundQueue;
	
	private byte invID;
	private int teleportID;
	private boolean teleportConfirmed = true;
	private HashMap<Integer, ItemStack> dragItems;
	private String advancementTabID;
	
	// Client Settings
	private String clientLocal;
	private ChannelType chatmode;
	private boolean chatColors = true;
	private byte viewDistance, skinparts, mainHand;
	
	// Client controlled values
	private boolean clientOnGround;
	
	// Events
	private PlayerMoveEvent eventMove;
	private PlayerAnimationEvent eventAnimation;
	private PlayerDiggingEvent eventDigging;
	private PlayerHeldItemChangeEvent eventHeldItemChange;
	private PlayerToggleSneakEvent eventSneak;
	private PlayerToggleSprintEvent eventSprint;
	
	// Keep Alive
	private long lastKeepAlive; // the Time the last KeepAlive has been send or received
	private boolean keepAliveResponse;
	
	// Click Control 
	private boolean ignoreClick;
	private short confirmNumber;
	
	// Digging related data
	private long diggingStartTime;
	private long diggingPosition;
	private BlockFace diggingFace;
	private boolean digging;
	
	// Recipe Book
	private RecipeBook recipeBook;
	
	public CorePlayerConnection(AtlasPlayer player, ConnectionHandler connection, ProtocolAdapter protocol) {
		this.aplayer = player;
		this.connection = connection;
		this.protocol = protocol;
		this.protocolPlay = protocol.getPlayProtocol();
		this.inboundQueue = new ConcurrentLinkedQueue<>();
		this.chatmode = ChannelType.CHAT;
	}
	
	@Override
	public void queueInboundPacket(Packet packet) {
		inboundQueue.add(packet);
	}
	
	@Override
	public void handleQueuedPackets() {
		final int size = inboundQueue.size();
		for (int i = 0; i < size; i++) {
			Packet packet = inboundQueue.poll();
			CorePacketListenerPlay.handlePacket(this, packet);
		}
	}

	@Override
	public void handlePacket(PacketInTeleportConfirm packet) {
		if (packet.getTeleportID() == teleportID) {
			teleportConfirmed = true;
		}
	}

	@Override
	public void handlePacket(PacketInQueryBlockNBT packet) {
		Location loc = MathUtil.getLocation(player.getWorld(), packet.getLocation());
		HandlerList.callEvent(new PlayerQueryBlockNBTEvent(player, packet.getTransactionID(), loc));
	}

	@Override
	public void handlePacket(PacketInQueryEntityNBT packet) {
		HandlerList.callEvent(new PlayerQueryEntityNBTEvent(player, packet.getTransactionID(), packet.getEntityID()));
	}

	@Override
	public void handlePacket(PacketInSetDifficulty packet) {
		// TODO Button not available in multiplayer clarification needed
		handleBadPacket(packet, BadPacketCause.UNEXPECTED_PACKET);
	}

	@Override
	public void handlePacket(PacketInChatMessage packet) {
		HandlerList.callEvent(new AsyncPlayerChatEvent(false, player, packet.getMessage()));
	}

	@Override
	public void handlePacket(PacketInClientStatus packet) {
		if (packet.getAction() == StatusAction.RESPAWN) {
			HandlerList.callEvent(new PlayerRespawnEvent(player));
		}
	}

	@Override
	public void handlePacket(PacketInClientSettings packet) {
		chatColors = packet.getChatColor();
		chatmode = packet.getChatMode();
		skinparts = packet.getDisplaySkinParts();
		String clientLocale = packet.getLocale();
		mainHand = (byte) packet.getMainHand();
		viewDistance = (byte) packet.getViewDistance();
		if (!clientLocale.equals(this.clientLocal)) {
			this.clientLocal = clientLocale;
			HandlerList.callEvent(new PlayerLocaleChangeEvent(player, clientLocale));
		}
	}

	@Override
	public void handlePacket(PacketInTabComplete packet) {
		// TODO command implementation needed
	}

	@Override
	public void handlePacket(PacketInWindowConfirmation packet) {
		if (ignoreClick) {
			if (confirmNumber == packet.getActionNumber()) {
				ignoreClick = false;
			}
		} else handleBadPacket(packet, BadPacketCause.UNEXPECTED_PACKET);
	}

	@Override
	public void handlePacket(PacketInClickWindowButton packet) {
		LocalServer s = getLocalSever();
		if (s == null) return;
		InventoryView inv = player.getOpenInventory();
		InventoryButtonType type = null;
		int id = packet.getButtonID();
		if (inv.getType() == InventoryType.ENCHANTING) {
			switch (id) {
			case 0: type = InventoryButtonType.ENCHANTING_TOP_ENCHANTMENT; break;
			case 1: type = InventoryButtonType.ENCHANTING_MIDDLE_ENCHANTMENT; break;
			case 2: type = InventoryButtonType.ENCHANTING_BOTTOM_EMCHANTMENT; break;
			}
			id = -1;
		} else if (inv.getType() == InventoryType.LECTERN) {
			switch (id) {
			case 1: type = InventoryButtonType.LECTERN_PREVIOUS_PAGE; break;
			case 2: type = InventoryButtonType.LECTERN_NEXT_PAGE; break;
			case 3: type = InventoryButtonType.LECTERN_TAKE_BOOK; break;
			default: type = InventoryButtonType.LECTERN_OPEN_PAGE_NUMBER; break;
			}
			if (id < 100) {
				id = -1;
			} else id -= 100;
		} else if (inv.getType() == InventoryType.STONECUTTER) {
			type = InventoryButtonType.STONECUTTER_RECIPE_NUMBER;
		} else if (inv.getType() == InventoryType.LOOM) {
			type = InventoryButtonType.LOOM_RECIPE_NUMBER;
		}
		HandlerList.callEvent(new InventoryClickButtonEvent(player.getOpenInventory(), type, id));
	}
	
	@Override
	public void handlePacket(PacketInClickWindow packet) {
		if (ignoreClick) {
			handleBadPacket(packet, BadPacketCause.BLOCKED_PACKET);
			return; // Block further clicks until PacketInWindowConfirmation after invalid click;
		}
		LocalServer s = getLocalSever();
		if (s == null) return;
		ClickType click = null;
		int key = -1;
		int slot = packet.getSlot();
		int button = packet.getButton();
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
			HandlerList.callEvent(new InventoryClickEvent(player.getOpenInventory(), slot, click, action, key));
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
	}

	@Override
	public void handlePacket(PacketInCloseWindow packet) {
		LocalServer s = getLocalSever();
		if (s == null) return;
		player.closeInventory();
	}

	@Override
	public void handlePacket(PacketInPluginMessage packet) {
		Atlas.getMessenger().handleInboundMessage(player, packet.getChannel(), packet.getData());
	}
	
	@Override
	public void handlePacket(PacketInEditBook packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInInteractEntity packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInGenerateStructure packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInKeepAlive packet) {
		if (packet.getKeepAliveID() != lastKeepAlive) return;
		lastKeepAlive = packet.getTimestamp();
		keepAliveResponse = true;
	}

	@Override
	public void handlePacket(PacketInLockDifficulty packet) {
		// TODO Button not available in multiplayer clarification needed
		handleBadPacket(packet, BadPacketCause.UNEXPECTED_PACKET);
	}

	@Override
	public void handlePacket(PacketInPlayerPosition packet) {
		eventMove.setCancelled(false);
		packet.getLocation(eventMove.getTo());
		clientOnGround = packet.isOnGround();
		player.getLocation(eventMove.getFrom());
		HandlerList.callEvent(eventMove);
	}

	@Override
	public void handlePacket(PacketInPlayerPositionAndRotation packet) {
		eventMove.setCancelled(false);
		packet.getLocation(eventMove.getTo());
		clientOnGround = packet.isOnGround();
		player.getLocation(eventMove.getFrom());
		HandlerList.callEvent(eventMove);
	}

	@Override
	public void handlePacket(PacketInPlayerRotation packet) {
		eventMove.setCancelled(false);
		packet.getLocation(eventMove.getTo());
		clientOnGround = packet.isOnGround();
		player.getLocation(eventMove.getFrom());
		HandlerList.callEvent(eventMove);
	}

	@Override
	public void handlePacket(PacketInPlayerMovement packet) {
		clientOnGround = packet.isOnGround();
	}

	@Override
	public void handlePacket(PacketInVehicleMove packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInSteerBoat packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInPickItem packet) {
		HandlerList.callEvent(new PlayerPickItemEvent(player, packet.getSlotToUse()));
	}

	@Override
	public void handlePacket(PacketInCraftRecipeRequest packet) {
		int windowID = packet.getWindowID();
		if (getInventoryID() != windowID) return;
		String recipe = packet.getRecipe();
		boolean craftAll = packet.getMakeAll();
		HandlerList.callEvent(new PlayerQuickcraftRequestEvent(player, recipe, craftAll));
	}
	
	@Override
	public void handlePacket(PacketInPlayerAbilities packet) {
		HandlerList.callEvent(new PlayerToggleFlightEvent(player, packet.isFlying()));
	}

	@Override
	public void handlePacket(PacketInPlayerDigging packet) {
		final int status = packet.getStatus();
		switch (status) {
		case 0: // Start Digging
			eventDigging.setCancelled(false);
			if (digging) {
				handleBadPacket(packet, BadPacketCause.UNEXPECTED_CONTENTS);
				eventDigging.setStatus(DiggingStatus.CANCELLED_DIGGING);
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
		case 1: // Cancelled Digging
		case 2: // Finished Digging
			eventDigging.setCancelled(false);
			if (!digging || diggingPosition != packet.getPosition() || diggingFace != packet.getFace()) {
				handleBadPacket(packet, BadPacketCause.UNEXPECTED_CONTENTS);
			}
			eventDigging.setStatus(status == 1 ? DiggingStatus.CANCELLED_DIGGING : DiggingStatus.FINISHED_DIGGING);
			eventDigging.setTime((int) (packet.getTimestamp()-diggingStartTime));
			HandlerList.callEvent(eventDigging);
			break;
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
	}

	@Override
	public void handlePacket(PacketInEntityAction packet) {
		// TODO
		switch (packet.getActionID()) {
		case 0: // Start sneaking
			eventSneak.setSneaking(true);
			HandlerList.callEvent(eventSneak);
			break;
		case 1: // Stop sneaking
			eventSneak.setSneaking(false);
			HandlerList.callEvent(eventSneak);
			break;
		case 2: // Leave Bed (GUI Button)
			HandlerList.callEvent(new PlayerLeaveBedEvent(player));
			break;
		case 3: // Start sprinting
			eventSprint.setSprinting(true);
			HandlerList.callEvent(eventSprint);
			break;
		case 4: // Stop sprinting
			eventSprint.setSprinting(false);
			HandlerList.callEvent(eventSprint);
			break;
		case 5: // Start horse jump
		case 6: // Stop horse jump
		case 7: // Open horse inventory (mounted)
		case 8: // start flying with elytra
			break;
		}
	}

	@Override
	public void handlePacket(PacketInSteerVehicle packet) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handlePacket(PacketInSetRecipeBookState packet) {
		HandlerList.callEvent(new PlayerSetRecipeBookStateEvent(player, packet.getBookID(), packet.getBookOpen(), packet.getFilterActive()));
	}

	@Override
	public void handlePacket(PacketInSetDisplayedRecipe packet) {
		HandlerList.callEvent(new PlayerSetDisplayRecipeEvent(player, packet.getRecipeID()));
	}

	@Override
	public void handlePacket(PacketInNameItem packet) {
		HandlerList.callEvent(new SmithingNameInputEvent(player.getOpenInventory(), packet.getItemName()));
	}

	@Override
	public void handlePacket(PacketInResourcePackStatus packet) {
		HandlerList.callEvent(new PlayerResourcePackStatusEvent(player, ResourcePackStatus.getByID(packet.getResult())));
	}

	@Override
	public void handlePacket(PacketInAdvancementTab packet) {
		if (packet.getAction() == Action.OPEN) {
			advancementTabID = packet.getTabID();
			HandlerList.callEvent(new AdvancementsOpenEvent(getPlayer(), advancementTabID));
		} else {
			HandlerList.callEvent(new AdvancementsCloseEvent(getPlayer()));
		}
	}

	@Override
	public void handlePacket(PacketInSelectTrade packet) {
		HandlerList.callEvent(new SelectTradeEvent(getPlayer().getOpenInventory(), packet.getSelectedSlot()));
	}

	@Override
	public void handlePacket(PacketInSetBeaconEffect packet) {
		int primaryID = packet.getPrimaryEffect();
		int secondaryID = packet.getSecondaryEffect();
		PotionEffect primary = PotionEffect.createByPotionID(primaryID);
		PotionEffect secondary = PotionEffect.createByPotionID(secondaryID);
		HandlerList.callEvent(new BeaconEffectChangeEvent(player.getOpenInventory(), primary, secondary, primaryID, secondaryID));
	}

	@Override
	public void handlePacket(PacketInHeldItemChange packet) {
		eventHeldItemChange.setCancelled(false);
		eventHeldItemChange.setNewSlot(packet.getSlot());
		HandlerList.callEvent(eventHeldItemChange);
	}

	@Override
	public void handlePacket(PacketInUpdateCommandBlock packet) {
		Location loc = MathUtil.getLocation(player.getWorld(), packet.getPosition());
		boolean trackoutput = (packet.getFlags() & 0x01) == 0x01;
		boolean conditional = (packet.getFlags() & 0x02) == 0x02;
		boolean alwaysactive = (packet.getFlags() & 0x04) == 0x04;
		HandlerList.callEvent(new PlayerUpdateCommandBlockEvent(player, loc, packet.getCommand(), packet.getMode(), trackoutput, conditional, alwaysactive));
	}

	@Override
	public void handlePacket(PacketInUpdateCommandBlockMinecart packet) {
		HandlerList.callEvent(new PlayerUpdateCommandBlockMinecartEvent(player, packet.getEntityID(), packet.getCommand(), packet.getTrackOutput()));
	}

	@Override
	public void handlePacket(PacketInCreativeInventoryAction packet) {
		HandlerList.callEvent(new InventoryCreativeClickEvent(player.getOpenInventory(), packet.getSlot() < 0 ? -999 : packet.getSlot(), packet.getClickedItem()));
	}

	@Override
	public void handlePacket(PacketInUpdateJigsawBlock packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInUpdateStructureBlock packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePacket(PacketInUpdateSign packet) {
		long pos = packet.getPosition();
		Block b = player.getWorld().getBlock(MathUtil.getPositionX(pos), MathUtil.getPositionY(pos), MathUtil.getPositionZ(pos));
		String[] lines = new String[] {
				packet.getLine1(),
				packet.getLine2(),
				packet.getLine3(),
				packet.getLine4()
		};
		HandlerList.callEvent(new SignChangeEvent(b, player, lines));
	}

	@Override
	public void handlePacket(PacketInAnimation packet) {
		eventAnimation.setAnimation(packet.getHand() == 0 ? 
				PlayerAnimationType.SWING_MAIN_HAND : PlayerAnimationType.SWING_OFF_HAND);
		eventAnimation.setCancelled(false);
		HandlerList.callEvent(eventAnimation);
	}

	@Override
	public void handlePacket(PacketInSpectate packet) {
		HandlerList.callEvent(new PlayerSpectateEvent(player, packet.getUUID()));
	}

	@Override
	public void handlePacket(PacketInPlayerBlockPlacement packet) {
		float cX = packet.getCursurPositionX();
		float cY = packet.getCursurPositionY();
		float cZ = packet.getCursurPositionZ();
		EquipmentSlot hand = packet.getHand();
		BlockFace face = packet.getFace();
		long pos = packet.getPosition();
		Location loc = MathUtil.getLocation(player.getWorld(), pos);
		Block block = new CoreBlockAccess(loc);
		Block against = new CoreBlock(loc, player.getInventory().getItemInMainHand().getType());
		HandlerList.callEvent(new BlockPlaceEvent(block, against, player, hand, face, cX, cY, cZ));
	}

	@Override
	public void handlePacket(PacketInUseItem packet) {
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
	}
	
	@Override
	public void handleUnhandledPacket(Packet packet) {
		// TODO unhandled Packet
	}
	
	/**
	 * Handling Packets that are not supposed to be received
	 * @param packet
	 */
	@Override
	public void handleBadPacket(Packet packet, BadPacketCause cause) {
		// TODO handle BadPacket
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
		confirmNumber = 0;
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
	public int getNextWindowActionID() {
		return confirmNumber;
	}
	
	@Override
	public int getNextWindowActionIDAndLock() {
		ignoreClick = true;
		return confirmNumber++;
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
	public ChannelType getChatMode() {
		return chatmode;
	}
	
	@Override
	public String getClientLocal() {
		return clientLocal;
	}
	
	public byte getViewDistance() {
		return viewDistance;
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
		if (!hasKeepAliveResponse()) return;
		lastKeepAlive = System.currentTimeMillis();
		keepAliveResponse = false;
		PacketOutKeepAlive packet = getProtocol().createPacket(PacketOutKeepAlive.class);
		packet.setKeepAlive(lastKeepAlive);
		sendPacked(packet);
	}

	@Override
	public void sendPacked(PacketProtocol packet) {
		connection.sendPacket(packet);
	}

	@Override
	public ProtocolPlay getProtocol() {
		return protocolPlay;
	}

	@Override
	public void setWindowLock() {
		PacketOutWindowConfirmation packet = getProtocol().createPacket(PacketOutWindowConfirmation.class);
		packet.setActionNumber(getNextWindowActionIDAndLock());
		packet.setAccepted(false);
		packet.setWindowID(invID);
		sendPacked(packet);
	}

}
