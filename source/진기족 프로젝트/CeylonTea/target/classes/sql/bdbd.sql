CREATE SEQUENCE SEQUENCE_NUM; --모든 강의 정보 시퀀스 번호

CREATE TABLE ALLLECTURES 
(
   SEQUENCE_NUM        NUMBER PRIMARY KEY,--시퀀스 번호
   S_CODE              VARCHAR2 (50) NOT NULL,--과목코드
   S_NAME              VARCHAR2 (100) NOT NULL,--과목명
   YEAR                NUMBER (4) NOT NULL,--년도
   SEMESTER            NUMBER NOT NULL,--학기
   P_NAME              VARCHAR2 (50) NOT NULL,--교수이름
   G_UNIT              VARCHAR2 (50) NOT NULL,--단위학점
   C_DIVISION          VARCHAR2 (50) NOT NULL,--CLASS_DIVISION 분반 A N K O G
   S_TYPE              VARCHAR2 (50) NOT NULL,--구분(전공, 교양)
   AVG_EVALUATION      NUMBER default 0,--종정시 평균
   P_NUMBER            NUMBER,--교수사번
   AVG_PASSION         NUMBER default 0,--열정
   AVG_COMMUNICATION   NUMBER default 0,--소통
   AVG_FAIRNESS        NUMBER default 0,--공정성
   AVG_BENEFIT         NUMBER default 0,--이득
   EVAL_COUNT		   NUMBER default 0--평가한 수
);

--회원 테이블
CREATE TABLE MEMBERS (
	MEMBER_ID VARCHAR2(50) NOT NULL PRIMARY KEY, --아이디
	MEMBER_PW VARCHAR2(50) NOT NULL,  --비밀번호
	MEMBER_NAME VARCHAR2(50) NOT NULL,--이름
	MEMBER_EMAIL VARCHAR2(50) NOT NULL --이메일
);

--수강한 강의 테이블
CREATE TABLE SILECTURES(
	SEQUENCE_NUM NUMBER NOT NULL,
	MEMBER_ID varchar2(50) NOT NULL,
	CONSTRAINT PK_SILECTURES PRIMARY KEY (SEQUENCE_NUM, MEMBER_ID),
	CONSTRAINT FK_SEQUENCE_NUM FOREIGN KEY(SEQUENCE_NUM) REFERENCES ALLLECTURES(SEQUENCE_NUM),
	CONSTRAINT FK_MEMBER_ID FOREIGN KEY(MEMBER_ID) REFERENCES MEMBERS(MEMBER_ID)
);

--강의평가 테이블
CREATE TABLE EVLECTURES(
	SEQUENCE_NUM NUMBER NOT NULL , --모든강의 정보 시퀀스 참조
	MEMBER_ID VARCHAR2(50) NOT NULL, -- 회원 MEMBER_ID 참조
	PASSION         NUMBER,--열정
   	COMMUNICATION   NUMBER,--소통
   	FAIRNESS        NUMBER,--공정성
   	BENEFIT         NUMBER,--이득
    OPINION VARCHAR2(1000), --종합 개인의견
    WRITE_DATE DATE,
    CONSTRAINT PK_EVLECTURES PRIMARY KEY (SEQUENCE_NUM, MEMBER_ID),
	CONSTRAINT FK_SEQUENCE_NUM2 FOREIGN KEY(SEQUENCE_NUM) REFERENCES ALLLECTURES(SEQUENCE_NUM),
	CONSTRAINT FK_MEMBER_ID2 FOREIGN KEY(MEMBER_ID) REFERENCES MEMBERS(MEMBER_ID)
);

select * from tab;

select * from members;

--delete from members;

select * from alllectures order by sequence_num desc;
select * from alllectures;
select * from evlectures;
select * from silectures;
select * from members;

select * from evlectures where sequence_num in( select sequence_num from silectures where member_id='papa');
select sequence_num from silectures where member_id='papa';

DROP TABLE SILECTURES;
DROP TABLE EVLECTURES;
DROP TABLE ALLLECTURES;
DROP TABLE MEMBERS;
DROP SEQUENCE SEQUENCE_NUM;
--SELECT * FROM TAB;
--SELECT * FROM USER_SEQUENCES;

select * from alllectures;
insert into evlectures values(96,'papa', 1,1,1,1,'굳굳', sysdate);
insert into evlectures values(102,'papa', 1,1,1,1,'굳굳2', sysdate);
insert into evlectures values(3001,'papa', 1,1,1,1,'굳굳3', sysdate);
insert into evlectures values(3017,'papa', 1,1,1,1,'굳굳4', sysdate);
select * from evlectures;
