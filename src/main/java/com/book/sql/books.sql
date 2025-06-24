CREATE TABLE IF NOT EXISTS books
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    author VARCHAR
(
    255
) NOT NULL,
    publication_date DATE NOT NULL,
    isbn VARCHAR
(
    20
) NOT NULL UNIQUE,
    quantity INT NOT NULL
    category VARCHAR
(
    50
) NOT NULL
    );