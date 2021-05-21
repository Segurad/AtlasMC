package de.atlascore.io.protocol;

import de.atlasmc.entity.Player;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.protocol.play.*;

public class CorePacketListenerPlay implements PacketListener {

	private static final PacketHandler[] handlers;
	private final Player player;
	
	static {
		handlers = new PacketHandler[] {
			(player, packet) -> { // 0x00
				if (packet instanceof PacketInTeleportConfirm) {
					player.getConnection().handlePacket((PacketInTeleportConfirm) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x01
				if (packet instanceof PacketInQueryBlockNBT) {
					player.getConnection().handlePacket((PacketInQueryBlockNBT) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x02
				if (packet instanceof PacketInSetDifficulty) {
					player.getConnection().handlePacket((PacketInSetDifficulty) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x03
				if (packet instanceof PacketInChatMessage) {
					player.getConnection().handlePacket((PacketInChatMessage) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x04
				if (packet instanceof PacketInClientStatus) {
					player.getConnection().handlePacket((PacketInClientStatus) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x05
				if (packet instanceof PacketInClientSettings) {
					player.getConnection().handlePacket((PacketInClientSettings) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x06
				if (packet instanceof PacketInTabComplete) {
					player.getConnection().handlePacket((PacketInTabComplete) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x07
				if (packet instanceof PacketInWindowConfirmation) {
					player.getConnection().handlePacket((PacketInWindowConfirmation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x08
				if (packet instanceof PacketInClickWindowButton) {
					player.getConnection().handlePacket((PacketInClickWindowButton) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x09
				if (packet instanceof PacketInClickWindow) {
					player.getConnection().handlePacket((PacketInClickWindow) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0A
				if (packet instanceof PacketInCloseWindow) {
					player.getConnection().handlePacket((PacketInCloseWindow) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0B
				if (packet instanceof PacketInPluginMessage) {
					player.getConnection().handlePacket((PacketInPluginMessage) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0C
				if (packet instanceof PacketInEditBook) {
					player.getConnection().handlePacket((PacketInEditBook) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0E
				if (packet instanceof PacketInInteractEntity) {
					player.getConnection().handlePacket((PacketInInteractEntity) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0D
				if (packet instanceof PacketInQueryEntityNBT) {
					player.getConnection().handlePacket((PacketInQueryEntityNBT) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x0F
				if (packet instanceof PacketInGenerateStructure) {
					player.getConnection().handlePacket((PacketInGenerateStructure) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x10
				if (packet instanceof PacketInKeepAlive) {
					player.getConnection().handlePacket((PacketInKeepAlive) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x11
				if (packet instanceof PacketInLockDifficulty) {
					player.getConnection().handlePacket((PacketInLockDifficulty) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x12
				if (packet instanceof PacketInPlayerPosition) {
					player.getConnection().handlePacket((PacketInPlayerPosition) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x13
				if (packet instanceof PacketInPlayerPositionAndRotation) {
					player.getConnection().handlePacket((PacketInPlayerPositionAndRotation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x14
				if (packet instanceof PacketInPlayerRotation) {
					player.getConnection().handlePacket((PacketInPlayerRotation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x15
				if (packet instanceof PacketInPlayerMovement) {
					player.getConnection().handlePacket((PacketInPlayerMovement) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x16
				if (packet instanceof PacketInVehicleMove) {
					player.getConnection().handlePacket((PacketInVehicleMove) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x17
				if (packet instanceof PacketInSteerBoat) {
					player.getConnection().handlePacket((PacketInSteerBoat) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x18
				if (packet instanceof PacketInPickItem) {
					player.getConnection().handlePacket((PacketInPickItem) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x19
				if (packet instanceof PacketInCraftRecipeRequest) {
					player.getConnection().handlePacket((PacketInCraftRecipeRequest) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1A
				if (packet instanceof PacketInPlayerAbilities) {
					player.getConnection().handlePacket((PacketInPlayerAbilities) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1B
				if (packet instanceof PacketInPlayerDigging) {
					player.getConnection().handlePacket((PacketInPlayerDigging) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1C
				if (packet instanceof PacketInEntityAction) {
					player.getConnection().handlePacket((PacketInEntityAction) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1D
				if (packet instanceof PacketInSteerVehicle) {
					player.getConnection().handlePacket((PacketInSteerVehicle) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1E
				if (packet instanceof PacketInSetRecipeBookState) {
					player.getConnection().handlePacket((PacketInSetRecipeBookState) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x1F
				if (packet instanceof PacketInSetDisplayedRecipe) {
					player.getConnection().handlePacket((PacketInSetDisplayedRecipe) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x20
				if (packet instanceof PacketInNameItem) {
					player.getConnection().handlePacket((PacketInNameItem) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x21
				if (packet instanceof PacketInResourcePackStatus) {
					player.getConnection().handlePacket((PacketInResourcePackStatus) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x22
				if (packet instanceof PacketInAdvancementTab) {
					player.getConnection().handlePacket((PacketInAdvancementTab) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x23
				if (packet instanceof PacketInSelectTrade) {
					player.getConnection().handlePacket((PacketInSelectTrade) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x24
				if (packet instanceof PacketInSetBeaconEffect) {
					player.getConnection().handlePacket((PacketInSetBeaconEffect) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x25
				if (packet instanceof PacketInHeldItemChange) {
					player.getConnection().handlePacket((PacketInHeldItemChange) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x26
				if (packet instanceof PacketInUpdateCommandBlock) {
					player.getConnection().handlePacket((PacketInUpdateCommandBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x27
				if (packet instanceof PacketInUpdateCommandBlockMinecart) {
					player.getConnection().handlePacket((PacketInUpdateCommandBlockMinecart) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x28
				if (packet instanceof PacketInCreativeInventoryAction) {
					player.getConnection().handlePacket((PacketInCreativeInventoryAction) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x29
				if (packet instanceof PacketInUpdateJigsawBlock) {
					player.getConnection().handlePacket((PacketInUpdateJigsawBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2A
				if (packet instanceof PacketInUpdateStructureBlock) {
					player.getConnection().handlePacket((PacketInUpdateStructureBlock) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2B
				if (packet instanceof PacketInUpdateSign) {
					player.getConnection().handlePacket((PacketInUpdateSign) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2C
				if (packet instanceof PacketInAnimation) {
					player.getConnection().handlePacket((PacketInAnimation) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2D
				if (packet instanceof PacketInSpectate) {
					player.getConnection().handlePacket((PacketInSpectate) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2E
				if (packet instanceof PacketInPlayerBlockPlacement) {
					player.getConnection().handlePacket((PacketInPlayerBlockPlacement) packet);
				} else unhandledPacket(player, packet);
			},
			(player, packet) -> { // 0x2F
				if (packet instanceof PacketInUseItem) {
					player.getConnection().handlePacket((PacketInUseItem) packet);
				} else unhandledPacket(player, packet);
			},
		};
	}
	
	public CorePacketListenerPlay(Player player) {
		this.player = player;
	}
	
	public static void handlePacket(Player player, Packet packet) {
		int id = packet.getID();
		if (id >= 0 && id < 48) {
			handlers[id].handle(player, packet);
		} else unhandledPacket(player, packet);
	}

	@Override
	public void handlePacket(Packet packet) {
		player.getConnection().queueInboundPacket(packet);
	}

	@Override
	public void handleUnregister() {}
	
	protected static void unhandledPacket(Player player, Packet packet) {
		player.getConnection().handleUnhandledPacket(packet);
	}
	
	public interface PacketHandler {
		public void handle(Player player, Packet packet);
	}

}
