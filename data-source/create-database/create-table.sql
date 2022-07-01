use schoo_conection;
CREATE TABLE Users (
MS INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
fullname VARCHAR(30) NOT NULL,
username VARCHAR(30) NOT NULL,
pass_word VARCHAR(30) NOT NULL,
phone_number VARCHAR(30) null,
email VARCHAR(50)
);
