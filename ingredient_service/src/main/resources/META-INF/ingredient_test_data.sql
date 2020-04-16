DROP TABLE BDD_ingredient if EXISTS;
CREATE TABLE IF NOT EXISTS BDD_ingredients (
	primary key (id)
    "NOM" TEXT,
    "POIDS_MOYEN_G" INT,
    "UNITES" TEXT,
    "CATEGORIE" TEXT
);
INSERT INTO BDD_ingredients (ID,'Abricot',60,'unité/g','Fruits/Fruits frais');
INSERT INTO BDD_ingredients (ID,'Abricot, sec',NULL,'g','Fruits/Fruits secs');
INSERT INTO BDD_ingredients (ID,'Abricot, sucré, conserve',NULL,'g','Fruits/Fruits cuits (conserves comprises)');
INSERT INTO BDD_ingredients (ID,'Agar Agar',NULL,'g','Divers/Gélifiants et liants');
INSERT INTO BDD_ingredients (ID,'Agneau, côtelette',100,'g/unité','Viande et abats/Agneau, mouton');
INSERT INTO BDD_ingredients (ID,'Agneau, filet',NULL,'g','Viande et abats/Agneau, mouton');
INSERT INTO BDD_ingredients (ID,'Agneau, gigot',NULL,'g','Viande et abats/Agneau, mouton');
    