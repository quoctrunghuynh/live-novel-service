CREATE TABLE IF NOT EXISTS user
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    username   varchar(255) UNIQUE,
    password   varchar(255),
    display_name  varchar(255),
    role       varchar(20),
    created_at timestamp default CURRENT_TIMESTAMP,
    is_deleted boolean default false
);