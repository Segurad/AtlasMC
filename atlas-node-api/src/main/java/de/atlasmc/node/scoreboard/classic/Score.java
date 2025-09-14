package de.atlasmc.node.scoreboard.classic;

public interface Score {
	
	String getName();
	
	Objective getObjective();
	
	int getScore();
	
	void setScore(int score);
	
	boolean isSet();
	
	void reset();

}
