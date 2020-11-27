package de.atlasmc.scoreboard.atlas;

import java.util.HashMap;

import de.atlasmc.scoreboard.Scoreboard;
import de.atlasmc.scoreboard.Team;

public class TabComponent implements Component {

	protected final Scoreboard board;
	private final PlayerScoreboard source;
	protected final HashMap<PlayerScoreboard, Team> wrapper;
	private boolean unregistered;
	
	public TabComponent(PlayerScoreboard board) {
		this.board = board.getScoreboard();
		this.source = board;
		wrapper = new HashMap<>();
	}
	
	@Override
	public void update() {
		if (unregistered) throw new Error("Component is not registered");
		for (PlayerScoreboard board : source.getScoreboardManager().getScoreboards()) {
			update(board);
		}
	}
	
	public void update(PlayerScoreboard board) {
		if (unregistered) throw new Error("Component is not registered");
		if (!wrapper.containsKey(board)) {
			if (!board.isRegistered()) return;
			add(board);
			return;
		} else if (!board.isRegistered()) {
			remove(board);
		}
		final Team t = wrapper.get(board);
		final TabDisplayHandler d = board.getTabDisplay();
		if (d == null) {
			remove(board);
			return;
		}
		t.setPrefix(d.getPrefix(source));
		t.setSuffix(d.getSuffix(source));
		t.setColor(d.getColor(source));
		d.getOptions(source).forEach(t::setOption);
		d.getOptions(source).forEach((k,v) -> t.setOption(k, v));
	}

	@Override
	public void unregister() {
		if (unregistered) return;
		unregistered = true;
		wrapper.values().forEach(t -> t.unregister());
		wrapper.clear();
		source.unregisterTab(this);
	}

	@Override
	public boolean isActive() {
		if (unregistered) throw new Error("Component is not registered");
		return source.getActiveTabComponent() == this;
	}

	@Override
	public void setActiv(boolean value) {
		if (unregistered) throw new Error("Component is not registered");
		source.setActiveTabComponent(value ? this : null);
		if (value) {
			update();
		} else {
			removeAll();
		}
	}
	public void removeAll() {
		wrapper.values().forEach(t -> t.unregister());
		wrapper.clear();
	}

	public void add(PlayerScoreboard board) {
		if (unregistered) throw new Error("Component is not registered");
		if (!board.isRegistered()) return;
		if (!isActive()) return;
		if (wrapper.containsKey(board)) {
			update(board);
		} else {
			wrapper.put(board, this.board.registerNewTeam(board.getTabDisplay().getPlayerGroupNameID(this.source)));
			update(board);
		}
	}
	
	public void remove(PlayerScoreboard board) {
		if (unregistered) throw new Error("Component is not registered");
		if (!board.isRegistered()) return;
		if (!isActive()) return;
		if (wrapper.containsKey(board)) wrapper.remove(board).unregister();
	}

	@Override
	public final boolean isRegistered() {
		return !unregistered;
	}

	@Override
	public final PlayerScoreboard getScoreboard() {
		return source;
	}

}
