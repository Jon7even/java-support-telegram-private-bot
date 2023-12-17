CREATE TABLE IF NOT EXISTS users (
    id                BIGINT            NOT NULL GENERATED ALWAYS AS IDENTITY,
    chat_id           BIGINT            NOT NULL,
    first_name        VARCHAR(250)              ,
    last_name         VARCHAR(250)              ,
    user_name         VARCHAR(250)              ,
    register_on       TIMESTAMP         WITHOUT TIME ZONE     NOT NULL,
    CONSTRAINT        PK_USER           PRIMARY KEY(id),
    CONSTRAINT        UNQ_USER_CHAT_ID  UNIQUE(chat_id)
);
