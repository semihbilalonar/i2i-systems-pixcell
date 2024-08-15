----------Customer Table--------------------


CREATE TABLE customer (
  cust_id NUMBER NOT NULL , 
  msisdn VARCHAR2(11) UNIQUE CHECK (LENGTH(msisdn) = 11) NOT NULL , 
  name VARCHAR2(30) NOT NULL , 
  surname VARCHAR2(30) NOT NULL , 
  email VARCHAR2(50) NOT NULL UNIQUE   , 
  password  VARCHAR2(20) NOT NULL , 
  str_date DATE DEFAULT SYSDATE  , 
  status VARCHAR2(20) DEFAULT 'A' NOT NULL , 
  security_key VARCHAR2(50) NOT NULL 
  
);

ALTER TABLE customer
ADD CONSTRAINT pk_cust_id
PRIMARY KEY (cust_id);



DROP SEQUENCE cust_id_seq

CREATE SEQUENCE cust_id_seq
START WITH 1
INCREMENT BY 1;


----------------package table------------------

CREATE TABLE package (
  package_id NUMBER ,
  package_name VARCHAR2(200) NOT NULL , 
  price NUMBER  NOT NULL , 
  amount_minutes NUMBER NOT NULL , 
  amount_data NUMBER NOT NULL , 
  amount_sms NUMBER NOT NULL , 
  period NUMBER NOT NULL 
);


ALTER TABLE package
ADD CONSTRAINT pk_package_id
PRIMARY KEY (package_id);




INSERT INTO package (
    package_id,package_name, price, amount_minutes, amount_data, amount_sms, period
) VALUES (
    1,              -- package_id
    'Basic Plan',   -- package_name
    100,            -- price
    299,            -- amount_minutes
    500,            -- amount_data
    50,             -- amount_sms
    30              -- period
);

INSERT INTO package (
    package_id, package_name,price, amount_minutes, amount_data, amount_sms, period
) 
VALUES (
    2,              -- package_id
    'Standard Plan',-- package_name
    300,            -- price
    499,            -- amount_minutes
    1000,           -- amount_data
    100,            -- amount_sms
    60              -- period
);


----------------balance table------------------

CREATE TABLE balance (
  balance_id NUMBER NOT NULL,
  package_id NUMBER,
  cust_id NUMBER,
  partition_id NUMBER NOT NULL,
  bal_lvl_minutes NUMBER NOT NULL,
  bal_lvl_sms NUMBER NOT NULL , 
  bal_lvl_data NUMBER NOT NULL,
  sdate DATE DEFAULT SYSDATE ,
  edate DATE DEFAULT SYSDATE ,
  price NUMBER NOT NULL,
  bal_lvl_money NUMBER NOT NULL
 );


ALTER TABLE BALANCE
ADD CONSTRAINT pk_balance_id
PRIMARY KEY (balance_id);


ALTER TABLE balance
ADD CONSTRAINT fk_package_id
FOREIGN KEY (package_id)
REFERENCES package (package_id);


ALTER TABLE balance
ADD CONSTRAINT fk_bal_cust_id
FOREIGN KEY (cust_id)
REFERENCES customer (cust_id);



DROP SEQUENCE balance_id_seq

CREATE SEQUENCE balance_id_seq
START WITH 1
INCREMENT BY 1;

------------userUsagedatail table-------------------
CREATE TABLE userUsageDetail (
msisdn varchar2(11),
usage_date TIMESTAMP , 
usage_type varchar2(50),
usage_duration NUMBER, 
remain NUMBER 

);

ALTER TABLE userUsageDetail
ADD CONSTRAINT fk_msisdn_usage
FOREIGN KEY (msisdn)
REFERENCES CUSTOMER(msisdn);


SELECT * FROM USERUSAGEDETAIL u 

DROP TABLE USERUSAGEDETAIL 
--------------------notification log-------------------------

CREATE TABLE notification_logs (
notification_id NUMBER NOT NULL ,
notification_type varchar2(100),
notification_time TIMESTAMP,
customer_id NUMBER 


);




CREATE TABLE notification_logs (
notification_id NUMBER NOT NULL ,
notification_type varchar2(100),
notification_time TIMESTAMP,
msisdn 

);
ALTER TABLE notification_logs
ADD (msisdn varchar(11));

ALTER TABLE notification_logs
ADD CONSTRAINT pk_notification_id PRIMARY KEY (notification_id);

CREATE SEQUENCE notification_id_sequence
START WITH 1
INCREMENT BY 1;



---------------------------------------------------------------------