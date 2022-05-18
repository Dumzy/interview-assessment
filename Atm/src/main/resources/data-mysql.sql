DROP TABLE IF EXISTS Account;
CREATE TABLE Account (
    accountnumber VARCHAR(50)  PRIMARY KEY,
    pin VARCHAR(50) NOT NULL,
    balance INT(8) NOT NULL,
    overdraft INT(8) NOT NULL
);

INSERT INTO Account (accountnumber,pin,balance,overdraft)
VALUES ('123456789', '1234',800,200);
INSERT INTO Account (accountnumber,pin,balance,overdraft)
VALUES ('987654321', '4321',1230,150);