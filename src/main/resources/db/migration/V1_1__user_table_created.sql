CREATE TABLE IF NOT EXISTS `users`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `password` varchar(255) DEFAULT NULL,
    `roles`    varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);