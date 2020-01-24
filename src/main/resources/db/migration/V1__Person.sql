CREATE TABLE payment(
  paymentId UUID NOT NULL PRIMARY KEY ,
  orderId UUID NOT NULL ,
  status VARCHAR (100) ,
  username VARCHAR (100)
);