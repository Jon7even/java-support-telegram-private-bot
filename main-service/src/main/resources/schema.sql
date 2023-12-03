CREATE TABLE IF NOT EXISTS users (
    id                BIGINT            NOT NULL GENERATED ALWAYS AS IDENTITY,
    chat_id           INTEGER           NOT NULL,
    first_name        VARCHAR(250)      NOT NULL,
    CONSTRAINT        PK_USER           PRIMARY KEY(id),
    CONSTRAINT        UNQ_USER_CHAT_ID  UNIQUE(chat_id)
);
