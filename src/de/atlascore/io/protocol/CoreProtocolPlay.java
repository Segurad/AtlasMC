package de.atlascore.io.protocol;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.play.*;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.ProtocolPlay;

public class CoreProtocolPlay implements ProtocolPlay {
	
	private final List<Class<? extends Packet>> playIn, playOut;
	
	public CoreProtocolPlay() {
		List<Class<? extends Packet>> playIn = new ArrayList<>(48);
		playIn.add(CorePacketInTeleportConfirm.class); // 0x00
		playIn.add(CorePacketInQueryBlockNBT.class); // 0x01
		playIn.add(CorePacketInSetDifficulty.class); // 0x02
		playIn.add(CorePacketInChatMessage.class); // 0x03
		playIn.add(CorePacketInClientStatus.class); // 0x04
		playIn.add(CorePacketInClientSettings.class); // 0x05
		playIn.add(CorePacketInTabComplete.class); // 0x06
		playIn.add(CorePacketInWindowConfirmation.class); // 0x07
		playIn.add(CorePacketInClickWindowButton.class); // 0x08
		playIn.add(CorePacketInClickWindow.class); // 0x09
		playIn.add(CorePacketInCloseWindow.class); // 0x0A
		playIn.add(CorePacketInPluginMessage.class); // 0x0B
		playIn.add(CorePacketInEditBook.class); // 0x0C
		playIn.add(CorePacketInQueryEntityNBT.class); // 0x0D
		playIn.add(CorePacketInInteractEntity.class); // 0x0E
		playIn.add(CorePacketInGenerateStructure.class); // 0x0F
		playIn.add(CorePacketInKeepAlive.class); // 0x10
		playIn.add(CorePacketInLockDifficulty.class); // 0x11
		playIn.add(CorePacketInPlayerPosition.class); // 0x12
		playIn.add(CorePacketInPlayerPositionAndRotation.class); // 0x13
		playIn.add(CorePacketInPlayerRotation.class); // 0x14
		playIn.add(CorePacketInPlayerMovement.class); // 0x15
		playIn.add(CorePacketInVehicleMove.class); // 0x16
		playIn.add(CorePacketInSteerBoat.class); // 0x17
		playIn.add(CorePacketInPickItem.class); // 0x18
		playIn.add(CorePacketInCraftRecipeRequest.class); // 0x19
		playIn.add(CorePacketInPlayerAbilities.class); // 0x1A
		playIn.add(CorePacketInPlayerDigging.class); // 0x1B
		playIn.add(CorePacketInEntityAction.class); // 0x1C
		playIn.add(CorePacketInSteerVehicle.class); // 0x1D
		playIn.add(CorePacketInSetRecipeBookState.class); // 0x1E
		playIn.add(CorePacketInSetDisplayedRecipe.class); // 0x1F
		playIn.add(CorePacketInNameItem.class); // 0x20
		playIn.add(CorePacketInResourcePackStatus.class); // 0x21
		playIn.add(CorePacketInAdvancementTab.class); // 0x22
		playIn.add(CorePacketInSelectTrade.class); // 0x23
		playIn.add(CorePacketInSetBeaconEffect.class); // 0x24
		playIn.add(CorePacketInHeldItemChange.class); // 0x25
		playIn.add(CorePacketInUpdateCommandBlock.class); // 0x26
		playIn.add(CorePacketInUpdateCommandBlockMinecart.class); // 0x27
		playIn.add(CorePacketInCreativeInventoryAction.class); // 0x28
		playIn.add(CorePacketInUpdateJigsawBlock.class); // 0x29
		playIn.add(CorePacketInUpdateStructureBlock.class); // 0x2A
		playIn.add(CorePacketInUpdateSign.class); // 0x2B
		playIn.add(CorePacketInAnimation.class); // 0x2C
		playIn.add(CorePacketInSpectate.class); // 0x2D
		playIn.add(CorePacketInPlayerBlockPlacement.class); // 0x2E
		playIn.add(CorePacketInUseItem.class); // 0x2F
		List<Class<? extends Packet>> playOut = new ArrayList<>(92);
		playOut.add(CorePacketOutSpawnEntity.class); // 0x00
		playOut.add(CorePacketOutSpawnExperienceOrb.class); // 0x01
		playOut.add(CorePacketOutSpawnLivingEntity.class); // 0x02
		playOut.add(CorePacketOutSpawnPainting.class); // 0x03
		playOut.add(CorePacketOutSpawnPlayer.class); // 0x04
		playOut.add(CorePacketOutEntityAnimation.class); // 0x05
		playOut.add(CorePacketOutStatistics.class); // 0x06
		playOut.add(CorePacketOutAcknowledgePlayerDigging.class); // 0x07
		playOut.add(CorePacketOutBlockBreakAnimation.class); // 0x08
		playOut.add(CorePacketOutBlockEntityData.class); // 0x09
		playOut.add(CorePacketOutBlockAction.class); // 0x0A
		playOut.add(CorePacketOutBlockChange.class); // 0x0B
		playOut.add(CorePacketOutBossBar.class); // 0x0C
		playOut.add(CorePacketOutServerDifficulty.class); // 0x0D
		playOut.add(CorePacketOutChatMessage.class); // 0x0E
		playOut.add(CorePacketOutTabComplete.class); // 0x0F
		playOut.add(CorePacketOutDeclareCommands.class); // 0x10
		playOut.add(CorePacketOutWindowConfirmation.class); // 0x11
		playOut.add(CorePacketOutCloseWindow.class); // 0x12
		playOut.add(CorePacketOutWindowItems.class); // 0x13
		playOut.add(CorePacketOutWindowProperty.class); // 0x14
		playOut.add(CorePacketOutSetSlot.class); // 0x15
		playOut.add(CorePacketOutSetCooldown.class); // 0x16
		playOut.add(CorePacketOutPluginMessage.class); // 0x17
		playOut.add(CorePacketOutNamedSoundEffect.class); // 0x18
		playOut.add(CorePacketOutDisconnect.class); // 0x19
		playOut.add(CorePacketOutEntityStatus.class); // 0x1A
		playOut.add(CorePacketOutExplosion.class); // 0x1B
		playOut.add(CorePacketOutUnloadChunk.class); // 0x1C
		playOut.add(CorePacketOutChangeGameState.class); // 0x1D
		playOut.add(CorePacketOutOpenHorseWindow.class); // 0x1E
		playOut.add(CorePacketOutKeepAlive.class); // 0x1F
		playOut.add(CorePacketOutChunkData.class); // 0x20
		playOut.add(CorePacketOutEffect.class); // 0x21
		playOut.add(CorePacketOutParticle.class); // 0x22
		playOut.add(CorePacketOutUpdateLight.class); // 0x23
		playOut.add(CorePacketOutJoinGame.class); // 0x24
		playOut.add(CorePacketOutMapData.class); // 0x25
		playOut.add(CorePacketOutTradeList.class); // 0x26
		playOut.add(CorePacketOutEntityPosition.class); // 0x27
		playOut.add(CorePacketOutEntityPositionAndRotation.class); // 0x28
		playOut.add(CorePacketOutEntityRotation.class); // 0x29
		playOut.add(CorePacketOutEntityMovement.class); // 0x2A
		playOut.add(CorePacketOutVehicleMove.class); // 0x2B
		playOut.add(CorePacketOutOpenBook.class); // 0x2C
		playOut.add(CorePacketOutOpenWindow.class); // 0x2D
		playOut.add(CorePacketOutOpenSignEditor.class); // 0x2E
		playOut.add(CorePacketOutCraftRecipeResponse.class); // 0x2F
		playOut.add(CorePacketOutPlayerAbilities.class); // 0x30
		playOut.add(CorePacketOutCombatEvent.class); // 0x31
		playOut.add(CorePacketOutPlayerInfo.class); // 0x32
		playOut.add(CorePacketOutFacePlayer.class); // 0x33
		playOut.add(CorePacketOutPlayerPositionAndLook.class); // 0x34
		playOut.add(CorePacketOutUnlockRecipes.class); // 0x35
		playOut.add(CorePacketOutDestroyEntities.class); // 0x36
		playOut.add(CorePacketOutRemoveEntityEffect.class); // 0x37
		playOut.add(CorePacketOutRessourcePackSend.class); // 0x38
		playOut.add(CorePacketOutRespawn.class); // 0x39
		playOut.add(CorePacketOutEntityHeadLook.class); // 0x3A
		playOut.add(CorePacketOutMultiBlockChange.class); // 0x3B
		playOut.add(CorePacketOutSelectAdvancementTag.class); // 0x3C
		playOut.add(CorePacketOutWorldBorder.class); // 0x3D
		playOut.add(CorePacketOutCamera.class); // 0x3E
		playOut.add(CorePacketOutHeldItemChange.class); // 0x3F
		playOut.add(CorePacketOutUpdateViewPosition.class); // 0x40
		playOut.add(CorePacketOutUpdateViewDistance.class); // 0x41
		playOut.add(CorePacketOutSpawnPosition.class); // 0x42
		playOut.add(CorePacketOutDisplayScoreboard.class); // 0x43
		playOut.add(CorePacketOutEntityMetadata.class); // 0x44
		playOut.add(CorePacketOutAttachEntity.class); // 0x45
		playOut.add(CorePacketOutEntityVelocity.class); // 0x46
		playOut.add(CorePacketOutEntityEquipment.class); // 0x47
		playOut.add(CorePacketOutSetExperience.class); // 0x48
		playOut.add(CorePacketOutUpdateHealth.class); // 0x49
		playOut.add(CorePacketOutScoreboardObjective.class); // 0x4A
		playOut.add(CorePacketOutSetPassengers.class); // 0x4B
		playOut.add(CorePacketOutTeams.class); // 0x4C
		playOut.add(CorePacketOutUpdateScore.class); // 0x4D
		playOut.add(CorePacketOutTimeUpdate.class); // 0x4E
		playOut.add(CorePacketOutTitle.class); // 0x4F
		playOut.add(CorePacketOutEntitySoundEffect.class); // 0x50
		playOut.add(CorePacketOutSoundEffect.class); // 0x51
		playOut.add(CorePacketOutStopSound.class); // 0x52
		playOut.add(CorePacketOutPlayerListHeaderAndFooter.class); // 0x53
		playOut.add(CorePacketOutNBTQueryResponse.class); // 0x54
		playOut.add(CorePacketOutCollectItem.class); // 0x55
		playOut.add(CorePacketOutEntityTeleport.class); // 0x56
		playOut.add(CorePacketOutAdvancements.class); // 0x57
		playOut.add(CorePacketOutEntityProperties.class); // 0x58
		playOut.add(CorePacketOutEntityEffect.class); // 0x59
		playOut.add(CorePacketOutDeclareRecipes.class); // 0x5A
		playOut.add(CorePacketOutTags.class); // 0x5B
		this.playIn = List.copyOf(playIn);
		this.playOut = List.copyOf(playOut);
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
	public PacketListener createPacketListener(Object o) {
		if (Player.class.isInstance(o)) return null;
		return new CorePacketListenerPlay((Player) o);
	}

	@Override
	public Packet createCopy(Packet packet) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public CorePacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item) {
		return new CorePacketOutSetSlot(windowID, slot, item);
	}

}
