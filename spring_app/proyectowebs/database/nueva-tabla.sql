
CREATE TABLE log (
    id                  int8           auto_increment,
    fecha               timestamp      not null DEFAULT CURRENT_TIMESTAMP,
    mensaje             varchar(300)    not null,
    primary key(id)
) 
ENGINE = INNODB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;