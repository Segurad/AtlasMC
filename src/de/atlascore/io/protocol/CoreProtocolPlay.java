package de.atlascore.io.protocol;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.protocol.play.PacketPlay.*;
import de.atlascore.io.protocol.play.*;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.protocol.ProtocolPlay;
import de.atlasmc.io.protocol.play.PacketPlay;

public class CoreProtocolPlay implements ProtocolPlay {
	
	private final List<Class<? extends PacketInbound>> playIn;
	private final List<Class<? extends PacketOutbound>> playOut;
	
	public CoreProtocolPlay() {
		playIn = new ArrayList<>(PacketPlay.PACKET_COUNT_IN);
		playIn.add(IN_TELEPORT_CONFIRM, CorePacketInTeleportConfirm.class); // 0x00
		playIn.add(IN_QUERY_BLOCK_NBT, CorePacketInQueryBlockNBT.class); // 0x01
		playIn.add(IN_SET_DIFFICULTY, CorePacketInSetDifficulty.class); // 0x02
		playIn.add(IN_CHAT_MESSAGE, CorePacketInChatMessage.class); // 0x03
		playIn.add(IN_CLIENT_STATUS, CorePacketInClientStatus.class); // 0x04
		playIn.add(IN_CLIENT_SETTINGS, CorePacketInClientSettings.class); // 0x05
		playIn.add(IN_TAB_COMPLETE, CorePacketInTabComplete.class); // 0x06
		playIn.add(IN_WINDOW_CONFIRMATION, CorePacketInWindowConfirmation.class); // 0x07
		playIn.add(IN_CLICK_WINDOW_BUTTON, CorePacketInClickWindowButton.class); // 0x08
		playIn.add(IN_CLICK_WINDOW, CorePacketInClickWindow.class); // 0x09
		playIn.add(IN_CLOSE_WINDOW, CorePacketInCloseWindow.class); // 0x0A
		playIn.add(IN_PLUGIN_MESSAGE, CorePacketInPluginMessage.class); // 0x0B
		playIn.add(IN_EDIT_BOOK, CorePacketInEditBook.class); // 0x0C
		playIn.add(IN_QUERY_ENTITY_NBT, CorePacketInQueryEntityNBT.class); // 0x0D
		playIn.add(IN_INTERACT_ENTITY, CorePacketInInteractEntity.class); // 0x0E
		playIn.add(IN_GENERATE_STRUCTURE, CorePacketInGenerateStructure.class); // 0x0F
		playIn.add(IN_KEEP_ALIVE, CorePacketInKeepAlive.class); // 0x10
		playIn.add(IN_LOCK_DIFFICULTY, CorePacketInLockDifficulty.class); // 0x11
		playIn.add(IN_PLAYER_POSITION, CorePacketInPlayerPosition.class); // 0x12
		playIn.add(IN_PLAYER_POSITION_AND_ROTATION, CorePacketInPlayerPositionAndRotation.class); // 0x13
		playIn.add(IN_PLAYER_ROTATION, CorePacketInPlayerRotation.class); // 0x14
		playIn.add(IN_PLAYER_MOVEMENT, CorePacketInPlayerMovement.class); // 0x15
		playIn.add(IN_VEHICLE_MOVE, CorePacketInVehicleMove.class); // 0x16
		playIn.add(IN_STEER_BOAT, CorePacketInSteerBoat.class); // 0x17
		playIn.add(IN_PICK_ITEM, CorePacketInPickItem.class); // 0x18
		playIn.add(IN_CRAFT_RECIPE_REQUEST, CorePacketInCraftRecipeRequest.class); // 0x19
		playIn.add(IN_PLAYER_ABILITIES, CorePacketInPlayerAbilities.class); // 0x1A
		playIn.add(IN_PLAYER_DIGGING, CorePacketInPlayerDigging.class); // 0x1B
		playIn.add(IN_ENTITY_ACTION, CorePacketInEntityAction.class); // 0x1C
		playIn.add(IN_STEER_VEHICLE, CorePacketInSteerVehicle.class); // 0x1D
		playIn.add(IN_SET_RECIPE_BOOK_STATE, CorePacketInSetRecipeBookState.class); // 0x1E
		playIn.add(IN_SET_DISPLAYED_RECIPE, CorePacketInSetDisplayedRecipe.class); // 0x1F
		playIn.add(IN_NAME_ITEM, CorePacketInNameItem.class); // 0x20
		playIn.add(IN_RESOURCE_PACKET_STATUS, CorePacketInResourcePackStatus.class); // 0x21
		playIn.add(IN_ADVANCEMENT_TAB, CorePacketInAdvancementTab.class); // 0x22
		playIn.add(IN_SELECT_TRADE, CorePacketInSelectTrade.class); // 0x23
		playIn.add(IN_SET_BEACON_EFFECT, CorePacketInSetBeaconEffect.class); // 0x24
		playIn.add(IN_HELD_ITEM_CHANGE, CorePacketInHeldItemChange.class); // 0x25
		playIn.add(IN_UPDATE_COMMAND_BLOCK, CorePacketInUpdateCommandBlock.class); // 0x26
		playIn.add(IN_UPDATE_COMMAND_BLOCK_MINECART, CorePacketInUpdateCommandBlockMinecart.class); // 0x27
		playIn.add(IN_CREATIVE_INVENTORY_ACTION, CorePacketInCreativeInventoryAction.class); // 0x28
		playIn.add(IN_UPDATE_JIGSAW_BLOCK, CorePacketInUpdateJigsawBlock.class); // 0x29
		playIn.add(IN_UPDATE_STRUCTURE_BLOCK, CorePacketInUpdateStructureBlock.class); // 0x2A
		playIn.add(IN_UPDATE_SIGN, CorePacketInUpdateSign.class); // 0x2B
		playIn.add(IN_ANIMATION, CorePacketInAnimation.class); // 0x2C
		playIn.add(IN_SPECTATE, CorePacketInSpectate.class); // 0x2D
		playIn.add(IN_PLAYER_BLOCK_PLACEMENT, CorePacketInPlayerBlockPlacement.class); // 0x2E
		playIn.add(IN_USE_ITEM, CorePacketInUseItem.class); // 0x2F
		// -----------------------------------------------------------------------------------
		playOut = new ArrayList<>(PacketPlay.PACKET_COUNT_OUT);
		playOut.add(OUT_SPAWN_ENTITY, CorePacketOutSpawnEntity.class); // 0x00
		playOut.add(OUT_SPAWN_EXPERIENCE_ORB, CorePacketOutSpawnExperienceOrb.class); // 0x01
		playOut.add(OUT_SPAWN_LIVING_ENTITY, CorePacketOutSpawnLivingEntity.class); // 0x02
		playOut.add(OUT_SPAWN_PAINTING, CorePacketOutSpawnPainting.class); // 0x03
		playOut.add(OUT_SPAWN_PLAYER, CorePacketOutSpawnPlayer.class); // 0x04
		playOut.add(OUT_ENTITY_ANIMATION, CorePacketOutEntityAnimation.class); // 0x05
		playOut.add(OUT_STATISTICS, CorePacketOutStatistics.class); // 0x06
		playOut.add(OUT_ACKNOWLEDGE_PLAYER_DIGGIN, CorePacketOutAcknowledgePlayerDigging.class); // 0x07
		playOut.add(OUT_BLOCK_BREAK_ANIMATION, CorePacketOutBlockBreakAnimation.class); // 0x08
		playOut.add(OUT_BLOCK_ENTITY_DATA, CorePacketOutBlockEntityData.class); // 0x09
		playOut.add(OUT_BLOCK_ACTION, CorePacketOutBlockAction.class); // 0x0A
		playOut.add(OUT_BLOCK_CHANGE, CorePacketOutBlockChange.class); // 0x0B
		playOut.add(OUT_BOSS_BAR, CorePacketOutBossBar.class); // 0x0C
		playOut.add(OUT_SERVER_DIFFICULTY, CorePacketOutServerDifficulty.class); // 0x0D
		playOut.add(OUT_CHAT_MESSAGE, CorePacketOutChatMessage.class); // 0x0E
		playOut.add(OUT_TAB_COMPLETE, CorePacketOutTabComplete.class); // 0x0F
		playOut.add(OUT_DECLARE_COMMANDS, CorePacketOutDeclareCommands.class); // 0x10
		playOut.add(OUT_WINDOW_CONFIRMATION, CorePacketOutWindowConfirmation.class); // 0x11
		playOut.add(OUT_CLOSE_WINDOW, CorePacketOutCloseWindow.class); // 0x12
		playOut.add(OUT_WINDOW_ITEMS, CorePacketOutWindowItems.class); // 0x13
		playOut.add(OUT_WINDOW_PROPERTY, CorePacketOutWindowProperty.class); // 0x14
		playOut.add(OUT_SET_SLOT, CorePacketOutSetSlot.class); // 0x15
		playOut.add(OUT_SET_COOLDOWN, CorePacketOutSetCooldown.class); // 0x16
		playOut.add(OUT_PLUGIN_MESSAGE, CorePacketOutPluginMessage.class); // 0x17
		playOut.add(OUT_NAMED_SOUD_EFFECT, CorePacketOutNamedSoundEffect.class); // 0x18
		playOut.add(OUT_DISCONNECT, CorePacketOutDisconnect.class); // 0x19
		playOut.add(OUT_ENTITY_STATUS, CorePacketOutEntityStatus.class); // 0x1A
		playOut.add(OUT_EXPLOSION, CorePacketOutExplosion.class); // 0x1B
		playOut.add(OUT_UNLOAD_CHUNK, CorePacketOutUnloadChunk.class); // 0x1C
		playOut.add(OUT_CHANGE_GAME_STATE, CorePacketOutChangeGameState.class); // 0x1D
		playOut.add(OUT_OPEN_HORSE_WINDOW, CorePacketOutOpenHorseWindow.class); // 0x1E
		playOut.add(OUT_KEEP_ALIVE, CorePacketOutKeepAlive.class); // 0x1F
		playOut.add(OUT_CHUNK_DATA, CorePacketOutChunkData.class); // 0x20
		playOut.add(OUT_EFFECT, CorePacketOutEffect.class); // 0x21
		playOut.add(OUT_PARTICLE, CorePacketOutParticle.class); // 0x22
		playOut.add(OUT_UPDATE_LIGHT, CorePacketOutUpdateLight.class); // 0x23
		playOut.add(OUT_JOIN_GAME, CorePacketOutJoinGame.class); // 0x24
		playOut.add(OUT_MAP_DATA, CorePacketOutMapData.class); // 0x25
		playOut.add(OUT_TRADE_LIST, CorePacketOutTradeList.class); // 0x26
		playOut.add(OUT_ENTITY_POSITION, CorePacketOutEntityPosition.class); // 0x27
		playOut.add(OUT_ENTITY_POSITION_AND_ROTATION, CorePacketOutEntityPositionAndRotation.class); // 0x28
		playOut.add(OUT_ENTITY_ROTATION, CorePacketOutEntityRotation.class); // 0x29
		playOut.add(OUT_ENTITY_MOVEMENT, CorePacketOutEntityMovement.class); // 0x2A
		playOut.add(OUT_VEHICLE_MOVE, CorePacketOutVehicleMove.class); // 0x2B
		playOut.add(OUT_OPEN_BOOK, CorePacketOutOpenBook.class); // 0x2C
		playOut.add(OUT_OPEN_WINDOW, CorePacketOutOpenWindow.class); // 0x2D
		playOut.add(OUT_OPEN_SIGN_EDITOR, CorePacketOutOpenSignEditor.class); // 0x2E
		playOut.add(OUT_CRAFT_RECIPE_RESPONSE, CorePacketOutCraftRecipeResponse.class); // 0x2F
		playOut.add(OUT_PLAYER_ABILITIES, CorePacketOutPlayerAbilities.class); // 0x30
		playOut.add(OUT_COMBAT_EVENT, CorePacketOutCombatEvent.class); // 0x31
		playOut.add(OUT_PLAYER_INFO, CorePacketOutPlayerInfo.class); // 0x32
		playOut.add(OUT_FACE_PLAYER, CorePacketOutFacePlayer.class); // 0x33
		playOut.add(OUT_PLAYER_POSITION_AND_LOOK, CorePacketOutPlayerPositionAndLook.class); // 0x34
		playOut.add(OUT_UNLOCK_RECIPES, CorePacketOutUnlockRecipes.class); // 0x35
		playOut.add(OUT_DESTROY_ENTITIES, CorePacketOutDestroyEntities.class); // 0x36
		playOut.add(OUT_REMOVE_ENTITY_EFFECT, CorePacketOutRemoveEntityEffect.class); // 0x37
		playOut.add(OUT_RESOURCE_PACK_SEND, CorePacketOutRessourcePackSend.class); // 0x38
		playOut.add(OUT_RESPAWN, CorePacketOutRespawn.class); // 0x39
		playOut.add(OUT_ENTITY_HEAD_LOOK, CorePacketOutEntityHeadLook.class); // 0x3A
		playOut.add(OUT_MULTI_BLOCK_CHANGE, CorePacketOutMultiBlockChange.class); // 0x3B
		playOut.add(OUT_SELECT_ADVANCEMENT_TAB, CorePacketOutSelectAdvancementTag.class); // 0x3C
		playOut.add(OUT_WORLD_BORDER, CorePacketOutWorldBorder.class); // 0x3D
		playOut.add(OUT_CAMERA, CorePacketOutCamera.class); // 0x3E
		playOut.add(OUT_HELD_ITEM_CHANGE, CorePacketOutHeldItemChange.class); // 0x3F
		playOut.add(OUT_UPDATE_VIEW_POSITION, CorePacketOutUpdateViewPosition.class); // 0x40
		playOut.add(OUT_UPDATE_VIEW_DISTANCE, CorePacketOutUpdateViewDistance.class); // 0x41
		playOut.add(OUT_SPAWN_POSITION, CorePacketOutSpawnPosition.class); // 0x42
		playOut.add(OUT_DISPLAY_SCOREBOARD, CorePacketOutDisplayScoreboard.class); // 0x43
		playOut.add(OUT_ENTITY_METADATA, CorePacketOutEntityMetadata.class); // 0x44
		playOut.add(OUT_ATTACH_ENTITY, CorePacketOutAttachEntity.class); // 0x45
		playOut.add(OUT_ENTITY_VELOCITY, CorePacketOutEntityVelocity.class); // 0x46
		playOut.add(OUT_ENTITY_EQUIPMENT, CorePacketOutEntityEquipment.class); // 0x47
		playOut.add(OUT_SET_EXPERIENCE, CorePacketOutSetExperience.class); // 0x48
		playOut.add(OUT_UPDATE_HEALTH, CorePacketOutUpdateHealth.class); // 0x49
		playOut.add(OUT_SCOREBOARD_OBJECTIVE, CorePacketOutScoreboardObjective.class); // 0x4A
		playOut.add(OUT_SET_PASSENGER, CorePacketOutSetPassengers.class); // 0x4B
		playOut.add(OUT_TEAMS, CorePacketOutTeams.class); // 0x4C
		playOut.add(OUT_UPDATE_SCORE, CorePacketOutUpdateScore.class); // 0x4D
		playOut.add(OUT_TIME_UPDATE, CorePacketOutTimeUpdate.class); // 0x4E
		playOut.add(OUT_TITLE, CorePacketOutTitle.class); // 0x4F
		playOut.add(OUT_ENTITY_SOUND_EFFECT, CorePacketOutEntitySoundEffect.class); // 0x50
		playOut.add(OUT_SOUND_EFFECT, CorePacketOutSoundEffect.class); // 0x51
		playOut.add(OUT_STOP_SOUND, CorePacketOutStopSound.class); // 0x52
		playOut.add(OUT_PLAYER_LIST_HEADER_AND_FOOTER, CorePacketOutPlayerListHeaderAndFooter.class); // 0x53
		playOut.add(OUT_NBT_QUERY_RESPONSE, CorePacketOutNBTQueryResponse.class); // 0x54
		playOut.add(OUT_COLLECT_ITEM, CorePacketOutCollectItem.class); // 0x55
		playOut.add(OUT_ENTITY_TELEPORT, CorePacketOutEntityTeleport.class); // 0x56
		playOut.add(OUT_ADVANCEMENTS, CorePacketOutAdvancements.class); // 0x57
		playOut.add(OUT_ENTITY_PROPERTIES, CorePacketOutEntityProperties.class); // 0x58
		playOut.add(OUT_ENTITY_EFFECT, CorePacketOutEntityEffect.class); // 0x59
		playOut.add(OUT_DECLARE_RECIPES, CorePacketOutDeclareRecipes.class); // 0x5A
		playOut.add(OUT_TAGS, CorePacketOutTags.class); // 0x5B
	}

	@Override
	public Packet createPacketIn(int id) {
		if (id > 47 || id < 0) return null;
		return construct(playIn.get(id));
	}

	@Override
	public Packet createPacketOut(int id) {
		if (id > 91 || id < 0) return null;
		return construct(playOut.get(id));
	}

	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createDefaultPacketListener(Object o) {
		if (Player.class.isInstance(o)) return null;
		return new CorePacketListenerPlay((Player) o);
	}

	@Override
	public Packet createCopy(Packet packet) {
		if (!(packet instanceof PacketPlay)) throw new IllegalArgumentException("Class is not assignable from PacketPlay!");
		// TODO
		if (packet instanceof PacketInbound) {
			return createPacketIn(packet.getDefaultID());
		} else {
			return createPacketOut(packet.getDefaultID());
		}
	}
	
	private Packet construct(Class<? extends Packet> clazz) {
		if (clazz == null) return null;
		try {
			Constructor<?> construct = clazz.getConstructor(null);
			return (Packet) construct.newInstance(null);
		} catch (NoSuchMethodException | SecurityException | 
				InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Packet> T createPacket(Class<T> clazz) {
		if (!clazz.isAssignableFrom(PacketPlay.class)) throw new IllegalArgumentException("Class is not assignable from PacketPlay!");
		DefaultPacketID id = clazz.getAnnotation(DefaultPacketID.class);
		if (id == null) throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation!");
		if (!clazz.isAssignableFrom(PacketInbound.class)) {
			return (T) createPacketIn(id.value());
		} else {
			return (T) createPacketOut(id.value());
		}
	}

	@Override
	public CorePacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item) {
		return new CorePacketOutSetSlot(windowID, slot, item);
	}

}
