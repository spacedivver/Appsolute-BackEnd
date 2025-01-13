INSERT INTO level (level_name, level_achievement, max_point)
VALUES
    ('F1-Ⅰ', 0, 13499),
    ('F1-Ⅱ', 13500, 26999),
    ('F2-Ⅰ', 27000, 38999),
    ('F2-Ⅱ', 39000, 50999),
    ('F2-Ⅲ', 51000, 62999),
    ('F3-Ⅰ', 63000, 77999),
    ('F3-Ⅱ', 78000, 92999),
    ('F3-Ⅲ', 93000, 107999),
    ('F4-Ⅰ', 108000, 125999),
    ('F4-Ⅱ', 126000, 143999),
    ('F4-Ⅲ', 144000, 161999),
    ('F5', 162000, 9223372036854775807);

UPDATE level l1
    JOIN level l2 ON l2.level_name = CASE
                                         WHEN l1.level_name = 'F1-Ⅰ' THEN 'F1-Ⅱ'
                                         WHEN l1.level_name = 'F1-Ⅱ' THEN 'F2-Ⅰ'
                                         WHEN l1.level_name = 'F2-Ⅰ' THEN 'F2-Ⅱ'
                                         WHEN l1.level_name = 'F2-Ⅱ' THEN 'F2-Ⅲ'
                                         WHEN l1.level_name = 'F2-Ⅲ' THEN 'F3-Ⅰ'
                                         WHEN l1.level_name = 'F3-Ⅰ' THEN 'F3-Ⅱ'
                                         WHEN l1.level_name = 'F3-Ⅱ' THEN 'F3-Ⅲ'
                                         WHEN l1.level_name = 'F3-Ⅲ' THEN 'F4-Ⅰ'
                                         WHEN l1.level_name = 'F4-Ⅰ' THEN 'F4-Ⅱ'
                                         WHEN l1.level_name = 'F4-Ⅱ' THEN 'F4-Ⅲ'
                                         WHEN l1.level_name = 'F4-Ⅲ' THEN 'F5'
                                         ELSE NULL
        END
SET l1.next_level_id = l2.level_id
WHERE l1.level_name IN (
                        'F1-Ⅰ', 'F1-Ⅱ', 'F2-Ⅰ', 'F2-Ⅱ', 'F2-Ⅲ',
                        'F3-Ⅰ', 'F3-Ⅱ', 'F3-Ⅲ', 'F4-Ⅰ', 'F4-Ⅱ', 'F4-Ⅲ'
    );

INSERT INTO level (level_name, level_achievement, max_point)
VALUES
    ('B1', 0, 24000),
    ('B2', 24000, 52000),
    ('B3', 52000, 78000),
    ('B4', 78000, 117000),
    ('B5', 117000, 169000),
    ('B6', 169000, 9223372036854775807);

UPDATE level l1
    JOIN level l2 ON l2.level_name = CASE
                                         WHEN l1.level_name = 'B1' THEN 'B2'
                                         WHEN l1.level_name = 'B2' THEN 'B3'
                                         WHEN l1.level_name = 'B3' THEN 'B4'
                                         WHEN l1.level_name = 'B4' THEN 'B5'
                                         WHEN l1.level_name = 'B5' THEN 'B6'
                                         ELSE NULL
        END
SET l1.next_level_id = l2.level_id
WHERE l1.level_name IN ('B1', 'B2', 'B3', 'B4', 'B5');

INSERT INTO level (level_name, level_achievement, max_point)
VALUES
    ('G1', 0, 24000),
    ('G2', 24000, 52000),
    ('G3', 52000, 78000),
    ('G4', 78000, 117000),
    ('G5', 117000, 169000),
    ('G6', 169000, 9223372036854775807);

UPDATE level l1
    JOIN level l2 ON l2.level_name = CASE
                                         WHEN l1.level_name = 'G1' THEN 'G2'
                                         WHEN l1.level_name = 'G2' THEN 'G3'
                                         WHEN l1.level_name = 'G3' THEN 'G4'
                                         WHEN l1.level_name = 'G4' THEN 'G5'
                                         WHEN l1.level_name = 'G5' THEN 'G6'
                                         ELSE NULL
        END
SET l1.next_level_id = l2.level_id
WHERE l1.level_name IN ('G1', 'G2', 'G3', 'G4', 'G5');
