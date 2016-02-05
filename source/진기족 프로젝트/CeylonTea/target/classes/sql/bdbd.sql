CREATE SEQUENCE SEQUENCE_NUM; --��� ���� ���� ������ ��ȣ

CREATE TABLE ALLLECTURES 
(
   SEQUENCE_NUM        NUMBER PRIMARY KEY,--������ ��ȣ
   S_CODE              VARCHAR2 (50) NOT NULL,--�����ڵ�
   S_NAME              VARCHAR2 (100) NOT NULL,--�����
   YEAR                NUMBER (4) NOT NULL,--�⵵
   SEMESTER            NUMBER NOT NULL,--�б�
   P_NAME              VARCHAR2 (50) NOT NULL,--�����̸�
   G_UNIT              VARCHAR2 (50) NOT NULL,--��������
   C_DIVISION          VARCHAR2 (50) NOT NULL,--CLASS_DIVISION �й� A N K O G
   S_TYPE              VARCHAR2 (50) NOT NULL,--����(����, ����)
   AVG_EVALUATION      NUMBER default 0,--������ ���
   P_NUMBER            NUMBER,--�������
   AVG_PASSION         NUMBER default 0,--����
   AVG_COMMUNICATION   NUMBER default 0,--����
   AVG_FAIRNESS        NUMBER default 0,--������
   AVG_BENEFIT         NUMBER default 0,--�̵�
   EVAL_COUNT		   NUMBER default 0--���� ��
);

--ȸ�� ���̺�
CREATE TABLE MEMBERS (
	MEMBER_ID VARCHAR2(50) NOT NULL PRIMARY KEY, --���̵�
	MEMBER_PW VARCHAR2(50) NOT NULL,  --��й�ȣ
	MEMBER_NAME VARCHAR2(50) NOT NULL,--�̸�
	MEMBER_EMAIL VARCHAR2(50) NOT NULL --�̸���
);

--������ ���� ���̺�
CREATE TABLE SILECTURES(
	SEQUENCE_NUM NUMBER NOT NULL,
	MEMBER_ID varchar2(50) NOT NULL,
	CONSTRAINT PK_SILECTURES PRIMARY KEY (SEQUENCE_NUM, MEMBER_ID),
	CONSTRAINT FK_SEQUENCE_NUM FOREIGN KEY(SEQUENCE_NUM) REFERENCES ALLLECTURES(SEQUENCE_NUM),
	CONSTRAINT FK_MEMBER_ID FOREIGN KEY(MEMBER_ID) REFERENCES MEMBERS(MEMBER_ID)
);

--������ ���̺�
CREATE TABLE EVLECTURES(
	SEQUENCE_NUM NUMBER NOT NULL , --��簭�� ���� ������ ����
	MEMBER_ID VARCHAR2(50) NOT NULL, -- ȸ�� MEMBER_ID ����
	PASSION         NUMBER,--����
   	COMMUNICATION   NUMBER,--����
   	FAIRNESS        NUMBER,--������
   	BENEFIT         NUMBER,--�̵�
    OPINION VARCHAR2(1000), --���� �����ǰ�
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
insert into evlectures values(96,'papa', 1,1,1,1,'����', sysdate);
insert into evlectures values(102,'papa', 1,1,1,1,'����2', sysdate);
insert into evlectures values(3001,'papa', 1,1,1,1,'����3', sysdate);
insert into evlectures values(3017,'papa', 1,1,1,1,'����4', sysdate);
select * from evlectures;
