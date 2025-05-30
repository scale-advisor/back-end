DROP TABLE IF EXISTS `COCOMO_SCALE_FACTOR`;

CREATE TABLE `COCOMO_SCALE_FACTOR`
(
    `COCOMO_SCALE_FACTOR_ID` BIGINT      NOT NULL,
    `PROJECT_ID`             BIGINT      NOT NULL,
    `PREC`                   VARCHAR(20) NOT NULL,
    `FLEX`                   VARCHAR(20) NOT NULL,
    `RESL`                   VARCHAR(20) NOT NULL,
    `TEAM`                   VARCHAR(20) NOT NULL,
    `PMAT`                   VARCHAR(20) NOT NULL,
    CONSTRAINT `PK_COCOMO_SCALE_FACTOR` PRIMARY KEY (`COCOMO_SCALE_FACTOR_ID`),
    CONSTRAINT `FK_COCOMO_SCALE_FACTOR_PROJECT_ID`
        FOREIGN KEY (`PROJECT_ID`) REFERENCES `PROJECT` (`PROJECT_ID`) ON DELETE NO ACTION
);
