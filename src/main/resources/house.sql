CREATE TABLE `house` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `flood` varchar(255) DEFAULT NULL,
  `house_info` varchar(255) DEFAULT NULL,
  `total_price` double(100,6) DEFAULT NULL,
  `unit_price` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `position_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
