package de.atlascore.v1_16_3.io;

import java.net.Socket;

import de.atlascore.v1_16_3.io.pack.PacketInChatMessageV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInClickWindowButtonV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInClientSettingsV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInClientStatusV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInCloseWindowV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInCraftRecipeRequestV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInGenerateStructureV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInInteractEntityV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInKeepAliveV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInLockDifficultyV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPickItemV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerAbilitiesV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerDiggingV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerMovementV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerPositionAndRotationV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerPositionV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPlayerRotationV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInPluginMessageV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInQueryBlockNBTV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInQueryEntityNBTV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInSetDifficultyV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInSteerBoatV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInTabCompleteV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInTeleportConfirmV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInVehicleMoveV1_16_3;
import de.atlascore.v1_16_3.io.pack.PacketInWindowConfirmationV1_16_3;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;

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
		return new ConnectionHandler(socket, this);
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
		default:
			break;
		}
		return null;
	}

	@Override
	public Packet createPlayOutPacket(int id) {
		return null;
	}

}
