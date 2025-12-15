CREATE SEQUENCE IF NOT EXISTS playlists_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS playlists (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('playlists_seq'),
    name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    thumbnail_path VARCHAR(255) NOT NULL
);

ALTER TABLE medias ADD COLUMN IF NOT EXISTS playlist_id BIGINT;

ALTER TABLE medias
    ADD CONSTRAINT fk_medias_playlists
    FOREIGN KEY (playlist_id)
    REFERENCES playlists (id);