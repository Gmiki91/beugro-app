CREATE TABLE Users(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
name VARCHAR(255),
password VARCHAR(255)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;