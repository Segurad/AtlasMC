package de.atlasmc.scoreboard.classic;

public interface Score {
	
	public String getName();
	
	public Objective getObjective();
	
	public int getScore();
	
	public void setScore(int score);
	
	public boolean isSet();
	
	public void reset();

}
