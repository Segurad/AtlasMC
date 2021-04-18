package de.atlascore.io.protocol;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.play.*;
import de.atlasmc.entity.Player;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;

public class CorePlayProtocol implements Protocol {
	
	private final List<Class<? extends Packet>> playIn, playOut;
	
	public CorePlayProtocol() {
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

}
