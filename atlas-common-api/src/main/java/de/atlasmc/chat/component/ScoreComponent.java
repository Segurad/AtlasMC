package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ScoreComponent extends AbstractBaseComponent<ScoreComponent> {

	public static final CharKey
	JSON_SCORE = CharKey.literal("score"),
	JSON_NAME = CharKey.literal("name"),
	JSON_OBJECTIVE = CharKey.literal("objective");
	
	private String entry;
	private String objective;

	public ScoreComponent(String entry, String objective) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (objective == null)
			throw new IllegalArgumentException("Objective can not be null!");
		this.entry = entry;
		this.objective = objective;
	}
	
	@Override
	protected String getType() {
		return "score";
	}
	
	@Override
	public void addContents(NBTWriter writer) throws IOException {
		super.addContents(writer);
		writer.writeCompoundTag(JSON_SCORE);
		writer.writeStringTag(JSON_OBJECTIVE, objective);
		writer.writeStringTag(JSON_NAME, entry);
		writer.writeEndTag();
	}

	@Override
	protected ScoreComponent getThis() {
		return this;
	}
	
}
