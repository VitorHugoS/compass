CREATE TABLE IF NOT EXISTS product(
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(15,4) NOT NULL
);