package de.atlasmc.network.player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.atlasmc.io.connection.ConnectionConfig;
import de.atlasmc.util.configuration.ConfigurationSection;

public class PlayerConnectionConfig extends ConnectionConfig {
	
	private int maxChunksPerTick;
	private boolean matchDomains;
	private List<Pattern> domainWhiteList;
	
	public PlayerConnectionConfig(ConfigurationSection config) {
		super(config);
		this.maxChunksPerTick = config.getInt("max-chunks-per-tick", 10);
		this.matchDomains = config.getBoolean("match-domains", false);
		this.domainWhiteList = new ArrayList<Pattern>();
		for (String domain : config.getStringList("domain-whitelist", List.of())) {
			this.domainWhiteList.add(Pattern.compile(domain));
		}
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		super.toConfiguration(config);
		config.set("max-chunks-per-tick", maxChunksPerTick);
		config.set("match-domains", matchDomains);
		if (!domainWhiteList.isEmpty()) {
			List<String> values = new ArrayList<>(domainWhiteList.size());
			for (Pattern p : domainWhiteList) {
				values.add(p.pattern());
			}
			config.set("domain-whitelist", values);
		}
		return config;
	}

}
