package de.atlasmc.world;

import de.atlasmc.event.EventHandler;
import de.atlasmc.event.Listener;

final class WorldManagerBlockEvents implements Listener {

	private WorldManager wmanager;

	public WorldManagerBlockEvents(WorldManager worldManager) {
		wmanager = worldManager;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPhysic(BlockPhysicsEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_BLOCKPHYSICS))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onFade(BlockFadeEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_BLOCKFADE))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onLeaveDecay(LeavesDecayEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_LEAVEDECAY))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onFluid(BlockFromToEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (data.hasFlag(WorldFlag.DISABLE_LIQUIDFLOW))
			e.setCancelled(true);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (!data.hasFlag(WorldFlag.DISABLE_BLOCKBREAK))
			return;
		if (!e.getPlayer().hasPermission("unioncore.world.blockbreak.bypass"))
			e.setCancelled(true);

	}

	@EventHandler(ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		WorldData data = wmanager.getData(e.getBlock().getWorld());
		if (data == null)
			return;
		if (!data.hasFlag(WorldFlag.DISABLE_BLOCKPLACE))
			return;
		if (!e.getPlayer().hasPermission("unioncore.world.blockplace.bypass"))
			e.setCancelled(true);
	}
}
