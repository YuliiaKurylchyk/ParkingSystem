CREATE TABLE CUSTOMERS(
CUSTOMER_ID INT AUTO_INCREMENT PRIMARY KEY,
NAME VARCHAR(45) NOT NULL,
SURNAME VARCHAR(45) NOT NULL,
PHONE_NUMBER VARCHAR(20) NOT NULL
);

CREATE TABLE VEHICLES(
MAKE VARCHAR(45) NOT NULL,
MODEL VARCHAR(45) NOT NULL,
LICENSE_PLATE VARCHAR(20) PRIMARY KEY,
TYPE VARCHAR(10) NOT NULL CHECK(TYPE IN('MOTORBIKE','CAR','TRUCK','BUS')),
CUSTOMER_ID INT,
FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID)
);

CREATE TABLE CARS(
LICENSE_PLATE VARCHAR(20) PRIMARY KEY,
CAR_SIZE VARCHAR(25) NOT NULL
);

CREATE TABLE TRUCKS(
LICENSE_PLATE VARCHAR(20) PRIMARY KEY,
TRAILER_PRESENT BOOLEAN NOT NULL
);

CREATE TABLE BUSES(
LICENSE_PLATE VARCHAR(20) PRIMARY KEY,
COUNT_OF_SEATS INT NOT NULL
);

CREATE TABLE PARKING_SLOTS (
PARKING_SLOT_ID INT AUTO_INCREMENT NOT NULL,
SIZE VARCHAR(10) NOT NULL CHECK (SIZE IN('SMALL','MEDIUM','LARGE')),
SLOT_STATUS VARCHAR(10) NOT NULL CHECK (SLOT_STATUS IN ('VACANT','OCCUPIED')),
PRIMARY KEY (PARKING_SLOT_ID, SIZE)
);


CREATE TABLE PARKING_SLOT_PRICES(
SIZE VARCHAR(10) NOT NULL,
PRICE DECIMAL NOT NULL
);

CREATE TABLE PARKING_TICKETS (
PARKING_TICKET_ID INT AUTO_INCREMENT PRIMARY KEY,
CUSTOMER_ID INT NOT NULL,
VEHICLE_ID VARCHAR(20) NOT NULL,
PARKING_SLOT_ID INT NOT NULL,
PARKING_SLOT_SIZE VARCHAR(10) NOT NULL ,
FROM_TIME TIMESTAMP NOT NULL,
TO_TIME TIMESTAMP ,
STATUS VARCHAR(10) NOT NULL CHECK (STATUS IN('PRESENT', 'LEFT')),
COST DECIMAL,

FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID),
FOREIGN KEY (VEHICLE_ID) REFERENCES VEHICLES(LICENSE_PLATE) ON UPDATE CASCADE ,
FOREIGN KEY (PARKING_SLOT_ID,PARKING_SLOT_SIZE) REFERENCES PARKING_SLOTS(PARKING_SLOT_ID, SIZE)
);

INSERT INTO PARKING_SLOTS VALUES
(1,'SMALL','VACANT'),
(2,'SMALL','VACANT'),
(3,'SMALL','VACANT'),
(4,'SMALL','VACANT'),
(5,'SMALL','VACANT'),

(1,'MEDIUM','OCCUPIED'),
(2,'MEDIUM','OCCUPIED'),
(3,'MEDIUM','VACANT'),
(4,'MEDIUM','VACANT'),
(5,'MEDIUM','VACANT'),
(6,'MEDIUM','VACANT'),
(7,'MEDIUM','VACANT'),
(8,'MEDIUM','VACANT'),
(9,'MEDIUM','VACANT'),
(10,'MEDIUM','VACANT'),

(1,'LARGE','OCCUPIED'),
(2,'LARGE','VACANT'),
(3,'LARGE','VACANT'),
(4,'LARGE','VACANT'),
(5,'LARGE','VACANT');


INSERT INTO PARKING_SLOT_PRICES VALUES
('SMALL',15),
('MEDIUM',25),
('LARGE',45);


INSERT INTO CUSTOMERS VALUES
(1,'Octavia','Blake','+380931092584'),
(2,'Sheldon','Cooper','+380961025896'),
(3,'Yuliia','Kurylchyk','+380964125789');

INSERT INTO VEHICLES VALUES
('Honda','Crosstour','BK 0300 IO','CAR',3),
('BMW','X5','BC 0001 IK','CAR',1),
('Harley Davidson','Iron 883','CE 2314 OI','MOTORBIKE',2),
('Iveco','Z','CE 3422 EI','TRUCK',2);

INSERT INTO CARS VALUES
('BK 0300 IO','SUV'),
('BC 0001 IK','SUV');

INSERT INTO TRUCKS VALUES
('CE 3422 EI',TRUE);

INSERT INTO PARKING_TICKETS VALUES
(1,1,'BC 0001 IK',1,'MEDIUM','2020-07-07 17:27:40.295',NULL,'PRESENT',NULL),
(2,2,'CE 3422 EI',1,'LARGE','2020-07-05 17:27:40.295',NULL,'PRESENT',NULL),
(3,2,'CE 2314 OI',1,'SMALL','2020-07-08 14:55:04.824',	'2020-07-08 19:55:04.824','LEFT',15),
(4,3,'BK 0300 IO',2,'MEDIUM','2020-07-08 14:55:04.824',NULL ,'PRESENT',NULL);

