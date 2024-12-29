CREATE TABLE IF NOT EXISTS companies (
    id                BIGINT               NOT NULL GENERATED ALWAYS AS IDENTITY,
    name_company      VARCHAR(64)          NOT NULL                             ,
    total_sum         BIGINT               NOT NULL                             ,
    gift_id           BIGINT               NOT NULL                             ,
    created_on        TIMESTAMP            WITHOUT TIME ZONE     NOT NULL       ,
    updated_on        TIMESTAMP            WITHOUT TIME ZONE                    ,
    is_given          BOOLEAN              NOT NULL                             ,
    given_on          TIMESTAMP            WITHOUT TIME ZONE                    ,
    creator_id        BIGINT               NOT NULL                             ,
    CONSTRAINT        PK_COMPANY           PRIMARY KEY(id)                      ,
    CONSTRAINT        UNQ_NAME_COMPANY     UNIQUE(name_company)                 ,
    CONSTRAINT        FK_COMPANY_TO_USER   FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE,
    CONSTRAINT        FK_COMPANY_TO_GIFT   FOREIGN KEY(gift_id) REFERENCES gifts(id)   ON DELETE CASCADE
);