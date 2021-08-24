CREATE TABLE IF NOT EXISTS book
(
    ID          BIGINT auto_increment primary key,
    AUTHOR      VARCHAR(255),
    DESCRIPTION VARCHAR(255),
    ISBN        VARCHAR(255),
    TITLE       VARCHAR(255)
);
