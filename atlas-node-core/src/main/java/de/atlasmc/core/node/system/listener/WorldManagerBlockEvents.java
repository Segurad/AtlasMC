package de.atlasmc.core.node.system.listener;

import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.node.event.block.BlockBreakEvent;
import de.atlasmc.node.event.block.BlockPhysicsEvent;
import de.atlasmc.node.event.block.BlockPlaceEvent;
import de.atlasmc.node.world.World;
import de.atlasmc.node.world.WorldFlag;

final class BlockEventHandler implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onPhysic(BlockPhysicsEvent e) {
		World world = e.getBlock().getWorld();
		if (world == null)
			return;
		if (world.hasFlag(WorldFlag.DISABLE_BLOCKPHYSICS))
			e.setCancelled(true);
	}
	/*
	@EventHandler(ignoreCancelled = true)
	public void onFade(BlockFadeEvent e) {
		World data = e.getBlock().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_BLOCKFADE))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onLeaveDecay(LeavesDecayEvent e) {
		World data = e.getBlock().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_LEAVEDECAY))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onFluid(BlockFromToEvent e) {
		World data = e.getBlock().getWorld();
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_LIQUIDFLOW))
			e.setCancelled(true);
	}*/

	@EventHandler(ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		World data = e.getBlock().getWorld();
		if (data == null)
			return;
		if (!data.hasFlag(WorldFlag.DISABLE_BLOCKBREAK))
			return;
		if (!e.getPlayer().hasPermission("unioncore.world.blockbreak.bypass"))
			e.setCancelled(true);

	}

	@EventHandler(ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		World world = e.getBlock().getWorld();
		if (world == null)
			return;
		if (!world.hasFlag(WorldFlag.DISABLE_BLOCKPLACE))
			return;
		if (!e.getPlayer().hasPermission("unioncore.world.blockplace.bypass"))
			e.setCancelled(true);
	}
}
