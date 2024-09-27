CREATE TABLE saved_item (
    client_id BIGINT,
    item_id BIGINT,
    PRIMARY KEY (client_id, item_id),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);
