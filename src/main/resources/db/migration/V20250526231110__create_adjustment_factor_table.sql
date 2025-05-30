DROP TABLE IF EXISTS `ADJUSTMENT_FACTOR`;

CREATE TABLE `ADJUSTMENT_FACTOR`
(
    `ADJUSTMENT_FACTOR_ID`    BIGINT       NOT NULL,
    `PROJECT_ID`              BIGINT       NOT NULL,
    `VERSION_MAJOR_NUMBER`    INT          NOT NULL,
    `VERSION_MINOR_NUMBER`    INT          NOT NULL,
    `ADJUSTMENT_FACTOR_TYPE`  VARCHAR(255) NOT NULL,
    `ADJUSTMENT_FACTOR_LEVEL` INT          NOT NULL,
    `CREATED_AT`              TIMESTAMP    NOT NULL,
    `UPDATED_AT`              TIMESTAMP    NOT NULL,

    CONSTRAINT `PK_ADJUSTMENT_FACTOR` PRIMARY KEY (`ADJUSTMENT_FACTOR_ID`),
    CONSTRAINT `FK_ADJUSTMENT_FACTOR_VERSION`
        FOREIGN KEY (`PROJECT_ID`, `VERSION_MAJOR_NUMBER`,`VERSION_MINOR_NUMBER`)
            REFERENCES `VERSION` (`PROJECT_ID`, `VERSION_MAJOR_NUMBER`, `VERSION_MINOR_NUMBER`)
            ON DELETE NO ACTION
)