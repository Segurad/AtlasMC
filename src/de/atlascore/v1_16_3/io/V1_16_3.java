package de.atlascore.v1_16_3.io;

import java.net.Socket;

import de.atlascore.v1_16_3.io.protocol.play.PacketInAdvancementTabV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInAnimationV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInChatMessageV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInClickWindowButtonV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInClientSettingsV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInClientStatusV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInCloseWindowV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInCraftRecipeRequestV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInCreativeInventoryActionV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInEntityActionV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInGenerateStructureV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInHeldItemChangeV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInInteractEntityV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInKeepAliveV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInLockDifficultyV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInNameItemV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPickItemV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerAbilitiesV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerBlockPlacementV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerDiggingV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerMovementV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerPositionAndRotationV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerPositionV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPlayerRotationV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInPluginMessageV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInQueryBlockNBTV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInQueryEntityNBTV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInResourcePackStatusV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSelectTradeV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSetBeaconEffectV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSetDifficultyV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSetDisplayedRecipeV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSetRecipeBookStateV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSpectateV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSteerBoatV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInSteerVehicleV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInTabCompleteV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInTeleportConfirmV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUpdateCommandBlockMinecartV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUpdateCommandBlockV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUpdateJigsawBlockV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUpdateSignV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUpdateStructureBlockV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInUseItemV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInVehicleMoveV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketInWindowConfirmationV1_16_3;
import de.atlascore.v1_16_3.io.protocol.play.PacketOutSetSlotV1_16_3;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;

public class V1_16_3 implements de.atlasmc.io.ProtocolAdapter {

	public static final int version = 753;
	
	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public String getVersionString() {
		return "v1.16.3";
	}

	@Override
	public ConnectionHandler createConnectionHandler(Socket socket) {
		return new ConnectionHandlerV1_16_3(socket, this);
	}

	@Override
	public Packet createPlayInPacket(int id) {
		switch (id) {
		case 0x00: return new PacketInTeleportConfirmV1_16_3();
		case 0x01: return new PacketInQueryBlockNBTV1_16_3();
		case 0x0D: return new PacketInQueryEntityNBTV1_16_3();
		case 0x02: return new PacketInSetDifficultyV1_16_3();
		case 0x03: return new PacketInChatMessageV1_16_3();
		case 0x04: return new PacketInClientStatusV1_16_3();
		case 0x05: return new PacketInClientSettingsV1_16_3();
		case 0x06: return new PacketInTabCompleteV1_16_3();
		case 0x07: return new PacketInWindowConfirmationV1_16_3();
		case 0x08: return new PacketInClickWindowButtonV1_16_3();
		case 0x0A: return new PacketInCloseWindowV1_16_3();
		case 0x0B: return new PacketInPluginMessageV1_16_3();
		case 0x0E: return new PacketInInteractEntityV1_16_3();
		case 0x0F: return new PacketInGenerateStructureV1_16_3();
		case 0x10: return new PacketInKeepAliveV1_16_3();
		case 0x11: return new PacketInLockDifficultyV1_16_3();
		case 0x12: return new PacketInPlayerPositionV1_16_3();
		case 0x13: return new PacketInPlayerPositionAndRotationV1_16_3();
		case 0x14: return new PacketInPlayerRotationV1_16_3();
		case 0x15: return new PacketInPlayerMovementV1_16_3();
		case 0x16: return new PacketInVehicleMoveV1_16_3();
		case 0x17: return new PacketInSteerBoatV1_16_3();
		case 0x18: return new PacketInPickItemV1_16_3();
		case 0x19: return new PacketInCraftRecipeRequestV1_16_3();
		case 0x1A: return new PacketInPlayerAbilitiesV1_16_3();
		case 0x1B: return new PacketInPlayerDiggingV1_16_3();
		case 0x1C: return new PacketInEntityActionV1_16_3();
		case 0x1D: return new PacketInSteerVehicleV1_16_3();
		case 0x1E: return new PacketInSetDisplayedRecipeV1_16_3();
		case 0x1F: return new PacketInSetRecipeBookStateV1_16_3();
		case 0x20: return new PacketInNameItemV1_16_3();
		case 0x21: return new PacketInResourcePackStatusV1_16_3();
		case 0x22: return new PacketInAdvancementTabV1_16_3();
		case 0x23: return new PacketInSelectTradeV1_16_3();
		case 0x24: return new PacketInSetBeaconEffectV1_16_3();
		case 0x25: return new PacketInHeldItemChangeV1_16_3();
		case 0x26: return new PacketInUpdateCommandBlockV1_16_3();
		case 0x27: return new PacketInUpdateCommandBlockMinecartV1_16_3();
		case 0x29: return new PacketInCreativeInventoryActionV1_16_3();
		case 0x28: return new PacketInUpdateJigsawBlockV1_16_3();
		case 0x2A: return new PacketInUpdateStructureBlockV1_16_3();
		case 0x2B: return new PacketInUpdateSignV1_16_3();
		case 0x2C: return new PacketInAnimationV1_16_3();
		case 0x2D: return new PacketInSpectateV1_16_3();
		case 0x2E: return new PacketInPlayerBlockPlacementV1_16_3();
		case 0x2F: return new PacketInUseItemV1_16_3();
		default:
			break;
		}
		return null;
	}

	@Override
	public Packet createPlayOutPacket(int id) {
		return null;
	}

	@Override
	public PacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item) {
		return new PacketOutSetSlotV1_16_3(windowID, (short) slot, item);
	}

	@Override
	public int getConnectionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setConnectionCount(int count) {
		// TODO Auto-generated method stub
		
	}

}
