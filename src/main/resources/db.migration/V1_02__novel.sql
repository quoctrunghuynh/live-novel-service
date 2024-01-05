CREATE TABLE IF NOT EXISTS novel(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    title varchar(255),
    content text,
    update_at timestamp default  CURRENT_TIMESTAMP,
    created_at timestamp default CURRENT_TIMESTAMP,
    is_deleted boolean default false,

    user_id bigint,
    foreign key (user_id) REFERENCES user (id)
);