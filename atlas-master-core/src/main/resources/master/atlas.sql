SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE `atlas` (
  `atlas_version` varchar(100) NOT NULL,
  `schema_version` int(11) NOT NULL,
  `installation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_context` (
  `context_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_context_perm` (
  `context_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_groups` (
  `group_id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `sort_weight` int(11) NOT NULL DEFAULT 0,
  `prefix` varchar(32) DEFAULT NULL,
  `suffix` varchar(32) DEFAULT NULL,
  `chat_color` mediumint(9) DEFAULT NULL COMMENT '3 byte RGB value',
  `name_color` mediumint(9) DEFAULT NULL COMMENT '3 byte RGB value ',
  `power` int(11) NOT NULL DEFAULT 0,
  `is_default` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_group_context` (
  `group_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_group_perm` (
  `group_id` int(11) NOT NULL,
  `perm` varchar(128) NOT NULL,
  `power` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_group_perm_context` (
  `group_id` int(11) NOT NULL,
  `context_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_user_context` (
  `user_id` int(11) NOT NULL,
  `ctx_key` varchar(128) NOT NULL,
  `ctx_value` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `perm_user_group` (
  `user_id` int(11) NOT NULL,
  `group` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `profiles` (
  `profile_id` int(11) NOT NULL,
  `mojang_uuid` varbinary(16) NOT NULL,
  `internal_uuid` varbinary(16) NOT NULL,
  `mojang_name` varchar(16) NOT NULL,
  `internal_name` varchar(16) DEFAULT NULL,
  `join_first` date NOT NULL,
  `join_last` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE `perm_context`
  ADD PRIMARY KEY (`context_id`);

ALTER TABLE `perm_context_perm`
  ADD KEY `context_perm_fk` (`context_id`);

ALTER TABLE `perm_groups`
  ADD PRIMARY KEY (`group_id`),
  ADD UNIQUE KEY `name` (`name`);

ALTER TABLE `perm_group_context`
  ADD UNIQUE KEY `perm_group_context_un` (`group_id`,`ctx_key`,`ctx_value`),
  ADD KEY `group_context_fk` (`group_id`);

ALTER TABLE `perm_group_perm`
  ADD UNIQUE KEY `perm_group_perm_un` (`group_id`,`perm`),
  ADD KEY `group_perm_fk` (`group_id`);

ALTER TABLE `perm_group_perm_context`
  ADD KEY `group_ctx_perm_fk` (`group_id`),
  ADD KEY `ctx_id_group_fk` (`context_id`);

ALTER TABLE `perm_user_context`
  ADD UNIQUE KEY `perm_user_context_un` (`user_id`,`ctx_key`,`ctx_value`),
  ADD KEY `profile_context_fk` (`user_id`);

ALTER TABLE `perm_user_group`
  ADD UNIQUE KEY `perm_user_group_un` (`user_id`,`group`),
  ADD KEY `profile_group_fk` (`user_id`),
  ADD KEY `group_profile_fk` (`group`);

ALTER TABLE `profiles`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `mojang_uuid` (`mojang_uuid`),
  ADD UNIQUE KEY `internal_uuid` (`internal_uuid`);


ALTER TABLE `perm_context`
  MODIFY `context_id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `perm_groups`
  MODIFY `group_id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `profiles`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `perm_context_perm`
  ADD CONSTRAINT `context_perm_fk` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE;

ALTER TABLE `perm_group_context`
  ADD CONSTRAINT `group_context_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE;

ALTER TABLE `perm_group_perm`
  ADD CONSTRAINT `group_perm_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE;

ALTER TABLE `perm_group_perm_context`
  ADD CONSTRAINT `ctx_id_group_fk` FOREIGN KEY (`context_id`) REFERENCES `perm_context` (`context_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `group_ctx_perm_fk` FOREIGN KEY (`group_id`) REFERENCES `perm_groups` (`group_id`) ON DELETE CASCADE;

ALTER TABLE `perm_user_context`
  ADD CONSTRAINT `profile_context_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE;

ALTER TABLE `perm_user_group`
  ADD CONSTRAINT `perm_user_group_group_name_fk` FOREIGN KEY (`group`) REFERENCES `perm_groups` (`name`) ON DELETE CASCADE,
  ADD CONSTRAINT `perm_user_group_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `profiles` (`profile_id`) ON DELETE CASCADE;
COMMIT;
