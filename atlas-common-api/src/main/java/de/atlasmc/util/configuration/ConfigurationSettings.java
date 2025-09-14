package de.atlasmc.util.configuration;

public class ConfigurationSettings  {

	private boolean preserveComments = true;
	private boolean beautify = false;
	private boolean autoSerialize = true;
	
	public boolean isPreserveComments() {
		return preserveComments;
	}
	
	public void setPreserveComments(boolean preserveComments) {
		this.preserveComments = preserveComments;
	}
	
	public boolean isBeautify() {
		return beautify;
	}
	
	public void setBeautify(boolean beautify) {
		this.beautify = beautify;
	}
	
	public boolean isAutoSerialize() {
		return autoSerialize;
	}
	
	public void setAutoSerialize(boolean autoSerialize) {
		this.autoSerialize = autoSerialize;
	}
	
}
