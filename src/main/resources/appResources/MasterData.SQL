SET DEFINE OFF;



Insert into CONTRACTORS_TYPES
   (ID, NAME)
 Values
   (1, 'عمالة');
Insert into CONTRACTORS_TYPES
   (ID, NAME)
 Values
   (2, 'موردين الكيماوي');
Insert into CONTRACTORS_TYPES
   (ID, NAME)
 Values
   (3, 'منوعات');





Insert into FRIDAGES
   (ID, NAME)
 Values
   (1, 'محل رقم 18');
Insert into FRIDAGES
   (ID, NAME)
 Values
   (2, 'محل رقم 17');
COMMIT;

Insert into SHOP2020.VEHICLE_TYPES
   (ID, NAME)
 Values
   (1, 'جامبو');
Insert into VEHICLE_TYPES
   (ID, NAME)
 Values
   (2, 'دبابة');
Insert into VEHICLE_TYPES
   (ID, NAME)
 Values
   (3, 'جامبو 1/2');
COMMIT;



Insert into INCOME_TYPES
   (ID, NAME)
 Values
   (1, 'نقدي');
Insert into INCOME_TYPES
   (ID, NAME)
 Values
   (2, 'تسديد فواتير بياعين');
Insert into INCOME_TYPES
   (ID, NAME)
 Values
   (3, 'دين داخلي');
Insert into INCOME_TYPES
   (ID, NAME)
 Values
   (4, 'تسديد دين خارج');
COMMIT;
 

Insert into SELLER_TYPES
   (ID, NAME)
 Values
   (2, 'دائم');
Insert into SELLER_TYPES
   (ID, NAME)
 Values
   (1, 'نقدي');
COMMIT;


Insert into CUSTOMER_TYPES
   (ID, NAME)
 Values
   (4, 'محمد علي');
Insert into CUSTOMER_TYPES
   (ID, NAME)
 Values
   (3, 'كريم');
Insert into CUSTOMER_TYPES
   (ID, NAME)
 Values
   (2, 'مشتروات');
Insert into CUSTOMER_TYPES
   (ID, NAME)
 Values
   (1, 'عميل');
COMMIT;



SET DEFINE OFF;
Insert into PRODUCTS
   (ID, NAME, UNIT, COMMISION)
 Values
   (1, 'موز بلدي', '1', 0);
Insert into PRODUCTS
   (ID, NAME, UNIT, COMMISION)
 Values
   (2, 'موز مستورد', '1', 0);
Insert into PRODUCTS
   (ID, NAME, UNIT, COMMISION)
 Values
   (4, 'عيوشي', '1', 0);
Insert into PRODUCTS
   (ID, NAME, UNIT, COMMISION)
 Values
   (3, 'قوطاع', '1', 0);
COMMIT;
SET DEFINE OFF;
Insert into STORES
   (ID)
 Values
   (1);
Insert into STORES
   (ID)
 Values
   (2);
Insert into STORES
   (ID)
 Values
   (3);
Insert into STORES
   (ID)
 Values
   (4);
Insert into STORES
   (ID)
 Values
   (5);
COMMIT;







SET DEFINE OFF;
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (1, 'varaity', 'نثريات');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (2, 'K_L', 'عمالة كريم');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (3, 'OUT_LOAN', 'دين لصالح الحل');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (4, 'labours', 'العمال');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (5, 'ORDER_PAY', 'دفع فواتيرالعملاء');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (6, 'maintaince', 'زكاة وصدقات');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (7, 'allah', 'مشتروات');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (8, 'OUT_PAY_LOAN', 'صيانة تلاجات');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (9, 'PURCHASES_WITHDRAWALS', 'تسديد دين');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (10, 'K_V', 'منوعات كريم');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (11, 'ORDER_TIPS', 'وهبة فواتير');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (12, 'TIPS', 'وهبة');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (13, 'K_S', 'موردين كيماوي');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (14, 'NOLOUN', 'نولون');
Insert into OUTCOME_TYPES
   (ID, NAME, NAME_AR)
 Values
   (15, 'forgivness', 'سماحات');
COMMIT;

SET DEFINE OFF;
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('CUSTOMER_ID', 2946);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SELLER_ORDER_WEIGHT_ID', 62981);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('CUSTOMER_ORDER_ID', 5220670);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('OUTCOME_ID', 5221941);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('OUTCOME_DETAIL_ID', 36907);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SAFE_ID', 100);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SELLER_ORDER_ID', 5241862);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SELLER_LOAN_BAG_ID', 5662261);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SELLER_ID', 25240);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SEASON_ID', 5);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('SAFE_TRACING_ID', 1);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('PURCHASED_CUSTMER_INSTS_ID', 14248);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('LOAN_PAYING_ID', 14408);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('LOANER_ID', 4811);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('LOAN_ACCOUNT_ID', 10099);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('INSTALLMENT_ID', 5233423);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('INCOME_DETAIL_ID', 45681);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('INCOME_ID', 5221960);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('INC_LOAN_ID', 16089);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('CONTRACTOR_TRANSACTIONS_ID', 18728);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('CONTRACTOR_ACCOUNT_ID', 2046);
Insert into SHOP2020.ID_TABLE
   (ID_TABLE_NAME, ID_TABLE_VALUE)
 Values
   ('CONTRACTOR_ID', 3185);
COMMIT;








CREATE VIEW INCOME AS 
select

TO_NUMBER(TO_CHAR(TRX.TRANSACTION_DAY,'YYYYMMDD'), '99999999') as ID,
 SUM(TRX.AMOUNT) as AMOUNT,
 TRX.TRANSACTION_DAY AS INCOME_DATE,
 TRX.SEASON_ID
 
  from shop2020.SAFE_TRANSACTIONS  trx 
  
  where TRX.TRANSACTION_TYPE like 'INCOME' GROUP BY  
  
  TO_NUMBER(''||TO_CHAR(TRX.TRANSACTION_DAY,'YYYYMMDD')||'', '99999999'),

 TRX.TRANSACTION_DAY ,
 TRX.SEASON_ID;

CREATE VIEW OUTCOME AS 
select

TO_NUMBER(TO_CHAR(TRX.TRANSACTION_DAY,'YYYYMMDD'), '99999999') as ID,
 SUM(TRX.AMOUNT) as AMOUNT,
 TRX.TRANSACTION_DAY AS OUTCOME_DATE,
 TRX.SEASON_ID
 
  from shop2020.SAFE_TRANSACTIONS  trx 
  
  where TRX.TRANSACTION_TYPE like 'OUTCOME' GROUP BY  
  
  TO_NUMBER(''||TO_CHAR(TRX.TRANSACTION_DAY,'YYYYMMDD')||'', '99999999'),

 TRX.TRANSACTION_DAY ,
 TRX.SEASON_ID;


      CREATE OR REPLACE FORCE VIEW "SHOP2020"."SEASON_SAFE" ("BALANCE", "SEASON_ID") AS 
  (  SELECT SUM (balance)+S.INTIAL_SAFE_BALANCE AS balance, SOD.SEASON_ID AS season_id
        FROM SAFE_OF_DAY sod,SEASONS S
        WHERE S.ID=sod.SEASON_ID
    GROUP BY SOD.SEASON_ID);

DROP VIEW SHOP2020.SAFE_OF_DAY;

/* Formatted on 5/28/2021 7:16:17 PM (QP5 v5.185.11230.41888) */
CREATE OR REPLACE FORCE VIEW SHOP2020.SAFE_OF_DAY
(
   ID,
   BALANCE,
   DAY_DATE,
   SEASON_ID
)
AS
     SELECT TO_NUMBER (TO_CHAR (TO_DATE (DATE_, 'DD/MM/YYYY'), 'YYYYMMDD'),
                       '99999999')
               AS ID,
            SUM (INCOME_AMOUNT - OUTCOME_AMOUNT) BALANCE,
            TO_DATE (DATE_, 'DD/MM/YYYY') AS DAY_DATE,
            SEASON_ID
       FROM (  SELECT SUM (INCOME.AMOUNT) INCOME_AMOUNT,
                      0 OUTCOME_AMOUNT,
                      TO_CHAR (INCOME.INCOME_DATE, 'DD/MM/YYYY') AS DATE_,
                      SEASON_ID,
                      'INCOME' AS INCOME_TYPE,
                      '' AS OUTCOME_TtYPE
                 FROM income income
             GROUP BY SEASON_ID, TO_CHAR (INCOME.INCOME_DATE, 'DD/MM/YYYY')
             UNION
               SELECT 0 AS INCOME_AMOUNT,
                      SUM (OUTCOME.AMOUNT) AS OUTCOME_AMOUNT,
                      TO_CHAR (OUTCOME.OUTCOME_DATE, 'DD/MM/YYYY') AS DATE_,
                      SEASON_ID,
                      '' AS INCOME_TYPE,
                      'OUTCOME_TtYPE' AS TYPE_
                 FROM outcome outcome
             GROUP BY SEASON_ID, TO_CHAR (OUTCOME.OUTCOME_DATE, 'DD/MM/YYYY'))
   GROUP BY DATE_, SEASON_ID;




CREATE OR REPLACE FORCE VIEW SHOP2020.SEASON_SAFE
(
   BALANCE,
   SEASON_ID
)
AS
   (  SELECT SUM (balance) AS balance, SOD.SEASON_ID AS season_id
        FROM SAFE_OF_DAY sod
    GROUP BY SOD.SEASON_ID);



update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from safe_transactions st)

where ID_TABLE_NAME like 'SAFE_TRANSACTION_ID'
;


update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from CUSTOMER_ORDERS st)

where ID_TABLE_NAME like 'CUSTOMER_ORDER_ID'
;

commit;


update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from CONTRACTOR_TRANSACTIONS st)
where ID_TABLE_NAME like 'CONTRACTOR_TRANSACTIONS_ID';
commit;


update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from CONTRACTORS st)
where ID_TABLE_NAME like 'CONTRACTOR_ID';
commit;

update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from SELLER_ORDER_WEIGHTs st)
where ID_TABLE_NAME like 'SELLER_ORDER_WEIGHT_ID';
commit;

update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from SELLER_ORDERs st)
where ID_TABLE_NAME like 'SELLER_ORDER_ID';
commit;


update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from SELLER_LOAN_BAGs st)
where ID_TABLE_NAME like 'SELLER_LOAN_BAG_ID';
commit;

update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from SELLERs st)
where ID_TABLE_NAME like 'SELLER_ID';
commit;

update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from PURCHASED_CUSTMER_INSTS st)
where ID_TABLE_NAME like 'PURCHASED_CUSTMER_INSTS_ID';
commit;

update id_table set ID_TABLE_VALUE=(
select max (ST.ID )+1 from INSTALLMENTs st)
where ID_TABLE_NAME like 'INSTALLMENT_ID';
commit;



 
/* Formatted on 8/7/2021 6:03:59 PM (QP5 v5.365) */
CREATE OR REPLACE FORCE VIEW SHOP2020.IN_LOAN_ACCOUNTS
(
    AMOUNT,
    LOANER_ID,
    NAME,
    TYPE
)
AS
      SELECT SUM (LOAN_DEBIT_AMOUNT - PAY_DEBIT_AMOUNT)     AMOUNT,
             ID                                             AS LOANER_ID,
             NAME,
             'IN_LOAN'                                      AS TYPE
        FROM (  SELECT SUM (TRX.AMOUNT)     LOAN_DEBIT_AMOUNT,
                       0                    PAY_DEBIT_AMOUNT,
                       L.ID,
                       L.NAME
                  FROM SHOP_LOANS_TRANSACTIONS TRX, LOANERS L
                 WHERE     TRX.LOANER_ID = L.ID
                       AND TRX.FINISHED = 0
                       AND TRX.TRANSACTION_TYPE LIKE 'LOAN_DEBIT'
              GROUP BY L.ID, L.NAME
              UNION
                SELECT 0                    LOAN_DEBIT_AMOUNT,
                       SUM (TRX.AMOUNT)     PAY_DEBIT_AMOUNT,
                       L.ID,
                       L.NAME
                  FROM SHOP_LOANS_TRANSACTIONS TRX, LOANERS L
                 WHERE     TRX.LOANER_ID = L.ID
                       AND TRX.FINISHED = 0
                       AND TRX.TRANSACTION_TYPE LIKE 'PAY_DEBIT'
              GROUP BY L.ID, L.NAME)
    GROUP BY ID, NAME
      HAVING (SUM (LOAN_DEBIT_AMOUNT - PAY_DEBIT_AMOUNT) <> 0);

      
      
 
/* Formatted on 8/7/2021 6:04:41 PM (QP5 v5.365) */
CREATE OR REPLACE FORCE VIEW SHOP2020.OUT_LOAN_ACCOUNTS
(
    AMOUNT,
    LOANER_ID,
    NAME,
    TYPE
)
AS
      SELECT SUM (LOAN_CREDIT_AMOUNT - PAY_CREDIT_AMOUNT)     AMOUNT,
             ID                                               AS LOANER_ID,
             NAME,
             'OUT_LOAN'                                       AS TYPE
        FROM (  SELECT SUM (TRX.AMOUNT)     LOAN_CREDIT_AMOUNT,
                       0                    PAY_CREDIT_AMOUNT,
                       L.ID,
                       L.NAME
                  FROM SHOP_LOANS_TRANSACTIONS TRX, LOANERS L
                 WHERE     TRX.LOANER_ID = L.ID
                       AND TRX.FINISHED = 0
                       AND TRX.TRANSACTION_TYPE LIKE 'LOAN_CREDIT'
              GROUP BY L.ID, L.NAME
              UNION
                SELECT 0                    LOAN_CREDIT_AMOUNT,
                       SUM (TRX.AMOUNT)     PAY_CREDIT_AMOUNT,
                       L.ID,
                       L.NAME
                  FROM SHOP_LOANS_TRANSACTIONS TRX, LOANERS L
                 WHERE     TRX.LOANER_ID = L.ID
                       AND TRX.FINISHED = 0
                       AND TRX.TRANSACTION_TYPE LIKE 'PAY_CREDIT'
              GROUP BY L.ID, L.NAME)
    GROUP BY ID, NAME;

    
    DROP VIEW SHOP2020.LOAN_ACCOUNTS;

/* Formatted on 8/7/2021 6:04:54 PM (QP5 v5.365) */
CREATE OR REPLACE FORCE VIEW SHOP2020.LOAN_ACCOUNTS
(
    AMOUNT,
    LOANER_ID,
    NAME,
    TYPE
)
AS
    SELECT "AMOUNT", "LOANER_ID", "NAME", "TYPE" FROM IN_LOAN_ACCOUNTS
    UNION
    SELECT "AMOUNT", "LOANER_ID", "NAME", "TYPE" FROM OUT_LOAN_ACCOUNTS;

    
    
    
    
CREATE OR REPLACE FORCE VIEW "SHOP2020"."CONTRACTOR_ACCOUNTS" ("ID", "AMOUNT", "CONTRACTOR_ID", "CONTRACTOR_NAME", "SEASON_ID", "OWNER_ID") AS 
select SEASON_Id||''||c.OWNER_Id||''||CONTRACTOR_ID as ID, sum(amount) as amount ,trx.CONTRACTOR_ID,c.NAME as CONTRACTOR_NAME, trx.SEASON_ID,c.OWNER_ID from CONTRACTOR_TRANSACTIONS trx ,contractors c
where c.id=trx.CONTRACTOR_ID
group by CONTRACTOR_ID,SEASON_ID,c.name,c.OWNER_ID,SEASON_Id||''||c.OWNER_Id||''||CONTRACTOR_ID;


INSERT INTO "SHOP2020"."USERS" (ID, PASSWORD, NAME) VALUES ('1', '1', 'h');
UPDATE "SHOP2020"."SEASONS" SET INTIAL_SAFE_BALANCE = '106005' WHERE id = 6;
