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

CREATE TABLE IF NOT EXISTS gifts (
    id                BIGINT            NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_company      VARCHAR(50)       NOT NULL,
    total_sum         INTEGER           NOT NULL,
    gift              VARCHAR(50)       NOT NULL,
    created_on        TIMESTAMP         WITHOUT TIME ZONE     NOT NULL,
    updated_on        TIMESTAMP         WITHOUT TIME ZONE             ,
    is_given          BOOLEAN           NOT NULL                      ,
    given_on          TIMESTAMP         WITHOUT TIME ZONE             ,
    CONSTRAINT        PK_GIFTS          PRIMARY KEY(id),
    CONSTRAINT        UNQ_NAME_COMPANY  UNIQUE(name_company)
);