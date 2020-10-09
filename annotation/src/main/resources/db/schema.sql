create table if not exists user
(
    use_id        int not null primary key auto_increment,
    use_name      varchar(100),
    use_sex       varchar(1),
    use_age       INT(3),
    use_id_no     VARCHAR(18),
    use_phone_num VARCHAR(11),
    use_email     VARCHAR(100),
    create_time   DATETIME,
    modify_time   DATETIME,
    use_state     VARCHAR(1)
);