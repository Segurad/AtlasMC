package de.atlasmc.scoreboard.atlas;

public class SimpleTextComponent implements TextComponent {

	private String text;

	public SimpleTextComponent(String text) {
		this.text = text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String getText() {
		return text;
	}
}
