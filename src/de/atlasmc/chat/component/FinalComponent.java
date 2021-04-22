package de.atlasmc.chat.component;

public class FinalComponent implements ChatComponent {
	
	private String json;
	private String legacy;
	
	public FinalComponent(String json) {
		this.json = json;
	}
	
	public FinalComponent(String json, String legacy) {
		this.json = json;
		this.legacy = legacy;
	}

	@Override
	public String getLegacyText() {
		return legacy;
	}

	@Override
	public String getJsonText() {
		return json;
	}

}
