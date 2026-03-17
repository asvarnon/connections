
CREATE INDEX idx_primary_archetype ON PLAYER(primary_archetype_id);
CREATE INDEX idx_artisan_id ON PLAYER_ARTISAN(artisan_id);

CREATE SEQUENCE dynamic_values_seq
    START WITH 1      -- Initial value
    INCREMENT BY 1    -- Increment step
    NOCACHE           -- Optionally prevent caching numbers
    NOCYCLE;          -- Prevents resetting the sequence after reaching the max value

CREATE OR REPLACE TRIGGER dynamic_values_before_insert
    BEFORE INSERT ON DYNAMIC_VALUES
    FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        :NEW.id := dynamic_values_seq.NEXTVAL;
END IF;
END;
/