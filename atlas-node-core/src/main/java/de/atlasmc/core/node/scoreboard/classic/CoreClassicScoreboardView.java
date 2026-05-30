package de.atlasmc.core.node.scoreboard.classic;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.scoreboard.classic.ClassicScoreboardView;
import de.atlasmc.util.annotation.NotNull;

class CoreClassicScoreboardView implements ClassicScoreboardView {

	private final CoreScoreboard board;
	private final Player player;
	
	CoreClassicScoreboardView(@NotNull CoreScoreboard board, @NotNull Player player) {
		this.board = board;
		this.player = player;
	}

	@Override
	public Player getViewer() {
		return player;
	}

	@Override
	public CoreScoreboard getHandler() {
		return board;
	}

	@Override
	public void remove() {
		board.remove(this);
	}

	@Override
	public void add() {
		board.add(this);
	}

}
