CREATE TABLE IF NOT EXISTS `items`
(
    `id`               bigint NOT NULL AUTO_INCREMENT,
    `name`             varchar(255) DEFAULT NULL,
    `description`      varchar(255) DEFAULT NULL,
    `photo_url`        varchar(255) DEFAULT NULL,
    `ending_date_time` timestamp    DEFAULT NULL,
    `bid_price`        bigint       DEFAULT NULL,
    `purchase_price`   bigint       DEFAULT NULL,
    `seller_id`        bigint       DEFAULT NULL,
    `buyer_id`         bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`buyer_id`) REFERENCES users (id),
    FOREIGN KEY (`seller_id`) REFERENCES users (id)
);