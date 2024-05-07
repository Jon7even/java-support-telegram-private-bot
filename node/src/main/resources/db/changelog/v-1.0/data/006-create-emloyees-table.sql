CREATE TABLE IF NOT EXISTS employees (
    id                BIGINT                   NOT NULL GENERATED ALWAYS AS IDENTITY,
    full_name         VARCHAR(128)             NOT NULL                             ,
    added_on          TIMESTAMP                WITHOUT TIME ZONE     NOT NULL       ,
    company_id        BIGINT                   NOT NULL                             ,
    creator_id        BIGINT                   NOT NULL                             ,
    CONSTRAINT        PK_EMPLOYEE              PRIMARY KEY(id)                      ,
    CONSTRAINT        FK_EMPLOYEE_TO_COMPANY   FOREIGN KEY(company_id) REFERENCES companies(id)   ON DELETE CASCADE,
    CONSTRAINT        FK_EMPLOYEE_TO_USER      FOREIGN KEY(creator_id) REFERENCES users(id)   ON DELETE CASCADE
);