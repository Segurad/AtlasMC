package de.atlascore.scoreboard.classic;

import de.atlasmc.scoreboard.classic.Objective;
import de.atlasmc.scoreboard.classic.Score;

class CoreScore implements Score {
	
	private final CoreObjective obj;
	private final String entry;
	
	CoreScore(CoreObjective obj, String entry) {
		this.obj = obj;
		this.entry = entry;
	}

	@Override
	public String getEntry() {
		return entry;
	}

	@Override
	public Objective getObjective() {
		return obj;
	}

	@Override
	public int getScore() {
		Integer score = obj.getScoreValue(entry);
		if (score == null)
			throw new IllegalStateException("Score is not set!");
		return score;
	}

	@Override
	public void setScore(int score) {
		obj.updateScore(entry, score);
	}

	@Override
	public void reset() {
		obj.resetScore(entry);
	}

	@Override
	public boolean isSet() {
		return obj.getScoreValue(entry) != null;
	}

}
