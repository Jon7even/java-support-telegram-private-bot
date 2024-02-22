CREATE TABLE IF NOT EXISTS gifts (
    id                INTEGER            NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_gift         VARCHAR(64)        NOT NULL                             ,
    creator_id        BIGINT             NOT NULL                             ,
    status            VARCHAR(16)        NOT NULL                             ,
    CONSTRAINT        PK_GIFT            PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_GIFT      UNIQUE(name_gift)                    ,
    CONSTRAINT        FK_GIFT_TO_USER    FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE
);