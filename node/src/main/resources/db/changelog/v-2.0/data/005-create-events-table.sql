CREATE TABLE IF NOT EXISTS events (
    id                BIGINT               NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_event        VARCHAR(64)          NOT NULL                             ,
    time_start        TIMESTAMP            WITHOUT TIME ZONE     NOT NULL       ,
    time_end          TIMESTAMP            WITHOUT TIME ZONE     NOT NULL       ,
    status            VARCHAR(32)          NOT NULL                             ,
    creator_id        BIGINT               NOT NULL                             ,
    CONSTRAINT        PK_EVENT             PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_EVENT       UNIQUE(name_event)                   ,
    CONSTRAINT        FK_EVENT_TO_USER     FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE
);