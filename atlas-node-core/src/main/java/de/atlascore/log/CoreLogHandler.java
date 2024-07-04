package de.atlascore.log;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.layout.PatternLayout.Builder;

import de.atlasmc.log.Log;
import de.atlasmc.log.LogHandler;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public class CoreLogHandler implements LogHandler {
	
	private final Configuration config;
	private final File workDir;
	private final PatternLayout defaultConsoleLayout;
	private final PatternLayout defaultFileLayout;
	
	public CoreLogHandler(File workDir, Configuration config) {
		if (workDir == null)
			throw new IllegalArgumentException("Work dir can not be null!");
		this.workDir = workDir;
		this.config = new MemoryConfiguration(config);
		defaultConsoleLayout = toLayout(this.config.getString("defaults.console-layout"), null);
		defaultFileLayout = toLayout(this.config.getString("defaults.file-layout"), null);
		for (String key : config.getConfigurationSection("groups").getKeys()) {
			getLog4jLogger(key);
		}
	}

	private Appender createFileAppender(File logDir, String fileName, String filePattern, String layout) {
		RollingRandomAccessFileAppender app = RollingRandomAccessFileAppender.newBuilder()
		.withFileName(new File(logDir, fileName).getPath().replace('\\', '/'))
		.withFilePattern(new File(logDir, filePattern).getPath().replace('\\', '/'))
		.setLayout(toLayout(layout, defaultFileLayout))
		.setName("File")
		.withPolicy(CompositeTriggeringPolicy.createPolicy(
				TimeBasedTriggeringPolicy.newBuilder().build(),
				OnStartupTriggeringPolicy.createPolicy(1)))
		.withStrategy(DefaultRolloverStrategy.newBuilder().withMax("50").build())
		.build();
		app.start();
		return app;
	}

	private Appender createConsoleAppender(String layout) {
		ConsoleAppender app = ConsoleAppender.newBuilder()
		.setLayout(toLayout(layout, defaultConsoleLayout))
		.setFilter(new CoreAtlasConsoleFilter())
		.setName("Console")
		.build();
		app.start();
		return app;
	}

	@Override
	public Log getLogger(Class<?> clazz, String group) {
		return getLogger(clazz.getSimpleName(), group);
	}

	@Override
	public Log getLogger(String name, String group) {
		return new CoreLog(name, getLog4jLogger(group));
	}
	
	private synchronized Logger getLog4jLogger(String name) {
		Logger logger = null;
		if (LogManager.exists(name)) {
			logger = (Logger) LogManager.getLogger(name);
		} else {
			logger = (Logger) LogManager.getLogger(name);
			ConfigurationSection config = this.config.getConfigurationSection("groups." + name);
			if (config == null)
				return logger;
			logger.setLevel(Level.getLevel(config.getString("level")));
			String fileName = config.getString("file.file-name");
			String filePattern = config.getString("file.file-pattern");
			String fileLayout = config.getString("file.layout");
			logger.addAppender(createFileAppender(new File(workDir, "logs/"), fileName, filePattern, fileLayout));
			String consoleLayout = config.getString("console.layout");
			logger.addAppender(createConsoleAppender(consoleLayout));
		}
		return logger;
	}
	
	private PatternLayout toLayout(String pattern, PatternLayout def) {
		if (pattern == null)
			return def;
		Builder builder = PatternLayout.newBuilder();
		builder.withPattern(pattern);
		return builder.build();
	}

}
