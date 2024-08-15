------------------------------------------------------------------------------------TRIGGERS--------------------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE TRIGGER trg_cust_id
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN
  IF :NEW.cust_id IS NULL THEN
    SELECT cust_id_seq.NEXTVAL INTO :NEW.cust_id FROM dual;
  END IF;
END;

DROP  TRIGGER trg_cust_id
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE TRIGGER trg_personal_usage_id
BEFORE INSERT ON userUsageDetail
FOR EACH ROW
BEGIN
  IF :NEW.personal_usage_id IS NULL THEN
    SELECT personal_usage_id.NEXTVAL INTO :NEW.personal_usage_id FROM dual;
  END IF;
END;


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE TRIGGER trg_balance_id
BEFORE INSERT ON balance
FOR EACH ROW
BEGIN
  IF :NEW.balance_id IS NULL THEN
    SELECT balance_id_seq.NEXTVAL INTO :NEW.balance_id FROM dual;
  END IF;
END;






------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE TRIGGER trg_notification_id
BEFORE INSERT ON notification_logs
FOR EACH ROW
BEGIN
  IF :NEW.notification_id IS NULL THEN
    SELECT notification_id_sequence.NEXTVAL INTO :NEW.notification_id FROM dual;
  END IF;
END;



----------------------------------------------------------------------------------PROCEDURES AND FUNCTION----------------------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE PROCEDURE getCustomerInformation (
    p_msisdn IN NUMBER,
    o_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN o_cursor FOR
        SELECT 
            c.CUST_ID, 
            c.MSISDN, 
            c.NAME, 
            c.SURNAME, 
            c.EMAIL, 
            c.PASSWORD, 
            c.STATUS, 
            c.SECURITY_KEY,
            b.PACKAGE_ID
        FROM 
            CUSTOMER c
        LEFT JOIN 
            BALANCE b ON c.CUST_ID = b.CUST_ID
        WHERE 
            c.MSISDN = p_msisdn;
END;

SELECT * FROM customer 
SELECT * FROM balance
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION login (
    U_MSISDN IN NUMBER, 
    U_PASSWORD IN VARCHAR2m 
) 
RETURN NUMBER
AS
    match_count NUMBER;
BEGIN
    
    SELECT COUNT(*) INTO match_count 
    FROM CUSTOMER 
    WHERE MSISDN = U_MSISDN AND password = U_PASSWORD;

    
    IF match_count = 0 THEN
        RETURN 0; 
    ELSE
        RETURN 1; 
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0; 
    WHEN OTHERS THEN
        RETURN 0; 
END;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE getAllCustomers (p_cursor OUT SYS_REFCURSOR) AS
BEGIN
    OPEN p_cursor FOR
    SELECT 
        cust_id,
        msisdn,
        name,
        surname,
        email,
        password,
        str_date,
        status,
        security_key
    FROM 
       customer;
END;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_remaining_data (
    p_msisdn NUMBER
) RETURN NUMBER
AS
    remaining_data NUMBER;
BEGIN
    -- Kalan veri miktarını hesapla
    SELECT (balance.bal_lvl_data)
    INTO remaining_data
    FROM customer
    INNER JOIN balance ON customer.cust_id = balance.cust_id
    INNER JOIN package ON balance.package_id = package.package_id
    WHERE customer.msisdn = p_msisdn;

    RETURN remaining_data;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Eğer msisdn ile eşleşen kayıt bulunamazsa, 0 döndür
        RETURN 0;
    WHEN OTHERS THEN
        -- Diğer hatalar için, hata mesajını yazdır veya uygun bir hata kodu döndür
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
        RETURN NULL; -- Veya uygun bir hata değeri döndür
END;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_remaining_sms (
    p_msisdn number
) RETURN NUMBER
AS
    remaining_sms NUMBER;
BEGIN
    -- Kalan SMS'leri hesapla
    SELECT (balance.bal_lvl_sms)
    INTO remaining_sms
    FROM customer
    INNER JOIN balance ON customer.cust_id = balance.cust_id
    INNER JOIN package ON balance.package_id = package.package_id
    WHERE customer.msisdn = p_msisdn;

    RETURN remaining_sms;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Eğer msisdn ile eşleşen kayıt bulunamazsa, 0 döndür
        RETURN 0;
    WHEN OTHERS THEN
        -- Diğer hatalar için, hata mesajını yazdır veya uygun bir hata kodu döndür
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
        RETURN NULL; -- Veya uygun bir hata değeri döndür
END;


SELECT get_remaining_sms(12345678901) AS remaining_sms FROM dual;



--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_remaining_minutes (
    p_msisdn NUMBER
) RETURN NUMBER
AS
    remaining_minutes NUMBER;
BEGIN
    -- Kalan dakika miktarını hesapla
    SELECT (balance.bal_lvl_minutes)
    INTO remaining_minutes
    FROM customer
    INNER JOIN balance ON customer.cust_id = balance.cust_id
    INNER JOIN package ON balance.package_id = package.package_id
    WHERE customer.msisdn = p_msisdn;

    RETURN remaining_minutes;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Eğer msisdn ile eşleşen kayıt bulunamazsa, 0 döndür
        RETURN 0;
    WHEN OTHERS THEN
        -- Diğer hatalar için, hata mesajını yazdır veya uygun bir hata kodu döndür
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
        RETURN NULL; -- Veya uygun bir hata değeri döndür
END;


SELECT get_remaining_minutes(55555555555) AS remaining_sms FROM dual;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE create_customer_with_package (
    p_msisdn       IN NUMBER,
    p_name         IN VARCHAR2,
    p_surname      IN VARCHAR2,
    p_email        IN VARCHAR2  ,
    p_password     IN VARCHAR2,
    p_security_key IN VARCHAR2,
    p_package_id   IN NUMBER
) AS
    v_max_cust_id NUMBER;
    v_max_balance_id NUMBER;
BEGIN
    -- 1. Yeni müşteri ekle
    SELECT COALESCE(MAX(cust_id), 0) INTO v_max_cust_id FROM customer;
    
    INSERT INTO customer (cust_id, msisdn, name, surname, email, password, str_date, status, security_key)
    VALUES (v_max_cust_id + 1, p_msisdn, p_name, p_surname, p_email, p_password, SYSDATE, 'A', p_security_key);

    -- 2. Balance tablosunda yeni bir kayıt oluştur
    SELECT COALESCE(MAX(balance_id), 0) INTO v_max_balance_id FROM balance;

    INSERT INTO balance (
        balance_id,
        package_id,
        cust_id,
        partition_id,
        bal_lvl_minutes,
        bal_lvl_sms,
        bal_lvl_data,
        sdate,
        edate,
        price,
        bal_lvl_money
    ) VALUES (
        v_max_balance_id + 1,  -- Yeni balance_id
        p_package_id,          -- Seçilen paket
        v_max_cust_id + 1,     -- Yeni müşteri ID'si
        1,                     -- Partition ID, burada sabit bir değer kullanılabilir veya dinamik olabilir
         (SELECT amount_minutes FROM package WHERE package_id = p_package_id),               -- Başlangıçta kalan dakika
         (SELECT amount_sms FROM package WHERE package_id = p_package_id),                     -- Başlangıçta kalan SMS
         (SELECT amount_data FROM package WHERE package_id = p_package_id),                     -- Başlangıçta kalan data
        SYSDATE,               -- Başlangıç tarihi
        SYSDATE + 30,          -- Bitiş tarihi (örnek olarak 30 gün sonrası)
        (SELECT price FROM package WHERE package_id = p_package_id), -- Seçilen paketin fiyatı
        1000                    -- Varsayılan olarak bal_lvl_money 100 olarak atanır
    );

END;




--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE update_balance_data

CREATE OR REPLACE PROCEDURE update_balance_data (
    p_msisdn IN NUMBER,            -- Telefon numarası
    p_data_used IN NUMBER           -- Kullanılan veri miktarı
) AS
    v_cust_id NUMBER;              -- Müşteri ID'si
BEGIN
    -- Müşteri ID'sini al
    SELECT c.cust_id
    INTO v_cust_id
    FROM customer c
    WHERE c.msisdn = p_msisdn;

    -- Veri seviyesini güncelle
    UPDATE balance
    SET bal_lvl_data = bal_lvl_data - p_data_used
    WHERE cust_id = v_cust_id;

    COMMIT;  -- Değişiklikleri kaydet
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Müşteri veya `balance` verisi bulunamazsa hata fırlat
        RAISE_APPLICATION_ERROR(-20001, 'Customer or balance data not found.');
    WHEN OTHERS THEN
        -- Diğer hatalar için genel hata fırlat
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred while updating balance. Error message: ' || SQLERRM);
END;
 




--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE PROCEDURE update_balance_sms (
    p_msisdn IN NUMBER,            -- Telefon numarası
    p_sms_used IN NUMBER            -- Kullanılan SMS miktarı
) AS
    v_cust_id NUMBER;              -- Müşteri ID'si
BEGIN
    -- Müşteri ID'sini al
    SELECT c.cust_id
    INTO v_cust_id
    FROM customer c
    WHERE c.msisdn = p_msisdn;

    -- SMS seviyesini güncelle
    UPDATE balance
    SET bal_lvl_sms = bal_lvl_sms - p_sms_used
    WHERE cust_id = v_cust_id;

    COMMIT;  -- Değişiklikleri kaydet
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Müşteri veya `balance` verisi bulunamazsa hata fırlat
        RAISE_APPLICATION_ERROR(-20001, 'Customer or balance data not found.');
    WHEN OTHERS THEN
        -- Diğer hatalar için genel hata fırlat
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred while updating balance. Error message: ' || SQLERRM);
END;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE PROCEDURE update_balance_minutes (
    p_msisdn IN NUMBER,            -- Telefon numarası
    p_minutes_used IN NUMBER        -- Kullanılan dakika miktarı
) AS
    v_cust_id NUMBER;              -- Müşteri ID'si
BEGIN
    -- Müşteri ID'sini al
    SELECT c.cust_id
    INTO v_cust_id
    FROM customer c
    WHERE c.msisdn = p_msisdn;

    -- Dakika seviyesini güncelle
    UPDATE balance
    SET bal_lvl_minutes = bal_lvl_minutes - p_minutes_used
    WHERE cust_id = v_cust_id;

    COMMIT;  -- Değişiklikleri kaydet
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Müşteri veya `balance` verisi bulunamazsa hata fırlat
        RAISE_APPLICATION_ERROR(-20001, 'Customer or balance data not found.');
    WHEN OTHERS THEN
        -- Diğer hatalar için genel hata fırlat
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred while updating balance. Error message: ' || SQLERRM);
END;



--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE get_all_package_details (
    p_cursor OUT SYS_REFCURSOR -- Cursor için OUT parametresi
)
IS
BEGIN
    -- Cursor'ı aç
    OPEN p_cursor FOR
    SELECT package_id, package_name, price, amount_minutes, amount_data, amount_sms, period
    FROM package;
END;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE update_password (
    p_msisdn IN NUMBER,          -- Kullanıcının MSISDN numarası
    p_security_key IN VARCHAR2,  -- Kullanıcının güvenlik anahtarı
    p_new_password IN VARCHAR2   -- Kullanıcının yeni şifresi
) IS
    v_cust_id NUMBER;           -- Müşteri ID'si
BEGIN
    -- Güvenlik anahtarını doğrulamak için müşteri ID'sini al
    SELECT c.cust_id
    INTO v_cust_id
    FROM customer c
    WHERE c.msisdn = p_msisdn
      AND c.security_key = p_security_key;

    -- Eğer güvenlik anahtarı doğruysa, yeni şifreyi güncelle
    UPDATE customer
    SET password = p_new_password
    WHERE cust_id = v_cust_id;

    COMMIT;  -- Değişiklikleri kaydet

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Müşteri veya güvenlik anahtarı bulunamazsa hata fırlat
        RAISE_APPLICATION_ERROR(-20001, 'Invalid MSISDN or security key.');
    WHEN OTHERS THEN
        -- Diğer hatalar için genel bir hata fırlat
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred while updating the password. Error message: ' || SQLERRM);
END;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE insert_userUsageDetail (
    p_giver_id IN VARCHAR2,
    p_receiver_id IN VARCHAR2,
    p_usage_date IN TIMESTAMP,
    p_usage_type IN VARCHAR2,
    p_usage_duration IN NUMBER
)
IS
BEGIN
    
    INSERT INTO userUsageDetail (
        ii
        GIVER_ID,
        RECEIVER_ID,
        USAGE_DATE,
        USAGE_TYPE,
        USAGE_DURATION
    ) VALUES (
        
        p_giver_id,
        p_receiver_id,
        p_usage_date,
        p_usage_type,
        p_usage_duration
    );

    -- İşlemi commit et
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        -- Herhangi bir hata durumunda işlem başarısız olur ve hata mesajı döner
        RAISE_APPLICATION_ERROR(-20003, 'Bir hata oluştu: ' || SQLERRM);
END insert_userUsageDetail;




--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_package_details_for_msisdn (
    p_msisdn       IN NUMBER
) RETURN SYS_REFCURSOR
AS
    v_cursor SYS_REFCURSOR;
BEGIN
    -- Telefon numarasına bağlı olarak kullanılan paket bilgilerini sorgula
    OPEN v_cursor FOR
    SELECT 
        p.package_id,
        p.package_name,
        b.edate,
        p.amount_data,
        p.amount_sms,
        p.amount_minutes
    FROM customer c
    INNER JOIN balance b ON c.cust_id = b.cust_id
    INNER JOIN package p ON b.package_id = p.package_id
    WHERE c.msisdn = p_msisdn;

    RETURN v_cursor;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Eğer telefon numarası ile eşleşen kayıt bulunamazsa, boş bir cursor döndür
        OPEN v_cursor FOR SELECT NULL AS package_name , NULL AS package_id, NULL AS edate, NULL AS amount_data, NULL AS amount_sms, NULL AS amount_minutes FROM dual;
        RETURN v_cursor;
    WHEN OTHERS THEN
        -- Diğer hatalar için, hata mesajını döndür
        OPEN v_cursor FOR SELECT NULL AS package_name, NULL AS package_id, NULL AS edate, NULL AS amount_data, NULL AS amount_sms, NULL AS amount_minutes FROM dual;
        RETURN v_cursor;
END;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE INSERT_NOTIFICATION_LOG_FOR_NF (
    p_notification_type IN NOTIFICATION_LOGS.NOTIFICATION_TYPE%TYPE,
    p_msisdn           IN CUSTOMER.MSISDN%TYPE
) AS
    v_customer_id CUSTOMER.CUST_ID%TYPE;
BEGIN
    -- MSISDN numarasına göre CUSTOMER tablosundan CUST_ID değerini al
    SELECT CUST_ID
    INTO v_customer_id
    FROM CUSTOMER
    WHERE MSISDN = p_msisdn;

    -- Eğer CUST_ID bulunduysa, NOTIFICATION_LOGS tablosuna kaydı ekle
    INSERT INTO NOTIFICATION_LOGS (
        NOTIFICATION_ID, -- Notification ID, sequence ile otomatik olarak atanacak
        MSISDN           -- MSISDN değerini kaydediyoruz
    ) VALUES (
        NOTIFICATION_ID_SEQUENCE.NEXTVAL, -- notification_id sequence ile atanıyor
        p_msisdn -- MSISDN değeri ekleniyor
    );
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Eğer MSISDN'e ait bir müşteri bulunamazsa, hata mesajı döndür
        RAISE_APPLICATION_ERROR(-20001, 'MSISDN numarasına ait müşteri bulunamadı.');
    WHEN OTHERS THEN
        -- Diğer hatalar için genel bir hata mesajı
        RAISE_APPLICATION_ERROR(-20002, 'Bir hata oluştu: ' || SQLERRM);
END;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

SELECT * FROM BALANCE b 


TRUNCATE TABLE customer 
TRUNCATE  TABLE balance 
ALTER SEQUENCE cust_id_seq   RESTART START  WITH 1
ALTER SEQUENCE balance_id_seq  RESTART START  WITH 1