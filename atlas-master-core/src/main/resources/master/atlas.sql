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
  `context_id` binary(16) NOT NULL COMMENT 'context UUID',
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  PRIMARY KEY (`context_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_groups definition

CREATE TABLE `perm_groups` (
  `group_id` binary(16) NOT NULL COMMENT 'group UUID',
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


-- atlas.perm_user_context definition

CREATE TABLE `perm_user_context` (
  `user_id` binary(16) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `uk_perm_user_context` (`user_id`,`ctx_key`,`ctx_value`),
  KEY `fk_profile_context` (`user_id`)
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
  `context_id` binary(16) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  KEY `fk_context_perm` (`context_id`),
  CONSTRAINT `fk_context_perm` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_context definition

CREATE TABLE `perm_group_context` (
  `group_id` binary(16) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `uk_perm_group_context` (`group_id`,`ctx_key`,`ctx_value`),
  KEY `fk_group_context` (`group_id`),
  CONSTRAINT `fk_group_context` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm definition

CREATE TABLE `perm_group_perm` (
  `group_id` binary(16) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  UNIQUE KEY `uk_perm_group_perm` (`group_id`,`perm`),
  KEY `fk_group_perm` (`group_id`),
  CONSTRAINT `fk_group_perm` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm_context definition

CREATE TABLE `perm_group_perm_context` (
  `group_id` binary(16) NOT NULL,
  `context_id` binary(16) NOT NULL,
  KEY `fk_group_ctx_perm` (`group_id`),
  KEY `fk_ctx_id_group` (`context_id`),
  CONSTRAINT `fk_ctx_id_group` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_group_ctx_perm` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_user_group definition

CREATE TABLE `perm_user_group` (
  `user_id` binary(16) NOT NULL,
  `group` varchar(64) NOT NULL,
  UNIQUE KEY `uk_perm_user_group` (`user_id`,`group`),
  KEY `fk_profile_group` (`user_id`),
  KEY `fk_group_profile` (`group`),
  CONSTRAINT `fk_perm_user_group_group_name` FOREIGN KEY (`group`) REFERENCES `perm_groups` (`name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;