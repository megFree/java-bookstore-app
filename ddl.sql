CREATE SCHEMA bookStoreTest;
USE bookStoreTest;

CREATE TABLE orders (
	id int primary key auto_increment,
    customerName varchar(50) not null,
    customerMail varchar(50) not null,
    closedDate datetime,
    status varchar(50) not null,
    totalPrice decimal(15,2) not null
);

CREATE TABLE books (
	id int primary key auto_increment,
    title varchar(50) not null,
    author varchar(50) not null,
    isInStock bool not null,
    publishDate datetime not null,
    price decimal(15,2) not null,
    incomeDate datetime not null,
    description varchar(2000)
);

CREATE TABLE requests (
	id int primary key auto_increment,
    dateCreated datetime not null,
    isClosed bool not null,
    bookId int not null,
    foreign key(bookId) references books(id)
);

CREATE TABLE orderBooks (
	id int primary key auto_increment,
	orderId int not null,
    bookId int not null,
    foreign key(orderId) references orders(id),
    foreign key(bookId) references books(id)
);