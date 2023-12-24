-- Add Test video games to Database
INSERT INTO VideoGames(title,  publisher, price, description, size, microtransactions) VALUES
('Shovel Knight Treasure Trove'	, 'Yacht Club Games', 39.99	, 'Shovel Knight: Treasure Trove is the complete Shovel Knight collection, containing all 5 games in the epic saga! Dig, blast, slash, and bash your way through a fantastical, 8-bit inspired world of pixel-perfect platforming, memorable characters, and world-class action-adventure gameplay.'	, '1.2GB'	, FALSE),
('Destiny 2'					, 'Bungie'			, 0.00	, 'Destiny 2 is an action MMO with a single evolving world that you and your friends can join anytime, anywhere, absolutely free.'							, '~98GB'	, TRUE),
('Omori'						, 'Omocat'			, 19.99	, 'Explore a strange world full of colorful friends and foes. When the time comes, the path you’ve chosen will determine your fate... and perhaps the fate of others as well.'						, '2.7GB'	, FALSE),
('League of Legends'			, 'Riotgames' 		, 0.00	, 'League of Legends, commonly referred to as League, is a 2009 multiplayer online battle arena video game developed and published by Riot Games'							, '13.6GB'	, TRUE),
('Terraria'						, 'Re-logic'		, 7.99	, 'Dig, fight, explore, build! Nothing is impossible in this action-packed adventure game. Four Pack also available!'					, '8.9GB'	, FALSE),
('Katana Zero'					, 'Akiisoft'		, 19.99 , 'Katana ZERO is a stylish neo-noir, action-platformer featuring breakneck action and instant-death combat. Slash, dash, and manipulate time to unravel your past in a beautifully brutal acrobatic display.'								, '12.6'	, FALSE),
('CS:GO 2'						, 'Steam' 			, 0.00	, 'For over two decades, Counter-Strike has offered an elite competitive experience, one shaped by millions of players from across the globe. And now the next chapter in the CS story is about to begin. This is Counter-Strike 2.'						, '15.8GB'	, TRUE);

-- Add Test users to Database --
INSERT INTO Users(username, email, password) VALUES
('Herozombie1'	, 'Herozombievasion@gmail.com'	, '$2a$10$7qCRLYdU67457beasMIcG.msfM2EBwVpnR11qrtUrCfUlF1ssFmy.'),
('Tyuio'		, 'Ty@gmail.com'				, '$2a$10$7qCRLYdU67457beasMIcG.msfM2EBwVpnR11qrtUrCfUlF1ssFmy.');

-- Add Roles to Roles table
INSERT INTO Roles (roleName)
VALUES ('ROLE_ADMIN');
 
INSERT INTO Roles (roleName)
VALUES ('ROLE_USER');
 
-- Give Me and Ty permissions 
INSERT INTO UserRoles (userId, roleId)
VALUES (1, 1);
 
INSERT INTO UserRoles (userId, roleId)
VALUES (2, 2);