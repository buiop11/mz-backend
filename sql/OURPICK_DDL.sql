-- ============================================
-- 회원 (MEMBER)
-- ============================================
CREATE TABLE `MEMBER` (
  `MEMBER_SEQ`                  INT            NOT NULL AUTO_INCREMENT                 COMMENT '회원_시퀀스',
  `MEMBER_TYPE_CD`              VARCHAR(20)    NOT NULL                                COMMENT '회원_유형_코드',
  `ID`                          VARCHAR(50)    NOT NULL                                COMMENT '아이디',
  `CI`                          VARCHAR(255)   NULL                                    COMMENT '연계정보_CI',
  `DI`                          VARCHAR(255)   NULL                                    COMMENT '중복가입확인정보_DI',
  `GOOGLE_SUB`                  VARCHAR(255)   NULL                                    COMMENT '구글_고유_식별자',
  `NAME`                        VARCHAR(50)    NOT NULL                                COMMENT '성명',
  `NICKNAME`                    VARCHAR(50)    NULL                                    COMMENT '닉네임',
  `GENDER_CD`                   VARCHAR(10)    NULL                                    COMMENT '성별_코드',
  `BIRTHDAY`                    VARCHAR(10)    NULL                                    COMMENT '생년월일',
  `INTRODUCTION`                TEXT           NULL                                    COMMENT '자기소개',
  `MOBILE_PHONE_NO`             VARCHAR(20)    NULL                                    COMMENT '휴대전화번호',
  `EMAIL`                       VARCHAR(100)   NULL                                    COMMENT '이메일',
  `TELECOM_SECTION_CD`          VARCHAR(10)    NULL                                    COMMENT '통신사_구분_코드',
  `NATIVE_SECTION_CD`           VARCHAR(10)    NULL                                    COMMENT '내외국인_구분_코드',
  `PASSWORD`                    VARCHAR(255)   NOT NULL                                COMMENT '비밀번호',
  `PASSWORD_CHANGE_DT`          DATETIME(3)    NULL                                    COMMENT '비밀번호_변경_일시',
  `LOGIN_FAILURE_COUNT`         INT            NOT NULL DEFAULT 0                      COMMENT '로그인_실패_횟수',
  `AUTO_LOGIN_YN`               TINYINT(1)     NOT NULL DEFAULT 0                      COMMENT '자동_로그인_여부',
  `MEMBER_STATUS_CD`            VARCHAR(20)    NOT NULL DEFAULT 'NORMAL'               COMMENT '회원_상태_코드',
  `MEMBER_STATUS_CHANGE_DT`     DATETIME(3)    NULL                                    COMMENT '회원_상태_변경_일시',
  `JOIN_DT`                     DATETIME(3)    NOT NULL                                COMMENT '가입_일시',
  `RECENT_LOGIN_DT`             DATETIME(3)    NULL                                    COMMENT '최근_로그인_일시',
  `RECENT_CONNECTION_DT`        DATETIME(3)    NULL                                    COMMENT '최근_접속_일시',
  `SERVICE_TERMS_AGREE_YN`      TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '서비스_이용약관_동의_여부',
  `SERVICE_TERMS_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '서비스_이용약관_동의_일시',
  `PERSONAL_INFO_USE_AGREE_YN`  TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '개인정보_이용_동의_여부',
  `PERSONAL_INFO_USE_AGREE_DT`  DATETIME(3)    NULL                                    COMMENT '개인정보_이용_동의_일시',
  `MARKETING_USE_AGREE_YN`      TINYINT(1)     NOT NULL DEFAULT 0                      COMMENT '마케팅_활용_동의_여부',
  `MARKETING_USE_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '마케팅_활용_동의_일시',
  `USE_YN`                      TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`                INT            NULL                                    COMMENT '등록자_시퀀스',
  `REGISTER_IP`                 VARCHAR(45)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`             DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`                 INT            NULL                                    COMMENT '수정자_시퀀스',
  `UPDATER_IP`                  VARCHAR(45)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`                   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`MEMBER_SEQ`),
  UNIQUE KEY `UK_MEMBER_ID` (`ID`)
) COMMENT='회원';

-- ============================================
-- 휴면 회원 (DORMANCY_MEMBER)
-- ============================================
CREATE TABLE `DORMANCY_MEMBER` (
  `MEMBER_SEQ`                  BIGINT         NOT NULL                                COMMENT '회원_시퀀스',
  `MEMBER_TYPE_CD`              VARCHAR(20)    NULL                                    COMMENT '회원_유형_코드',
  `ID`                          VARCHAR(500)   NULL                                    COMMENT '아이디_암호화',
  `CI`                          VARCHAR(500)   NULL                                    COMMENT '연계정보_CI',
  `DI`                          VARCHAR(500)   NULL                                    COMMENT '중복가입확인정보_DI',
  `GOOGLE_SUB`                  VARCHAR(255)   NULL                                    COMMENT '구글_고유_식별자',
  `NAME`                        VARCHAR(100)   NULL                                    COMMENT '성명',
  `NICKNAME`                    VARCHAR(100)   NULL                                    COMMENT '닉네임',
  `GENDER_CD`                   VARCHAR(10)    NULL                                    COMMENT '성별_코드',
  `BIRTHDAY`                    VARCHAR(20)    NULL                                    COMMENT '생년월일',
  `INTRODUCTION`                VARCHAR(500)   NULL                                    COMMENT '자기소개',
  `MOBILE_PHONE_NO`             VARCHAR(500)   NULL                                    COMMENT '휴대전화번호_암호화',
  `EMAIL`                       VARCHAR(500)   NULL                                    COMMENT '이메일_암호화',
  `TELECOM_SECTION_CD`          VARCHAR(10)    NULL                                    COMMENT '통신사_구분_코드',
  `NATIVE_SECTION_CD`           VARCHAR(10)    NULL                                    COMMENT '내외국인_구분_코드',
  `PASSWORD`                    VARCHAR(255)   NULL                                    COMMENT '비밀번호',
  `PASSWORD_CHANGE_DT`          DATETIME(3)    NULL                                    COMMENT '비밀번호_변경_일시',
  `LOGIN_FAILURE_COUNT`         INT            NOT NULL DEFAULT 0                      COMMENT '로그인_실패_횟수',
  `AUTO_LOGIN_YN`               TINYINT(1)     NOT NULL DEFAULT 0                      COMMENT '자동_로그인_여부',
  `MEMBER_STATUS_CD`            VARCHAR(20)    NULL                                    COMMENT '회원_상태_코드',
  `MEMBER_STATUS_CHANGE_DT`     DATETIME(3)    NULL                                    COMMENT '회원_상태_변경_일시',
  `JOIN_DT`                     DATETIME(3)    NULL                                    COMMENT '가입_일시',
  `RECENT_LOGIN_DT`             DATETIME(3)    NULL                                    COMMENT '최근_로그인_일시',
  `RECENT_CONNECTION_DT`        DATETIME(3)    NULL                                    COMMENT '최근_접속_일시',
  `APP_VERSION`                 VARCHAR(50)    NULL                                    COMMENT '앱_버전',
  `OS_VERSION`                  VARCHAR(50)    NULL                                    COMMENT 'OS_버전',
  `OS_SECTION_CD`               VARCHAR(10)    NULL                                    COMMENT 'OS_구분_코드',
  `DEVICE_MODEL_NAME`           VARCHAR(100)   NULL                                    COMMENT '기기_모델명',
  `DEVICE_MARKETING_NO`         VARCHAR(100)   NULL                                    COMMENT '기기_마케팅_번호',
  `APP_PUSH_VALUE`              VARCHAR(500)   NULL                                    COMMENT '앱_푸시_값',
  `SERVICE_TERMS_AGREE_YN`      TINYINT(1)     NULL                                    COMMENT '서비스_이용약관_동의_여부',
  `SERVICE_TERMS_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '서비스_이용약관_동의_일시',
  `PERSONAL_INFO_USE_AGREE_YN`  TINYINT(1)     NULL                                    COMMENT '개인정보_이용_동의_여부',
  `PERSONAL_INFO_USE_AGREE_DT`  DATETIME(3)    NULL                                    COMMENT '개인정보_이용_동의_일시',
  `MARKETING_USE_AGREE_YN`      TINYINT(1)     NULL                                    COMMENT '마케팅_활용_동의_여부',
  `MARKETING_USE_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '마케팅_활용_동의_일시',
  `DORMANCY_CONVERSION_DT`      DATETIME(3)    NULL                                    COMMENT '휴면_전환_일시',
  `USE_YN`                      TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`                BIGINT         NULL                                    COMMENT '등록자_시퀀스',
  `REGISTER_IP`                 VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`             DATETIME(3)    NULL                                    COMMENT '등록_일시',
  `UPDATER_SEQ`                 BIGINT         NULL                                    COMMENT '수정자_시퀀스',
  `UPDATER_IP`                  VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`                   DATETIME(3)    NULL                                    COMMENT '수정_일시',
  PRIMARY KEY (`MEMBER_SEQ`),
  KEY `IDX_DORMANCY_MEMBER_GOOGLE_SUB` (`GOOGLE_SUB`),
  KEY `IDX_DORMANCY_MEMBER_ID` (`ID`),
  KEY `IDX_DORMANCY_MEMBER_DI` (`DI`)
) COMMENT='휴면_회원';

-- ============================================
-- 탈퇴 회원 (SECESSION_MEMBER)
-- ============================================
CREATE TABLE `SECESSION_MEMBER` (
  `MEMBER_SEQ`                  BIGINT         NOT NULL                                COMMENT '회원_시퀀스',
  `MEMBER_TYPE_CD`              VARCHAR(20)    NULL                                    COMMENT '회원_유형_코드',
  `ID`                          VARCHAR(500)   NULL                                    COMMENT '아이디',
  `CI`                          VARCHAR(500)   NULL                                    COMMENT '연계정보_CI',
  `DI`                          VARCHAR(500)   NULL                                    COMMENT '중복가입확인정보_DI',
  `GOOGLE_SUB`                  VARCHAR(255)   NULL                                    COMMENT '구글_고유_식별자',
  `NAME`                        VARCHAR(100)   NULL                                    COMMENT '성명',
  `NICKNAME`                    VARCHAR(100)   NULL                                    COMMENT '닉네임',
  `GENDER_CD`                   VARCHAR(10)    NULL                                    COMMENT '성별_코드',
  `BIRTHDAY`                    VARCHAR(20)    NULL                                    COMMENT '생년월일',
  `INTRODUCTION`                VARCHAR(500)   NULL                                    COMMENT '자기소개',
  `MOBILE_PHONE_NO`             VARCHAR(500)   NULL                                    COMMENT '휴대전화번호',
  `EMAIL`                       VARCHAR(500)   NULL                                    COMMENT '이메일',
  `TELECOM_SECTION_CD`          VARCHAR(10)    NULL                                    COMMENT '통신사_구분_코드',
  `NATIVE_SECTION_CD`           VARCHAR(10)    NULL                                    COMMENT '내외국인_구분_코드',
  `PASSWORD`                    VARCHAR(255)   NULL                                    COMMENT '비밀번호',
  `PASSWORD_CHANGE_DT`          DATETIME(3)    NULL                                    COMMENT '비밀번호_변경_일시',
  `LOGIN_FAILURE_COUNT`         INT            NOT NULL DEFAULT 0                      COMMENT '로그인_실패_횟수',
  `AUTO_LOGIN_YN`               TINYINT(1)     NOT NULL DEFAULT 0                      COMMENT '자동_로그인_여부',
  `MEMBER_STATUS_CD`            VARCHAR(20)    NULL                                    COMMENT '회원_상태_코드',
  `MEMBER_STATUS_CHANGE_DT`     DATETIME(3)    NULL                                    COMMENT '회원_상태_변경_일시',
  `JOIN_DT`                     DATETIME(3)    NULL                                    COMMENT '가입_일시',
  `RECENT_LOGIN_DT`             DATETIME(3)    NULL                                    COMMENT '최근_로그인_일시',
  `RECENT_CONNECTION_DT`        DATETIME(3)    NULL                                    COMMENT '최근_접속_일시',
  `APP_VERSION`                 VARCHAR(50)    NULL                                    COMMENT '앱_버전',
  `OS_VERSION`                  VARCHAR(50)    NULL                                    COMMENT 'OS_버전',
  `OS_SECTION_CD`               VARCHAR(10)    NULL                                    COMMENT 'OS_구분_코드',
  `DEVICE_MODEL_NAME`           VARCHAR(100)   NULL                                    COMMENT '기기_모델명',
  `DEVICE_MARKETING_NO`         VARCHAR(100)   NULL                                    COMMENT '기기_마케팅_번호',
  `APP_PUSH_VALUE`              VARCHAR(500)   NULL                                    COMMENT '앱_푸시_값',
  `SERVICE_TERMS_AGREE_YN`      TINYINT(1)     NULL                                    COMMENT '서비스_이용약관_동의_여부',
  `SERVICE_TERMS_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '서비스_이용약관_동의_일시',
  `PERSONAL_INFO_USE_AGREE_YN`  TINYINT(1)     NULL                                    COMMENT '개인정보_이용_동의_여부',
  `PERSONAL_INFO_USE_AGREE_DT`  DATETIME(3)    NULL                                    COMMENT '개인정보_이용_동의_일시',
  `MARKETING_USE_AGREE_YN`      TINYINT(1)     NULL                                    COMMENT '마케팅_활용_동의_여부',
  `MARKETING_USE_AGREE_DT`      DATETIME(3)    NULL                                    COMMENT '마케팅_활용_동의_일시',
  `SECESSION_DT`                DATETIME(3)    NULL                                    COMMENT '탈퇴_일시',
  `USE_YN`                      TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`                BIGINT         NULL                                    COMMENT '등록자_시퀀스',
  `REGISTER_IP`                 VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`             DATETIME(3)    NULL                                    COMMENT '등록_일시',
  `UPDATER_SEQ`                 BIGINT         NULL                                    COMMENT '수정자_시퀀스',
  `UPDATER_IP`                  VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`                   DATETIME(3)    NULL                                    COMMENT '수정_일시',
  PRIMARY KEY (`MEMBER_SEQ`),
  KEY `IDX_SECESSION_MEMBER_ID` (`ID`),
  KEY `IDX_SECESSION_MEMBER_DI` (`DI`),
  KEY `IDX_SECESSION_MEMBER_MOBILE` (`MOBILE_PHONE_NO`)
) COMMENT='탈퇴_회원';

-- ============================================
-- 카테고리 (CATEGORY)
-- ============================================
CREATE TABLE `CATEGORY` (
  `CATEGORY_SEQ`     INT            NOT NULL AUTO_INCREMENT                 COMMENT '카테고리_시퀀스',
  `MEMBER_SEQ`      INT            NOT NULL                                COMMENT '회원_시퀀스',
  `NAME`             VARCHAR(50)    NOT NULL                                COMMENT '카테고리명',
  `EMOJI`            VARCHAR(20)    NULL                                    COMMENT '이모지',
  `USE_YN`           TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`     INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`      VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`  DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`      INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`       VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`        DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`CATEGORY_SEQ`),
  KEY `IDX_CATEGORY_MEMBER_USE` (`MEMBER_SEQ`, `USE_YN`)
) COMMENT='카테고리';

-- ============================================
-- 토픽 안건 (TOPIC)
-- ============================================
CREATE TABLE `TOPIC` (
  `TOPIC_SEQ`         INT            NOT NULL AUTO_INCREMENT                 COMMENT '토픽_시퀀스',
  `CATEGORY_SEQ`      INT            NOT NULL                                COMMENT '카테고리_시퀀스',
  `EMOJI`             VARCHAR(20)    NULL                                    COMMENT '이모지',
  `TITLE`             VARCHAR(200)   NOT NULL                                COMMENT '제목',
  `STATUS`            VARCHAR(20)    NOT NULL DEFAULT 'VOTING'               COMMENT '상태',
  `CANDIDATE_SEQ`     INT            NULL                                    COMMENT '선정_후보_시퀀스',
  `GOOGLE_EVENT_ID`   VARCHAR(255)   NULL                                    COMMENT '구글_이벤트_ID',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`TOPIC_SEQ`)
) COMMENT='토픽_안건';

-- ============================================
-- 토픽 참여 회원 (TOPIC_MEMBER)
-- ============================================
CREATE TABLE `TOPIC_MEMBER` (
  `TOPIC_MEMBER_SEQ`  INT            NOT NULL AUTO_INCREMENT                 COMMENT '토픽_참여_시퀀스',
  `TOPIC_SEQ`         INT            NOT NULL                                COMMENT '토픽_시퀀스',
  `MEMBER_SEQ`        INT            NOT NULL                                COMMENT '회원_시퀀스',
  `ROLE_TYPE`         VARCHAR(20)    NOT NULL DEFAULT 'PARTICIPANT'          COMMENT '참여_역할',
  `JOIN_DT`           DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '참여_일시',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`TOPIC_MEMBER_SEQ`)
) COMMENT='토픽_참여_회원';

-- ============================================
-- 안건 후보 (CANDIDATE)
-- ============================================
CREATE TABLE `CANDIDATE` (
  `CANDIDATE_SEQ`     INT            NOT NULL AUTO_INCREMENT                 COMMENT '후보_시퀀스',
  `TOPIC_SEQ`         INT            NOT NULL                                COMMENT '토픽_시퀀스',
  `MEMBER_SEQ`        INT            NULL                                    COMMENT '회원_시퀀스',
  `NAME`              VARCHAR(200)   NOT NULL                                COMMENT '후보명',
  `PRICE`             INT            NOT NULL DEFAULT 0                      COMMENT '가격',
  `PICK_DATE`         DATETIME(3)    NULL                                    COMMENT '일자',
  `INFO`              VARCHAR(500)   NULL                                    COMMENT '후보_정보',
  `IMAGE_URL`         VARCHAR(555)   NULL                                    COMMENT '이미지_URL',
  `LINK_URL`          TEXT           NULL                                    COMMENT '링크_URL',
  `IS_FIXED`          TINYINT(1)     NOT NULL DEFAULT 0                      COMMENT '고정_여부',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`CANDIDATE_SEQ`)
) COMMENT='안건_후보';

-- ============================================
-- 댓글 (COMMENT)
-- ============================================
CREATE TABLE `COMMENT` (
  `COMMENT_SEQ`       INT            NOT NULL AUTO_INCREMENT                 COMMENT '댓글_시퀀스',
  `CANDIDATE_SEQ`     INT            NOT NULL                                COMMENT '후보_시퀀스',
  `MEMBER_SEQ`        INT            NULL                                    COMMENT '회원_시퀀스',
  `CONTENT`           TEXT           NOT NULL                                COMMENT '댓글_내용',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      BIGINT         NULL                                    COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(45)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       BIGINT         NULL                                    COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(45)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`COMMENT_SEQ`),
  KEY `IDX_COMMENT_CANDIDATE` (`CANDIDATE_SEQ`)
) COMMENT='댓글';

-- ============================================
-- 투표 (VOTE)
-- ============================================
CREATE TABLE `VOTE` (
  `VOTE_SEQ`          INT            NOT NULL AUTO_INCREMENT                 COMMENT '투표_시퀀스',
  `TOPIC_SEQ`         INT            NOT NULL                                COMMENT '토픽_시퀀스',
  `CANDIDATE_SEQ`     INT            NULL                                    COMMENT '후보_시퀀스',
  `MEMBER_SEQ`        INT            NOT NULL                                COMMENT '회원_시퀀스',
  `COMMENT`           VARCHAR(1000)  NULL                                    COMMENT '투표_코멘트',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`VOTE_SEQ`)
) COMMENT='투표';

-- ============================================
-- 외부 캘린더 (EXCALENDAR) — 기능 미구현
-- ============================================
CREATE TABLE `EXCALENDAR` (
  `CALENDAR_SEQ`      INT            NOT NULL AUTO_INCREMENT                 COMMENT '캘린더_시퀀스',
  `USER_SEQ`          INT            NOT NULL                                COMMENT '회원_시퀀스',
  `GOOGLE_CAL_ID`     VARCHAR(255)   NOT NULL                                COMMENT '구글_캘린더_ID',
  `ACCESS_TOKEN`      TEXT           NOT NULL                                COMMENT '액세스_토큰',
  `REFRESH_TOKEN`     TEXT           NOT NULL                                COMMENT '리프레시_토큰',
  `USE_YN`            TINYINT(1)     NOT NULL DEFAULT 1                      COMMENT '사용_여부',
  `REGISTER_SEQ`      INT            NOT NULL                                COMMENT '등록자_시퀀스',
  `REGISTER_IP`       VARCHAR(50)    NULL                                    COMMENT '등록자_IP',
  `REGISTRATION_DT`   DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3)   COMMENT '등록_일시',
  `UPDATER_SEQ`       INT            NOT NULL                                COMMENT '수정자_시퀀스',
  `UPDATER_IP`        VARCHAR(50)    NULL                                    COMMENT '수정자_IP',
  `UPDATE_DT`         DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '수정_일시',
  PRIMARY KEY (`CALENDAR_SEQ`)
) COMMENT='외부_캘린더';

