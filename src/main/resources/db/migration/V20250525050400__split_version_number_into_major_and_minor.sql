/*********************************************************************
 * V2__split_version_number_to_major_minor.sql
 * - VERSION.VERSION_NUMBER(varchar) → MAJOR_NUMBER(int) + MINOR_NUMBER(int)
 * - FILE.VERSION_NUMBER(varchar)   → MAJOR_NUMBER(int) + MINOR_NUMBER(int)
 * - 모든 제약조건(UK/PK/FK) 안전하게 재구성
 *********************************************************************/

-- 0) FK-CHECKS 비활성화 (필수)
SET @OLD_FK_CHECK = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;

/*-------------------------------------------------------------------
 | 1. 새 컬럼 추가 및 데이터 이행
 *------------------------------------------------------------------*/
ALTER TABLE VERSION
    ADD COLUMN MAJOR_NUMBER INT NULL AFTER PROJECT_ID,
    ADD COLUMN MINOR_NUMBER INT NULL AFTER MAJOR_NUMBER;

UPDATE VERSION
SET MAJOR_NUMBER = CAST(SUBSTRING_INDEX(VERSION_NUMBER, '.',  1) AS UNSIGNED),
    MINOR_NUMBER = CAST(SUBSTRING_INDEX(VERSION_NUMBER, '.', -1) AS UNSIGNED)
WHERE VERSION_NUMBER IS NOT NULL ;

ALTER TABLE FILE
    ADD COLUMN MAJOR_NUMBER INT NULL AFTER PROJECT_ID,
    ADD COLUMN MINOR_NUMBER INT NULL AFTER MAJOR_NUMBER;

UPDATE FILE  f
    JOIN   VERSION v
    ON f.PROJECT_ID     = v.PROJECT_ID
        AND f.VERSION_NUMBER = v.VERSION_NUMBER
SET f.MAJOR_NUMBER = v.MAJOR_NUMBER,
    f.MINOR_NUMBER = v.MINOR_NUMBER;

/*-------------------------------------------------------------------
 | 2. 기존 FK·UK 제거 (VERSION_NUMBER 참조하는 것만)
 *------------------------------------------------------------------*/
ALTER TABLE FILE
    DROP FOREIGN KEY FK_FILE_VERSION;
ALTER TABLE FILE
    DROP FOREIGN KEY FK_FILE_PROJECT_ID;
ALTER TABLE FILE
    DROP INDEX UK_FILE_PROJECT_VERSION;   -- unique (PROJECT_ID, VERSION_NUMBER)

/*-------------------------------------------------------------------
 | 3. VERSION 테이블 : PK 재정의, 문자열 컬럼 제거
 *------------------------------------------------------------------*/
ALTER TABLE VERSION
    DROP FOREIGN KEY FK_VERSION_PROJECT_ID;
ALTER TABLE VERSION
    DROP PRIMARY KEY;

ALTER TABLE VERSION
    MODIFY MAJOR_NUMBER INT NOT NULL,
    MODIFY MINOR_NUMBER INT NOT NULL,
    DROP  COLUMN VERSION_NUMBER,
    ADD   CONSTRAINT PK_VERSION
        PRIMARY KEY (PROJECT_ID, MAJOR_NUMBER, MINOR_NUMBER),
    ADD   CONSTRAINT FK_VERSION_PROJECT_ID
        FOREIGN KEY (PROJECT_ID)
            REFERENCES PROJECT (PROJECT_ID);

/*-------------------------------------------------------------------
 | 4. FILE 테이블 : 문자열 컬럼 제거, 새 UK·FK 생성
 *------------------------------------------------------------------*/
ALTER TABLE FILE
    MODIFY MAJOR_NUMBER INT NOT NULL,
    MODIFY MINOR_NUMBER INT NOT NULL,
    DROP  COLUMN VERSION_NUMBER,
    ADD   CONSTRAINT UK_FILE_PROJECT_VERSION
        UNIQUE (PROJECT_ID, MAJOR_NUMBER, MINOR_NUMBER),
    ADD   CONSTRAINT FK_FILE_PROJECT_ID
        FOREIGN KEY (PROJECT_ID)
            REFERENCES PROJECT (PROJECT_ID),
    ADD   CONSTRAINT FK_FILE_VERSION
        FOREIGN KEY (PROJECT_ID, MAJOR_NUMBER, MINOR_NUMBER)
            REFERENCES VERSION (PROJECT_ID, MAJOR_NUMBER, MINOR_NUMBER);

/*-------------------------------------------------------------------
 | 5. FK-CHECKS 복원
 *------------------------------------------------------------------*/
SET FOREIGN_KEY_CHECKS = @OLD_FK_CHECK;