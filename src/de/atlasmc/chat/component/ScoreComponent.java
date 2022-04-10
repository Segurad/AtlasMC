package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

public class ScoreComponent extends BaseComponent {

	public static final String
	JSON_SCORE = "score",
	JSON_NAME = "name",
	JSON_OBJECTIVE = "objective";
	
	private String entry, objective, score;

	public ScoreComponent(String entry, String objective, String score) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (objective == null)
			throw new IllegalArgumentException("Objective can not be null!");
		if (score == null)
			throw new IllegalArgumentException("Score can not be null!");
		this.entry = entry;
		this.objective = objective;
		this.score = score;
	}
	
	@Override
	public void addContents(JsonBuffer buff) {
		buff.beginObject(JSON_SCORE);
		buff.appendText(JSON_OBJECTIVE, objective);
		buff.appendText(JSON_NAME, entry);
		buff.appendText(JSON_VALUE, score);
		buff.endObject();
		super.addContents(buff);
	}
	
}
