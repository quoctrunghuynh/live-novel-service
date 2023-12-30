CREATE TABLE IF NOT EXISTS book(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    title varchar(255),
    content text,
    update_at timestamp default  CURRENT_TIMESTAMP,
    created_at timestamp default CURRENT_TIMESTAMP,
    is_deleted boolean default false,

    user_id bigint,
    foreign key (user_id) REFERENCES user (id)
);

insert into book(title,
                 content,
                 update_at,
                 created_at,
                 is_deleted,
                 user_id)
values (
        'The play has begun',
        'I got ADHD',
        '2090-12-31 00:59:00',
        '2023-12-31 00:41:07',
        0,
        1
);