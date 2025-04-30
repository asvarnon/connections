-- Create Tables
CREATE TABLE PRIMARY_ARCHETYPE (
primary_archetype_id INT PRIMARY KEY,
archetype_name VARCHAR(50) NOT NULL,
type_category VARCHAR(50),
role_category VARCHAR(50),
combat_resource VARCHAR(50)
);


CREATE TABLE ARTISAN (
 artisan_id INT PRIMARY KEY,
 artisan_name VARCHAR(100) NOT NULL,
 artisan_type VARCHAR(15) NOT NULL
);

CREATE TABLE PLAYER (
player_id INT PRIMARY KEY,
player_name VARCHAR(100) NOT NULL,
primary_archetype_id INT NOT NULL,
IS_ACTIVE CHAR(1) DEFAULT 'Y' NOT NULL,
FOREIGN KEY (primary_archetype_id) REFERENCES PRIMARY_ARCHETYPE(primary_archetype_id)
);


CREATE TABLE PLAYER_ARTISAN (
player_id INT NOT NULL,
artisan_id INT NOT NULL,
artisan_level INT DEFAULT 0,
PRIMARY KEY (player_id, artisan_id),
FOREIGN KEY (player_id) REFERENCES PLAYER(player_id),
FOREIGN KEY (artisan_id) REFERENCES ARTISAN(artisan_id)
);


CREATE TABLE DYNAMIC_VALUES (
id NUMBER PRIMARY KEY,
category VARCHAR2(50) NOT NULL,       -- Entity type (e.g., 'player', 'artisan')
value VARCHAR2(100) UNIQUE NOT NULL, -- The dynamic value (e.g., 'Cleric', 'Mage', 'Gathering')
type VARCHAR2(50) NOT NULL,          -- Context (e.g., 'archetype', 'name', etc.)
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




