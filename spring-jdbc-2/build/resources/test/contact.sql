DROP TABLE IF EXISTS CONTACT;

CREATE TABLE CONTACT (
     ID serial primary key ,
     NAME varchar(255),
     SURNAME varchar(255),
     EMAIL varchar (255),
     PHONE_NUMBER varchar (255)
);