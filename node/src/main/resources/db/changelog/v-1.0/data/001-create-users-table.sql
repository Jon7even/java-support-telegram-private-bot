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