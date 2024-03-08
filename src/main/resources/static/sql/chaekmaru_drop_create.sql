-- DROP TABLE
DROP TABLE TBL_MEMBER;
DROP TABLE TBL_ADMIN;
DROP TABLE TBL_BOOK;
DROP TABLE TBL_REVIEW;
DROP TABLE TBL_POINT_LIST;
DROP TABLE TBL_MEMBER_PICK;
DROP TABLE TBL_MEMBER_CART;
DROP TABLE TBL_SALED_BOOK;
DROP TABLE TBL_ATTEND_CHECK;

-- DROP SEQUENCE
DROP SEQUENCE ADMIN_SEQ;
DROP SEQUENCE BOOK_SEQ;
DROP SEQUENCE MEMBER_PICK_SEQ;
DROP SEQUENCE MEMBER_CART_SEQ;
DROP SEQUENCE SALED_BOOK_SEQ;
DROP SEQUENCE REVIEW_SEQ;

-- CREATE TABLE
CREATE TABLE TBL_MEMBER(
	M_ID			VARCHAR2(20)	PRIMARY KEY,
	M_PW			VARCHAR2(100)	NOT NULL,
	M_NAME			VARCHAR2(20)	NOT NULL,
	M_MAIL			VARCHAR2(50)	NOT NULL,
	M_PHONE			VARCHAR2(30)	NOT NULL,
	M_ADDR_CODE		VARCHAR2(100)	NOT NULL,
	M_ADDR			VARCHAR2(200)	NOT NULL,
	M_DETAIL_ADDR	VARCHAR2(200),
	M_GRADE			NUMBER(1)		DEFAULT 0,
	M_STATE			NUMBER(1)		DEFAULT 1,
	M_REG_DATE		TIMESTAMP,
	M_MOD_DATE		TIMESTAMP
);

CREATE TABLE TBL_ADMIN(
	A_NO		NUMBER(1)		PRIMARY KEY,
	A_ID		VARCHAR2(20)	NOT NULL,
	A_PW		VARCHAR2(100)	NOT NULL,
	A_GRADE		NUMBER(1)		DEFAULT 0,
	A_NAME		VARCHAR2(20)	NOT NULL,
	A_PHONE		VARCHAR2(30)	NOT NULL,
	A_REG_DATE	TIMESTAMP,
	A_MOD_DATE	TIMESTAMP
);

CREATE TABLE TBL_BOOK(
	B_NO			NUMBER(4)		PRIMARY KEY,
	B_THUMBNAIL		VARCHAR2(1200)	NOT NULL,
	B_NAME			VARCHAR2(1400)	NOT NULL,
	B_AUTHOR		VARCHAR2(1200)	NOT NULL,
	B_INTRODUCE		VARCHAR2(4000)	NOT NULL,
	B_PUBLISHER		VARCHAR2(1200)	NOT NULL,
	B_PUBLISH_DATE	VARCHAR2(40)	NOT NULL,
	B_KDC			VARCHAR2(30)	NOT NULL,
	B_ISBN			VARCHAR2(14)	NOT NULL,
	B_PRICE			NUMBER(20)		NOT NULL,
	B_COUNT			NUMBER(4)		DEFAULT 0,
	B_REG_DATE		TIMESTAMP,
	B_MOD_DATE		TIMESTAMP
);

CREATE TABLE TBL_REVIEW(
	R_NO		NUMBER(4)		PRIMARY KEY,
	M_ID		VARCHAR2(20),
	B_NO		NUMBER(4),
	R_TEXT		VARCHAR2(2400)	NOT NULL,
	R_STARS		NUMBER(4)		NOT NULL,
	R_REG_DATE	TIMESTAMP,
	R_MOD_DATE	TIMESTAMP
);

CREATE TABLE TBL_POINT_LIST(
	M_ID					VARCHAR2(20),
	PL_PAYMENT_BOOK_POINT	NUMBER(20)		NOT NULL,
	PL_DESC					VARCHAR2(200)	NOT NULL,
	PL_REG_DATE				TIMESTAMP
);

CREATE TABLE TBL_MEMBER_PICK(
	MP_NO		NUMBER(4)		PRIMARY KEY,
	M_ID		VARCHAR2(20),
	B_NO		NUMBER(4),
	MP_REG_DATE	TIMESTAMP
);

CREATE TABLE TBL_MEMBER_CART(
	C_NO			NUMBER(4),
	M_ID			VARCHAR2(20),
	B_NO			NUMBER(4),
	C_BOOK_COUNT	NUMBER(4),
	C_REG_DATE		TIMESTAMP,
	C_MOD_DATE		TIMESTAMP
);

CREATE TABLE TBL_SALED_BOOK(
	SB_NO				NUMBER(4)		PRIMARY KEY,
	B_NO				NUMBER(4),
	M_ID				VARCHAR2(20),
	SB_BOOK_COUNT		NUMBER(4)		NOT NULL,
	SB_NAME				VARCHAR2(20)	NOT NULL,
	SB_ADDR_CODE		VARCHAR2(100)	NOT NULL,
	SB_ADDR				VARCHAR2(200)	NOT NULL,
	SB_DETAIL_ADDR		VARCHAR2(200)	NOT NULL,
	SB_MEMO				VARCHAR2(100),
	SB_SALE_DATE		TIMESTAMP		NOT NULL,
	SB_ALL_PRICE		NUMBER(20)		NOT NULL,
	SB_SALED_PRICE		NUMBER(20)		NOT NULL,
	SB_STATE			NUMBER(1)		DEFAULT 1	NOT NULL,
	SB_RETURN_DATE		TIMESTAMP,
	SB_RETURN_MOD_DATE	TIMESTAMP,
	SB_REG_DATE			TIMESTAMP,
	SB_MOD_DATE			TIMESTAMP
);

CREATE TABLE TBL_ATTEND_CHECK(
	M_ID			VARCHAR2(20),
	AC_ATTEND_DATE	NUMBER(7),
	AC_REG_DATE		TIMESTAMP
);

-- CREATE SEQUENCE
CREATE SEQUENCE ADMIN_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100 CYCLE NOCACHE;
CREATE SEQUENCE BOOK_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 10000 CYCLE NOCACHE;
CREATE SEQUENCE MEMBER_PICK_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 10000 CYCLE NOCACHE;
CREATE SEQUENCE MEMBER_CART_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 10000 CYCLE NOCACHE;
CREATE SEQUENCE SALED_BOOK_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 10000 CYCLE NOCACHE;
CREATE SEQUENCE REVIEW_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100000 CYCLE NOCACHE;