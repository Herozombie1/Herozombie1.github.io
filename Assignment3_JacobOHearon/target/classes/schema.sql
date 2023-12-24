-- PROJECT TABLES --
-- User Table --
CREATE TABLE Users
(
	userId LONG PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(100),
	email VARCHAR(255),
	password VARCHAR(255)
);

-- Video Game Table --
CREATE TABLE VideoGames
(
	videoGameId LONG PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255),
	publisher VARCHAR(255),
	price FLOAT(25),
	description VARCHAR(65535),
	size VARCHAR(15),
	microtransactions BOOLEAN
);

-- Owned Games Table --
CREATE TABLE OwnedGames
(
	ownedGameId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	userId LONG NOT NULL,
	videoGameId LONG NOT NULL
);


-- SECURITY TABLES --
-- Roles Table --
CREATE TABLE Roles
(
  roleId   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
);

-- User Roles Table --
CREATE TABLE UserRoles
(
  id     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

-- MISC FUNCTIONS --
-- Ensure there are no duplicate 
ALTER TABLE UserRoles
  ADD CONSTRAINT UserRolesUk UNIQUE (userId, roleId);

-- Link User Roles to Users using foreign key --  
ALTER TABLE UserRoles
  ADD CONSTRAINT UserRoleFk1 FOREIGN KEY (userId)
  REFERENCES Users (userId);
 
-- Link User ROles to Roles using foreign key --
ALTER TABLE UserRoles
  ADD CONSTRAINT UserRoleFk2 FOREIGN KEY (roleId)
  REFERENCES Roles (roleId);
	
-- Link Users Table to Owned Games table --
ALTER TABLE OwnedGames
	ADD CONSTRAINT UserIdOwnedGamesFk FOREIGN KEY (userId)
	REFERENCES Users (userId);
	
-- Link Video Games Table to Owned Games table --
ALTER TABLE OwnedGames
	ADD CONSTRAINT VideoGameIdOwnedGamesFk FOREIGN KEY (videoGameId)
	REFERENCES VideoGames(videoGameId);