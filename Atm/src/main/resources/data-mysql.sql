DROP TABLE IF EXISTS Atm_Admin;
CREATE TABLE Atm_Admin (
    id INT(8) AUTO_INCREMENT PRIMARY KEY,
    amount VARCHAR(50) NOT NULL,
    fifty_notes INT(8) NOT NULL,
    twenty_notes INT(8) NOT NULL,
    ten_notes INT(8) NOT NULL,
    five_notes INT(8) NOT NULL
);

INSERT INTO Atm_Admin (amount,fifty_notes,twenty_notes,ten_notes,five_notes)
VALUES (1500,10,30,30,20);

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