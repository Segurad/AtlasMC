package de.atlascore.scoreboard.classic;

import de.atlasmc.entity.Player;
import de.atlasmc.scoreboard.classic.ClassicScoreboardView;

class CoreClassicScoreboardView implements ClassicScoreboardView {

	private final CoreScoreboard board;
	private final Player player;
	
	CoreClassicScoreboardView(CoreScoreboard board, Player player) {
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
