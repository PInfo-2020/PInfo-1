DROP TABLE BDD_ingredient if EXISTS;
drop sequence if exists INGREDIENT_SEQ;
create sequence INGREDIENT_SEQ start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS BDD_ingredients (
	id bigint not null,
	primary key (id),
    NOM varchar(255),
    POIDS_MOYEN_G bigint,
    UNITES varchar(255),
    CATEGORIE varchar(255)
);

INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES (1,'Abricot',60,'unité/g','Fruits/Fruits frais');
INSERT INTO BDD_ingredients  (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES  (2,'Abricot, sec',NULL,'g','Fruits/Fruits secs');
INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES  (3,'Abricot, sucré, conserve',NULL,'g','Fruits/Fruits cuits (conserves comprises)');
INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES  (4,'Agar Agar',NULL,'g','Divers/Gélifiants et liants');
INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES  (5,'Agneau, côtelette',100,'g/unité','Viande et abats/Agneau, mouton');
INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES (6,'Agneau, filet',NULL,'g','Viande et abats/Agneau, mouton');
INSERT INTO BDD_ingredients (ID, NOM, POIDS_MOYEN_G, UNITES, CATEGORIE) VALUES (7,'Agneau, gigot',NULL,'g','Viande et abats/Agneau, mouton');
    