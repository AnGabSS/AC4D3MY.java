CREATE SEQUENCE IF NOT EXISTS medias_seq start 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS medias(
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('medias_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    CONSTRAINT type_check CHECK (type in ("VIDEO", "IMAGE"))
);