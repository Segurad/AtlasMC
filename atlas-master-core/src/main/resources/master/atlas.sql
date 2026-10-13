-- atlas.nodes definition

CREATE TABLE `nodes` (
  `node_uuid` binary(16) NOT NULL,
  `public_key` varbinary(1024) NOT NULL,
  `first_connection` datetime NOT NULL,
  `last_connection` datetime NOT NULL,
  `version` varchar(128) NOT NULL,
  `disabled` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`node_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_context definition

CREATE TABLE `perm_context` (
  `context_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  PRIMARY KEY (`context_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_groups definition

CREATE TABLE `perm_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT,
  `name` varchar(64) NOT NULL,
  `sort_weight` int(11) NOT NULL DEFAULT 0,
  `prefix` varchar(32) DEFAULT NULL,
  `suffix` varchar(32) DEFAULT NULL,
  `chat_color` mediumint(9) DEFAULT NULL COMMENT '3 byte RGB value',
  `name_color` mediumint(9) DEFAULT NULL COMMENT '3 byte RGB value ',
  `power` int(11) NOT NULL DEFAULT 0,
  `is_default` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `uk_perm_groups_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_handler_context definition

CREATE TABLE `perm_handler_context` (
  `handler_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `uk_perm_handler_context` (`handler_id`,`ctx_key`,`ctx_value`),
  KEY `fk_perm_handler_context_handler_id` (`handler_id`)
  CONSTRAINT `fk_perm_handler_context_handler_id` FOREIGN KEY (`handler_id`) REFERENCES `perm_handler` (`handler_id`) ON DELETE CASCADE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.profiles definition

CREATE TABLE `profiles` (
  `profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `mojang_uuid` binary(16) NOT NULL,
  `internal_uuid` binary(16) NOT NULL,
  `mojang_name` varchar(16) NOT NULL,
  `internal_name` varchar(16) DEFAULT NULL,
  `join_first` date NOT NULL,
  `join_last` date NOT NULL,
  PRIMARY KEY (`profile_id`),
  UNIQUE KEY `uk_profiles_mojang_uuid` (`mojang_uuid`),
  UNIQUE KEY `uk_profiles_internal_uuid` (`internal_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.schema_versions definition

CREATE TABLE `schema_versions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT 'name used for this schema',
  `plugin` varchar(64) NOT NULL COMMENT 'plugin this schema belongs to',
  `plugin_version` varchar(64) NOT NULL,
  `version` int(11) NOT NULL COMMENT 'version id',
  `created` datetime NOT NULL DEFAULT current_timestamp(),
  `last_updated` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_schema_versions_name_plugin` (`name`,`plugin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_context_perm definition

CREATE TABLE `perm_context_perm` (
  `context_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  KEY `fk_context_perm` (`context_id`),
  UNIQUE KEY `uk_perm_context_perm` (`context_id`,`perm`),
  CONSTRAINT `fk_context_perm` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_context definition

CREATE TABLE `perm_group_context` (
  `group_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `uk_perm_group_context` (`group_id`,`ctx_key`,`ctx_value`),
  KEY `fk_group_context` (`group_id`),
  CONSTRAINT `fk_group_context` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm definition

CREATE TABLE `perm_group_perm` (
  `group_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  UNIQUE KEY `uk_perm_group_perm` (`group_id`,`perm`),
  KEY `fk_group_perm` (`group_id`),
  CONSTRAINT `fk_group_perm` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm_context definition

CREATE TABLE `perm_group_perm_context` (
  `group_id` int(11) NOT NULL,
  `context_id` int(11) NOT NULL,
  KEY `fk_group_ctx_perm` (`group_id`),
  KEY `fk_ctx_id_group` (`context_id`),
  UNIQUE KEY `uk_perm_group_perm_context` (`group_id`,`context_id`),
  CONSTRAINT `fk_ctx_id_group` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_group_ctx_perm` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_handler_group definition

CREATE TABLE `perm_handler_group` (
  `handler_id` int(11) NOT NULL,
  `group` varchar(64) NOT NULL,
  UNIQUE KEY `uk_perm_handler_group` (`handler_id`,`group`),
  KEY `fk_perm_handler_group_handler_id` (`handler_id`),
  KEY `fk_perm_handler_group_group_name` (`group`),
  CONSTRAINT `fk_perm_handler_group_handler_id` FOREIGN KEY (`handler_id`) REFERENCES `perm_handler` (`handler_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_perm_handler_group_group_name` FOREIGN KEY (`group`) REFERENCES `perm_groups` (`name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;