CREATE TABLE IF NOT EXISTS competitors (
    id                BIGINT                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_competitor   VARCHAR(64)            NOT NULL                             ,
    created_on        TIMESTAMP              WITHOUT TIME ZONE     NOT NULL       ,
    creator_id        BIGINT                 NOT NULL                             ,
    CONSTRAINT        PK_COMPETITOR          PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_COMPETITOR    UNIQUE(name_competitor)              ,
    CONSTRAINT        FK_COMPETITOR_TO_USER  FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE
);