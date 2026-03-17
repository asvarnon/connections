-- Archetypes
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (1, 'Bard', 'Arcane', 'Support', 'Themes');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (2, 'Cleric', 'Arcane', 'Support', 'Divine Power');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (3, 'Fighter', 'Martial', 'Damage', 'Momentum');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (4, 'Mage', 'Arcane', 'Damage', 'Spell charges');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (5, 'Ranger', 'Martial', 'Damage', 'Focus');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (6, 'Rogue', 'Martial', 'Damage', 'Advantage');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (7, 'Summoner', 'Arcane', NULL, NULL);
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (8, 'Tank', 'Martial', 'Tank', 'Courage');
INSERT INTO PRIMARY_ARCHETYPE (primary_archetype_id, archetype_name, type_category, role_category, combat_resource) VALUES
    (9, 'Flexible', NULL, NULL, NULL);


-- Gathering Artisans
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (1, 'Fishing', 'Gathering');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (2, 'Herbalism', 'Gathering');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (3, 'Hunting', 'Gathering');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (4, 'Lumberjacking', 'Gathering');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (5, 'Mining', 'Gathering');

-- Processing Artisans
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (6, 'Alchemy', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (7, 'Animal Husbandry', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (8, 'Cooking', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (9, 'Farming', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (10, 'Lumber Milling', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (11, 'Metalworking', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (12, 'Stonemasonry', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (13, 'Tanning', 'Processing');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (14, 'Weaving', 'Processing');

-- Crafting Artisans
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (15, 'Arcane Engineering', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (16, 'Armor Smithing', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (17, 'Carpentry', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (18, 'Jeweler', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (19, 'Leatherworking', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (20, 'Scribe', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (21, 'Tailoring', 'Crafting');
INSERT INTO ARTISAN (artisan_id, artisan_name, artisan_type) VALUES (22, 'Weapon Smithing', 'Crafting');

-- Players
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (1, 'Joemama'  , 2, 'Y');  -- Cleric
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (2, 'Mewtwo'    , 6, 'Y');    -- Rogue
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (3, 'Uncle Ruckus' , 8, 'Y'); -- Tank
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (4, 'Fart Sniffer', 4, 'Y'); -- Mage
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (5, 'Momma', 2, 'Y');       -- Cleric
INSERT INTO PLAYER (player_id, player_name, primary_archetype_id, is_active) VALUES (6, 'Patrick', 6, 'Y');  -- Rogue


-- Player Artisan Associations
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (1, 16);  -- Joemama - Armor Smithing
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (2, 18);  -- Mewtwo - Jeweler
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (3, 22);  -- Uncle Ruckus - Weapon Smithing
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (4, 17);  -- Fart Sniffer - Carpentry
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (5, 16);  -- Momma - Armor Smithing
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (6, 15); --  Patrick - Arcane Engineering
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (6, 16); --  Patrick - Armor Smithing
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (6, 18); --  Patrick - Jeweler
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (6, 20); --  Patrick - Scribe
INSERT INTO PLAYER_ARTISAN (player_id, artisan_id) VALUES (6, 22); --  Patrick - Weapon Smithing


-- Archetypes
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Bard', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Cleric', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Fighter', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Mage', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Ranger', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Rogue', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Summoner', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Tank', 'archetype');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('player', 'Flexible', 'archetype');

-- Artisan Types
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('class', 'Gathering', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('class', 'Processing', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('class', 'Crafting', 'artisan-type');

-- Specific Artisan Names
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Fishing', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Lumberjacking', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Alchemy', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Armor Smithing', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Arcane Engineering', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Herbalism', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Hunting', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Lumber Milling', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Metalworking', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Mining', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Stonemasonry', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Tanning', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Weaving', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Animal Husbandry', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Cooking', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Farming', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Jeweler', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Leatherworking', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Scribe', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Tailoring', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Weapon Smithing', 'artisan-type');
INSERT INTO DYNAMIC_VALUES (category, value, type) VALUES ('artisan', 'Carpentry', 'artisan-type');


commit;