CREATE DATABASE Mobiles

CREATE TABLE Mobiles (
    mobileId VARCHAR(10) PRIMARY KEY,
    description NVARCHAR(250) NOT NULL,
    price FLOAT,
    mobileName NVARCHAR(20) NOT NULL,
    yearOfProduction INT,
    quantity INT,
    notSale BIT
);

CREATE TABLE Users (
    userId VARCHAR(100) PRIMARY KEY,
    password VARCHAR(50),
    fullName NVARCHAR(50) NOT NULL,
    role VARCHAR(10), -- 0: user, 1: manager, 2: staff
    gmailID INT,
    gmailLink VARCHAR(50)
);

CREATE TABLE [Order] (
    id varchar(5), 
    date date, 
    customer nvarchar(50) NOT NULL,
    address nvarchar(150) NOT NULL,
    email varchar(50) NOT NULL,
    total float,
    PRIMARY KEY (id, date) 
);

CREATE TABLE OrderDetail (
    id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    mobileId VARCHAR(10),
    unitprice FLOAT,
    quantity INT,
    orderId VARCHAR(5),
    orderDate DATE, -- Adding date field
    total FLOAT,
    FOREIGN KEY(mobileId) REFERENCES dbo.Mobiles(mobileId),
    FOREIGN KEY(orderId, orderDate) REFERENCES dbo.[Order](id, date) 
);

CREATE TABLE Comment (
    commentId INT IDENTITY(1,1) PRIMARY KEY,
    userId NVARCHAR(100) NOT NULL,
    commentText NVARCHAR(500) NOT NULL,
    commentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE TempCart (
    tempCartId INT PRIMARY KEY IDENTITY(1,1), -- Tự động tăng từ 1, 2, 3, ...
    mobileId VARCHAR(10),
    quantity INT,
    FOREIGN KEY (mobileId) REFERENCES dbo.Mobiles(mobileId)
);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB001', N'Samsung Galaxy S21 Ultra: Thiết kế cho ngày mai...', 1299, 'Galaxy S21 Ultra', 2021, 50, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB002', N'iPhone 13 Pro Max: Thiết kế tốt nhất...', 1199, 'iPhone 13 Pro Max', 2021, 30, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB003', N'Samsung Galaxy S20 Ultra: Thiết kế sang trọng...', 1699, 'Galaxy S20 Ultra', 2020, 20, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB004', N'iPhone 15 Pro Max: Thiết kế đẳng cấp...', 2999, 'iPhone 15 Pro Max', 2023, 35, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB005', N'Samsung Galaxy Z Flip 4: Thiết kế sang trọng...', 1439, 'Galaxy Z Flip 4', 2019, 15, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB006', N'iPhone 14 Pro: Thiết kế bền vững...', 1799, 'iPhone 14 Pro', 2022, 30, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB007', N'iPhone 11 Pro: Thiết kế tối giản...', 999, 'iPhone 11 Pro', 2019, 15, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB008', N'iPhone 13 Pro: Thiết kế tối tân...', 1089, 'iPhone 13 Pro', 2022, 30, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB009', N'iPhone 12 Pro Max: Thiết kế vững vàng...', 1329, 'iPhone 12 Pro Max', 2020, 15, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB010', N'iPhone 13 Pro Max: Thiết kế bền vững...', 1499, 'iPhone 13 Pro Max', 2022, 30, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB011', N'iPhone Xr: Thiết kế sáng bừng', 899, 'iPhone xr', 2022, 26, 0);

INSERT INTO Mobiles (mobileId, description, price, mobileName, yearOfProduction, quantity, notSale)
VALUES ('MOB012', N'iPhone 8 Plus: Thiết kế sáng tốt', 699, 'iPhone 8 Plus', 2022, 26, 0);

INSERT INTO Users (userId, password, fullName, role, gmailID, gmailLink)
VALUES ('user01@gmail.com', '1', N'Lê Gia Hân', 'User', NULL, NULL);

INSERT INTO Users (userId, password, fullName, role, gmailID, gmailLink)
VALUES ('dothanhdat339@gmail.com','thanhdat3009', N'Đỗ Thành Đạt', 'Manager', NULL, NULL);

INSERT INTO Users (userId, password, fullName, role, gmailID, gmailLink)
VALUES ('staff01@gmail.com', '1', N'Trần Trung Văn', 'Staff', NULL, 'trungvan@gmail.com');
