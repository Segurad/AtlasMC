package de.atlasmc.io.protocol;

import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketInAdvancementTab;
import de.atlasmc.io.protocol.play.PacketInAnimation;
import de.atlasmc.io.protocol.play.PacketInChatMessage;
import de.atlasmc.io.protocol.play.PacketInClickWindow;
import de.atlasmc.io.protocol.play.PacketInClickWindowButton;
import de.atlasmc.io.protocol.play.PacketInClientSettings;
import de.atlasmc.io.protocol.play.PacketInClientStatus;
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

public interface PacketListenerPlayIn {
	
	public void handlePacket(PacketInTeleportConfirm packet);

	public void handlePacket(PacketInQueryBlockNBT packet);

	public void handlePacket(PacketInQueryEntityNBT packet);

	public void handlePacket(PacketInSetDifficulty packet);

	public void handlePacket(PacketInChatMessage packet);

	public void handlePacket(PacketInClientStatus packet);

	public void handlePacket(PacketInClientSettings packet);

	public void handlePacket(PacketInTabComplete packet);

	public void handlePacket(PacketInWindowConfirmation packet);

	public void handlePacket(PacketInClickWindowButton packet);
	
	public void handlePacket(PacketInClickWindow packet);

	public void handlePacket(PacketInCloseWindow packet);

	public void handlePacket(PacketInPluginMessage packet);
	
	public void handlePacket(PacketInEditBook packet);

	public void handlePacket(PacketInInteractEntity packet);

	public void handlePacket(PacketInGenerateStructure packet);

	public void handlePacket(PacketInKeepAlive packet);

	public void handlePacket(PacketInLockDifficulty packet);

	public void handlePacket(PacketInPlayerPosition packet);

	public void handlePacket(PacketInPlayerPositionAndRotation packet);

	public void handlePacket(PacketInPlayerRotation packet);

	public void handlePacket(PacketInPlayerMovement packet);

	public void handlePacket(PacketInVehicleMove packet);

	public void handlePacket(PacketInSteerBoat packet);

	public void handlePacket(PacketInPickItem packet);

	public void handlePacket(PacketInCraftRecipeRequest packet);
	
	public void handlePacket(PacketInPlayerAbilities packet);

	public void handlePacket(PacketInPlayerDigging packet);

	public void handlePacket(PacketInEntityAction packet);

	public void handlePacket(PacketInSteerVehicle packet);
	
	public void handlePacket(PacketInSetRecipeBookState packet);

	public void handlePacket(PacketInSetDisplayedRecipe packet);

	public void handlePacket(PacketInNameItem packet);

	public void handlePacket(PacketInResourcePackStatus packet);

	public void handlePacket(PacketInAdvancementTab packet);

	public void handlePacket(PacketInSelectTrade packet);

	public void handlePacket(PacketInSetBeaconEffect packet);

	public void handlePacket(PacketInHeldItemChange packet);

	public void handlePacket(PacketInUpdateCommandBlock packet);

	public void handlePacket(PacketInUpdateCommandBlockMinecart packet);

	public void handlePacket(PacketInCreativeInventoryAction packet);

	public void handlePacket(PacketInUpdateJigsawBlock packet);

	public void handlePacket(PacketInUpdateStructureBlock packet);

	public void handlePacket(PacketInUpdateSign packet);

	public void handlePacket(PacketInAnimation packet);

	public void handlePacket(PacketInSpectate packet);

	public void handlePacket(PacketInPlayerBlockPlacement packet);

	public void handlePacket(PacketInUseItem packet);

	public void handleUnhandledPacket(Packet packet);

}
