CREATE DATABASE employee_db;
USE employee_db;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(100),
    Salary DECIMAL(10,2)
);

INSERT INTO Employee VALUES (1, 'Alice Smith', 50000);
INSERT INTO Employee VALUES (2, 'Bob Johnson', 60000);
INSERT INTO Employee VALUES (3, 'Carol White', 55000);
