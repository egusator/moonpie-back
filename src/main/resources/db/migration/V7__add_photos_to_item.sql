CREATE TABLE item_photos (
    item_id BIGINT,
    photoURL VARCHAR(255),
    FOREIGN KEY (item_id) REFERENCES item(id)
);