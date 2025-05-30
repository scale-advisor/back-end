CREATE TABLE `REQUIREMENT`
(
    `REQUIREMENT_ID`            BIGINT       NOT NULL,
    `PROJECT_ID`                BIGINT       NOT NULL,
    `VERSION_MAJOR_NUMBER`      INT          NOT NULL,
    `VERSION_MINOR_NUMBER`      INT          NOT NULL,
    `REQUIREMENT_NUMBER`        INT          NOT NULL,
    `REQUIREMENT_NAME`          VARCHAR(255) NOT NULL,
    `REQUIREMENT_DETAIL_NUMBER` INT          NOT NULL,
    `REQUIREMENT_DETAIL`        TEXT,
    `REQUIREMENT_TYPE`          VARCHAR(255) NOT NULL,
    `CREATED_AT`                TIMESTAMP    NOT NULL,
    `UPDATED_AT`                TIMESTAMP    NOT NULL,

    CONSTRAINT `PK_REQUIREMENT` PRIMARY KEY (`REQUIREMENT_ID`),
    CONSTRAINT `FK_REQUIREMENT_PROJECT_ID`
        FOREIGN KEY (`PROJECT_ID`) REFERENCES `PROJECT` (`PROJECT_ID`) ON DELETE NO ACTION,
    CONSTRAINT `FK_REQUIREMENT_VERSION`
        FOREIGN KEY (`PROJECT_ID`, `VERSION_MAJOR_NUMBER`, `VERSION_MINOR_NUMBER`) REFERENCES `VERSION` (`PROJECT_ID`, `VERSION_MAJOR_NUMBER`, `VERSION_MINOR_NUMBER`) ON DELETE NO ACTION
);