create database smarthomesdb;

use smarthomesdb;

create table Registration(username varchar(40),password varchar(40),repassword varchar(40),
usertype varchar(40));


Create table CustomerOrders
(
    OrderId integer,
    userName varchar(40),
    customerName varchar(40),
    customerAddress varchar(60),
    creditCardNo varchar(40),
    purchaseDate DATE,
    shipDate DATE,
    orderName varchar(40),
    productCategory varchar(30),
    quantity integer,
    orderPrice double,
    shippingCost double,
    discount double,
    totalSales integer,
    storeId varchar(100),
    storeAddress varchar(100),
    
    Primary key(OrderId,userName,orderName)
);

Create table Productdetails
(
ProductType varchar(20),
Id varchar(60),
productName varchar(40),
productPrice double,
productImage varchar(40),
productManufacturer varchar(40),
productCondition varchar(40),
productDiscount double,
productOnSale varchar(20),
productQuantity integer,
Primary key(Id)
);

CREATE TABLE Product_accessories (
    productName varchar(20),
    accessoriesName  varchar(20),
    
    
    FOREIGN KEY (productName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (accessoriesName) REFERENCES Productdetails(Id) ON DELETE SET NULL
        ON UPDATE CASCADE    
);


Create table Stores
(
    storeId varchar(40),
    name varchar(40),
    street varchar(40),
    city varchar(20),
    state varchar(40),
    zip varchar(40),
    Primary key(storeId)
);

