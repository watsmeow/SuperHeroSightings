DROP DATABASE IF EXISTS superHeroDB;
CREATE DATABASE superHeroDB;
USE superHeroDB;

CREATE TABLE orgAddresses (
	orgAddressID INT PRIMARY KEY, 
    orgAddress VARCHAR(500) NOT NULL,
    orgCity VARCHAR(100) NOT NULL,
    orgState VARCHAR(2) NOT NULL, 
    orgZip VARCHAR(10) NOT NULL
);

CREATE TABLE orgPhoneNumbers (
	phoneNumberID INT PRIMARY KEY,
    phoneNumber VARCHAR(50) NOT NULL
);

CREATE TABLE locations (
    locationID int PRIMARY KEY AUTO_INCREMENT,
    locationName varchar(50) NOT NULL,
    locationDescription varchar(250) NOT NULL,
	latitude FLOAT(10, 6) NOT NULL,  
	longitude FLOAT(10, 6) NOT NULL,
	locAddress VARCHAR(500) NOT NULL,
    locCity VARCHAR(100) NOT NULL,
    locState VARCHAR(2) NOT NULL, 
    locZip VARCHAR(10) NOT NULL
);

CREATE TABLE superHeroes (
    superID int PRIMARY KEY AUTO_INCREMENT,
    superName varchar(50) NOT NULL,
    superDescription varchar(250) NOT NULL,
    superPower varchar(250) NOT NULL
);

CREATE TABLE orgs (
    orgID int PRIMARY KEY AUTO_INCREMENT,
    orgName varchar(50) NOT NULL,
    orgDescription varchar(250) NOT NULL,
	orgAddressID INT NOT NULL,
    orgPhoneNumberID INT NOT NULL,
    FOREIGN KEY fk_orgAddressID (orgAddressID) 
		REFERENCES orgAddresses (orgAddressID),
	FOREIGN KEY fk_orgPhoneNumberID (orgPhoneNumberID)
		REFERENCES orgPhoneNumbers (phoneNumberID)
);

CREATE TABLE superToOrgMapping (
    mappingID int PRIMARY KEY AUTO_INCREMENT,
	superID INT NOT NULL,
	orgID INT NOT NULL,
	FOREIGN KEY fk_superID (superID)
		REFERENCES superHeroes(superID),
    FOREIGN KEY fk_orgID (orgID) 
		REFERENCES orgs (orgID)
);

CREATE TABLE sightings (
	sightingID INT PRIMARY KEY,
	Timestamp timeStamp,
    superID INT NOT NULL,
    locationID INT NOT NULL,
	FOREIGN KEY fk_superID (superID)
		REFERENCES superHeroes(superID),
	FOREIGN KEY fk_locationID (locationID)
		REFERENCES locations (locationID)
);