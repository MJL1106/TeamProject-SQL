CREATE TABLE menu(
    item_id INTEGER NOT NULL,
    selection VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    allergen_ids TEXT[],
    in_stock BOOLEAN NOT NULL,
    fillings VARCHAR,
    allergies VARCHAR,
    calories VARCHAR,
    type VARCHAR,
    PRIMARY KEY (item_id)
);

CREATE TABLE allergens(
    allergen_id INTEGER NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY(allergen_id)
);

CREATE TABLE orders(
    cust_id INTEGER NOT NULL,
    items TEXT[],
    status VARCHAR,
    time VARCHAR,
    tableNum INTEGER,
    PRIMARY KEY (cust_id)
);

CREATE TABLE tables(
    table_num INTEGER,
    status VARCHAR,
    help VARCHAR,
    paid VARCHAR,
    PRIMARY KEY (table_num)
);
