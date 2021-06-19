DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity` (
                          `eid` int NOT NULL AUTO_INCREMENT,
                          `gid` int NOT NULL,
                          `entity_name` varchar(50) NOT NULL,
                          `x` double NOT NULL,
                          `y` double NOT NULL,
                          `category` varchar(50) NOT NULL,
                          `color` varchar(50) NOT NULL,
                          PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
LOCK TABLES `entity` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `graph`;
CREATE TABLE `graph` (
                         `gid` int NOT NULL AUTO_INCREMENT,
                         `graph_name` varchar(50) NOT NULL,
                         `uid` int NOT NULL,
                         PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

LOCK TABLES `graph` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
                            `rid` int NOT NULL AUTO_INCREMENT,
                            `relation_name` varchar(50) NOT NULL,
                            `gid` int NOT NULL,
                            `first_entity` int NOT NULL,
                            `second_entity` int NOT NULL,
                            PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

LOCK TABLES `relation` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `uid` int NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(50) NOT NULL unique,
                        `password` varchar(50) NOT NULL,
                        PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

LOCK TABLES `entity` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
                        `hid` int NOT NULL AUTO_INCREMENT,
                        `uid` int NOT NULL,
                        `history` varchar(200) NOT NULL unique,
                        `update_time` varchar(50),
                        PRIMARY KEY (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

LOCK TABLES `history` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
                           `did` int NOT NULL,
                           `url` varchar(100),
                           `jpg` varchar(100),
                           `description` varchar(750),
                           `tags` varchar(100),
                           PRIMARY KEY (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

LOCK TABLES `detail` WRITE;
UNLOCK TABLES;