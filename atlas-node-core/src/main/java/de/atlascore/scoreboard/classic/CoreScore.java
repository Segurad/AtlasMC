package de.atlascore.scoreboard.classic;

import de.atlasmc.scoreboard.classic.Objective;
import de.atlasmc.scoreboard.classic.Score;

class CoreScore implements Score {
	
	private final CoreObjective obj;
	private final String name;
	
	CoreScore(CoreObjective obj, String name) {
		this.obj = obj;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Objective getObjective() {
		return obj;
	}

	@Override
	public int getScore() {
		Integer score = obj.getScoreValue(name);
		if (score == null)
			throw new IllegalStateException("Score is not set!");
		return score;
	}

	@Override
	public void setScore(int score) {
		obj.updateScore(name, score);
	}

	@Override
	public void reset() {
		obj.resetScore(name);
	}

	@Override
	public boolean isSet() {
		return obj.getScoreValue(name) != null;
	}

}
