DROP TABLE IF EXISTS project;

CREATE TABLE project
(
    project_id  BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description LONGTEXT     NOT NULL,
    created_at  timestamp    NOT NULL,
    updated_at  timestamp    NOT NULL,
    CONSTRAINT pk_project PRIMARY KEY (project_id)
);