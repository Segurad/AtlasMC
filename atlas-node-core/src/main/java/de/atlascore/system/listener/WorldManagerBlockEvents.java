package de.atlascore.system.listener;

import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;
import de.atlasmc.event.block.BlockBreakEvent;
import de.atlasmc.event.block.BlockPhysicsEvent;
import de.atlasmc.event.block.BlockPlaceEvent;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldFlag;

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
