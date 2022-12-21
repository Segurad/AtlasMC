package de.atlascore.io.protocol;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.protocol.play.PacketPlay.*;

import de.atlascore.io.CoreAbstractHandler;
import de.atlascore.io.protocol.play.*;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.ProtocolPlay;
import de.atlasmc.io.protocol.play.PacketPlay;
import de.atlasmc.io.protocol.play.PacketPlayIn;
import de.atlasmc.io.protocol.play.PacketPlayOut;
import io.netty.buffer.ByteBuf;

public class CoreProtocolPlay implements ProtocolPlay {
	
	private final List<CoreAbstractHandler<? extends PacketPlayIn>> playIn;
	private final List<CoreAbstractHandler<? extends PacketPlayOut>> playOut;
	
	public CoreProtocolPlay() {
		playIn = new ArrayList<>(PacketPlay.PACKET_COUNT_IN);
		playIn.add(IN_TELEPORT_CONFIRM, new CorePacketInTeleportConfirm()); // 0x00
		playIn.add(IN_QUERY_BLOCK_NBT, new CorePacketInQueryBlockNBT()); // 0x01
		playIn.add(IN_SET_DIFFICULTY, new CorePacketInSetDifficulty()); // 0x02
		playIn.add(IN_CHAT_MESSAGE, new CorePacketInChatMessage()); // 0x03
		playIn.add(IN_CLIENT_STATUS, new CorePacketInClientStatus()); // 0x04
		playIn.add(IN_CLIENT_SETTINGS, new CorePacketInClientSettings()); // 0x05
		playIn.add(IN_TAB_COMPLETE, new CorePacketInTabComplete()); // 0x06
		playIn.add(IN_WINDOW_CONFIRMATION, new CorePacketInWindowConfirmation()); // 0x07
		playIn.add(IN_CLICK_WINDOW_BUTTON, new CorePacketInClickWindowButton()); // 0x08
		playIn.add(IN_CLICK_WINDOW, new CorePacketInClickWindow()); // 0x09
		playIn.add(IN_CLOSE_WINDOW, new CorePacketInCloseWindow()); // 0x0A
		playIn.add(IN_PLUGIN_MESSAGE, new CorePacketInPluginMessage()); // 0x0B
		playIn.add(IN_EDIT_BOOK, new CorePacketInEditBook()); // 0x0C
		playIn.add(IN_QUERY_ENTITY_NBT, new CorePacketInQueryEntityNBT()); // 0x0D
		playIn.add(IN_INTERACT_ENTITY, new CorePacketInInteractEntity()); // 0x0E
		playIn.add(IN_GENERATE_STRUCTURE, new CorePacketInGenerateStructure()); // 0x0F
		playIn.add(IN_KEEP_ALIVE, new CorePacketInKeepAlive()); // 0x10
		playIn.add(IN_LOCK_DIFFICULTY, new CorePacketInLockDifficulty()); // 0x11
		playIn.add(IN_PLAYER_POSITION, new CorePacketInPlayerPosition()); // 0x12
		playIn.add(IN_PLAYER_POSITION_AND_ROTATION, new CorePacketInPlayerPositionAndRotation()); // 0x13
		playIn.add(IN_PLAYER_ROTATION, new CorePacketInPlayerRotation()); // 0x14
		playIn.add(IN_PLAYER_MOVEMENT, new CorePacketInPlayerMovement()); // 0x15
		playIn.add(IN_VEHICLE_MOVE, new CorePacketInVehicleMove()); // 0x16
		playIn.add(IN_STEER_BOAT, new CorePacketInSteerBoat()); // 0x17
		playIn.add(IN_PICK_ITEM, new CorePacketInPickItem()); // 0x18
		playIn.add(IN_CRAFT_RECIPE_REQUEST, new CorePacketInCraftRecipeRequest()); // 0x19
		playIn.add(IN_PLAYER_ABILITIES, new CorePacketInPlayerAbilities()); // 0x1A
		playIn.add(IN_PLAYER_DIGGING, new CorePacketInPlayerDigging()); // 0x1B
		playIn.add(IN_ENTITY_ACTION, new CorePacketInEntityAction()); // 0x1C
		playIn.add(IN_STEER_VEHICLE, new CorePacketInSteerVehicle()); // 0x1D
		playIn.add(IN_SET_RECIPE_BOOK_STATE, new CorePacketInSetRecipeBookState()); // 0x1E
		playIn.add(IN_SET_DISPLAYED_RECIPE, new CorePacketInSetDisplayedRecipe()); // 0x1F
		playIn.add(IN_NAME_ITEM, new CorePacketInNameItem()); // 0x20
		playIn.add(IN_RESOURCE_PACKET_STATUS, new CorePacketInResourcePackStatus()); // 0x21
		playIn.add(IN_ADVANCEMENT_TAB, new CorePacketInAdvancementTab()); // 0x22
		playIn.add(IN_SELECT_TRADE, new CorePacketInSelectTrade()); // 0x23
		playIn.add(IN_SET_BEACON_EFFECT, new CorePacketInSetBeaconEffect()); // 0x24
		playIn.add(IN_HELD_ITEM_CHANGE, new CorePacketInHeldItemChange()); // 0x25
		playIn.add(IN_UPDATE_COMMAND_BLOCK, new CorePacketInUpdateCommandBlock()); // 0x26
		playIn.add(IN_UPDATE_COMMAND_BLOCK_MINECART, new CorePacketInUpdateCommandBlockMinecart()); // 0x27
		playIn.add(IN_CREATIVE_INVENTORY_ACTION, new CorePacketInCreativeInventoryAction()); // 0x28
		playIn.add(IN_UPDATE_JIGSAW_BLOCK, new CorePacketInUpdateJigsawBlock()); // 0x29
		playIn.add(IN_UPDATE_STRUCTURE_BLOCK, new CorePacketInUpdateStructureBlock()); // 0x2A
		playIn.add(IN_UPDATE_SIGN, new CorePacketInUpdateSign()); // 0x2B
		playIn.add(IN_ANIMATION, new CorePacketInAnimation()); // 0x2C
		playIn.add(IN_SPECTATE, new CorePacketInSpectate()); // 0x2D
		playIn.add(IN_PLAYER_BLOCK_PLACEMENT, new CorePacketInPlayerBlockPlacement()); // 0x2E
		playIn.add(IN_USE_ITEM, new CorePacketInUseItem()); // 0x2F
		// -----------------------------------------------------------------------------------
		playOut = new ArrayList<>(PacketPlay.PACKET_COUNT_OUT);
		playOut.add(OUT_SPAWN_ENTITY, new CorePacketOutSpawnEntity()); // 0x00
		playOut.add(OUT_SPAWN_EXPERIENCE_ORB, new CorePacketOutSpawnExperienceOrb()); // 0x01
		playOut.add(OUT_SPAWN_LIVING_ENTITY, new CorePacketOutSpawnLivingEntity()); // 0x02
		playOut.add(OUT_SPAWN_PAINTING, new CorePacketOutSpawnPainting()); // 0x03
		playOut.add(OUT_SPAWN_PLAYER, new CorePacketOutSpawnPlayer()); // 0x04
		playOut.add(OUT_ENTITY_ANIMATION, new CorePacketOutEntityAnimation()); // 0x05
		playOut.add(OUT_STATISTICS, new CorePacketOutStatistics()); // 0x06
		playOut.add(OUT_ACKNOWLEDGE_PLAYER_DIGGIN, new CorePacketOutAcknowledgePlayerDigging()); // 0x07
		playOut.add(OUT_BLOCK_BREAK_ANIMATION, new CorePacketOutBlockBreakAnimation()); // 0x08
		playOut.add(OUT_BLOCK_ENTITY_DATA, new CorePacketOutBlockEntityData()); // 0x09
		playOut.add(OUT_BLOCK_ACTION, new CorePacketOutBlockAction()); // 0x0A
		playOut.add(OUT_BLOCK_CHANGE, new CorePacketOutBlockChange()); // 0x0B
		playOut.add(OUT_BOSS_BAR, new CorePacketOutBossBar()); // 0x0C
		playOut.add(OUT_SERVER_DIFFICULTY, new CorePacketOutServerDifficulty()); // 0x0D
		playOut.add(OUT_CHAT_MESSAGE, new CorePacketOutChatMessage()); // 0x0E
		playOut.add(OUT_TAB_COMPLETE, new CorePacketOutTabComplete()); // 0x0F
		playOut.add(OUT_DECLARE_COMMANDS, new CorePacketOutDeclareCommands()); // 0x10
		playOut.add(OUT_WINDOW_CONFIRMATION, new CorePacketOutWindowConfirmation()); // 0x11
		playOut.add(OUT_CLOSE_WINDOW, new CorePacketOutCloseWindow()); // 0x12
		playOut.add(OUT_WINDOW_ITEMS, new CorePacketOutWindowItems()); // 0x13
		playOut.add(OUT_WINDOW_PROPERTY, new CorePacketOutWindowProperty()); // 0x14
		playOut.add(OUT_SET_SLOT, new CorePacketOutSetSlot()); // 0x15
		playOut.add(OUT_SET_COOLDOWN, new CorePacketOutSetCooldown()); // 0x16
		playOut.add(OUT_PLUGIN_MESSAGE, new CorePacketOutPluginMessage()); // 0x17
		playOut.add(OUT_NAMED_SOUD_EFFECT, new CorePacketOutNamedSoundEffect()); // 0x18
		playOut.add(OUT_DISCONNECT, new CorePacketOutDisconnect()); // 0x19
		playOut.add(OUT_ENTITY_STATUS, new CorePacketOutEntityStatus()); // 0x1A
		playOut.add(OUT_EXPLOSION, new CorePacketOutExplosion()); // 0x1B
		playOut.add(OUT_UNLOAD_CHUNK, new CorePacketOutUnloadChunk()); // 0x1C
		playOut.add(OUT_CHANGE_GAME_STATE, new CorePacketOutChangeGameState()); // 0x1D
		playOut.add(OUT_OPEN_HORSE_WINDOW, new CorePacketOutOpenHorseWindow()); // 0x1E
		playOut.add(OUT_KEEP_ALIVE, new CorePacketOutKeepAlive()); // 0x1F
		playOut.add(OUT_CHUNK_DATA, new CorePacketOutChunkData()); // 0x20
		playOut.add(OUT_EFFECT, new CorePacketOutEffect()); // 0x21
		playOut.add(OUT_PARTICLE, new CorePacketOutParticle()); // 0x22
		playOut.add(OUT_UPDATE_LIGHT, new CorePacketOutUpdateLight()); // 0x23
		playOut.add(OUT_JOIN_GAME, new CorePacketOutJoinGame()); // 0x24
		playOut.add(OUT_MAP_DATA, new CorePacketOutMapData()); // 0x25
		playOut.add(OUT_TRADE_LIST, new CorePacketOutTradeList()); // 0x26
		playOut.add(OUT_ENTITY_POSITION, new CorePacketOutEntityPosition()); // 0x27
		playOut.add(OUT_ENTITY_POSITION_AND_ROTATION, new CorePacketOutEntityPositionAndRotation()); // 0x28
		playOut.add(OUT_ENTITY_ROTATION, new CorePacketOutEntityRotation()); // 0x29
		playOut.add(OUT_ENTITY_MOVEMENT, new CorePacketOutEntityMovement()); // 0x2A
		playOut.add(OUT_VEHICLE_MOVE, new CorePacketOutVehicleMove()); // 0x2B
		playOut.add(OUT_OPEN_BOOK, new CorePacketOutOpenBook()); // 0x2C
		playOut.add(OUT_OPEN_WINDOW, new CorePacketOutOpenWindow()); // 0x2D
		playOut.add(OUT_OPEN_SIGN_EDITOR, new CorePacketOutOpenSignEditor()); // 0x2E
		playOut.add(OUT_CRAFT_RECIPE_RESPONSE, new CorePacketOutCraftRecipeResponse()); // 0x2F
		playOut.add(OUT_PLAYER_ABILITIES, new CorePacketOutPlayerAbilities()); // 0x30
		playOut.add(OUT_COMBAT_EVENT, new CorePacketOutCombatEvent()); // 0x31
		playOut.add(OUT_PLAYER_INFO, new CorePacketOutPlayerInfo()); // 0x32
		playOut.add(OUT_FACE_PLAYER, new CorePacketOutFacePlayer()); // 0x33
		playOut.add(OUT_PLAYER_POSITION_AND_LOOK, new CorePacketOutPlayerPositionAndLook()); // 0x34
		playOut.add(OUT_UNLOCK_RECIPES, new CorePacketOutUnlockRecipes()); // 0x35
		playOut.add(OUT_DESTROY_ENTITIES, new CorePacketOutDestroyEntities()); // 0x36
		playOut.add(OUT_REMOVE_ENTITY_EFFECT, new CorePacketOutRemoveEntityEffect()); // 0x37
		playOut.add(OUT_RESOURCE_PACK_SEND, new CorePacketOutRessourcePackSend()); // 0x38
		playOut.add(OUT_RESPAWN, new CorePacketOutRespawn()); // 0x39
		playOut.add(OUT_ENTITY_HEAD_LOOK, new CorePacketOutEntityHeadLook()); // 0x3A
		playOut.add(OUT_MULTI_BLOCK_CHANGE, new CorePacketOutMultiBlockChange()); // 0x3B
		playOut.add(OUT_SELECT_ADVANCEMENT_TAB, new CorePacketOutSelectAdvancementTag()); // 0x3C
		playOut.add(OUT_WORLD_BORDER, new CorePacketOutWorldBorder()); // 0x3D
		playOut.add(OUT_CAMERA, new CorePacketOutCamera()); // 0x3E
		playOut.add(OUT_HELD_ITEM_CHANGE, new CorePacketOutHeldItemChange()); // 0x3F
		playOut.add(OUT_UPDATE_VIEW_POSITION, new CorePacketOutUpdateViewPosition()); // 0x40
		playOut.add(OUT_UPDATE_VIEW_DISTANCE, new CorePacketOutUpdateViewDistance()); // 0x41
		playOut.add(OUT_SPAWN_POSITION, new CorePacketOutSpawnPosition()); // 0x42
		playOut.add(OUT_DISPLAY_SCOREBOARD, new CorePacketOutDisplayScoreboard()); // 0x43
		playOut.add(OUT_ENTITY_METADATA, new CorePacketOutEntityMetadata()); // 0x44
		playOut.add(OUT_ATTACH_ENTITY, new CorePacketOutAttachEntity()); // 0x45
		playOut.add(OUT_ENTITY_VELOCITY, new CorePacketOutEntityVelocity()); // 0x46
		playOut.add(OUT_ENTITY_EQUIPMENT, new CorePacketOutEntityEquipment()); // 0x47
		playOut.add(OUT_SET_EXPERIENCE, new CorePacketOutSetExperience()); // 0x48
		playOut.add(OUT_UPDATE_HEALTH, new CorePacketOutUpdateHealth()); // 0x49
		playOut.add(OUT_SCOREBOARD_OBJECTIVE, new CorePacketOutScoreboardObjective()); // 0x4A
		playOut.add(OUT_SET_PASSENGER, new CorePacketOutSetPassengers()); // 0x4B
		playOut.add(OUT_TEAMS, new CorePacketOutTeams()); // 0x4C
		playOut.add(OUT_UPDATE_SCORE, new CorePacketOutUpdateScore()); // 0x4D
		playOut.add(OUT_TIME_UPDATE, new CorePacketOutTimeUpdate()); // 0x4E
		playOut.add(OUT_TITLE, new CorePacketOutTitle()); // 0x4F
		playOut.add(OUT_ENTITY_SOUND_EFFECT, new CorePacketOutEntitySoundEffect()); // 0x50
		playOut.add(OUT_SOUND_EFFECT, new CorePacketOutSoundEffect()); // 0x51
		playOut.add(OUT_STOP_SOUND, new CorePacketOutStopSound()); // 0x52
		playOut.add(OUT_PLAYER_LIST_HEADER_AND_FOOTER, new CorePacketOutPlayerListHeaderAndFooter()); // 0x53
		playOut.add(OUT_NBT_QUERY_RESPONSE, new CorePacketOutNBTQueryResponse()); // 0x54
		playOut.add(OUT_COLLECT_ITEM, new CorePacketOutCollectItem()); // 0x55
		playOut.add(OUT_ENTITY_TELEPORT, new CorePacketOutEntityTeleport()); // 0x56
		playOut.add(OUT_ADVANCEMENTS, new CorePacketOutAdvancements()); // 0x57
		playOut.add(OUT_ENTITY_PROPERTIES, new CorePacketOutEntityProperties()); // 0x58
		playOut.add(OUT_ENTITY_EFFECT, new CorePacketOutEntityEffect()); // 0x59
		playOut.add(OUT_DECLARE_RECIPES, new CorePacketOutDeclareRecipes()); // 0x5A
		playOut.add(OUT_TAGS, new CorePacketOutTags()); // 0x5B
	}

	@Override
	public Packet createPacketIn(int id) {
		if (id > 47 || id < 0) 
			return null;
		return playIn.get(id).createPacketData();
	}

	@Override
	public Packet createPacketOut(int id) {
		if (id > 91 || id < 0) 
			return null;
		return playOut.get(id).createPacketData();
	}

	@Override
	public int getVersion() {
		return CoreProtocolAdapter.VERSION;
	}

	@Override
	public PacketListener createDefaultPacketListener(Object o) {
		if (o instanceof PlayerConnection) 
			return null;
		return new CorePacketListenerPlay((PlayerConnection) o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P readPacket(P packet, ByteBuf in, ConnectionHandler con) throws IOException {
		CoreAbstractHandler<P> handler = null;
		if (packet instanceof PacketPlayIn)
			handler = (CoreAbstractHandler<P>) playIn.get(packet.getID());
		else if (packet instanceof PacketPlayOut)
			handler = (CoreAbstractHandler<P>) playOut.get(packet.getID());
		else
			throw new IOException("Unable to read this packet! (Packet is not instance of PacketPlayIn or PacketPlayOut)");
		if (handler == null)
			throw new IOException("Unable to find handler for packet: " + packet.getClass());
		handler.read(packet, in, con);
		return packet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P writePacket(P packet, ByteBuf out, ConnectionHandler con) throws IOException {
		CoreAbstractHandler<P> handler = null;
		if (packet instanceof PacketPlayIn)
			handler = (CoreAbstractHandler<P>) playIn.get(packet.getID());
		else if (packet instanceof PacketPlayOut)
			handler = (CoreAbstractHandler<P>) playOut.get(packet.getID());
		else
			throw new IOException("Unable to write this packet! (Packet is not instance of PacketPlayIn or PacketPlayOut)");
		if (handler == null)
			throw new IOException("Unable to find handler for packet: " + packet.getClass());
		handler.write(packet, out, con);
		return packet;
	}

}
