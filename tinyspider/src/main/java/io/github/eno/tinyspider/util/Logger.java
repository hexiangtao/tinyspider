package io.github.eno.tinyspider.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	private Class<?> clazz;

	public static enum Level {
		DEBUG(1, "DEBUG"), INFO(2, "INFO"), WARN(3, "WARN"), ERROR(4, "ERROR"), NONE(5, "NONE");
		int level;
		String name;

		Level(int level, String name) {
			this.level = level;
			this.name = name;
		}

	}

	private static Level current = Level.INFO;

	public static void setLevel(int le) {

		for (Level level : Level.values()) {
			if (level.level == le) {
				current = level;
				return;
			}
		}

	}

	public Logger(Class<?> clazz) {
		this.clazz = clazz;
	}

	public static Logger getLogger(Class<?> clazz) {
		return new Logger(clazz);
	}

	public static Logger getLogger() {
		return new Logger(null);
	}

	private void log(String template, Level level, Object... args) {
		String current = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm:ss"));

		String prefix = current +"-" + Thread.currentThread().getName()  + "-[" + level.name + "]-" + (clazz != null ? clazz.getName() : "");
		template = prefix +"-"+ template.replaceAll("\\{\\}", "%s");
		System.out.println(String.format(template, args));
	}

	public void error(String template, Object... args) {
		if (current.level <= Level.ERROR.level) {
			log(template, Level.ERROR, args);
		}
	}

	public void warn(String template, Object... args) {
		if (current.level <= Level.WARN.level) {
			log(template, Level.WARN, args);
		}
	}

	public void info(String template, Object... args) {
		if (current.level <= Level.INFO.level) {
			log(template, Level.INFO, args);
		}
	}

	public void debug(String template, Object... args) {
		if (current.level <= Level.DEBUG.level) {
			log(template, Level.DEBUG, args);
		}
	}

}
