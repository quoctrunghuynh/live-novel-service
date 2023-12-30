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

insert into user(username,
                 password,
                 display_name,
                 role,
                 created_at,
                 is_deleted)
values( 'trunghuynh',
        '$2a$10$jPbQMCbiC5gASPeHGEzxhOBLpv4FmHK86P/hiXtB.Pc.s1K0035bG',
        'Hidden Changer',
        'USER',
        '2023-12-31 00:41:07',
        0
);