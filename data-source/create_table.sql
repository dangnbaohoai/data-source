CREATE DATABASE `heroku_f3b7e4b3cc4d840` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_message` text,
  `message` text,
  `time_send` datetime DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_chat_message` (`room_id`),
  KEY `Fk_user_send_message` (`user_id`),
  CONSTRAINT `Fk_chat_message` FOREIGN KEY (`room_id`) REFERENCES `room_chat` (`id`),
  CONSTRAINT `Fk_user_send_message` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1225 DEFAULT CHARSET=utf8;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `like_comment` int(11) DEFAULT NULL,
  `link_image` text,
  `reply_comment_id` bigint(20) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_post_comment` (`post_id`),
  KEY `Fk_user_comment` (`user_id`),
  CONSTRAINT `Fk_post_comment` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `Fk_user_comment` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=535 DEFAULT CHARSET=utf8;

CREATE TABLE `forums` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cover_image` varchar(255) DEFAULT NULL,
  `description` text,
  `forum_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `censor_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3mthuopkcsbn0cw732h2nrklj` (`forum_name`),
  KEY `Fk_censor_forum` (`censor_id`),
  CONSTRAINT `Fk_censor_forum` FOREIGN KEY (`censor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

CREATE TABLE `image_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link_image` text,
  `post_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_image_post` (`post_id`),
  CONSTRAINT `Fk_image_post` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8;

CREATE TABLE `member_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `date_of_event` date DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_event_user` (`event_id`),
  KEY `Fk_user_event` (`user_id`),
  CONSTRAINT `Fk_event_user` FOREIGN KEY (`event_id`) REFERENCES `post` (`id`),
  CONSTRAINT `Fk_user_event` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

CREATE TABLE `member_forums` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_forum_user` (`forum_id`),
  KEY `Fk_user_forum` (`user_id`),
  CONSTRAINT `Fk_forum_user` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`id`),
  CONSTRAINT `Fk_user_forum` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8;

CREATE TABLE `member_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `notify_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_notify_user` (`notify_id`),
  KEY `Fk_user_notify` (`user_id`),
  CONSTRAINT `Fk_notify_user` FOREIGN KEY (`notify_id`) REFERENCES `notify` (`id`),
  CONSTRAINT `Fk_user_notify` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1025 DEFAULT CHARSET=utf8;

CREATE TABLE `notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_forum_notify` (`forum_id`),
  KEY `Fk_post_notify` (`post_id`),
  CONSTRAINT `Fk_forum_notify` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`id`),
  CONSTRAINT `Fk_post_notify` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=755 DEFAULT CHARSET=utf8;

CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address_of_event` varchar(255) DEFAULT NULL,
  `content` longtext,
  `date_of_event` date DEFAULT NULL,
  `name_event` varchar(255) DEFAULT NULL,
  `number_like` int(11) DEFAULT NULL,
  `time_of_event` time DEFAULT NULL,
  `user_post` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_user_post` (`user_post`),
  CONSTRAINT `Fk_user_post` FOREIGN KEY (`user_post`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=845 DEFAULT CHARSET=utf8;

CREATE TABLE `post_forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_forum_post` (`forum_id`),
  KEY `Fk_post_forum` (`post_id`),
  CONSTRAINT `Fk_forum_post` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`id`),
  CONSTRAINT `Fk_post_forum` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=865 DEFAULT CHARSET=utf8;

CREATE TABLE `report_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) DEFAULT NULL,
  `comment_id` bigint(20) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_comment_report` (`comment_id`),
  KEY `Fk_forum_comment_report` (`forum_id`),
  KEY `Fk_user_comment_report` (`user_id`),
  CONSTRAINT `Fk_comment_report` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `Fk_forum_comment_report` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`id`),
  CONSTRAINT `Fk_user_comment_report` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

CREATE TABLE `report_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) DEFAULT NULL,
  `forum_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_forum_post_report` (`forum_id`),
  KEY `Fk_post_report` (`post_id`),
  KEY `Fk_user_report_post` (`user_id`),
  CONSTRAINT `Fk_forum_post_report` FOREIGN KEY (`forum_id`) REFERENCES `forums` (`id`),
  CONSTRAINT `Fk_post_report` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `Fk_user_report_post` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8;

CREATE TABLE `report_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `message_report` bigint(20) DEFAULT NULL,
  `user_report` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_chat_report` (`room_id`),
  KEY `Fk_user_chat_report` (`user_id`),
  KEY `Fk_message_report` (`message_report`),
  CONSTRAINT `Fk_chat_report` FOREIGN KEY (`room_id`) REFERENCES `room_chat` (`id`),
  CONSTRAINT `Fk_message_report` FOREIGN KEY (`message_report`) REFERENCES `chat_message` (`id`),
  CONSTRAINT `Fk_user_chat_report` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code_role` varchar(255) DEFAULT NULL,
  `name_role` varchar(255) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`code_role`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

CREATE TABLE `room_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id1` bigint(20) DEFAULT NULL,
  `user_id2` bigint(20) DEFAULT NULL,
  `new_message` varchar(255) DEFAULT NULL,
  `status_room` varchar(255) DEFAULT NULL,
  `status_message` varchar(255) DEFAULT NULL,
  `message_recipient` varchar(255) DEFAULT NULL,
  `username_recipient` varchar(255) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Fk_user1_chat` (`user_id1`),
  KEY `Fk_user2_chat` (`user_id2`),
  KEY `username_index` (`username_recipient`),
  CONSTRAINT `Fk_user1_chat` FOREIGN KEY (`user_id1`) REFERENCES `users` (`id`),
  CONSTRAINT `Fk_user2_chat` FOREIGN KEY (`user_id2`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address_of_user` varchar(255) DEFAULT NULL,
  `avatar` text,
  `code_user` bigint(20) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` text,
  `gender` varchar(255) DEFAULT NULL,
  `number_phone` varchar(255) DEFAULT NULL,
  `passwords` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `innitiated_date` datetime DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `repairer` varchar(255) DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm4jd4m15ekgap4ai10nd7clof` (`code_user`),
  UNIQUE KEY `UKmmns67o5v4bfippoqitu4v3t6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8;

CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `Fk_role_user` (`role_id`),
  CONSTRAINT `Fk_role_user` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `Fk_user_role` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
