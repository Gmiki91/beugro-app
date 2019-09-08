CREATE TABLE Products(
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
name VARCHAR(128),
SKU VARCHAR(8),
qty int unsigned,
status bit,
created_at Timestamp
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;
