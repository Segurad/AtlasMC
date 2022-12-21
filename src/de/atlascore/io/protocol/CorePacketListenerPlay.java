package de.atlascore.io.protocol;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.*;

public class CorePacketListenerPlay implements PacketListener {

	private static final PacketHandler[] handlers;
	
	private final PlayerConnection player;
	
	static {
		handlers = new PacketHandler[] {
			(player, packet) -> { // 0x00
				if (packet instanceof PacketInTeleportConfirm) {
					player.handlePacket((PacketInTeleportConfirm) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x01
				if (packet instanceof PacketInQueryBlockNBT) {
					player.handlePacket((PacketInQueryBlockNBT) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x02
				if (packet instanceof PacketInSetDifficulty) {
					player.handlePacket((PacketInSetDifficulty) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x03
				if (packet instanceof PacketInChatMessage) {
					player.handlePacket((PacketInChatMessage) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x04
				if (packet instanceof PacketInClientStatus) {
					player.handlePacket((PacketInClientStatus) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x05
				if (packet instanceof PacketInClientSettings) {
					player.handlePacket((PacketInClientSettings) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x06
				if (packet instanceof PacketInTabComplete) {
					player.handlePacket((PacketInTabComplete) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x07
				if (packet instanceof PacketInWindowConfirmation) {
					player.handlePacket((PacketInWindowConfirmation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x08
				if (packet instanceof PacketInClickWindowButton) {
					player.handlePacket((PacketInClickWindowButton) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x09
				if (packet instanceof PacketInClickWindow) {
					player.handlePacket((PacketInClickWindow) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0A
				if (packet instanceof PacketInCloseWindow) {
					player.handlePacket((PacketInCloseWindow) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0B
				if (packet instanceof PacketInPluginMessage) {
					player.handlePacket((PacketInPluginMessage) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0C
				if (packet instanceof PacketInEditBook) {
					player.handlePacket((PacketInEditBook) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0E
				if (packet instanceof PacketInInteractEntity) {
					player.handlePacket((PacketInInteractEntity) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0D
				if (packet instanceof PacketInQueryEntityNBT) {
					player.handlePacket((PacketInQueryEntityNBT) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0F
				if (packet instanceof PacketInGenerateStructure) {
					player.handlePacket((PacketInGenerateStructure) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x10
				if (packet instanceof PacketInKeepAlive) {
					player.handlePacket((PacketInKeepAlive) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x11
				if (packet instanceof PacketInLockDifficulty) {
					player.handlePacket((PacketInLockDifficulty) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x12
				if (packet instanceof PacketInPlayerPosition) {
					player.handlePacket((PacketInPlayerPosition) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x13
				if (packet instanceof PacketInPlayerPositionAndRotation) {
					player.handlePacket((PacketInPlayerPositionAndRotation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x14
				if (packet instanceof PacketInPlayerRotation) {
					player.handlePacket((PacketInPlayerRotation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x15
				if (packet instanceof PacketInPlayerMovement) {
					player.handlePacket((PacketInPlayerMovement) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x16
				if (packet instanceof PacketInVehicleMove) {
					player.handlePacket((PacketInVehicleMove) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x17
				if (packet instanceof PacketInSteerBoat) {
					player.handlePacket((PacketInSteerBoat) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x18
				if (packet instanceof PacketInPickItem) {
					player.handlePacket((PacketInPickItem) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x19
				if (packet instanceof PacketInCraftRecipeRequest) {
					player.handlePacket((PacketInCraftRecipeRequest) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1A
				if (packet instanceof PacketInPlayerAbilities) {
					player.handlePacket((PacketInPlayerAbilities) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1B
				if (packet instanceof PacketInPlayerDigging) {
					player.handlePacket((PacketInPlayerDigging) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1C
				if (packet instanceof PacketInEntityAction) {
					player.handlePacket((PacketInEntityAction) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1D
				if (packet instanceof PacketInSteerVehicle) {
					player.handlePacket((PacketInSteerVehicle) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1E
				if (packet instanceof PacketInSetRecipeBookState) {
					player.handlePacket((PacketInSetRecipeBookState) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1F
				if (packet instanceof PacketInSetDisplayedRecipe) {
					player.handlePacket((PacketInSetDisplayedRecipe) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x20
				if (packet instanceof PacketInNameItem) {
					player.handlePacket((PacketInNameItem) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x21
				if (packet instanceof PacketInResourcePackStatus) {
					player.handlePacket((PacketInResourcePackStatus) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x22
				if (packet instanceof PacketInAdvancementTab) {
					player.handlePacket((PacketInAdvancementTab) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x23
				if (packet instanceof PacketInSelectTrade) {
					player.handlePacket((PacketInSelectTrade) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x24
				if (packet instanceof PacketInSetBeaconEffect) {
					player.handlePacket((PacketInSetBeaconEffect) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x25
				if (packet instanceof PacketInHeldItemChange) {
					player.handlePacket((PacketInHeldItemChange) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x26
				if (packet instanceof PacketInUpdateCommandBlock) {
					player.handlePacket((PacketInUpdateCommandBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x27
				if (packet instanceof PacketInUpdateCommandBlockMinecart) {
					player.handlePacket((PacketInUpdateCommandBlockMinecart) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x28
				if (packet instanceof PacketInCreativeInventoryAction) {
					player.handlePacket((PacketInCreativeInventoryAction) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x29
				if (packet instanceof PacketInUpdateJigsawBlock) {
					player.handlePacket((PacketInUpdateJigsawBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2A
				if (packet instanceof PacketInUpdateStructureBlock) {
					player.handlePacket((PacketInUpdateStructureBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2B
				if (packet instanceof PacketInUpdateSign) {
					player.handlePacket((PacketInUpdateSign) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2C
				if (packet instanceof PacketInAnimation) {
					player.handlePacket((PacketInAnimation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2D
				if (packet instanceof PacketInSpectate) {
					player.handlePacket((PacketInSpectate) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2E
				if (packet instanceof PacketInPlayerBlockPlacement) {
					player.handlePacket((PacketInPlayerBlockPlacement) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2F
				if (packet instanceof PacketInUseItem) {
					player.handlePacket((PacketInUseItem) packet);
				} else unhandledPacket(player, packet);
			},
		};
	}
	
	public CorePacketListenerPlay(PlayerConnection player) {
		this.player = player;
	}
	
	public static void handlePacket(PlayerConnection player, Packet packet) {
		int id = packet.getID();
		if (id >= 0 && id < 48) {
			handlers[id].handle(player, packet);
		} else unhandledPacket(player, packet);
	}

	@Override
	public void handlePacket(Packet packet) {
		player.queueInboundPacket(packet);
	}

	@Override
	public void handleUnregister() {}
	
	protected static void unhandledPacket(PlayerConnection player, Packet packet) {
		player.handleUnhandledPacket(packet);
	}
	
	public interface PacketHandler {
		public void handle(PlayerConnection player, Packet packet);
	}

	@Override
	public void handleSyncPackets() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
