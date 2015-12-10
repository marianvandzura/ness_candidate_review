CREATE TABLE `candidates_reports` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `candidate_name` varchar(255) DEFAULT NULL,
  `candidate_surname` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `number_of_questions` int(11) DEFAULT NULL,
  `pdfResult` longblob,
  `test_name` varchar(255) DEFAULT NULL,
  `test_result` varchar(255) DEFAULT NULL,
  `total_time` int(11) DEFAULT NULL,
  `test_report` tinyblob,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `options` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT,
  `option` varchar(255) DEFAULT NULL,
  `truth` bit(1) DEFAULT NULL,
  PRIMARY KEY (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `question_results` (
  `question_result_id` int(11) NOT NULL AUTO_INCREMENT,
  `google_time` int(11) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `FK_3p60yw443e2grxc32ukcbnbgh` (`category_id`),
  CONSTRAINT `FK_3p60yw443e2grxc32ukcbnbgh` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `questions_options` (
  `questions_question_id` int(11) NOT NULL,
  `options_option_id` int(11) NOT NULL,
  UNIQUE KEY `UK_nxmgyb6et540jnsocoss0sa3l` (`options_option_id`),
  KEY `FK_9bm0jnnne5dwibclau4k5wucq` (`questions_question_id`),
  CONSTRAINT `FK_9bm0jnnne5dwibclau4k5wucq` FOREIGN KEY (`questions_question_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `FK_nxmgyb6et540jnsocoss0sa3l` FOREIGN KEY (`options_option_id`) REFERENCES `options` (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `settings` (
  `setting_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `test_questions` (
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  KEY `FK_kbsgu8ro6h5joyvd9g40yynsi` (`question_id`),
  KEY `FK_smd91buf2i2aycop6tcuewnnm` (`test_id`),
  CONSTRAINT `FK_kbsgu8ro6h5joyvd9g40yynsi` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `FK_smd91buf2i2aycop6tcuewnnm` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tests` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `FK_itf15pmrnxmvldik0gckrqksj` (`user_id`),
  CONSTRAINT `FK_itf15pmrnxmvldik0gckrqksj` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_password` (
  `password_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`password_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL DEFAULT 'user_name',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `users_user_roles` (
  `users_user_id` int(11) NOT NULL,
  `userRoles_user_role_id` int(11) NOT NULL,
  KEY `FK_siyvft4oukabns98mhjbva05v` (`userRoles_user_role_id`),
  KEY `FK_b0yynlk7rs3r75rhqswgruyil` (`users_user_id`),
  CONSTRAINT `FK_b0yynlk7rs3r75rhqswgruyil` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_siyvft4oukabns98mhjbva05v` FOREIGN KEY (`userRoles_user_role_id`) REFERENCES `user_roles` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
