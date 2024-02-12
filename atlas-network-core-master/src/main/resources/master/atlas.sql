-- atlas.atlas definition

CREATE TABLE `atlas` (
  `atlas_version` varchar(100) NOT NULL,
  `schema_version` int(11) NOT NULL,
  `installation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_context definition

CREATE TABLE `perm_context` (
  `context_id` int(11) NOT NULL AUTO_INCREMENT,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  PRIMARY KEY (`context_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_groups definition

CREATE TABLE `perm_groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `sort_weight` int(11) NOT NULL,
  `prefix` varchar(32) DEFAULT NULL,
  `suffix` varchar(32) DEFAULT NULL,
  `chat_color` mediumint(9) NOT NULL COMMENT '3 byte RGB value',
  `name_color` tinyint(4) NOT NULL COMMENT 'id of ChatColor enum',
  `power` int(11) NOT NULL DEFAULT 0,
  `is_default` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


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
  UNIQUE KEY `mojang_uuid` (`mojang_uuid`),
  UNIQUE KEY `internal_uuid` (`internal_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_context_perm definition

CREATE TABLE `perm_context_perm` (
  `context_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  KEY `context_perm_fk` (`context_id`),
  CONSTRAINT `context_perm_fk` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_context definition

CREATE TABLE `perm_group_context` (
  `group_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `perm_group_context_un` (`group_id`,`ctx_key`,`ctx_value`),
  KEY `group_context_fk` (`group_id`),
  CONSTRAINT `group_context_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_inherit definition

CREATE TABLE `perm_group_inherit` (
  `group_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  KEY `group_inherit_parent_fk` (`parent_id`),
  KEY `group_inherit_self_fk` (`group_id`),
  CONSTRAINT `group_inherit_parent_fk` FOREIGN KEY (`parent_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE,
  CONSTRAINT `group_inherit_self_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm definition

CREATE TABLE `perm_group_perm` (
  `group_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  UNIQUE KEY `perm_group_perm_un` (`group_id`,`perm`),
  KEY `group_perm_fk` (`group_id`),
  CONSTRAINT `group_perm_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_group_perm_context definition

CREATE TABLE `perm_group_perm_context` (
  `group_id` int(11) NOT NULL,
  `context_id` int(11) NOT NULL,
  KEY `group_ctx_perm_fk` (`group_id`),
  KEY `ctx_id_group_fk` (`context_id`),
  CONSTRAINT `ctx_id_group_fk` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE,
  CONSTRAINT `group_ctx_perm_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_user_context definition

CREATE TABLE `perm_user_context` (
  `user_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL,
  UNIQUE KEY `perm_user_context_un` (`user_id`,`ctx_key`,`ctx_value`),
  KEY `profile_context_fk` (`user_id`),
  CONSTRAINT `profile_context_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_user_group definition

CREATE TABLE `perm_user_group` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  UNIQUE KEY `perm_user_group_un` (`user_id`,`group_id`),
  KEY `profile_group_fk` (`user_id`),
  KEY `group_profile_fk` (`group_id`),
  CONSTRAINT `group_profile_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE,
  CONSTRAINT `profile_group_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_user_perm definition

CREATE TABLE `perm_user_perm` (
  `user_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL,
  UNIQUE KEY `perm_user_perm_un` (`user_id`,`perm`),
  KEY `profile_perm_fk` (`user_id`),
  CONSTRAINT `profile_perm_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- atlas.perm_user_perm_context definition

CREATE TABLE `perm_user_perm_context` (
  `user_id` int(11) NOT NULL,
  `context_id` int(11) NOT NULL,
  KEY `profile_perm_ctx_fk` (`user_id`),
  KEY `ctx_id_user_fk` (`context_id`),
  CONSTRAINT `ctx_id_user_fk` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE,
  CONSTRAINT `profile_perm_ctx_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;