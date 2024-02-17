CREATE TABLE IF NOT EXISTS users (
    id                BIGINT            NOT NULL GENERATED ALWAYS AS IDENTITY,
    chat_id           BIGINT            NOT NULL                             ,
    first_name        VARCHAR(255)                                           ,
    last_name         VARCHAR(255)                                           ,
    user_name         VARCHAR(255)                                           ,
    register_on       TIMESTAMP         WITHOUT TIME ZONE     NOT NULL       ,
    auth_on           BOOLEAN           DEFAULT FALSE                        ,
    CONSTRAINT        PK_USER           PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_USER_CHAT_ID  UNIQUE(chat_id)
);

CREATE TABLE IF NOT EXISTS companies (
    id                BIGINT               NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_company      VARCHAR(64)          NOT NULL                             ,
    total_sum         INTEGER              NOT NULL                             ,
    gift_id           INTEGER              NOT NULL                             ,
    created_on        TIMESTAMP            WITHOUT TIME ZONE     NOT NULL       ,
    updated_on        TIMESTAMP            WITHOUT TIME ZONE                    ,
    is_given          BOOLEAN              NOT NULL                             ,
    given_on          TIMESTAMP            WITHOUT TIME ZONE                    ,
    creator_id        BIGINT               NOT NULL                             ,
    CONSTRAINT        PK_COMPANY           PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_COMPANY     UNIQUE(name_company)                 ,
    CONSTRAINT        FK_COMPANY_TO_USER   FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE,
    CONSTRAINT        FK_COMPANY_TO_GIFT   FOREIGN KEY(gift_id) REFERENCES users(id)   ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS gifts (
    id                INTEGER            NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_gift         VARCHAR(64)        NOT NULL                             ,
    creator_id        BIGINT             NOT NULL                             ,
    status            VARCHAR(16)        NOT NULL                             ,
    CONSTRAINT        PK_GIFT            PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_GIFT      UNIQUE(name_gift)                    ,
    CONSTRAINT        FK_GIFT_TO_USER    FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE
);