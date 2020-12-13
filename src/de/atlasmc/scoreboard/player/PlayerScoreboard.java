package de.atlasmc.scoreboard.player;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.scoreboard.Scoreboard;

public class PlayerScoreboard {

	private final Player player;
	private final ScoreboardManager manager;
	private final Scoreboard board;
	private final List<SideComponent> side;
	private SideComponent aside;
	private final List<TabComponent> tab;
	private TabComponent atab;
	private TabDisplayHandler tabd;
	private boolean unregistered;
	
	PlayerScoreboard(Player player, ScoreboardManager manager, TabDisplayHandler comp) {
		this.player = player;
		this.manager = manager;
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(board);
		side = new ArrayList<>();
		tab = new ArrayList<>();
		tabd = comp;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ScoreboardManager getScoreboardManager() {
		return manager;
	}
	
	public void unregister() {
		if (unregistered) return;
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		unregistered = true;
		manager.unregisterPlayer(player);
	}
	
	public void update() {
		updateTab();
		updateSide();
	}
	
	public void updateSide() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		aside.update();
	}
	
	public void updateTab() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		atab.update();
	}
	
	public Scoreboard getScoreboard() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return board;
	}
	
	public SideComponent registerSideComponent(String name, String display) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		SideComponent comp = new SideComponent(this, name, display);
		side.add(0, comp);
		return comp;
	}
	
	/**
	 * Register a SideComponent to this PlayerScoreboard
	 * @param comp the component you want to register
	 * @return the component
	 * @throws Error if this PlayerScoreboard is not registered
	 * @throws IllegalArgumentException if the component is null
	 * @throws IllegalArgumentException if the components PlayerScoreboard != this
	 */
	public SideComponent registerSideComponent(SideComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (comp == null) throw new IllegalArgumentException("SideComponent can not be null");
		if (comp.getScoreboard() != this) throw new IllegalArgumentException("SideComponent is not supported by this PlayerScoreboard");
		if (side.contains(comp)) return comp;
		side.add(0, comp);
		return comp;
	}
	
	public List<SideComponent> getSideComponents() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return side;
	}
	
	public void setActiveSideComponent(SideComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (!side.contains(comp) || !comp.isRegistered()) throw new IllegalArgumentException("The Component is not registered");
		if (aside != null) {
			final Component oldside = aside;
			aside = null;
			oldside.setActiv(false);
		}
		aside = comp;
	}
	
	public SideComponent getActiveSideComponent() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return aside;
	}

	void unregisterSide(SideComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (aside == comp) aside = null;
		side.remove(comp);
	}
	
	public List<TabComponent> getTabComponents() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return tab;
	}

	public void setActiveTabComponent(TabComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (!tab.contains(comp) || !comp.isRegistered()) throw new IllegalArgumentException("The Component is not registered");
		if (atab != null) {
			final Component oldtab = atab;
			atab = null;
			oldtab.setActiv(false);
		}
		atab = comp;
	}
	
	public TabComponent registerTabComponent() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		TabComponent comp = new TabComponent(this);
		tab.add(0, comp);
		return comp;
	}
	
	/**
	 * Register a TabComponent to this PlayerScoreboard
	 * @param comp the component you want to register
	 * @return the component
	 * @throws Error if this PlayerScoreboard is not registered
	 * @throws IllegalArgumentException if the component is null
	 * @throws IllegalArgumentException if the components PlayerScoreboard != this
	 */
	public TabComponent registerTabComponent(TabComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (comp == null) throw new IllegalArgumentException("TabComponent can not be null");
		if (comp.getScoreboard() != this) throw new IllegalArgumentException("TabComponent is not supported by this PlayerScoreboard");
		if (tab.contains(comp)) return comp;
		tab.add(0, comp);
		return comp;
	}
	
	public TabComponent getActiveTabComponent() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return atab;
	}
	
	public void setTabDisplay(TabDisplayHandler comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		tabd = comp;
	}

	public TabDisplayHandler getTabDisplay() {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		return tabd;
	}

	void unregisterTab(TabComponent comp) {
		if (unregistered) throw new Error("Playerscoreboard is not registered");
		if (atab == comp) atab = null;
		tab.remove(comp);
	}
	
	public boolean isRegistered() {
		return !unregistered;
	}

}
