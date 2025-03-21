package de.atlasmc.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Dependency {
	
	// ^(?P<order>[\+\-\~])?(?P<type>[!\?\*])?(?: ?(?P<name>[a-zA-Z0-9-_]+))(?: (?P<operation>\>|\<|\=\=|\>\=|\<\=) (?P<version>v?[0-9a-zA-Z-.\+]+))?(?: (?P<from>v?[0-9a-zA-Z-.\+]+) - (?P<to>v?[0-9a-zA-Z-.\+]+))?$
	public static final Pattern PATTERN = Pattern.compile("^(?P<order>[\\+\\-\\~])?(?P<type>[!\\?\\*])?(?: ?(?P<name>[a-zA-Z0-9-_]+))(?: (?P<operation>\\>|\\<|\\=\\=|\\>\\=|\\<\\=) (?P<version>v?[0-9a-zA-Z-.\\+]+))?(?: (?P<from>v?[0-9a-zA-Z-.\\+]+) - (?P<to>v?[0-9a-zA-Z-.\\+]+))?$");
	
	public final String name;
	public final Version version;
	public final Version toVersion;
	public final MatchOperation operation;
	public final Order order;
	public final Type type;
	
	public Dependency(String name) {
		this(name, Type.REQUIRED);
	}
	
	public Dependency(String name, Type type) {
		this(name, type, Order.AFTER);
	}
	
	public Dependency(String name, Type type, Order order) {
		this(name, type, order, MatchOperation.ANY, null);
	}
	
	public Dependency(String name, Type type, MatchOperation operation, Version verion) {
		this(name, type, Order.AFTER, operation, verion);
	}
	
	public Dependency(String name, Type type, Order order, MatchOperation operation, Version verion) {
		this(name, type, order, operation, verion, null);
	}
	
	public Dependency(String name, Type type, Version from, Version to) {
		this(name, type, Order.AFTER, from, to);
	}
	
	public Dependency(String name, Type type, Order order, Version from, Version to) {
		this(name, type, order, MatchOperation.RANGE, from, to);
	}
	
	private Dependency(String name, Type type, Order order, MatchOperation operation, Version from, Version to) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (order == null)
			throw new IllegalArgumentException("Order can not be null!");
		if (operation == null)
			throw new IllegalArgumentException("Operation can not be null!");
		if (operation != MatchOperation.ANY) {
			if (operation == MatchOperation.RANGE) {
				if (from == null)
					throw new IllegalArgumentException("From can not be null!");
				if (to == null)
					throw new IllegalArgumentException("To can not be null!");
				this.version = from;
				this.toVersion = to;
			} else if (from == null) {
				throw new IllegalArgumentException("Version can not be null!");
			} else {
				this.version = from;
				this.toVersion = null;
			}
		} else {
			this.version = null;
			this.toVersion = null;
		}
		this.name = name;
		this.type = type;
		this.operation = operation;
		this.order = order;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append(order.identifier)
				.append(type.identifier)
				.append(' ')
				.append(name);
		if (operation == MatchOperation.RANGE) {
			builder.append(' ')
				.append(version.toString())
				.append(" - ")
				.append(toVersion.toString());
		} else if (operation != MatchOperation.ANY) {
			builder.append(' ')
				.append(operation.identifier)
				.append(version.toString());
		}
		return builder.toString();
	}
	
	/**
	 * Parses the given string as dependency.
	 * <br><br>
	 * Format:
	 * <ul>
	 * <li>"(order)(type) (name) (operation) (version)"</li>
	 * <li>"(order)(type) (name) (from) - (to)"</li>
	 * </ul>
	 * @param dependency
	 * @return dependency
	 */
	public static Dependency of(String dependency) {
		if (dependency == null)
			throw new IllegalArgumentException("Dependency can not be null!");
		Matcher matcher = PATTERN.matcher(dependency);
		String name = matcher.group("name");
		if (name == null)
			throw new IllegalArgumentException("Invalid dependency String: " + dependency);
		Order order = Order.getByIdentifier(matcher.group("order"));
		Type type = Type.getByIdentifier(matcher.group("type"));
		MatchOperation operation = MatchOperation.getByIdentifier(matcher.group("operation"));
		Version from = null;
		Version to = null;
		if (operation == MatchOperation.ANY) {
			String rawFrom = matcher.group("from");
			if (rawFrom != null) {
				String rawTo = matcher.group("to");
				try {
					from = new Version(rawFrom);
				} catch(IllegalArgumentException e) {
					throw new IllegalArgumentException("Invalid from version format in dependency String: " + dependency);
				}
				try {
					to = new Version(rawTo);
				} catch(IllegalArgumentException e) {
					throw new IllegalArgumentException("Invalid from version format in dependency String: " + dependency);
				}
				operation = MatchOperation.RANGE;
			}
		} else {
			try {
				from = new Version(matcher.group("version"));
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Invalid version format in dependency String: " + dependency);
			}
		}
		return new Dependency(name, type, order, operation, from, to);
	}
	
	public static enum Order {
		
		/**
		 * Indicates that the resource should be loaded before the dependency
		 */
		BEFORE("-"),
		/**
		 * Indicates that the resource should be loaded after the dependency
		 */
		AFTER("+"),
		/**
		 * Indicates the dependency should only be present and no load order will be adjusted
		 */
		WHATEVER("~");
		
		private final String identifier;
		
		private Order(String identifier) {
			this.identifier = identifier;
		}
		
		public String getIdentifier() {
			return identifier;
		}
		
		public static Order getByIdentifier(String identifier) {
			if (identifier == null)
				return getDefault();
			switch (identifier) {
			case "+": return AFTER;
			case "-": return BEFORE;
			case "~": return WHATEVER;
			default:
				return getDefault();
			}
		}
		
		public static Order getDefault() {
			return AFTER;
		}
		
	}
	
	public static enum Type {
		
		/**
		 * The matching resource should not be present
		 */
		INCOMPATIPLE("!"),
		/**
		 * The matching resource must be present
		 */
		REQUIRED("*"),
		/**
		 * The matching resource may be present
		 */
		OPTIONAL("?");
		
		private final String identifier;
		
		private Type(String identifier) {
			this.identifier = identifier;
		}
		
		public String getIdentifier() {
			return identifier;
		}
		
		public static Type getByIdentifier(String identifier) {
			if (identifier == null)
				return getDefault();
			switch (identifier) {
			case "?": return OPTIONAL;
			case "!": return INCOMPATIPLE;
			case "*": return REQUIRED;
			default:
				return getDefault();
			}
		}
		
		public static Type getDefault() {
			return REQUIRED;
		}
		
	}
	
	public static enum MatchOperation {
		/**
		 * Any {@link Version} is valid
		 */
		ANY("", Integer.MAX_VALUE, false),
		LOWER("<", 1, false),
		LOWER_EQUAL("<=", 1, true),
		EQUAL("==", 0, true),
		GREATER_EQUAL(">=", -1, true),
		GREATER("", -1, false),
		/**
		 * The Version must be in range
		 */
		RANGE(null, Integer.MAX_VALUE, false);
		
		private final String identifier;
		private boolean equal;
		private int comp;
		
		private MatchOperation(String identifier, int comp, boolean equal) {
			this.equal = equal;
			this.comp = comp;
			this.identifier = identifier;
		}
		
		public String getIdentifier() {
			return identifier;
		}
		
		public static MatchOperation getByIdentifier(String identifier) {
			if (identifier == null)
				return getDefault();
			switch (identifier) {
			case "<": return LOWER;
			case "<=": return LOWER_EQUAL;
			case "==": return EQUAL;
			case ">=": return GREATER_EQUAL;
			case ">": return GREATER;
			default:
				return getDefault();
			}
		}
		
		public static MatchOperation getDefault() {
			return ANY;
		}
		
	}
	
	public boolean matches(Version version) {
		if (version == null)
			throw new IllegalArgumentException("Version can not be null!");
		if (operation == MatchOperation.ANY)
			return true;
		if (operation == MatchOperation.RANGE) {
			return this.version.compareTo(version) != 1 && this.toVersion.compareTo(version) != -1;
		}
		int result = this.version.compareTo(version);
		if (result == operation.comp)
			return true;
		return operation.equal && result == 0;
	}
	
}
