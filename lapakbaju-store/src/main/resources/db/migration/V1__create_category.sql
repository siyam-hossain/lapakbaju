CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    icon VARCHAR(255),
    bg VARCHAR(255),
    color VARCHAR(255),
    default_active TINYINT(1) DEFAULT 0
);


